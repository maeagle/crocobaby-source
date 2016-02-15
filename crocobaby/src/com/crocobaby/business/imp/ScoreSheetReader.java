package com.crocobaby.business.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.crocobaby.beans.EvaluationInfo;
import com.crocobaby.beans.PeriodInfo;
import com.crocobaby.beans.RateInfo;
import com.crocobaby.dao.ImportDataDAO;
import com.crocobaby.frame.common.CommonUtil;
import com.crocobaby.frame.db.DataBaseManager;
import com.crocobaby.frame.log.LogManager;

/**
 * The Class ScoreSheetReader.
 */
public class ScoreSheetReader {

	private Workbook workBook;

	private InputStream stream;

	private String productCode;

	private Set<String> payPeriodDataSet = new HashSet<String>();

	private Set<String> insPeriodDataSet = new HashSet<String>();

	/**
	 * 测试使用.
	 *
	 */
	public static void main(String[] args) {

		try {
			LogManager.initLog();
			DataBaseManager.initDataBase();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		ScoreSheetReader reader = null;
		File dir = new File("/Users/maeagle/Downloads/xlsx");
		File[] fileList = dir.listFiles();
		for (File file : fileList) {
			String productNo = file.getName().split("\\.")[0];

			System.out.println("===正在导入产品数据【" + productNo + "】===");
			try {
				reader = new ScoreSheetReader(file, productNo);
				// 责任得分数据导入
				List<EvaluationInfo> evaluationList = reader.buildEvaluationInfo(Constants.SHEET_ZEREN);
				ImportDataDAO.saveEvaluationInfo(evaluationList);
				System.out.println("===产品【" + productNo + "】责任得分数据导入成功（" + evaluationList.size() + "）===");
				// 性价比得分数据导入
				evaluationList = reader.buildEvaluationInfo(Constants.SHEET_XINGJIABI);
				ImportDataDAO.saveEvaluationInfo(evaluationList);
				System.out.println("===产品【" + productNo + "】性价比得分数据导入成功（" + evaluationList.size() + "）===");
				// 杠杆得分数据导入
				evaluationList = reader.buildEvaluationInfo(Constants.SHEET_GANGGAN);
				ImportDataDAO.saveEvaluationInfo(evaluationList);
				System.out.println("===产品【" + productNo + "】杠杆得分数据导入成功（" + evaluationList.size() + "）===");
				// 费率数据导入
				List<RateInfo> rateList = reader.buildRateInfo(Constants.SHEET_RATE);
				ImportDataDAO.saveRateInfo(rateList);
				System.out.println("===产品【" + productNo + "】费率数据导入成功（" + rateList.size() + "）===");
				// 保险期间，缴费期间 数据导入
				List<PeriodInfo> periodList = reader.buildPeriod();
				ImportDataDAO.savePeriodInfo(periodList);
				System.out.println("===产品【" + productNo + "】保险期间/缴费期间数据导入成功（" + periodList.size() + "）===");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 构造方法.
	 *
	 * @param excelFile
	 *            the excel file
	 * @param productCode
	 *            the product code
	 * @throws Exception
	 *             the exception
	 */
	public ScoreSheetReader(File excelFile, String productCode) throws Exception {
		stream = new FileInputStream(excelFile);
		workBook = WorkbookFactory.create(stream);
		this.productCode = productCode;
	}

	/**
	 * 构建期间类信息.（包括保险期间，缴费期间）
	 *
	 * @return the list
	 */
	public List<PeriodInfo> buildPeriod() {
		List<PeriodInfo> resultList = new ArrayList<PeriodInfo>();

		// 缴费期间信息
		for (String periodStr : payPeriodDataSet) {
			PeriodInfo info = new PeriodInfo();
			info.setPkid(CommonUtil.createSID());
			info.setPeriod_type("2");
			info.setPro_code(productCode);
			Matcher mat = Constants.REG_PERIOD.matcher(periodStr);
			if (mat.find()) {
				info.setPeriod_value(mat.group(1));
				info.setPeriod_unit(mat.group(2));
			} else {
				info.setPeriod_unit(periodStr);
			}
			resultList.add(info);
		}

		// 保险期间信息
		for (String periodStr : insPeriodDataSet) {
			PeriodInfo info = new PeriodInfo();
			info.setPkid(CommonUtil.createSID());
			info.setPeriod_type("1");
			info.setPro_code(productCode);
			Matcher mat = Constants.REG_PERIOD.matcher(periodStr);
			if (mat.find()) {
				info.setPeriod_value(mat.group(1));
				info.setPeriod_unit(mat.group(2));
			} else {
				info.setPeriod_unit(periodStr);
			}
			resultList.add(info);
		}

		return resultList;
	}

	/**
	 * 构建并汇总费率信息.
	 *
	 * @param sheetName
	 *            Excel中Sheet页的名称
	 * @return the list
	 */
	public List<RateInfo> buildRateInfo(String sheetName) {

		List<RateInfo> returnList = new ArrayList<RateInfo>();

		Sheet sheet = workBook.getSheet(sheetName);

		int mergeAreaCount = sheet.getNumMergedRegions();

		for (int i = 0; i < mergeAreaCount; i++) {

			CellRangeAddress mergeArea = sheet.getMergedRegion(i);
			// 如果不是第一行，则说明不是投保期间
			if (mergeArea.getFirstRow() != 0)
				continue;
			// 解析得到保险期间
			String insPeriod = parseInsPeriod(sheet, mergeArea);
			insPeriodDataSet.add(insPeriod);

			// 解析男性的缴费期间，并根据年龄段汇总全部得分信息
			CellRangeAddress maleMergerArea = findSubMergerArea(sheet, mergeArea, "M");
			Map<Integer, String> malePayPeriodSet = parsePayPeriodList(sheet, maleMergerArea);
			List<RateInfo> maleEvalList = buildRateInfo(sheet, insPeriod, "M", malePayPeriodSet);

			// 解析女性的缴费期间，并根据年龄段汇总全部得分信息
			CellRangeAddress femaleMergerArea = findSubMergerArea(sheet, mergeArea, "F");
			Map<Integer, String> femalePayPeriodSet = parsePayPeriodList(sheet, femaleMergerArea);
			List<RateInfo> femaleEvalList = buildRateInfo(sheet, insPeriod, "F", femalePayPeriodSet);

			// 汇总男女两个列表信息
			returnList.addAll(maleEvalList);
			returnList.addAll(femaleEvalList);
		}
		return returnList;
	}

	/**
	 * 构建并汇总得分信息.
	 *
	 * @param sheetName
	 *            Excel中Sheet页的名称
	 * @return the list
	 */
	public List<EvaluationInfo> buildEvaluationInfo(String sheetName) {

		List<EvaluationInfo> returnList = new ArrayList<EvaluationInfo>();

		Sheet sheet = workBook.getSheet(sheetName);

		int mergeAreaCount = sheet.getNumMergedRegions();

		for (int i = 0; i < mergeAreaCount; i++) {

			CellRangeAddress mergeArea = sheet.getMergedRegion(i);
			// 如果不是第一行，则说明不是投保期间
			if (mergeArea.getFirstRow() != 0)
				continue;
			// 解析得到保险期间
			String insPeriod = parseInsPeriod(sheet, mergeArea);
			insPeriodDataSet.add(insPeriod);

			// 解析男性的缴费期间，并根据年龄段汇总全部得分信息
			CellRangeAddress maleMergerArea = findSubMergerArea(sheet, mergeArea, "M");
			Map<Integer, String> malePayPeriodSet = parsePayPeriodList(sheet, maleMergerArea);
			List<EvaluationInfo> maleEvalList = buildEvaluationInfo(sheet, insPeriod, "M", malePayPeriodSet);

			// 解析女性的缴费期间，并根据年龄段汇总全部得分信息
			CellRangeAddress femaleMergerArea = findSubMergerArea(sheet, mergeArea, "F");
			Map<Integer, String> femalePayPeriodSet = parsePayPeriodList(sheet, femaleMergerArea);
			List<EvaluationInfo> femaleEvalList = buildEvaluationInfo(sheet, insPeriod, "F", femalePayPeriodSet);

			// 汇总男女两个列表信息
			returnList.addAll(maleEvalList);
			returnList.addAll(femaleEvalList);
		}

		return returnList;
	}

	/**
	 * 男性的缴费期间，并根据年龄段汇总全部得分信息.
	 *
	 * @param sheet
	 *            the sheet
	 * @param insPeriod
	 *            the ins period
	 * @param sex
	 *            the sex
	 * @param payPeriodSet
	 *            the pay period set
	 * @return the list
	 */
	private List<EvaluationInfo> buildEvaluationInfo(Sheet sheet, String insPeriod, String sex,
			Map<Integer, String> payPeriodSet) {

		List<EvaluationInfo> resultList = new ArrayList<EvaluationInfo>();

		int lastRowNum = sheet.getLastRowNum();
		String valType = Constants.SHEET_DATASET.get(sheet.getSheetName());

		Iterator<Entry<Integer, String>> iter = payPeriodSet.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, String> payPeriodEntry = iter.next();
			for (int i = 3; i <= lastRowNum; i++) {
				Cell valueCell = sheet.getRow(i).getCell(payPeriodEntry.getKey());

				// 空值跳过
				if (valueCell == null || valueCell.getCellType() == Cell.CELL_TYPE_BLANK)
					continue;

				EvaluationInfo info = new EvaluationInfo();
				info.setPkid(CommonUtil.createSID());
				info.setVal_sex(sex);
				info.setVal_ins_period(insPeriod);
				info.setVal_pay_period(payPeriodEntry.getValue());

				info.setVal_age(Double.toString(sheet.getRow(i).getCell(0).getNumericCellValue()));
				info.setVal_rate(CommonUtil.getDoubleValue(valueCell.getNumericCellValue()));

				info.setVal_type(valType);
				info.setPro_code(productCode);
				resultList.add(info);
			}
		}
		return resultList;
	}

	/**
	 * 根据年龄段汇总全部费率信息信息.
	 *
	 * @param sheet
	 *            the sheet
	 * @param insPeriod
	 *            the ins period
	 * @param sex
	 *            the sex
	 * @param payPeriodSet
	 *            the pay period set
	 * @return the list
	 */
	private List<RateInfo> buildRateInfo(Sheet sheet, String insPeriod, String sex, Map<Integer, String> payPeriodSet) {

		int lastRowNum = sheet.getLastRowNum();
		List<RateInfo> resultList = new ArrayList<RateInfo>();
		Iterator<Entry<Integer, String>> iter = payPeriodSet.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, String> payPeriodEntry = iter.next();
			for (int i = 3; i <= lastRowNum; i++) {
				Cell valueCell = sheet.getRow(i).getCell(payPeriodEntry.getKey());
				if (valueCell == null || valueCell.getCellType() == Cell.CELL_TYPE_BLANK)
					continue;
				RateInfo info = new RateInfo();
				info.setRate_pkid(CommonUtil.createSID());
				info.setRate_sex(sex);
				info.setRate_ins_period(insPeriod);
				info.setRate_pay_period(payPeriodEntry.getValue());
				info.setRate_age(Double.toString(sheet.getRow(i).getCell(0).getNumericCellValue()));
				info.setRate_permium(CommonUtil.getDoubleValue(valueCell.getNumericCellValue()));
				info.setPro_code(productCode);
				resultList.add(info);
			}
		}
		return resultList;
	}

	/**
	 * 识别投保期间信息.
	 *
	 * @param sheet
	 *            the sheet
	 * @param mergeArea
	 *            the merge area
	 * @return the string
	 */
	private String parseInsPeriod(Sheet sheet, CellRangeAddress mergeArea) {

		String insPeriod = "";
		String titleString = sheet.getRow(mergeArea.getFirstRow()).getCell(mergeArea.getFirstColumn())
				.getStringCellValue();
		if (StringUtils.isBlank(titleString))
			throw new NullPointerException("The [titleString] is Null");
		Matcher mat = Constants.REG_PERIOD.matcher(titleString);
		if (mat.find() || Constants.CELL_INS_PERIOD_ALL_LIFE.equals(titleString)) {
			insPeriod = titleString;
		}
		if (StringUtils.isBlank(insPeriod))
			throw new NullPointerException("Can't Parse String to [insPeriod]!");

		return insPeriod;
	}

	/**
	 * 识别缴费期间信息.
	 *
	 * @param sheet
	 *            Excel的Sheet对象
	 * @param parentMergerArea
	 *            父级合并区域对象(性别区域)
	 * @return the map
	 */

	private Map<Integer, String> parsePayPeriodList(Sheet sheet, CellRangeAddress parentMergerArea) {

		Map<Integer, String> payPeriodMap = new HashMap<Integer, String>();

		payPeriodDataSet.clear();
		for (int i = parentMergerArea.getFirstColumn(); i <= parentMergerArea.getLastColumn(); i++) {

			String payPeriodString = sheet.getRow(2).getCell(i).getStringCellValue();

			Matcher mat = Constants.REG_PERIOD.matcher(payPeriodString);
			if (!mat.find() && !Constants.CELL_PAY_PERIOD_ONCE.equals(payPeriodString)) {
				throw new NullPointerException("Can't parse to [payPeriodString] in Cell[" + i + "]");
			}
			payPeriodMap.put(i, payPeriodString);
			payPeriodDataSet.add(payPeriodString);
		}
		return payPeriodMap;
	}

	/**
	 * 查找指定的合并单元格.
	 *
	 * @param sheet
	 *            Excel的Sheet对象
	 * @param parentMergerArea
	 *            父级合并区域对象
	 * @param cellString
	 *            要查找的合并单元格内的字符串
	 * @return the int
	 */
	private CellRangeAddress findSubMergerArea(Sheet sheet, CellRangeAddress parentMergerArea, String cellString) {
		int mergeAreaCount = sheet.getNumMergedRegions();
		for (int i = 0; i < mergeAreaCount; i++) {
			CellRangeAddress mergeArea = sheet.getMergedRegion(i);
			// 子合并区域的列区域，一定在父合并区域内部
			if (mergeArea.getFirstColumn() < parentMergerArea.getFirstColumn())
				continue;
			if (mergeArea.getLastColumn() > parentMergerArea.getLastColumn())
				continue;
			// 子合并区域的行区域，一定在父合并区域外部
			if (mergeArea.getFirstRow() <= parentMergerArea.getLastRow())
				continue;
			// 按照字符串查找
			if (cellString.equals(
					sheet.getRow(mergeArea.getFirstRow()).getCell(mergeArea.getFirstColumn()).getStringCellValue()))
				return mergeArea;
		}
		return null;
	}

	/**
	 * 关闭文件资源.
	 */
	public void close() {
		try {
			workBook.close();
		} catch (Exception e) {
		}
		try {
			stream.close();
		} catch (Exception e) {
		}
	}
}
