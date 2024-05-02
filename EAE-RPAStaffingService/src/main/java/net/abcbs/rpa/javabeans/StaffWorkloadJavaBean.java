package net.abcbs.rpa.javabeans;
/***********************************************************************************************************************************************************************
 * @author ABCBS resource
 * 
 * Description: StaffWorkloadJavaBean class will be used to perform the proper connection with Oracle database and query the correct value required by the user 
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.*;

import net.abcbs.eae.jdbc.PropertiesLoader;
import net.abcbs.issh.util.pub.javabeans.IsSharedJavaBean;
import net.abcbs.rpa.dto.RefRulesDTO;
import net.abcbs.rpa.dto.StaffWorkloadDTO;
import net.abcbs.rpa.dto.OrgRegionDTO;



public class StaffWorkloadJavaBean extends IsSharedJavaBean {
	private static final String FROM = "FROM";
	private static Logger logger = LogManager.getLogger(StaffWorkloadJavaBean.class);
	
	PropertiesLoader jdbcprop = new PropertiesLoader();
	
	
	/**
	 * GET Request
	 * 
	 * Retrieves a single row from the workload table 
	 * 
	 * Username is required
	 */
	public List<StaffWorkloadDTO> queryStaffWorkload(String dataSource, String scheme, String user, String entityType, String userType) {
		this.setDbFunctionSelect(dbFunctionSelect);
		ArrayList<StaffWorkloadDTO> arrayList = new ArrayList<>();
		boolean getAll = true;
		
		
		try {
			this.initializeConnection(dataSource, "");
			 
			sqlStatement.append("SELECT *");
			sqlStatement.append(" "+ FROM +" " + scheme + ".EAE_CM_STAFFWORKLOAD");
			if(!user.toLowerCase().contains("all")) {
				sqlStatement.append(" WHERE ECS_USERNAME = ? AND ECS_ENTITYTYPE = ? AND ECS_USERTYPE = ? ");
				getAll = false;
			}
			sqlStatement.append(" WITH UR");
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			if(!getAll) {
				preparedStatement.setString(1, user);
				preparedStatement.setString(2, entityType);
				preparedStatement.setString(3, userType);
			}
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				StaffWorkloadDTO staffworkloadList = new StaffWorkloadDTO();
				
				if (!StringUtils.isBlank(resultSet.getString(1))) {
					staffworkloadList.setEntityType(resultSet.getString(1).trim());
				}
				else {
					staffworkloadList.setEntityType("null");
				}
				if (!StringUtils.isBlank(resultSet.getString(2))) {
					staffworkloadList.setUserName(resultSet.getString(2).trim());
				}
				else {
					staffworkloadList.setUserName("null");
				}
				
				if (!StringUtils.isBlank(resultSet.getString(3))) {
					staffworkloadList.setOrgRegionName(resultSet.getString(3).trim());
				}
				else {
					staffworkloadList.setOrgRegionName("null");
				}
				if (!StringUtils.isBlank(resultSet.getString(4))) {
					staffworkloadList.setUserType(resultSet.getString(4).trim());
				}
				else {
					staffworkloadList.setUserType("null");
				}
				if (!StringUtils.isBlank(resultSet.getString(5))) {
					staffworkloadList.setFirstName(resultSet.getString(5).trim());
				}
				else {
					staffworkloadList.setFirstName("null");
				}
				if (!StringUtils.isBlank(resultSet.getString(6))) {
					staffworkloadList.setLastName(resultSet.getString(6).trim());
				}
				else {
					staffworkloadList.setLastName("null");
				}
				if (!StringUtils.isBlank(resultSet.getString(6))) {
					staffworkloadList.setLastName(resultSet.getString(6).trim());
				}
				else {
					staffworkloadList.setLastName("null");
				}
				if (resultSet.getInt(7) != 0) {
					staffworkloadList.setMaxCases(resultSet.getInt(7));
				}
				else {
					staffworkloadList.setMaxCases(0);
				}
				if (resultSet.getInt(8) != 0) {
					staffworkloadList.setMaxDailyCases(resultSet.getInt(8));
				}
				else {
					staffworkloadList.setMaxDailyCases(0);
				}
				if (resultSet.getInt(9) != 0) {
					staffworkloadList.setMaxOverallCases(resultSet.getInt(9));
				}
				else {
					staffworkloadList.setMaxOverallCases(0);
				}
				if (resultSet.getInt(10) != 0) {
					staffworkloadList.setMaxAssignments(resultSet.getInt(10));
				}
				else {
					staffworkloadList.setMaxAssignments(0);
				}
				if (!StringUtils.isBlank(resultSet.getString(11))) {
					staffworkloadList.setActive(resultSet.getString(11).trim());
				}
				else {
					staffworkloadList.setActive("0");
				}
				if (resultSet.getDate(12) != null) {
					staffworkloadList.setCreateTs(resultSet.getDate(12));
				}
				else {
					staffworkloadList.setCreateTs(null);
				}
				
				if (!StringUtils.isBlank(resultSet.getString(13))) {
					staffworkloadList.setCreateUserId(resultSet.getString(13).trim());
				}
				else {
					staffworkloadList.setCreateUserId("null");
				}
				
				if (resultSet.getDate(14) != null) {
					staffworkloadList.setUpdateTs(resultSet.getDate(14));
				}
				else {
					staffworkloadList.setUpdateTs(null);
				}
				if (!StringUtils.isBlank(resultSet.getString(15))) {
					staffworkloadList.setUpdateUserId(resultSet.getString(15).trim());
				}
				else {
					staffworkloadList.setUpdateUserId("null");
				}
				
				arrayList.add(staffworkloadList);
			}
			//Alternative check since the checking with resultSet was giving me nullPointers - eclipse bug
			if(arrayList.size()==0){
				StaffWorkloadDTO staffworkloadList = new StaffWorkloadDTO();
				staffworkloadList.setError("The following username was not found in the database: " + user);
				arrayList.add(staffworkloadList);
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
	 * Create a single row from the workload table 
	 * 
	 * Username is required
	 */
	public String createStaffWorkload(String dataSource, String scheme, StaffWorkloadDTO staffworkload) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "post";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			 
			sqlStatement.append("INSERT INTO " + scheme + ".EAE_CM_STAFFWORKLOAD");
			sqlStatement.append(" (ECS_ORG_REGION_NAME"); 
			sqlStatement.append(" , ECS_ENTITYTYPE"); 
			sqlStatement.append(" , ECS_USERTYPE"); 
			sqlStatement.append(" , ECS_USERNAME"); 
			sqlStatement.append(" , ECS_FIRSTNAME"); 
			sqlStatement.append(" , ECS_LASTNAME"); 
			sqlStatement.append(" , ECS_MAXCASES"); 
			sqlStatement.append(" , ECS_MAXDAILYCASES"); 
			sqlStatement.append(" , ECS_MAXOVERALLCASES"); 
			sqlStatement.append(" , ECS_MAXASSIGNMENTS"); 
			sqlStatement.append(" , ECS_ACTIVE"); 
			sqlStatement.append(" , ECS_CREATE_TS"); 
			sqlStatement.append(" , ECS_CREATE_USERID)");  

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
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , CURRENT TIMESTAMP"); 
			sqlStatement.append(" , ?)"); 
		
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			if(!StringUtils.isBlank(staffworkload.getOrgRegionName())) {
				preparedStatement.setString(1, staffworkload.getOrgRegionName().trim());
			}
			else {
				preparedStatement.setString(1, "null");
			}
			if(!StringUtils.isBlank(staffworkload.getEntityType())) {
				preparedStatement.setString(2, staffworkload.getEntityType().trim());
			}
			else {
				preparedStatement.setString(2, "null");
			}
			if(!StringUtils.isBlank(staffworkload.getUserType())) {
				preparedStatement.setString(3, staffworkload.getUserType().trim());
			}
			else {
				preparedStatement.setString(3, "null");
			}
			if(!StringUtils.isBlank(staffworkload.getUserName())) {
				preparedStatement.setString(4, staffworkload.getUserName().trim());
			}
			else {
				preparedStatement.setString(4, "null");
				staffworkload.setUserName("not provided by the user");
			}
			if(!StringUtils.isBlank(staffworkload.getFirstName())) {
				preparedStatement.setString(5, staffworkload.getFirstName().trim());
			}
			else {
				preparedStatement.setString(5, "null");
			}
			if(!StringUtils.isBlank(staffworkload.getLastName())) {
				preparedStatement.setString(6, staffworkload.getLastName().trim());
			}
			else {
				preparedStatement.setString(6, "null");
			}
			if(staffworkload.getMaxCases() != 0) {
				preparedStatement.setInt(7, staffworkload.getMaxCases());
			}
			else {
				preparedStatement.setInt(7, 0);
			}
			if(staffworkload.getMaxDailyCases() != 0) {
				preparedStatement.setInt(8, staffworkload.getMaxDailyCases());
			}
			else {
				preparedStatement.setInt(8, 0);
			}
			if(staffworkload.getMaxOverallCases() != 0) {
				preparedStatement.setInt(9, staffworkload.getMaxOverallCases());
			}
			else {
				preparedStatement.setInt(9, 0);
			}
			if(staffworkload.getMaxAssignments() != 0) {
				preparedStatement.setInt(10, staffworkload.getMaxAssignments());
			}
			else {
				preparedStatement.setInt(10, 0);
			}
			//Default for Active is 0
			if(!StringUtils.isBlank(staffworkload.getActive())) {
				preparedStatement.setString(11, staffworkload.getActive().trim());
			}
			else {
				preparedStatement.setString(11, "0");
			}
			
			if(!StringUtils.isBlank(staffworkload.getCreateUserId())) {
				preparedStatement.setString(12, staffworkload.getCreateUserId().trim());
			}
			else {
				info = "Create UserID cannot be null! ";
			}
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  staffworkload.getUserName();
			
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
	 * Update a single row from the workload table 
	 * 
	 * Username is optional
	 */
	public String updateStaffWorkload(String dataSource, String schema, StaffWorkloadDTO staffRecord, String username, String entityType, String userType) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "put";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			 
			sqlStatement.append("UPDATE " + schema + ".EAE_CM_STAFFWORKLOAD");
			sqlStatement.append(" SET ");
			
			if (!StringUtils.isBlank(staffRecord.getEntityType())) {
				sqlStatement.append("  ECS_ENTITYTYPE = ?,");
			}
			 
			if (!StringUtils.isBlank(staffRecord.getUserType())) {
				sqlStatement.append("  ECS_USERTYPE = ?,");
			}
			
			if (!StringUtils.isBlank(staffRecord.getOrgRegionName())) {
				sqlStatement.append("  ECS_ORG_REGION_NAME = ?,");
			}
			 
			if (!StringUtils.isBlank(staffRecord.getUserName())) {
				sqlStatement.append("  ECS_USERNAME = ?,");
			}
			 
			if (!StringUtils.isBlank(staffRecord.getFirstName())) {
				sqlStatement.append("  ECS_FIRSTNAME = ?,"); 
			}
			
			if (!StringUtils.isBlank(staffRecord.getLastName())) {
				sqlStatement.append("  ECS_LASTNAME = ?,");
			}
			 
			if (staffRecord.getMaxCases() != 0) {
				sqlStatement.append("  ECS_MAXCASES = ?,"); 
			}
			
			if (staffRecord.getMaxDailyCases() != 0) {
				sqlStatement.append("  ECS_MAXDAILYCASES = ?,");
			}
			 
			if (staffRecord.getMaxOverallCases() != 0) {
				sqlStatement.append("  ECS_MAXOVERALLCASES = ?,");
			}
			 
			if (staffRecord.getMaxAssignments() != 0) {
				sqlStatement.append("  ECS_MAXASSIGNMENTS = ?,");
			}
			 
			if (!StringUtils.isBlank(staffRecord.getActive())) {
				sqlStatement.append("  ECS_ACTIVE = ?,");
			}
			
			sqlStatement.append("  ECS_UPDATE_TS = CURRENT TIMESTAMP,"); 
			
			if (!StringUtils.isBlank(staffRecord.getUpdateUserId())) {
				sqlStatement.append("  ECS_UPDATE_USERID = ?");
			}
			sqlStatement.append(" WHERE ECS_USERNAME = ? AND ECS_ENTITYTYPE = ? AND ECS_USERTYPE = ? ");
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			int countSqlStatement = 0;
			
			if(!StringUtils.isBlank(staffRecord.getEntityType())) {
				preparedStatement.setString(1 + countSqlStatement , staffRecord.getEntityType());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getUserType())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getUserType());
				countSqlStatement++;
			}
			
			if(!StringUtils.isBlank(staffRecord.getOrgRegionName())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getOrgRegionName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getUserName())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getUserName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getFirstName())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getFirstName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getLastName())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getLastName());
				countSqlStatement++;
			}
			if(staffRecord.getMaxCases() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, staffRecord.getMaxCases());
				countSqlStatement++;
			}
			if(staffRecord.getMaxDailyCases() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, staffRecord.getMaxDailyCases());
				countSqlStatement++;
			}
			if(staffRecord.getMaxOverallCases() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, staffRecord.getMaxOverallCases());
				countSqlStatement++;
			}
			if(staffRecord.getMaxAssignments() != 0) {
				preparedStatement.setInt(1 + countSqlStatement, staffRecord.getMaxAssignments());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getActive())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getActive());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(staffRecord.getUpdateUserId())) {
				preparedStatement.setString(1 + countSqlStatement, staffRecord.getUpdateUserId());
				countSqlStatement++;
			}
			preparedStatement.setString(1 + countSqlStatement, username);
			countSqlStatement++;
			preparedStatement.setString(1 + countSqlStatement, entityType);  
			countSqlStatement++;
			preparedStatement.setString(1 + countSqlStatement, userType);
			
			updateCount = preparedStatement.executeUpdate();
			info =  username;
			
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
	 * Drop a single row from the workload table 
	 * 
	 * Username is required
	 * @param userType 
	 * @param entityType 
	 */
	public String deleteStaffWorkload(String dataSource, String schema, String username, String entityType, String userType) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "delete";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			 //todo change the query to OrgRegion one
			sqlStatement.append("DELETE FROM " + schema + ".EAE_CM_STAFFWORKLOAD");
			sqlStatement.append(" WHERE ECS_USERNAME = ? AND ECS_ENTITYTYPE = ? AND ECS_USERTYPE = ? "); 
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, entityType);
			preparedStatement.setString(3, userType);
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  username;
			
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
	
	
	public List<StaffWorkloadDTO> swExceptionMessage (){
		ArrayList<StaffWorkloadDTO> arrayList = new ArrayList<>();
	
		StaffWorkloadDTO errorMessage = new StaffWorkloadDTO();
		
		errorMessage.setError("\"Error processing current request\"");
		
		arrayList.add(errorMessage);
		return arrayList;
	}
	
	public String outputResults(String msg, String info, int count) {
		if (msg.toLowerCase().contains("post")) {
			if (info.toLowerCase().contains("exception")) {
				msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
			}else if (count == 0) {
				msg = "Record(s) has not been created! User has not been found. Rows modified: " + updateCount;
			}
			else {
				msg = "Record(s) created successfully! User created: " + info + ". Rows modified: " + updateCount;
			}	
		}
		else {
			if (msg.toLowerCase().contains("get")) {
				
				if (info.toLowerCase().contains("exception")) {
					msg = "The " + msg + " operation has failed to be executed. Reason: " + info;
				}else if (count == 0) {
					msg = "Record(s) has not been found. Rows modified: " + updateCount;
				}
				else {
					msg = "Record(s) retrieved successfully! User id: " + info;
				}
			}
			else {
				if (msg.toLowerCase().contains("put")) {
					
					if (info.toLowerCase().contains("exception")) {
						msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
					}else if (count == 0) {
						msg = "Record(s) has not been updated! Username has not been found. Rows modified: " + updateCount;
					}
					else {
						msg = "Record(s) updated successfully! Username: " + info + ". Rows modified: " + updateCount;
					}
				}
				else {
					if (msg.toLowerCase().contains("delete")) {
						
						if (info.toLowerCase().contains("exception")) {
							msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
						}
						else if (count == 0) {
							msg = "Record(s) has not been deleted! Username has not been found. Rows modified: " + updateCount;
						}
						else {
							msg = "Record(s) deleted successfully!  Username deleted: " + info + ". Rows modified: " + updateCount;
						}
					}
				}
			}
		}
		
		logger.info(msg);
		return msg;
	}




	




	
	
	
}


