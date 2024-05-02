package net.abcbs.rpa.javabeans;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import net.abcbs.issh.util.pub.javabeans.IsSharedJavaBean;
import net.abcbs.rpa.dto.RPAQueryDTO;

public class RPAQueryJavaBean extends IsSharedJavaBean {
	public ArrayList<RPAQueryDTO> queryRPA(String dataSource, String schema) {
		this.setDbFunctionSelect(true);
		ArrayList<RPAQueryDTO> arrayList = new ArrayList<RPAQueryDTO>(); // Returned Array

		try {
			this.initializeConnection(dataSource, "");

			sqlStatement.append(" SELECT RQ_SYSTEM");
			sqlStatement.append(" , RQ_TENANT");
			sqlStatement.append(" , RQ_TYPE");
			sqlStatement.append(" , RQ_SEQ");
			sqlStatement.append(" , RQ_PROCESS");
			sqlStatement.append(" , RQ_SCHEMA");
			sqlStatement.append(" , RQ_QUERY_STRING");
			sqlStatement.append(" , RQ_DESCRIPTION");
			sqlStatement.append(" , RQ_QUERY_PARAMETER");
			sqlStatement.append(" , RQ_QUERY_FILENAME");
			sqlStatement.append(" , RQ_DB_TYPE");
			sqlStatement.append(" , RQ_OUTPUT_DATA");
			sqlStatement.append(" , RQ_OUTPUT_TYPE");
			sqlStatement.append(" , RQ_OUTPUT_ENDPOINT");
			sqlStatement.append(" , RQ_ACTIVE");
			sqlStatement.append(" , RQ_CREATE_TS");
			sqlStatement.append(" , RQ_CREATE_USERID");
			sqlStatement.append(" , RQ_UPDATE_TS");
			sqlStatement.append(" , RQ_UPDATE_USERID");
			sqlStatement.append(" FROM  " + schema + ".EAE_RPA_QUERY");
      
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				RPAQueryDTO rpaNotificationsOutput = new RPAQueryDTO();

				rpaNotificationsOutput.setSystem(resultSet.getString(1).trim());
				rpaNotificationsOutput.setTenant(resultSet.getString(2).trim());
				rpaNotificationsOutput.setType(resultSet.getString(3).trim());
				rpaNotificationsOutput.setSeq(resultSet.getInt(4));
				rpaNotificationsOutput.setProcess(resultSet.getString(5).trim());
				rpaNotificationsOutput.setSchema(resultSet.getString(6).trim());
				rpaNotificationsOutput.setQuery(resultSet.getString(7).trim());
				rpaNotificationsOutput.setDescription(resultSet.getString(8).trim());
				rpaNotificationsOutput.setParameter(resultSet.getString(9).trim());
				rpaNotificationsOutput.setFileName(resultSet.getString(10).trim());
				rpaNotificationsOutput.setDBType(resultSet.getString(11).trim());
				rpaNotificationsOutput.setOutputData(resultSet.getString(12).trim());
				rpaNotificationsOutput.setOutputType(resultSet.getString(13).trim());
				rpaNotificationsOutput.setOutputEndpoint(resultSet.getString(14).trim());
				rpaNotificationsOutput.setActive(resultSet.getInt(15));
				rpaNotificationsOutput.setCreateTs(resultSet.getTimestamp(16));
				rpaNotificationsOutput.setCreateUserId(resultSet.getString(17).trim());
				rpaNotificationsOutput.setUpdateTs(resultSet.getTimestamp(18));
				rpaNotificationsOutput.setUpdateUserId(resultSet.getString(19).trim());
				arrayList.add(rpaNotificationsOutput);
				updateCount++;
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
			this.closeConnections();
		}

		return arrayList;
	}

	public int createRPAQuery(RPAQueryDTO rpaQuery, String dataSource, String schema) {
		
		this.setDbFunctionInsert(true);

		try {
			this.initializeConnection(dataSource, "");

			sqlStatement.append(" INSERT INTO  " + schema + ".EAE_RPA_QUERY");
			sqlStatement.append(" (RQ_SYSTEM"); 
			sqlStatement.append(" , RQ_TENANT"); 
			sqlStatement.append(" , RQ_TYPE"); 
			sqlStatement.append(" , RQ_SEQ"); 
			sqlStatement.append(" , RQ_PROCESS"); 
			sqlStatement.append(" , RQ_SCHEMA"); 
			sqlStatement.append(" , RQ_QUERY_STRING"); 
			sqlStatement.append(" , RQ_DESCRIPTION"); 
			sqlStatement.append(" , RQ_QUERY_PARAMETER"); 
			sqlStatement.append(" , RQ_QUERY_FILENAME"); 
			sqlStatement.append(" , RQ_DB_TYPE"); 
			sqlStatement.append(" , RQ_OUTPUT_DATA"); 
			sqlStatement.append(" , RQ_OUTPUT_TYPE"); 
			sqlStatement.append(" , RQ_OUTPUT_ENDPOINT"); 
			sqlStatement.append(" , RQ_ACTIVE"); 
			sqlStatement.append(" , RQ_CREATE_TS"); 
			sqlStatement.append(" , RQ_CREATE_USERID"); 
			sqlStatement.append(" , RQ_UPDATE_TS"); 
			sqlStatement.append(" , RQ_UPDATE_USERID)"); 
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
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , CURRENT TIMESTAMP"); 
			sqlStatement.append(" , ?"); 
			sqlStatement.append(" , CURRENT TIMESTAMP"); 
			sqlStatement.append(" , ?)"); 

			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, rpaQuery.getSystem());
			preparedStatement.setString(2, rpaQuery.getTenant());
			preparedStatement.setString(3, rpaQuery.getType());
			preparedStatement.setInt(4, rpaQuery.getSeq());
			preparedStatement.setString(5, rpaQuery.getProcess());
			preparedStatement.setString(6, rpaQuery.getSchema());
			preparedStatement.setString(7, rpaQuery.getQuery());
			preparedStatement.setString(8, rpaQuery.getDescription());
			preparedStatement.setString(9, rpaQuery.getParameter());
			preparedStatement.setString(10, rpaQuery.getFileName());
			preparedStatement.setString(11, rpaQuery.getDBType());
			preparedStatement.setString(12, rpaQuery.getOutputData());
			preparedStatement.setString(13, rpaQuery.getOutputType());
			preparedStatement.setString(14, rpaQuery.getOutputEndpoint());
			preparedStatement.setInt(15, rpaQuery.getActive());
			preparedStatement.setString(16, rpaQuery.getCreateUserId());
			preparedStatement.setString(17, rpaQuery.getUpdateUserId());

			updateCount = preparedStatement.executeUpdate();
			
		}
		catch (SQLTimeoutException ste) {
			this.processException(ste);
		}
		catch (SQLException se) {
			this.processException(se);
		}
		finally {
			displayResults();
			this.closeConnections();
		}

		return updateCount;
	}
	
	
public int updateRPAQuery(RPAQueryDTO rpaQuery, String dataSource, String schema) {
		this.setDbFunctionUpdate(true);
		sqlStatement = new StringBuilder();

		try {

			this.initializeConnection(dataSource, "");

			sqlStatement.append(" UPDATE  " + schema + ".EAE_RPA_QUERY");
			sqlStatement.append(" SET ");
			sqlStatement.append("RQ_SCHEMA = ?");
			sqlStatement.append(" , RQ_QUERY_STRING = ?");
			sqlStatement.append(" , RQ_DESCRIPTION = ?");
			sqlStatement.append(" , RQ_QUERY_PARAMETER = ?");
			sqlStatement.append(" , RQ_QUERY_FILENAME = ?");
			sqlStatement.append(" , RQ_DB_TYPE = ?");
			sqlStatement.append(" , RQ_OUTPUT_DATA = ?");
			sqlStatement.append(" , RQ_OUTPUT_TYPE = ?");
			sqlStatement.append(" , RQ_OUTPUT_ENDPOINT = ?");
			sqlStatement.append(" , RQ_ACTIVE = ?");
			sqlStatement.append(" , RQ_CREATE_TS = CURRENT TIMESTAMP");
			sqlStatement.append(" , RQ_CREATE_USERID= ?");
			sqlStatement.append(" , RQ_UPDATE_TS = CURRENT TIMESTAMP");
			sqlStatement.append(" , RQ_UPDATE_USERID= ?");
		    sqlStatement.append(" WHERE RQ_SYSTEM= ?");
	        sqlStatement.append(" and RQ_TENANT= ?");
	        sqlStatement.append(" and RQ_TYPE= ?");
	        sqlStatement.append(" and RQ_PROCESS= ?");
	        sqlStatement.append(" and RQ_SEQ= ?");

			preparedStatement = connection.prepareStatement(sqlStatement.toString());
		
			preparedStatement.setString(1, rpaQuery.getSchema());	
			preparedStatement.setString(2, rpaQuery.getQuery());
			preparedStatement.setString(3, rpaQuery.getDescription());
			preparedStatement.setString(4, rpaQuery.getParameter());
			preparedStatement.setString(5, rpaQuery.getFileName());
			preparedStatement.setString(6, rpaQuery.getDBType());
			preparedStatement.setString(7, rpaQuery.getOutputData());
			preparedStatement.setString(8, rpaQuery.getOutputType());
			preparedStatement.setString(9, rpaQuery.getOutputEndpoint());
			preparedStatement.setInt(10, rpaQuery.getActive());
			preparedStatement.setString(11, rpaQuery.getCreateUserId());
			preparedStatement.setString(12, rpaQuery.getUpdateUserId());
			preparedStatement.setString(13, rpaQuery.getSystem());
			preparedStatement.setString(14, rpaQuery.getTenant());
			preparedStatement.setString(15, rpaQuery.getType());
			preparedStatement.setString(16, rpaQuery.getProcess());
			preparedStatement.setInt(17, rpaQuery.getSeq());
	
			updateCount = preparedStatement.executeUpdate();
		}
		catch (SQLTimeoutException ste) {
			this.processException(ste);

		}
		catch (SQLException se) {
			this.processException(se);
		}
		finally {
			//Display a couple messages and close the connection
			displayResults();
			this.closeConnections();
		}

		return updateCount;
	}

public int deleteRPAQuery(RPAQueryDTO rpaQuery, String dataSource, String schema) {
	this.setDbFunctionDelete(true);
	sqlStatement = new StringBuilder();
	
	try {
		this.initializeConnection(dataSource, "");

		sqlStatement.append(" DELETE FROM " + schema + ".EAE_RPA_QUERY");
        sqlStatement.append(" WHERE RQ_SYSTEM= ?");
        sqlStatement.append(" and RQ_TENANT= ?");
        sqlStatement.append(" and RQ_TYPE= ?");
        sqlStatement.append(" and RQ_PROCESS= ?");
        sqlStatement.append(" and RQ_SEQ= ?");
		preparedStatement = connection.prepareStatement(sqlStatement.toString());
		preparedStatement.setString(1, rpaQuery.getSystem());
		preparedStatement.setString(2, rpaQuery.getTenant());
		preparedStatement.setString(3, rpaQuery.getType());
		preparedStatement.setString(4, rpaQuery.getProcess());
		preparedStatement.setInt(5, rpaQuery.getSeq());

		updateCount = preparedStatement.executeUpdate();
	}
	catch (SQLTimeoutException ste) {
		this.processException(ste);
	}
	catch (SQLException se) {
		this.processException(se);
	}
	finally {
		displayResults();
		this.closeConnections();
	}

	return updateCount;
}
	
}
