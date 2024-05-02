package net.abcbs.rpa.javabeans;

/***********************************************************************************************************************************************************************
 * @author ABCBS resource
 * 
 * Description: RefRulesJaveBean class will be used to perform the proper connection with DB2 database and query the correct value required by the user 
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.*;
import net.abcbs.eae.jdbc.PropertiesLoader;
import net.abcbs.issh.util.pub.javabeans.IsSharedJavaBean;
import net.abcbs.rpa.dto.RefRulesDTO;



public class RefRulesJavaBean extends IsSharedJavaBean {
	private static final String FROM = "FROM";
	private static Logger logger = LogManager.getLogger(StaffWorkloadJavaBean.class);
	String sqlPrePaidManipulationStatement = "SCCF_ID IN";
	
	PropertiesLoader jdbcprop = new PropertiesLoader();
	
	/**
	 * GET Request
	 * 
	 * Retrieves a single row from the ref rules table 
	 * 
	 * ECR_Type is required
	 */
	public List<RefRulesDTO> queryRefRules(String dataSource, String scheme, String ruleId, String ruleType, String orgRegionName) {
		this.setDbFunctionDelete(dbFunctionDelete);
		ArrayList<RefRulesDTO> arrayList = new ArrayList<>();
		boolean getAll = true;
		int id = 0;
		
		if (!ruleId.toLowerCase().contains("all")) {
			getAll = false;
			id = Integer.parseInt(ruleId);
		}
		try {
			this.initializeConnection(dataSource, "");
			 
			sqlStatement.append("SELECT *");
			sqlStatement.append(" "+ FROM +" " + scheme + ".EAE_CM_REFRULES");
			if(!getAll) {
				sqlStatement.append(" WHERE  ECR_RULE_ID = ? AND ECR_TYPE = ? AND ECR_ORG_REGION_NAME = ? "); 
			}
			sqlStatement.append(" WITH UR");
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			if(!getAll) {
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, ruleType);
				preparedStatement.setString(3, orgRegionName);
			}
			resultSet = preparedStatement.executeQuery();
			//Result set should be executed once for individual records, all will run to the while loop
			if (!resultSet.next()) {
				RefRulesDTO refRulesOutput = new RefRulesDTO();
				refRulesOutput.setError("The following rule id was not found in the database: " + ruleId);
				arrayList.add(refRulesOutput);
			}
			else {
				do {
					RefRulesDTO refRulesOutput = new RefRulesDTO();

					if (!StringUtils.isBlank(resultSet.getString(1))) {
						refRulesOutput.setOrgRegionName(resultSet.getString(1).trim());
					}
					else {
						refRulesOutput.setOrgRegionName("null");
					}
					//Default for type is blank
					if (!StringUtils.isBlank(resultSet.getString(2))) {
						refRulesOutput.setType(resultSet.getString(2).trim());
					}
					else {
						refRulesOutput.setType("");
					}
					if (resultSet.getInt(3) != 0) {
						refRulesOutput.setRuleId(resultSet.getInt(3));
					}
					else {
						refRulesOutput.setRuleId(0);
					}
					if (resultSet.getInt(4) != 0) {
						refRulesOutput.setRulePriority(resultSet.getInt(4));
					}
					else {
						refRulesOutput.setRulePriority(0);
					}
					if (!StringUtils.isBlank(resultSet.getString(5))) {
						refRulesOutput.setRuleName(resultSet.getString(5).trim());
					}
					else {
						refRulesOutput.setRuleName("null");
					}
					if (!StringUtils.isBlank(resultSet.getString(6))) {
						refRulesOutput.setRuleDescription(resultSet.getString(6).trim());
					}
					else {
						refRulesOutput.setRuleDescription("null");
					}
					if (!StringUtils.isBlank(resultSet.getString(7))) {
						refRulesOutput.setUserType(resultSet.getString(7).trim());
					}
					else {
						refRulesOutput.setUserType("null");
					}
					//Default for Active is 0
					if (!StringUtils.isBlank(resultSet.getString(8))) {
						refRulesOutput.setActive(resultSet.getString(8).trim());
					}
					else {
						refRulesOutput.setActive("0");
					}
					if (resultSet.getInt(9) != 0) {
						refRulesOutput.setMaxPercent(resultSet.getInt(9));
					}
					else {
						refRulesOutput.setMaxPercent(0);
					}
					if (resultSet.getInt(10) != 0) {
						refRulesOutput.setMaxMember(resultSet.getInt(10));
					}
					else {
						refRulesOutput.setMaxMember(0);
					}
					if (resultSet.getDate(11) != null ) {
						refRulesOutput.setCreateTs(resultSet.getDate(11));
					}
					else {
						refRulesOutput.setCreateTs(null);
					}
					if (!StringUtils.isBlank(resultSet.getString(12))) {
						refRulesOutput.setCreateUserId(resultSet.getString(12).trim());
					}
					else {
						refRulesOutput.setCreateUserId("null");
					}
					
					if (resultSet.getDate(13) != null ) {
						refRulesOutput.setUpdateTs(resultSet.getDate(13));
					}
					else {
						refRulesOutput.setUpdateTs(null);
					}
					if (!StringUtils.isBlank(resultSet.getString(14))) {
						refRulesOutput.setUpdateUserId(resultSet.getString(14).trim());
					}
					else {
						refRulesOutput.setUpdateUserId("null");
					}
					
					arrayList.add(refRulesOutput);
				} while (resultSet.next());
			}
			
			
		}
		catch (SQLException se) {
			this.processException(se);
		}
		catch (Exception e) {
			this.processException(e);
		}
		finally {
			displayResults();
			logger.info("Connection successfully closed");
			this.closeConnections();
		}

		return arrayList;
	}
	
	/**
	 * POST Request
	 * 
	 * Create a single row from the ref rules table 
	 * 
	 * JSON Body is required
	 */
	public String createRefRules(String dataSource, String scheme, RefRulesDTO refRulesRecord) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "post";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			 //todo change the query to OrgRegion one
			sqlStatement.append("INSERT INTO " + scheme + ".EAE_CM_REFRULES");
			sqlStatement.append(" (ECR_ORG_REGION_NAME"); 
			sqlStatement.append(" , ECR_TYPE"); 
			sqlStatement.append(" , ECR_RULE_ID"); 
			sqlStatement.append(" , ECR_RULE_PRIORITY"); 
			sqlStatement.append(" , ECR_RULE_NAME");
			sqlStatement.append(" , ECR_RULE_DESCRIPTON");
			sqlStatement.append(" , ECR_USER_TYPE"); 
			sqlStatement.append(" , ECR_ACTIVE");
			sqlStatement.append(" , ECR_MAX_PERCENT"); 
			sqlStatement.append(" , ECR_MAX_MEMBER"); 
			sqlStatement.append(" , ECR_CREATE_TS"); 
			sqlStatement.append(" , ECR_CREATE_USERID)"); 
			
			sqlStatement.append(" VALUES ");
			sqlStatement.append(" ( ?"); 
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , ?");
			sqlStatement.append(" , CURRENT TIMESTAMP"); 
			sqlStatement.append(" , ?)"); 
			
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			if(!StringUtils.isBlank(refRulesRecord.getOrgRegionName())) {
				preparedStatement.setString(1, refRulesRecord.getOrgRegionName());
			}
			else {
				preparedStatement.setString(1, "null");
			}
			if(!StringUtils.isBlank(refRulesRecord.getType())) {
				preparedStatement.setString(2, refRulesRecord.getType());
			}
			else {
				preparedStatement.setString(2, "");
			}
			if(refRulesRecord.getRuleId() != 0) {
				preparedStatement.setInt(3, refRulesRecord.getRuleId());
			}
			else {
				preparedStatement.setInt(3, 0);
			}
			if(refRulesRecord.getRulePriority() != 0) {
				preparedStatement.setInt(4, refRulesRecord.getRulePriority());
			}
			else {
				preparedStatement.setInt(4, 0);
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleName())) {
				preparedStatement.setString(5, refRulesRecord.getRuleName());
			}
			else {
				preparedStatement.setString(5, "null");
				refRulesRecord.setRuleName("not provided by the user");
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleDescription())) {
				preparedStatement.setString(6, refRulesRecord.getRuleDescription());
			}
			else {
				preparedStatement.setString(6, "null");
			}
			if(!StringUtils.isBlank(refRulesRecord.getUserType())) {
				preparedStatement.setString(7, refRulesRecord.getUserType());
			}
			else {
				preparedStatement.setString(7, "null");
			}
			if(!StringUtils.isBlank(refRulesRecord.getActive())) {
				preparedStatement.setString(8, refRulesRecord.getActive());
			}
			else {
				preparedStatement.setString(8, "0");
			}
			if(refRulesRecord.getMaxPercent() != 0) {
				preparedStatement.setInt(9, refRulesRecord.getMaxPercent());
			}
			else {
				preparedStatement.setInt(9, 0);
			}
			if(refRulesRecord.getMaxMember() != 0) {
				preparedStatement.setInt(10, refRulesRecord.getMaxMember());
			}
			else {
				preparedStatement.setInt(10, 0);
			}
			if(!StringUtils.isBlank(refRulesRecord.getCreateUserId())) {
				preparedStatement.setString(11, refRulesRecord.getCreateUserId());
			}
			else {
				info = "Create UserID cannot be null! ";
			}
			
			
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  refRulesRecord.getRuleName();
			
		}
		catch (SQLTimeoutException ste) {
			this.processException(ste);
			info = "TimeOutException: " + ste;
		}
		catch (SQLException se) {
			this.processException(se);
			info += "SQLException: " + se;
		}
		catch (Exception e) {
			this.processException(e);
			info = "SystemException: " + e;
		}
		finally {
			displayResults();
			logger.info("Connection successfully closed");
			this.closeConnections();
			
		}
		return outputResults(msg, info, updateCount);
		
	}
	
	/**
	* PUT Request
	* 
	* Update a single row from the RefRules table 
	* 
	* RefRules id is optional
	*/
	public String updateRefRules(String dataSource, String schema, RefRulesDTO refRulesRecord, String refRuleId, String ruleType, String orgName) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "put";
		String info = "";
		int id = 0; 
		id = Integer.parseInt(refRuleId);
		
		try {
			this.initializeConnection(dataSource, "");
			 //todo change the query to OrgRegion one
			sqlStatement.append("UPDATE " + schema + ".EAE_CM_REFRULES");
			sqlStatement.append(" SET"); 
			if(!StringUtils.isBlank(refRulesRecord.getOrgRegionName())) {
				sqlStatement.append("  ECR_ORG_REGION_NAME = ?,");
			}
			if(!StringUtils.isBlank(refRulesRecord.getType())) {
				sqlStatement.append("  ECR_TYPE = ?,");
			}
			if(refRulesRecord.getRuleId() != 0) {
				sqlStatement.append("  ECR_RULE_ID = ?,"); 
			}
			if(refRulesRecord.getRulePriority() != 0) {
				sqlStatement.append("  ECR_RULE_PRIORITY = ?,"); 
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleName())) {
				sqlStatement.append("  ECR_RULE_NAME = ?,");
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleDescription())) {
				sqlStatement.append("  ECR_RULE_DESCRIPTON = ?,");
			}
			if(!StringUtils.isBlank(refRulesRecord.getUserType())) {
				sqlStatement.append("  ECR_USER_TYPE = ?,"); 
			}
			if(refRulesRecord.getMaxPercent() != 0 ) {
				sqlStatement.append("  ECR_MAX_PERCENT = ?,"); 
			}
			if(refRulesRecord.getMaxMember() != 0) {
				sqlStatement.append("  ECR_MAX_MEMBER = ?,"); 
			}
			if(!StringUtils.isBlank(refRulesRecord.getActive())) {
				sqlStatement.append("  ECR_ACTIVE = ?,"); 
			}
			sqlStatement.append("  ECR_UPDATE_TS = CURRENT TIMESTAMP,"); 
			if(!StringUtils.isBlank(refRulesRecord.getUpdateUserId())) {
				sqlStatement.append("  ECR_UPDATE_USERID = ?"); 
			}
				
			sqlStatement.append(" WHERE ECR_RULE_ID = ? AND ECR_TYPE = ? AND ECR_ORG_REGION_NAME = ?");
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			int countSqlStatement = 0;
			if(!StringUtils.isBlank(refRulesRecord.getOrgRegionName())) {
				preparedStatement.setString(1, refRulesRecord.getOrgRegionName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getType())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getType());
				countSqlStatement++;
			}
			if(refRulesRecord.getRuleId() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, refRulesRecord.getRuleId());
				countSqlStatement++;
			}
			if(refRulesRecord.getRulePriority() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, refRulesRecord.getRulePriority());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleName())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getRuleName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getRuleDescription())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getRuleDescription());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getUserType())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getUserType());
				countSqlStatement++;
			}
			if(refRulesRecord.getMaxPercent() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, refRulesRecord.getMaxPercent());
				countSqlStatement++;
			}
			if(refRulesRecord.getMaxMember() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, refRulesRecord.getMaxMember());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getActive())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getActive());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(refRulesRecord.getUpdateUserId())) {
				preparedStatement.setString(1 + countSqlStatement, refRulesRecord.getUpdateUserId());
				countSqlStatement++;
			}
			
			preparedStatement.setInt(1 + countSqlStatement, id);
			countSqlStatement++;
			
			preparedStatement.setString(1 + countSqlStatement, ruleType);
			countSqlStatement++;
			
			preparedStatement.setString(1 + countSqlStatement, orgName);
			countSqlStatement++;
			
			updateCount = preparedStatement.executeUpdate();
			info =  refRuleId;
			
		}
		catch (SQLTimeoutException ste) {
			this.processException(ste);
			info = "TimeOutException: " + ste;
		}
		catch (SQLException se) {
			this.processException(se);
			info = "SQLException: " + se;
		}
		catch (Exception e) {
			this.processException(e);
			info = "SystemException: " + e;
		}
		finally {
			displayResults();
			logger.info("Connection successfully closed");
			this.closeConnections();
			
		}
		return outputResults(msg, info, updateCount);
	}
	/**
	* DELETE Request
	* 
	* Delete a single row from the OrgRegion table 
	* 
	* OrgRegion Name Required
	 * @param orgRegionName 
	 * @param ruleType 
	*/
	public String deleteRefRule(String dataSource, String schema, String refRuleId, String ruleType, String orgRegionName) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "delete";
		String info = "";
		int id = 0; 
		id = Integer.parseInt(refRuleId);
		
		try {
			this.initializeConnection(dataSource, "");
			 //todo change the query to OrgRegion one
			sqlStatement.append("DELETE FROM " + schema + ".EAE_CM_REFRULES");
			sqlStatement.append(" WHERE ECR_RULE_ID = ? AND ECR_TYPE = ? AND ECR_ORG_REGION_NAME = ?"); 
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, ruleType);
			preparedStatement.setString(3, orgRegionName);
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  refRuleId;
			
		}
		catch (SQLTimeoutException ste) {
			this.processException(ste);
			info = "TimeOutException: " + ste;
		}
		catch (SQLException se) {
			this.processException(se);
			info = "SQLException: " + se;
		}
		catch (Exception e) {
			this.processException(e);
			info = "SystemException: " + e;
		}
		finally {
			displayResults();
			logger.info("Connection successfully closed");
			this.closeConnections();
			
		}
		return outputResults(msg, info, updateCount);
	}
	
	
	public List<RefRulesDTO> exceptionRefRulesMessage (){
		ArrayList<RefRulesDTO> arrayList = new ArrayList<>();
	
		RefRulesDTO errorMessage = new RefRulesDTO();
		
		errorMessage.setError("\"Error processing current request\"");
		
		arrayList.add(errorMessage);
		return arrayList;
	}
	
	public String outputResults(String msg, String info, int count) {
		if (msg.toLowerCase().contains("post")) {
			if (info.toLowerCase().contains("exception")) {
				msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
			}
			else if (count == 0) {
				msg = "Record(s) has not been created! Rule id has not been found. Rows modified: " + updateCount;
			}
			else {
				msg = "Record(s) created successfully! Rule name: " + info + ". Rows modified: " + updateCount;
			}	
		}
		else {
			if (msg.toLowerCase().contains("get")) {
				
				if (info.toLowerCase().contains("exception")) {
					msg = "The " + msg + " operation has failed to be executed. Reason: " + info;
				}
				else if (count == 0) {
					msg = "Record(s) has not been found! Rows modified: " + updateCount;
				}
				else {
					msg = "Record(s) retrieved successfully! Rule name: " + info;
				}
			}
			else {
				if (msg.toLowerCase().contains("put")) {
					
					if (info.toLowerCase().contains("exception")) {
						msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
					}
					else if (count == 0) {
						msg = "Record(s) has not been updated! Rule id has not been found. Rows modified: " + updateCount;
					}
					else {
						msg = "Record(s) updated successfully! Rule id: " + info + ". Rows modified: " + updateCount;
					}
				}
				else {
					if (msg.toLowerCase().contains("delete")) {
						
						if (info.toLowerCase().contains("exception")) {
							msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
						}
						else if (count == 0) {
							msg = "Record(s) has not been deleted! Rule id has not been found. Rows modified: " + updateCount;
						}
						else {
							msg = "Record(s) deleted successfully!  Rule id deleted: " + info + ". Rows modified: " + updateCount;
						}
					}
				}
			}
		}
		
		logger.info(msg);
		return msg;
	}

	

	
	
}
