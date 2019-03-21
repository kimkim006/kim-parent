package com.kim.impexp.excel.imp.handler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.imp.model.ContentData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.kim.impexp.excel.DefaultValue;
import com.kim.impexp.excel.imp.Convertor;

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
 * excel文件内容解析实现
 * @date 2016年12月1日
 * @author liubo04
 */
public class BodyContentReaderHandler<T> extends AbstractContentReaderHandler 
									implements BodyReaderHandler<T>{
	
	/** 承载数据的类 */
	protected Class<T> clazz;
	/** 解析出来的数据是否使用Map承载，true则使用，false则不使用 */
	protected boolean useMap = DefaultValue.DEFAULT_IMPORT_USE_MAP;
	
	/** 
	 * 解析出来的数据使用自定义对象时，转换过程是否使用默认转换程序，true则使用，false则不使用，
	 * 如果不使用默认的转换程序，则需要实体类重写transfer2Object方法 
	 */
	protected boolean useSelf = DefaultValue.DEFAULT_IMPORT_USE_SELF;
	
	/** 表头处理工具 */
	protected HeaderReaderHandler headerReaderHandler;
	/** 表头对应字段的setter方法  */
	protected Map<String, Method> headerMethodMapping = new HashMap<>();
	
	public BodyContentReaderHandler(HeaderReaderHandler headerReaderHandler) {
		super();
		this.headerReaderHandler = headerReaderHandler;
	}
	
	public BodyContentReaderHandler(HeaderReaderHandler headerReaderHandler, Class<T> clazz) {
		super();
		this.headerReaderHandler = headerReaderHandler;
		setClazz(clazz);
	}

	@Override
	protected String getCheckTip() {
		if(!useMap && clazz == null){
			return "未设置承载数据的Java类[clazz]";
		}
		if(headerReaderHandler == null){
			return "未设置对应的表头";
		}
		return null;
	}

	protected void creatHeaderMethodMapping() throws IntrospectionException {
		if(!useMap && !useSelf){
			//获取字段的setter方法
	    	for (Entry<String, String> entry : headerReaderHandler.getHeaderMapping().entrySet()) {
	    		headerMethodMapping.put(entry.getKey(), new PropertyDescriptor(entry.getValue(), clazz).getWriteMethod());
	    	}
        }
	}
	
	@Override
	public BodyContentReaderHandler<T> ready(Sheet sheet, SheetReaderExecutor sheetExecutor) throws Exception {
		//检查必须的参数
		checkParam();
		
		creatHeaderMethodMapping();
		
		this.data = new ContentData(sheet.getSheetName(), index, new ArrayList<>(), 0, startRow)
				.setUseList(true);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean read(Sheet sheet, SheetReaderExecutor sheetExecutor, Row row, int cursor) throws Exception {
		
		//解析内容
		return this.data.add((T)createObj(row));
	}
	
	/**
	 * 生成对象
	 * @param row
	 * @return
	 * @throws Exception
	 */
    protected Object createObj(Row row) throws Exception {
    	Object obj = null;
    	String[] headers = headerReaderHandler.getHeaders();
    	if(useMap){
    		Map<String, Object> map = new LinkedHashMap<>();
    		for (int i = 0; i < headers.length; i++) {
    			map.put(headerReaderHandler.getHeaderMapping().get(headers[i]), getCellValue(row.getCell(i)));
    		}
    		obj = map;
    	}else{
    		obj = clazz.newInstance();
    		if(useSelf){
    			Map<String, Object> map = new LinkedHashMap<>();
        		for (int i = 0; i < headers.length; i++) {
        			map.put(headerReaderHandler.getHeaderMapping().get(headers[i]), getCellValue(row.getCell(i)));
        		}
        		Convertor ct = (Convertor)obj;
        		ct.transfer2Object(map);
    		}else{
    			for (int i = 0; i < headers.length; i++) {
    				headerMethodMapping.get(headers[i]).invoke(obj, getCellValue(row.getCell(i)));
    			}
    		}
    	}
        return obj;
    }
	
	@Override
	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	public void setClazz(Class<T> clazz) {
		if(clazz != null){
			this.useMap = false;
			this.clazz = clazz;
		}
	}

	@Override
	public boolean isUseMap() {
		return useMap;
	}

	public void setUseMap(boolean useMap) {
		this.useMap = useMap;
	}

	@Override
	public boolean isUseSelf() {
		return useSelf;
	}

	@Override
	public void setUseSelf(boolean useSelf) {
		this.useSelf = useSelf;
	}

	@Override
	public HeaderReaderHandler getHeaderReaderHandler() {
		return headerReaderHandler;
	}

	@Override
	public void setHeaderReaderHandler(HeaderReaderHandler headerReaderHandler) {
		this.headerReaderHandler = headerReaderHandler;
	}
	
	

}
