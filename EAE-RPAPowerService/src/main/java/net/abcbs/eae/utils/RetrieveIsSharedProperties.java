package net.abcbs.eae.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.abcbs.issh.util.pub.property.IsSharedPropertyObj;

//This class is for retrieving properties set in the IsSharedPropertyService through IsSharedAdminWeb
public class RetrieveIsSharedProperties {
	private static final Logger logger = LogManager.getLogger(RetrieveIsSharedProperties.class);
	private IsSharedPropertyObj isSharedPropertyObj;

	public RetrieveIsSharedProperties() {
		isSharedPropertyObj = new IsSharedPropertyObj(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
	}

	// url for POWER
	public String getPowerUrl() {
		logger.info("Attempting to get property powerURL");
		return isSharedPropertyObj.getProperty("powerURL");
	}
	
	// credentials for POWER
	public String getPowerCredentials() {
		logger.info("Attempting to get property powerCredentials");
		return isSharedPropertyObj.getProperty("powerCredentials");
	}

}
