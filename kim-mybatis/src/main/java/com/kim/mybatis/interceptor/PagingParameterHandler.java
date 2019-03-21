package com.kim.mybatis.interceptor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.util.BeanUtil;
import com.kim.common.util.CollectionUtil;

public class PagingParameterHandler implements ParameterHandler {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final TypeHandlerRegistry typeHandlerRegistry;
	private final MappedStatement mappedStatement;
	private final Object parameterObject;
	private BoundSql boundSql;
	private Configuration configuration;
	
	private int preQMCount = 0;
	private int sufQMCount = 0;

	public PagingParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql, int preQMCount, int sufQMCount) {
		this.mappedStatement = mappedStatement;
		this.configuration = mappedStatement.getConfiguration();
		this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		this.parameterObject = parameterObject;
		this.boundSql = boundSql;
		this.preQMCount = preQMCount;
		this.sufQMCount = sufQMCount;
	}

	public PagingParameterHandler(DefaultParameterHandler defaultParameterHandler, int preQMCount, int sufQMCount){
		this.mappedStatement = (MappedStatement)getValue(defaultParameterHandler, "mappedStatement");
		this.configuration = mappedStatement.getConfiguration();
		this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		this.parameterObject = defaultParameterHandler.getParameterObject();
		this.boundSql = (BoundSql)getValue(defaultParameterHandler, "boundSql");
		this.preQMCount = preQMCount;
		this.sufQMCount = sufQMCount;
	}
	
	private Object getValue(Object bean, String name){
		try {
			return BeanUtil.getValueByName(bean, name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
			
	public Object getParameterObject() {
		return parameterObject;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameters(PreparedStatement ps) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if(CollectionUtil.isEmpty(parameterMappings)){
			return;
		}
		for (int i = preQMCount; i < parameterMappings.size()-sufQMCount; i++) {
			ParameterMapping parameterMapping = parameterMappings.get(i);
			if (parameterMapping.getMode() != ParameterMode.OUT) {
				Object value;
				String propertyName = parameterMapping.getProperty();
				if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
					value = boundSql.getAdditionalParameter(propertyName);
				} else if (parameterObject == null) {
					value = null;
				} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
					value = parameterObject;
				} else {
					MetaObject metaObject = configuration.newMetaObject(parameterObject);
					value = metaObject.getValue(propertyName);
				}
				TypeHandler typeHandler = parameterMapping.getTypeHandler();
				JdbcType jdbcType = parameterMapping.getJdbcType();
				if (value == null && jdbcType == null){
					jdbcType = configuration.getJdbcTypeForNull();
				}
				typeHandler.setParameter(ps, i-preQMCount + 1, value, jdbcType);
			}
		}
	}

	public int getPreQMCount() {
		return preQMCount;
	}

	public void setPreQMCount(int preQMCount) {
		this.preQMCount = preQMCount;
	}

	public int getSufQMCount() {
		return sufQMCount;
	}

	public void setSufQMCount(int sufQMCount) {
		this.sufQMCount = sufQMCount;
	}

}
