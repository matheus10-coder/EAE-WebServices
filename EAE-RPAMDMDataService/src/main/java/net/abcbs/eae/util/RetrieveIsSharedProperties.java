package net.abcbs.eae.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.abcbs.issh.util.pub.property.IsSharedPropertyObj;

// This class is for retrieving properties set in the IsSharedPropertyService through IsSharedAdminWeb
public class RetrieveIsSharedProperties {
	private static final Logger logger = LogManager.getLogger(RetrieveIsSharedProperties.class);
	private IsSharedPropertyObj isSharedPropertyObj;
	
	public RetrieveIsSharedProperties() {
		isSharedPropertyObj = new IsSharedPropertyObj(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
	}
	
	public String getJdbcUrl() {
		logger.info("Attempting to get property jdbcUrl");
		return isSharedPropertyObj.getProperty("jdbcUrl");
	}
	
	public String getJdbcUsername() {
		logger.info("Attempting to get property dbUser");
		return isSharedPropertyObj.getProperty("dbUser");
	}
	
	public String getJdbcPassword() {
		logger.info("Attempting to get property dbPassword");
		return isSharedPropertyObj.getProperty("dbPassword");
	}
	
	public String buildJdbcUrl() {
		logger.info("Building JDBC url");
		String jdbcUrl = getJdbcUrl();
		String username = getJdbcUsername();
		String password = getJdbcPassword();
		jdbcUrl = jdbcUrl + "user=" + username + ";password=" + password;
		return jdbcUrl;
	}
	
	public String getRpaQueryServiceUrl() {
		logger.info("Attempting to get property queryServiceUrl");
		return isSharedPropertyObj.getProperty("queryServiceUrl");
	}
	
	public String getRpaQueryServiceNurseDataUrl() {
		logger.info("Attempting to get property queryServiceNurseDataUrl");
		return isSharedPropertyObj.getProperty("queryServiceNurseDataUrl");
	}
	
	public String getAffiniteUrl() {
		logger.info("Attempting to get property affiniteApiUrl");
		return isSharedPropertyObj.getProperty("affiniteApiUrl");
	}
	
	public String getProxyUsername() {
		logger.info("Attempting to get property proxyUsername");
		return isSharedPropertyObj.getProperty("proxyUsername");
	}
	
	public String getProxyPassword() {
		logger.info("Attempting to get property proxyPassword");
		return isSharedPropertyObj.getProperty("proxyPassword");
	}
	
	public String getRpaWorkdayUrl() {
		logger.info("Attempting to get property rpaWorkdayUrl");
		return isSharedPropertyObj.getProperty("rpaWorkdayUrl");
	}
}
