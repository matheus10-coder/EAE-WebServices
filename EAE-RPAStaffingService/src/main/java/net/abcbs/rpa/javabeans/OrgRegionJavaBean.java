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



public class OrgRegionJavaBean extends IsSharedJavaBean {
	private static final String FROM = "FROM";
	private static Logger logger = LogManager.getLogger(StaffWorkloadJavaBean.class);
	
	PropertiesLoader jdbcprop = new PropertiesLoader();
	
	/**
	* GET Request
	* 
	* Retrieves a single row from the Org Region table 
	* 
	* Username is required
	*/
	public ArrayList<OrgRegionDTO> queryOrgRegion(String dataSource, String scheme, String orgRegion) {
		this.setDbFunctionSelect(dbFunctionDelete);
		ArrayList<OrgRegionDTO> arrayList = new ArrayList<>();
		boolean getAll = true;
		
		        try {
					this.initializeConnection(dataSource, "");
					sqlStatement.append("SELECT *");
					sqlStatement.append(" "+ FROM +" " + scheme + ".EAE_CM_ORGREGION");
					//All versus individual type
					if(!orgRegion.toLowerCase().contains("all")) {
						sqlStatement.append(" WHERE ECO_ORG_REGION_NAME = ? ");
						getAll = false;
					}
					sqlStatement.append(" WITH UR");
					logger.info("SQL: {}", sqlStatement);
					preparedStatement = connection.prepareStatement(sqlStatement.toString());
					if(!getAll) {
						preparedStatement.setString(1, orgRegion);
					}
					resultSet = preparedStatement.executeQuery();
					
					while (resultSet.next()) {
						OrgRegionDTO orgRegionOutput = new OrgRegionDTO();
						if (!StringUtils.isBlank(resultSet.getString(1))) {
							orgRegionOutput.setOrgRegionName(resultSet.getString(1).trim());
						}
						else {
							orgRegionOutput.setOrgRegionName("null");
						}
						if (!StringUtils.isBlank(resultSet.getString(2))) {
							orgRegionOutput.setActive(resultSet.getString(2).trim());
						}
						else {
							orgRegionOutput.setActive("null");
						}
						if (resultSet.getDate(3) != null ) {
							orgRegionOutput.setCreateTs(resultSet.getDate(3));
						}
						else {
							orgRegionOutput.setCreateTs(null);
						}
						if (!StringUtils.isBlank(resultSet.getString(4))) {
							orgRegionOutput.setCreateUserId(resultSet.getString(4).trim());
						}
						else {
							orgRegionOutput.setCreateUserId("null");
						}
						
						if (resultSet.getDate(5) != null ) {
							orgRegionOutput.setUpdateTs(resultSet.getDate(5));
						}
						else {
							orgRegionOutput.setUpdateTs(null);
						}
						if (!StringUtils.isBlank(resultSet.getString(6))) {
							orgRegionOutput.setUpdateUserId(resultSet.getString(6).trim());
						}
						else {
							orgRegionOutput.setUpdateUserId("null");
						}
					 
						arrayList.add(orgRegionOutput);
					}
					if(arrayList.size() == 0) {
						OrgRegionDTO orgRegionOutput = new OrgRegionDTO();
						orgRegionOutput.setError("The following region name was not found in the database: " + orgRegion);
						arrayList.add(orgRegionOutput);
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
	* JSON Body is required
	*/
	public String createOrgRegion(String dataSource, String scheme, OrgRegionDTO orgRegionRecord) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "post";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			sqlStatement.append("INSERT INTO " + scheme + ".EAE_CM_ORGREGION");
			sqlStatement.append(" (ECO_ORG_REGION_NAME"); 
			sqlStatement.append(" , ECO_ACTIVE"); 
			sqlStatement.append(" , ECO_CREATE_TS"); 
			sqlStatement.append(" , ECO_CREATE_USERID)"); 
		

			sqlStatement.append(" VALUES ");
			sqlStatement.append(" ( ?"); 
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , CURRENT TIMESTAMP"); 
			sqlStatement.append(" , ?)"); 
		
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			if(!StringUtils.isBlank(orgRegionRecord.getOrgRegionName())) {
				preparedStatement.setString(1, orgRegionRecord.getOrgRegionName());
			}
			else {
				preparedStatement.setString(1, "null");
				orgRegionRecord.setOrgRegionName("not provided by the user");
			}
			//Default for Active is 0
			if(!StringUtils.isBlank(orgRegionRecord.getActive())){
				preparedStatement.setString(2, orgRegionRecord.getActive());
			}
			else {
				preparedStatement.setString(2, "0");
			}
			//POST cannot have create user id null
			if(!StringUtils.isBlank(orgRegionRecord.getCreateUserId())){
				preparedStatement.setString(3, orgRegionRecord.getCreateUserId());
			}
			else {
				info = "Create UserID cannot be null! ";
			}
			
			updateCount = preparedStatement.executeUpdate();
			info =  orgRegionRecord.getOrgRegionName();
			
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
	* Update a single row from the OrgRegion table 
	* 
	* Org Region Name is optional
	*/
	public String updateOrgRegionRecord(String dataSource, String schema, OrgRegionDTO orgRegionUpdate, String orgRegName) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "put";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			sqlStatement.append("UPDATE " + schema + ".EAE_CM_ORGREGION");
			sqlStatement.append(" SET");
			if(!StringUtils.isBlank(orgRegionUpdate.getOrgRegionName())) {
				sqlStatement.append("  ECO_ORG_REGION_NAME = ?,");
			}
			if(!StringUtils.isBlank(orgRegionUpdate.getActive())) {
				sqlStatement.append("  ECO_ACTIVE = ?,"); 
			} 
			sqlStatement.append("  ECO_UPDATE_TS = CURRENT TIMESTAMP,"); 
			if(!StringUtils.isBlank(orgRegionUpdate.getUpdateUserId())) {
				sqlStatement.append("  ECO_UPDATE_USERID = ?");
			} 
			
			sqlStatement.append(" WHERE ECO_ORG_REGION_NAME = ? ");
			
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			int countSqlStatement = 0;
			if(!StringUtils.isBlank(orgRegionUpdate.getOrgRegionName())) {
				preparedStatement.setString(1, orgRegionUpdate.getOrgRegionName());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(orgRegionUpdate.getActive())) {
				preparedStatement.setString(1 + countSqlStatement, orgRegionUpdate.getActive());
				countSqlStatement++;
			}
			if(!StringUtils.isBlank(orgRegionUpdate.getUpdateUserId())) {
				preparedStatement.setString(1 + countSqlStatement, orgRegionUpdate.getUpdateUserId());
				countSqlStatement++;
			}
			
			preparedStatement.setString(1 + countSqlStatement, orgRegName);
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  orgRegName;
			
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
	* Org Region Name Required
	*/
	public String deleteOrgRegion(String dataSource, String schema, String orgRegionName) {
		this.setDbFunctionInsert(dbFunctionInsert);
		String msg = "delete";
		String info = "";
		
		try {
			this.initializeConnection(dataSource, "");
			 //todo change the query to OrgRegion one
			sqlStatement.append("DELETE FROM " + schema + ".EAE_CM_ORGREGION");
			sqlStatement.append(" WHERE ECO_ORG_REGION_NAME = ?"); 
			
			logger.info("SQL: {}", sqlStatement);
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, orgRegionName);
			
			
			updateCount = preparedStatement.executeUpdate();
			info =  orgRegionName;
			
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
	
	
	public List<OrgRegionDTO> exceptionHostClaimIdMessage (){
		ArrayList<OrgRegionDTO> arrayList = new ArrayList<>();
		
		OrgRegionDTO errorMessage = new OrgRegionDTO();
		errorMessage.setError("\"Error processing host claim id info request\"");
		arrayList.add(errorMessage);
		return arrayList;
	}
	
	public String outputResults(String msg, String info, int count) {
		if (msg.toLowerCase().contains("post")) {
			if (info.toLowerCase().contains("exception")) {
				msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
			}
			else if (count == 0) {
				msg = "Record(s) has not been created! Org Region has not been found. Rows modified: " + updateCount;
			}	
			else {
				msg = "Record(s) created successfully! Org Region created: " + info + ". Rows modified: " + updateCount;
			}
		}
		else {
			if (msg.toLowerCase().contains("get")) {
				
				if (info.toLowerCase().contains("exception")) {
					msg = "The " + msg + " operation has failed to be executed. Reason: " + info;
				}
				else if (count == 0) {
					msg = "Record(s) has not been found. Rows modified: " + updateCount;
				}
				else {
					msg = "Record(s) retrieved successfully! Org Region retrived: " + info;
				}
			}
			else {
				if (msg.toLowerCase().contains("put")) {
					
					if (info.toLowerCase().contains("exception")) {
						msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
					}
					else if (count == 0) {
						msg = "Record(s) has not been updated! Org Region has not been found. Rows modified: " + updateCount;
					}
					else {
						msg = "Record(s) updated successfully!  Org Region updated: " + info + ". Rows modified: " + updateCount;
					}
				}
				else {
					if (msg.toLowerCase().contains("delete")) {
						
						if (info.toLowerCase().contains("exception")) {
							msg = "The " + msg + " operation has failed to be executed. Reason: " + info + ". Rows modified: " + updateCount;;
						}
						else if (count == 0) {
							msg = "Record(s) has not been deleted! Org Region has not been found. Rows modified: " + updateCount;
						}
						else {
							msg = "Record(s) deleted successfully!  OrgRegion deleted: " + info + ". Rows modified: " + updateCount;
						}
					}
				}
			}
		}
		
		logger.info(msg);
		return msg;
	}



	
	
	
	

}

