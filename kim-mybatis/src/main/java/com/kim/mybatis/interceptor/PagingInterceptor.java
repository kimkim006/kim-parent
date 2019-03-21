package com.kim.mybatis.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;
import com.kim.common.util.CollectionUtil;

/**
 * @Description 分页查询时把List放入参数PageRes中并返回
 * @date 2017年9月16日
 * @author liubo04
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PagingInterceptor implements Interceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //存储所有语句名称
    HashMap<String, String> statementMap = new HashMap<>();
    //用户提供分页计算条数后缀
    private static final String OUTER_SQL_REGEX = "(?<= FROM \\().*(?=\\))";
    private static final String COUNT_ID = "Count";
    private static final String COUNT_SQL_PREFIX = "SELECT COUNT(1) ";
    private static final String LIMIT_SQL_UPPER = " LIMIT ";
    private static final String LIMIT_SQL_LOWER = " limit ";
    private static final String ORDER_BY_SQL = " ORDER BY ";
    private static final String FROM_SQL = " FROM ";
    private static final String GROUP_BY_SQL = " GROUP BY ";
 
    /**
     * 获取所有statement语句的名称
     * @param configuration 
    */
    protected synchronized void initStatementMap(Configuration configuration) {
        if (!statementMap.isEmpty()) {
            return;
        }
        Collection<String> statements = configuration.getMappedStatementNames();
        for (Iterator<String> iter = statements.iterator(); iter.hasNext();) {
            String element = iter.next();
            statementMap.put(element, element);
        }
    }
 
    /**
     * 获取数据库连接
     * @param transaction
     * @param statementLog
     * @return
     * @throws SQLException 
    */
    protected Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
        Connection connection = transaction.getConnection();
        if (statementLog.isDebugEnabled()) {
            return ConnectionLogger.newInstance(connection, statementLog, 0);
        } else {
            return connection;
        }
    }
 
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
        PageVo page = seekPage(parameter);
        if (page == null) {
            return invocation.proceed();
        } else {
            return handling(invocation, parameter, page);
        }
 
    }
 
    /**
     * 处理分页的情况
     * <p>
     * @param invocation
     * @param parameter
     * @param page
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws SQLException 
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected PageRes handling(Invocation invocation, Object parameter, PageVo page) 
    		throws IllegalAccessException, InvocationTargetException, SQLException  {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Configuration configuration = mappedStatement.getConfiguration();
        if (statementMap.isEmpty()) {
            initStatementMap(configuration);
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        int total = getTotalSize(invocation, configuration, mappedStatement, boundSql, parameter);
        // 查询结果集
        List list = (List) exeQuery(invocation, mappedStatement);
        //设置到page对象
        PageRes pageResult = new PageRes(page);
        pageResult.setList(list);
        pageResult.setTotalSize(total);
        pageResult.addAll(list);
        return pageResult;
    }
 
    /**
     * 根据提供的语句执行查询操作
     * <p>
     *
     * @param invocation
     * @param queryStatement
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws Exception 
    */
    protected Object exeQuery(Invocation invocation, MappedStatement queryStatement) throws IllegalAccessException, InvocationTargetException  {
        Object[] args = invocation.getArgs();
        return invocation.getMethod().invoke(invocation.getTarget(),
        		queryStatement, args[1], args[2], args[3]);
    }
 
    /**
     * 获取总记录数量
     * @param configuration
     * @param mappedStatement
     * @param parameter
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws SQLException 
    */
    @SuppressWarnings("rawtypes")
    protected int getTotalSize(Invocation invocation, Configuration configuration, MappedStatement mappedStatement,
            BoundSql boundSql, Object parameter) throws IllegalAccessException, InvocationTargetException, SQLException  {
 
        String countId = mappedStatement.getId() + COUNT_ID;
        int totalSize = 0;
        if (statementMap.containsKey(countId)) {
            // 优先查找能统计条数的sql
            List data = (List) exeQuery(invocation, mappedStatement.getConfiguration().getMappedStatement(countId));
            if (CollectionUtil.isNotEmpty(data)) {
                totalSize = Integer.parseInt(data.get(0).toString());
            }
        } else {
            Executor exe = (Executor) invocation.getTarget();
            Connection connection = getConnection(exe.getTransaction(), mappedStatement.getStatementLog());
            totalSize = getTotalSize(mappedStatement, boundSql, connection, parameter);
        }
 
        return totalSize;
    }
 
    /**
     * 拼接获取条数的sql语句
     * @param sqlPrimary
     * @throws SQLException 
    */
    private Object[] getCountSql(String sqlPrimary) throws SQLException{
    	sqlPrimary = sqlPrimary.replaceAll("\\s+", " ").replaceAll(" from ", FROM_SQL);
    	String countSqlTmp = sqlPrimary.replaceAll(OUTER_SQL_REGEX, "");
        if(countSqlTmp.contains(GROUP_BY_SQL)){
        	throw new SQLException("不支持GROUP BY分页,请自行提供sql语句,以查询语句+Count结尾.");
        }
        sqlPrimary = sqlPrimary.replaceAll(" from ", FROM_SQL);
        int fromIndex = sqlPrimary.indexOf(FROM_SQL);
        if(fromIndex < 0){
            logger.error("sql语法错误，sql：{}", sqlPrimary);
        	throw new SQLException("sql语法错误，请检查");
        }
        int preQMCount = countSqlTmp.substring(0, fromIndex).split("\\?").length-1;
        int sufQMCount = 0;
        String countSql = COUNT_SQL_PREFIX + sqlPrimary.substring(fromIndex);
        if(countSqlTmp.contains(LIMIT_SQL_LOWER) || countSqlTmp.contains(LIMIT_SQL_UPPER)){
        	int l = countSql.lastIndexOf(LIMIT_SQL_UPPER);
        	l = l != -1 ? l : countSql.lastIndexOf(LIMIT_SQL_LOWER);
        	if(l > -1){
        		sufQMCount = 2;
        		countSql = countSql.substring(0, l);
        	}
        }
        if(countSqlTmp.contains(ORDER_BY_SQL)){
        	int l = countSql.lastIndexOf(ORDER_BY_SQL);
        	if(l > -1){
        		countSql = countSql.substring(0, l);
        	}
        }
        Object[] res = new Object[3];
        res[0]=countSql.trim();
        res[1]=preQMCount;
        res[2]=sufQMCount;
        return res;
    }
 
    /**
     * 计算总条数
     * @param connection
     * @return
    */
    protected int getTotalSize(MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object parameter) throws SQLException {
        
    	Object[] res = getCountSql(boundSql.getSql());
    	String countSql = res[0].toString();
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalSize = 0;
        try {
//            ParameterHandler handler = configuration.newParameterHandler(mappedStatement, parameter, boundSql)
            ParameterHandler handler = new PagingParameterHandler(mappedStatement, parameter, boundSql, Integer.parseInt(res[1].toString()), Integer.parseInt(res[2].toString()));
            stmt = connection.prepareStatement(countSql);
            handler.setParameters(stmt);
            rs = stmt.executeQuery();
            if (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (SQLException e) {
        	logger.error(e.getMessage(), e);
            throw e;
        } finally {
            close(rs);
            close(stmt);
        }
        return totalSize;
    }

    public void close(AutoCloseable c){
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 寻找page对象
     * @param parameter
    */
    @SuppressWarnings("rawtypes")
	protected PageVo seekPage(Object parameter) {
    	PageVo page = null;
        if (parameter == null) {
            return null;
        }
        if (parameter instanceof PageVo) {
            page = (PageVo) parameter;
        } else if (parameter instanceof Map) {
            Map map = (Map) parameter;
            for (Object arg : map.values()) {
                if (arg instanceof PageVo) {
                    page = (PageVo) arg;
                }
            }
        }
        return page;
    }
 
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
 
    @Override
    public void setProperties(Properties properties) {
    }
}
