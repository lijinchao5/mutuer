/**
 * @fileName:  QuartzConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年3月1日 上午10:18:07
 */
package com.xuanli.oepcms.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author QiaoYu[www.codelion.cn]
 */
public class QuartzConnectionConfig implements ConnectionProvider {
	private static final Logger logger = LoggerFactory.getLogger(QuartzConnectionConfig.class);
	public String url;
	public String username;
	public String password;
	public String driverClassName;
	public String validationQuery;
	public String type;
	public String filters;
	public boolean testWhileIdle;
	public boolean testOnBorrow;
	public boolean testOnReturn;
	public boolean poolPreparedStatements;
	public int maxActive;
	public int maxWait;
	public int initialSize;
	public int minIdle;
	public int timeBetweenEvictionRunsMillis;
	public int minEvictableIdleTimeMillis;
	public int maxOpenPreparedStatements;
	public DruidDataSource dataSource;

	@Override
	public Connection getConnection() throws SQLException {
		logger.trace("quartz获取connection............");
		return dataSource.getConnection();
	}

	@Override
	public void shutdown() throws SQLException {
		logger.info("quartz关闭dataScource............");
		dataSource.close();
	}

	@Override
	public void initialize() throws SQLException {
		try {
			// 开启druid监控
			dataSource = new DruidDataSource();
			dataSource.setUrl(url);
			dataSource.setUsername(username);// 用户名
			dataSource.setPassword(password);// 密码
			dataSource.setDriverClassName(driverClassName);
			dataSource.setInitialSize(initialSize);
			dataSource.setMaxActive(maxActive);
			dataSource.setMinIdle(minIdle);
			dataSource.setMaxWait(maxWait);
			dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
			dataSource.setValidationQuery(validationQuery);
			dataSource.setTestOnBorrow(testOnBorrow);
			dataSource.setTestWhileIdle(testWhileIdle);
			dataSource.setTestOnReturn(testOnReturn);
			dataSource.setPoolPreparedStatements(poolPreparedStatements);
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxOpenPreparedStatements);
			dataSource.init();
			logger.info("数据源初始化完成.............;");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		logger.info("quartz初始化中.........");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public int getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	public DruidDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	
}
