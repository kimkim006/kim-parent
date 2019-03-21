package com.kim.impexp.excel.imp.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.impexp.common.Constant;
import com.kim.impexp.excel.DefaultValue;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.imp.model.ContentData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.kim.common.exception.BusinessException;

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
 * excel文件表头解析实现
 * @date 2016年12月1日
 * @author liubo04
 */
public class HeaderContentReaderHandler extends AbstractContentReaderHandler
										implements HeaderReaderHandler{
	
	/** 表头和字段的映射 */
	protected Map<String, String> headerMapping;
	/** 表头映射的行，应该遵循 headMappingNum∈[0,headRowNum) */
	protected int headerMappingNum;
	
	/** 实际Excel中的表头 */
	protected String[] headers = null;
	
	public HeaderContentReaderHandler(Map<String, String> headerMapping) {
		super();
		this.headerMapping = headerMapping;
		setRangeNum(DefaultValue.DEFAULT_IMPORT_HEADER_ROW_NUM);
	}

	public HeaderContentReaderHandler(String[] mappings) {
		super();
		setHeaderMapping(mappings);
		setRangeNum(DefaultValue.DEFAULT_IMPORT_HEADER_ROW_NUM);
	}
	
	public HeaderContentReaderHandler() {
		super();
		setRangeNum(DefaultValue.DEFAULT_IMPORT_HEADER_ROW_NUM);
	}

	/**
	 * 校验必须的参数是否符合要求
	 */
	@Override
	protected void checkParam() {
		String tip = getCheckTip();
		if (tip != null) {
			logger.error(tip);
			throw new RuntimeException(tip);
		}
	}
	
	@Override
	protected String getCheckTip() {
		if(rangeNum < 1){
			return "表头行数[rangeNum]必须大于0";
		}
		if(headerMappingNum < 0 || headerMappingNum >= rangeNum){
			return "表头映射的行[]headMappingNum必须大于等于0，且小于表头行数[rangeNum]";
		}
		if(headerMapping == null || headerMapping.size() == 0){
			return "未设置 表头和字段的映射关系[headerMapping]";
		}
		return null;
	}
	
	@Override
	public HeaderContentReaderHandler ready(Sheet sheet, SheetReaderExecutor sheetExecutor) throws Exception {
		//检查必须的参数
		checkParam();
		this.data = new ContentData(sheet.getSheetName(), index, startRow);
		return this;
	}
	
	@Override
	public boolean read(Sheet sheet, SheetReaderExecutor sheetExecutor, Row row, int cursor) throws Exception {
		
		//获取表头, 默认读取表头的最后一行作为表头映射
		if(cursor == getStartRowNum()){
			headers = getHeaderAndCheck(row);
			this.data.setData(headers).setRowNum(1);
			return true;
		}
		return false;
	}

	/**
	 * 获取内容开始的行号
	 * @return
	 */
	protected int getStartRowNum() {
		return startRow + headerMappingNum;
	}

	protected String[] getHeaderAndCheck(Row row) {
		int length = headerMapping.size();
		String[] headers = new String[length];
        for (int i = 0; i < length; i++) {
        	String header = String.valueOf(getCellValue(row.getCell(i))).trim();
        	if(!headerMapping.containsKey(header)){
        		logger.error("不存在的表头：{}, 可识别表头：{}", header, headerMapping.keySet());
        		throw new BusinessException("表头不一致，请核对");
        	}
        	headers[i] = header;
		}
        return headers;
	}
	
	
	@Override
	public void setHeaderMapping(Map<String, String> headerMapping) {
		this.headerMapping = headerMapping;
	}
	
	@Override
	public Map<String, String> setHeaderMapping(String[] mappings) {
		if(mappings == null || mappings.length == 0){
			return null;
		}
		
		headerMapping = new LinkedHashMap<>(); 
		String[] ss;
		for (String mapping : mappings) {
			if(mapping.indexOf(Constant.SEPERATOR) != -1
					&& (ss = mapping.split(Constant.SEPERATOR)).length > 1){
				headerMapping.put(ss[0].trim(), ss[1].trim());
			}else{
				headerMapping.put(mapping, mapping);
			}
		}
		return headerMapping;
	}
	
	@Override
	public HeaderContentReaderHandler setRangeNum(int rangeNum) {
		this.rangeNum = rangeNum;
		this.headerMappingNum = rangeNum - 1;
		return this;
	}

	public int getHeaderMappingNum() {
		return headerMappingNum;
	}
	
	public HeaderContentReaderHandler setHeaderMappingNum(int headerMappingNum) {
		this.headerMappingNum = headerMappingNum;
		return this;
	}

	@Override
	public String[] getHeaders() {
		if(headers == null && headerMapping != null) {
			headers = new String[headerMapping.size()];
		}
		if(headers != null && headers.length == 0){
			int i = 0;
			for (Entry<String, String> entry : headerMapping.entrySet()) {
				headers[i++] = entry.getKey();
			}
		}
		return headers;
	}

	@Override
	public Map<String, String> getHeaderMapping() {
		return headerMapping;
	}

}
