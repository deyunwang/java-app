package cn.deyun.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

	
	/**
	 * Apache POI 3.9 2012.12.3 发布了，该版本显著提升了性能，修复了很多 bug
	 * Apache POI是一个开源的Java读写Excel、WORD等微软OLE2组件文档的项目。目前POI已经有了Ruby版本。
	 * HSSF － 提供读写Microsoft Excel XLS格式档案的功能
	 * XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
	 * HWPF － 提供读写Microsoft Word DOC格式档案的功能
	 * HSLF － 提供读写Microsoft PowerPoint格式档案的功能
	 * HDGF － 提供读Microsoft Visio格式档案的功能
	 * HPBF － 提供读Microsoft Publisher格式档案的功能
	 * HSMF － 提供读Microsoft Outlook格式档案的功能
	 * @author Kervin
	 */
public class ExcelReader {
	
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;
	
	/**
	 * 读取Excel表格表头的内容
	 * @param is
	 * @return
	 */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }
	
    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                str += getCellFormatValue(row.getCell(j)).trim() + "    ";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * @param cell
     * @return
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }
    
    /**
     * 获取单元格数据内容为日期类型的数据
     * @param cell
     * @return
     */
    @SuppressWarnings("all")
	private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }
    
    
	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellValue = "";
		if(cell != null){
			//判断当前Cell的Type
			switch (cell.getCellType()) {
			//如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA:
				//判断当前的cell是否为Date
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					//如果是Date类型，则转化为Data格式
					
					//方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
					
					//方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(date);
				}
				//如果是纯数字
				else{
					//取得当前Cell的数值
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
				//如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				//取得当前的Cell字符串
				cellValue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellValue = " ";
			}
		}else{
			cellValue = "";
		}
		return cellValue;
	}
	
	public static void main(String[] args) {
		try {
			//对读取Excel表格标题测试
			InputStream is = new FileInputStream("d://b.xls");
			ExcelReader excelReader = new ExcelReader();
			String[] title = excelReader.readExcelTitle(is);
			System.out.println("获得Excel表格的标题:");
			for(String s : title){
				System.out.println(s + " ");
			}
			
			//对读取Excel表格内容测试
			InputStream is2 = new FileInputStream("d://b.xls");
			Map<Integer, String> map = excelReader.readExcelContent(is2);
			System.out.println("获得Excel表格的内容:");
			for(int i = 1;i<map.size();i++){
				System.out.println(map.get(i));
			}
		} catch (Exception e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}
}
