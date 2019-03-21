package com.kim.impexp.excel.imp.handler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kim.impexp.excel.imp.model.ContentData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.util.StringUtil;
import com.kim.impexp.excel.imp.ContentReaderHandler;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.monitorjbl.xlsx.impl.StreamingCell;

/**
 *     .     . . .,,   ,r22ZMMMMMaSZX77r ,r  X   . . 
 *                  ,i77iii:iXa0M@M0;,,i  ;  i .     
 * . .   .      ,;i::,           .;ZMM27 ,i  7,X;  . 
 * .  .....    7S:                  ,0Ma     X727    
 * . . ,.     20                      XMXii  2XZi  . 
 * .  .      ,Z                        rM0B. XXXi    
 * . . .     2r;7772MX    ,W2iir,       XMZ: :iri  . 
 *    . ,.  ia:28B80M.    iMM@MBX  .     ZM7  ;:;    
 * .   .,.  Xa  :Mi          WM          .MM..;;i  . 
 *    . ..  X7   .            .       .   M@2;7:i    
 * .     ,  ;X                           .MM@@S.;,   
 *    . .,  :r                    . ...  aMMMMM,X;   
 * .     ,   ;      XMMMMMMM.    .   .. :M0X@@WZ;r.  
 * .  . . ., .r     .;ri;;r:    . .    .8Wr,ZMBSX;i  
 * . . . . .. ;X                       iXi:;:8Mi,i;  
 *    . .  .,  ;Z                 ... ,    ,:.M7..;  
 *     .   .,   MMS.             :.  ,,:,.   .2MXi:  
 * .. . . . ,  ,@ZMMMWaiii;ir7aS7i  i;i...    2MBii :
 * . .   .: ,  BX,aMMMMMMMMMMW7..;rSi.     . .iMMSrr 
 *      ..  , 7B   aMMMMMMBZZ7iXXr,       .. ,SMMr.X 
 *   .  ,,.   rMa    aWMZZ;;:..       ,.      X@MX :.
 *  ,.  :,i ,rB0ZaX.r         .     ;X; ..    :MWXX;i
 *    .,:r :iW2   aM2  ;:. ,ii:70M0:r:   .    ;M88W02
 *    ,.:  ,;M0    :MM0ZBWMW.   ;M8       .   WMMMM@2
 *   .,i;2Sa8BMi     BXS;2M:     7@    . .   rMMMMMM@
 * :iXSZ2S;.  WW   a  ;7:22    ,i r:  .     .MM8;,;2@
 * i::        ,Mi  7@    Sr    ,2  ,       rMMZ,     
 *          .  XZ   B@i. i;    .B.       7@MW:       
 * . .      .. :X   8MMMZZi    SM0r.:i72BMa.     . . 
 *    . . . . .2Z7XBB;7MMSi   XMMMM0SX;:      .   .  
 * .             .:     i:,,,Xr.                     
 */
/**
 * 
 * 抽象的内容解析类
 * @date 2016年12月1日
 * @author liubo04
 */
public abstract class AbstractContentReaderHandler implements ContentReaderHandler {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Workbook workbook;
	protected Sheet sheet;
	
	/** 需要处理的行数 */
	protected int rangeNum = -1;
	
	/** 开始读取数据的行 */
	protected int startRow;
	
	/** 所在sheet内的内容序号，从0开始 */
	protected int index;
	
	/** 读取到的数据 */
	protected ContentData data;
	
	/** 数字类型的格式工具 */
	protected DecimalFormat decimalFormat;
	/** 日期类型的格式工具 */
	protected SimpleDateFormat simpleDateFormat;
	
	protected FormulaEvaluator evaluator;
	
	public AbstractContentReaderHandler(int rangeNum) {
		super();
		setRangeNum(rangeNum);
	}
	
	public AbstractContentReaderHandler() {
		super();
	}
	
	@Override
	public AbstractContentReaderHandler ready(Sheet sheet, SheetReaderExecutor sheetExecutor) throws Exception {
		return this;
	}
	
	@Override
	public ContentData complete(Sheet sheet, SheetReaderExecutor sheetExecutor) {
		return this.data;
	}
	
	public ContentData getData() {
		return data;
	}

	public void setData(ContentData data) {
		this.data = data;
	}
	
	@Override
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	@Override
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * 校验必须的参数是否符合要求
	 */
	protected void checkParam() {
		String tip = getCheckTip();
		if (tip != null) {
			logger.error(tip);
			throw new RuntimeException(tip);
		}
	}
	
	protected String getCheckTip() {
		return null;
	}

	/**
     * 获取单元格数据
     * @param cell
     * @return
     * @date 2016年12月1日
     * @author liubo04
     */
	protected Object getCellValue(Cell cell) {
    	if(cell == null){
			return "";
		}
    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)){
			Date d = cell.getDateCellValue();
			if(d == null){
				return "";
			}
			return getSimpleDateFormat().format(d);
    	}
    	
    	if(workbook instanceof XSSFWorkbook){
    		if(evaluator == null){
        		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        	}
        	CellValue cellValue = evaluator.evaluate(cell);
        	if(cellValue == null){
        		return StringUtil.EMPTY;
        	}
        	switch (cellValue.getCellType()) {
    		case Cell.CELL_TYPE_STRING:return cellValue.getStringValue().trim();			
    		case Cell.CELL_TYPE_BOOLEAN:return String.valueOf(cellValue.getBooleanValue());	
    		case Cell.CELL_TYPE_NUMERIC:return String.valueOf(getDecimalFormat().format(cellValue.getNumberValue()));	
    		case Cell.CELL_TYPE_BLANK:return StringUtil.EMPTY;
    		default:return cell.toString().trim();
    		}
    	}else{
    		switch (cell.getCellType()) {
    		case Cell.CELL_TYPE_STRING:return cell.getStringCellValue().trim();			
    		case Cell.CELL_TYPE_BOOLEAN:return String.valueOf(cell.getBooleanCellValue());	
    		case Cell.CELL_TYPE_NUMERIC:return String.valueOf(getDecimalFormat().format(cell.getNumericCellValue()));	
    		case Cell.CELL_TYPE_ERROR:
    			if(cell instanceof StreamingCell){
    				return String.valueOf(((StreamingCell)cell).getRawContents()).trim();
    			}
    			return cell.toString().trim();
    		case Cell.CELL_TYPE_BLANK:return StringUtil.EMPTY;
    		default:return cell.toString().trim();
    		}
    	}
    }

	private SimpleDateFormat getSimpleDateFormat() {
		if(simpleDateFormat == null){
			//日期格式
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return simpleDateFormat;
	}

	public DecimalFormat getDecimalFormat() {
		if(decimalFormat == null){
			//最多保留4位小数点
			decimalFormat = new DecimalFormat("0.####");
		}
		return decimalFormat;
	}
	
	@Override
	public int getRangeNum() {
		return rangeNum;
	}

	@Override
	public AbstractContentReaderHandler setRangeNum(int rangeNum) {
		this.rangeNum = rangeNum;
		return this;
	}

	@Override
	public AbstractContentReaderHandler setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}
	
	@Override
	public int getStartRow() {
		return startRow;
	}
	
	public int getIndex() {
		return index;
	}

	@Override
	public AbstractContentReaderHandler setIndex(int index) {
		this.index = index;
		return this;
	}

}
