package com.kim.common.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;


/**
 * 数据访问层基础支撑接口
 * 
 * @author liubo
 */
public interface BaseDao<E extends BaseEntity, V extends BaseVo> {

	/**
	 * 单条插入数据
	 * 
	 * @param e 要插入的数据
	 * @return 插入的行数
	 */
	int insert(E e);

	/**
	 * 批量插入数据，调用此方法时建议使用BatchUtil工具
	 * 
	 * @param list 批量插入的数据对象
	 * @return 插入的行数
	 */
	int insertBatch(List<E> list);

	/**
	 * 修改数据
	 * 
	 * @param e 要修改的实体对象
	 * @return 修改的行数
	 */
	int update(E e);
	
	/**
	 * 根据条件删除数据(物理删除)
	 * 
	 * @param v 要删除的数据
	 * @return 删除的行数
	 */
	//int delete(V v);
	
	/**
	 * 根据条件删除数据(逻辑删除)
	 * 
	 * @param v 要删除的数据
	 * @return 删除的行数
	 */
	int deleteLogic(V v);

	/**
	 * 根据主键查询单条数据
	 * 
	 * @param id 主键id
	 * @return <tt>null<tt>若查无此数据
	 */
	//E findById(String id);
	
	/**
	 * 根据主键检查是否存在，用于修改时检查
	 * 
	 * @param v 查询条件
	 * @return <tt>null<tt>若查无此数据
	 * <br><b><h2>警告：</h2></b>
	 * <br>(1).如果在此方法对应sql中没有添加<tt>limit 1<tt>关键字，则会自动增加<tt>limit 1<tt>;
	 * <br>(2).如果在此方法对应sql中没有指定排序字段和类型，则返回默认排序后的第一条数据。
	 */
	String checkUnique(V v);
	
	/**
	 * 根据条件查询单条数据
	 * 
	 * @param v 查询条件
	 * @return <tt>null<tt>若查无此数据
	 * <br><b><h2>警告：</h2></b>
	 * <br>(1).如果在此方法对应sql中没有添加<tt>limit 1<tt>关键字，则会自动增加<tt>limit 1<tt>;
	 * <br>(2).如果在此方法对应sql中没有指定排序字段和类型，则返回默认排序后的第一条数据。
	 */
	E find(V v);

	/**
	 * 根据条件查询（不带分页）
	 * 
	 * @param v 查询参数对象
	 * @return 对象List
	 */
	List<E> list(@Param("obj")V v);

	/**
	 * 根据条件查询（带分页）
	 * 
	 * @param v 查询参数对象
	 * @param page 分页参数对象
	 * @return 分页对象PageRes
	 */
	PageRes<E> listByPage(@Param("obj") V v, @Param("page") PageVo page);

}
