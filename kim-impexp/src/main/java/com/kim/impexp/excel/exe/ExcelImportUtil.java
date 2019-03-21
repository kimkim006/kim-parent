package com.kim.impexp.excel.exe;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kim.impexp.excel.entity.ImportEntity;
import com.kim.impexp.excel.entity.TestExcelReadEntity;
import com.kim.impexp.excel.exception.ImportInterceptException;
import com.kim.impexp.excel.interceptor.ImportInterceptorAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.exception.BusinessException;
import com.kim.impexp.excel.validation.Verifier;
import com.kim.impexp.excel.validation.VerifierAdaptor;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

/**
 * 批量导入工具
 * @date 2017年4月7日
 * @author liubo04
 */
public class ExcelImportUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);
	
	/**
	 * 导入数据处理
	 * @param fileName 文件名称，绝对路径
	 * @param executor 导入处理器
	 * @param verifier 验证器
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	public static <T extends ImportEntity> String importData(String fileName, AbstractExcelImpExecutor<T> executor, Verifier<T> verifier) {
		return importData(executor.readData(fileName), executor, verifier);
	}
	
	/**
	 * 导入数据处理
	 * @param dataList 要导入的数据
	 * @param executor 导入处理器
	 * @param verifier 验证器
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	public static <T extends ImportEntity> String importData(List<T> dataList, AbstractExcelImpExecutor<T> executor, Verifier<T> verifier) {
		try {
			return imp(dataList, executor, verifier);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("导入数据异常");
		}
	}
	
	
	private static Comparator<ImportEntity> comparator = new Comparator<ImportEntity>() {
		@Override
		public int compare(ImportEntity o1, ImportEntity o2) {
			return o1.getSortNum() == o2.getSortNum() ? 0 : (o1.getSortNum() > o2.getSortNum() ? 1 : -1);
		}
	};
	
	/**
	 * 导入数据处理
	 * @param fileName
	 * @param executor
	 * @param verifier
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	private static <T extends ImportEntity> String imp(List<T> dataList, AbstractExcelImpExecutor<T> executor, Verifier<T> verifier) {
		
		if(CollectionUtil.isEmpty(dataList)){
			logger.error("Excel未读取到数据行");
			throw new BusinessException("Excel没有数据行");
		}
		
        List<T> addList = new ArrayList<>();
        //要导出的结果数据
        List<T> resultList = new ArrayList<>();
		
        //校验数据
		check(verifier, dataList, addList, resultList);
		
		//前置拦截
		beforeImport(executor, dataList);
		
		//处理导入
		dealImport(executor, addList, resultList);
        
        //还原数据原来的顺序
        Collections.sort(resultList, comparator);
        
        //输出结果前拦截
        beforeWrite(executor, resultList);
        
		//输出导入结果到Excel文件
		return executor.writeData(resultList);
	}

	private static <T extends ImportEntity> void beforeImport(ExcelImpExecutor<T> executor, List<T> dataList) {
		if(executor.getInterceptor() != null){
			if(!executor.getInterceptor().beforeImport(dataList)){
				logger.error("数据导入前置拦截，导入操作已中断!");
				throw new ImportInterceptException("数据导入前置拦截，导入操作已中断!");
			}
		}
	}
	
	private static <T extends ImportEntity> void beforeWrite(ExcelImpExecutor<T> executor, List<T> dataList) {
		if(executor.getInterceptor() != null){
			if(!executor.getInterceptor().beforeWrite(dataList)){
				logger.error("数据导入输出结果前拦截，导入操作已中断!");
				throw new ImportInterceptException("数据导入输出结果前拦截，导入操作已中断!");
			}
		}
	}

	private static <T extends ImportEntity> void check(Verifier<T> verifier, List<T> dataList, 
			List<T> addList, List<T> resultList) {
		int sortNum = 0;
		if(verifier.isActiveBatchCheck()){
			verifier.checkList(dataList, addList, resultList);
			for (T entity : dataList) {
				//用于记录导入时读到的数据顺序
				entity.setSortNum(sortNum++);
			}
		}else{
			for (T entity : dataList) {
				//用于记录导入时读到的数据顺序
				entity.setSortNum(sortNum++);
				//校验数据和合法性
				if(!verifier.check(entity)){
					if(StringUtil.isBlank(entity.getImportRes())){
						entity.setImportRes("数据校验未通过");
					}
					resultList.add(entity);
				}else{
					addList.add(entity);
				}
			}
		}
	}

	private static <T extends ImportEntity> void dealImport(ExcelImpExecutor<T> executor, List<T> addList, List<T> resultList) {
		if(CollectionUtil.isNotEmpty(addList)){
			logger.info("导入数据count：{}", addList.size());
			List<T> resList = executor.execute(addList);
			if(resList == null){
				for (T entity : addList) {
					entity.setImportRes("导入成功");
					resultList.add(entity);
				}
			}else{
				for (T entity : resList) {
					if(StringUtil.isBlank(entity.getImportRes())){
						entity.setImportRes("导入成功");
					}
					resultList.add(entity);
				}
			}
		}else{
			logger.info("未有校验通过的数据，无需进行导入操作");
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String[] headerMapping = new String[] { 
				"车牌号码=>carNo", 
				"车辆身份=>carType", 
				"入场时间=>inTime", 
				"出场时间=>outTime",
				"缴费方式=>payType", 
				"应收金额=>fee", 
				"实收金额=>money", 
				"收费时间=>feeTime", 
				"收费员=>feeUser", 
				"移动端支付订单号=>order"
		};
		AbstractExcelImpExecutor<TestExcelReadEntity> executor = new AbstractExcelImpExecutor<TestExcelReadEntity>(headerMapping, TestExcelReadEntity.class) {

			@Override
			public List<TestExcelReadEntity> execute(List<TestExcelReadEntity> list) {
				System.out.println("插入数据");
				for (TestExcelReadEntity e : list) {
					System.out.println(e);
				}
				System.out.println("插入数据");
				return list;
			}

		};
		executor.setInterceptor(new ImportInterceptorAdaptor<TestExcelReadEntity>() {
			@Override
			public boolean beforeWrite(List<TestExcelReadEntity> list) {
				System.out.println("写excel之前的操作");
				return true;
			}
		});
		Verifier<TestExcelReadEntity> verifier = new VerifierAdaptor<TestExcelReadEntity>() {

			@Override
			public boolean check(TestExcelReadEntity entity) {
				return true;
			}
		};
		String path = ExcelImportUtil.importData("D:\\中海怡翠_睿停车缴费记录导出.xls", executor , verifier);
		System.out.println(path);
		
	}
	
}
