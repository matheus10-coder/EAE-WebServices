package net.abcbs.eae.jaxrs;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;
import net.abcbs.rpa.dto.RPAQueryDTO;
import net.abcbs.rpa.javabeans.RPAQueryJavaBean;
import net.abcbs.issh.util.pub.property.IsSharedPropertyObj;

/*Create the following operations:
Create a new record
Read an existing single record
Read an existing group of records
Update an existing record
Delete an existing record
*/
@Path("/eventQuery")
@OpenAPIDefinition(
		servers = {
				@Server(
					description = "localhost",
					url = "http://localhost:9080/RPAQueryService/resources"),
				@Server(
					description = "development",
					url = "https://isshareddev.abcbs.net/RPAQueryService/resources"),
				@Server(
						description = "stage",
						url = "https://issharedstg.abcbs.net/RPAQueryService/resources"),
				@Server(
						description = "production",
						url = "https://isshared.abcbs.net/RPAQueryService/resources")
		})
@SecurityScheme(name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		description = "Username and Password are used for authorization") 


//Event Query Resource class (Main class which is called in Event Query Application
public class EventQueryResource  {

	// Data object to get database information
private static IsSharedApplicationDataObject isSharedApplicationDataObject = null;
private static IsSharedPropertyObj isSharedPropertyObj;

static {
	try {
		isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
		isSharedPropertyObj = new IsSharedPropertyObj(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
	} catch (Exception e) {
		e.printStackTrace();
	}
};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Service Endpoint",
	description = "Returns a response from service. Response is a single string. Usually to determine whether service is up")
	public String getMessage() {
		return "{'RPAQueryService Basepoint message!'}";
	} 

	@GET
	@Path("/Records")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve all queries saved to RPA Query Service database based on enviroment",
	description = "Brings back all queries from database as an array of json objects")
	public ArrayList<RPAQueryDTO> getRecords() {
		RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
		return rpaQueryJavaBean.queryRPA(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{System}/{Tenant}/{Type}/{Process}/{Sequence}")
	@Operation(summary = "Retrieve a single query from the RPA Query Service database",
	description = "Brings back a query record from the database as json object  with the specified path /System/Tenant/Type/Process/Sequence")
	public RPAQueryDTO getRecord(
	@PathParam("System") String system,
	@PathParam("Tenant") String tenant,
	@PathParam("Type") String type,
	@PathParam("Process") String process,
	@PathParam("Sequence") Integer sequence) {
		RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
		ArrayList<RPAQueryDTO> rpaArrayList = rpaQueryJavaBean.queryRPA(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
		for (int i = 0; i < rpaArrayList.size(); i++) {
			if (system.equals(rpaArrayList.get(i).getSystem()) && tenant.equals(rpaArrayList.get(i).getTenant()) && type.equals(rpaArrayList.get(i).getType()) && process.equals(rpaArrayList.get(i).getProcess()) && sequence.equals(rpaArrayList.get(i).getSeq())) {
				return rpaArrayList.get(i);
			}
		}
		return null;
		
   }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("SingleRecord/{System}/{Tenant}/{Type}/{Process}/{Sequence}")
	@Operation(summary = "Retrieve a single record from the RPA Query Service database.This method is the currently used to get queries by DispatcherService",
	description = "Brings back single query from QueryService Database. This methods replaces DB schema in query retrivies. Eg: ITS or ABCBS Schemas")
	public RPAQueryDTO getSingleRecord(
	@PathParam("System") String system,
	@PathParam("Tenant") String tenant,
	@PathParam("Type") String type,
	@PathParam("Process") String process,
	@PathParam("Sequence") Integer sequence) {
		String responseStr;
		RPAQueryDTO response = new RPAQueryDTO();
		RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
		ArrayList<RPAQueryDTO> rpaArrayList = rpaQueryJavaBean.queryRPA(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
		for (int i = 0; i < rpaArrayList.size(); i++) {
			if (system.equals(rpaArrayList.get(i).getSystem()) && tenant.equals(rpaArrayList.get(i).getTenant()) && type.equals(rpaArrayList.get(i).getType()) && process.equals(rpaArrayList.get(i).getProcess()) && sequence.equals(rpaArrayList.get(i).getSeq())) {
			    responseStr = rpaArrayList.get(i).getQuery().replace("@@itsSchema@@",isSharedPropertyObj.getProperty("itsSchema"));
			    response.setQuery(responseStr.replace("@@abcbsSchema@@",isSharedPropertyObj.getProperty("abcbsSchema")));
				return response;
			}
		}
		return null;	
   }
	  
	//Create record to db using html form
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Produces(MediaType.APPLICATION_JSON)
	  @Operation(summary = "Create record to RPA Query Service database using HTML form",
	  description = "Create record to Query Service Database using form found at RPAQueryService/index.jsp")
	  public String createRecord(
			  @FormParam("system") final String system, 
			  @FormParam("tenant") final String tenant,
			  @FormParam("type") final String type,
			  @FormParam("seq") final int sequence,
			  @FormParam("process") final String process,
			  @FormParam("schema") final String schema, 
			  @FormParam("query") final String query, 
			  @FormParam("description") final String description, 
			  @FormParam("parameter") final String parameter, 
			  @FormParam("fileName") final String fileName,
			  @FormParam("dbType") final String dbType, 
			  @FormParam("outputData") final String outputData, 
			  @FormParam("outputType") final String outputType, 
			  @FormParam("outputEndpoint") final String outputEndpoint, 
			  @FormParam("active") final int active,
			  @FormParam("createUserId") final String CreateUserId, 
			  @FormParam("updateUserId") final String updateUserId){
		  RPAQueryDTO rpaRecord = new RPAQueryDTO();
			try {
	            rpaRecord.setSystem(system);
	            rpaRecord.setTenant(tenant);
	            rpaRecord.setType(type);
	            rpaRecord.setSeq(sequence);
	            rpaRecord.setProcess(process);
	            rpaRecord.setSchema(schema);
	            rpaRecord.setQuery(query);
	            rpaRecord.setDescription(description);
	            rpaRecord.setParameter(parameter);
	            rpaRecord.setFileName(fileName);
	            rpaRecord.setDBType(dbType);
	            rpaRecord.setOutputData(outputData);
	            rpaRecord.setOutputType(outputType);
	            rpaRecord.setOutputEndpoint(outputEndpoint);
	            rpaRecord.setActive(active);
	            rpaRecord.setCreateUserId(CreateUserId);
	            rpaRecord.setUpdateUserId(updateUserId);

				RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
				rpaQueryJavaBean.createRPAQuery(rpaRecord, isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
			} catch (Exception e) {
				return "{\"message\": \"couldn't add record. Exception message: " + e.getMessage() + "\"}";
			} 
			return "{\"message\": \"record created\"}";
		}
	  
	//Create record to db consuiming a json file 
	  @POST
	  @Path("/add")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  @Operation(summary = "Create record to RPA Query Service database using JSON object",
	  description = "Create a single query to RPA Query Service database sending JSON object.")
	  public String createRecord(Query query) {
		  RPAQueryDTO rpaRecord = new RPAQueryDTO();
			try {
				rpaRecord.setSystem(query.getSystem()); 
				rpaRecord.setTenant(query.getTenant()); 
				rpaRecord.setType(query.getType()); 
				rpaRecord.setSeq(query.getSeq()); 
				rpaRecord.setProcess(query.getProcess()); 
				rpaRecord.setSchema(query.getSchema()); 
				rpaRecord.setQuery(query.getQuery()); 
				rpaRecord.setDescription(query.getDescription());
				rpaRecord.setParameter(query.getParameter()); 
				rpaRecord.setFileName(query.getFileName()); 
				rpaRecord.setDBType(query.getDBType()); 
				rpaRecord.setOutputData(query.getOutputData()); 
				rpaRecord.setOutputType(query.getOutputType()); 
				rpaRecord.setOutputEndpoint(query.getOutputEndpoint()); 
				rpaRecord.setActive(query.getActive()); 
				rpaRecord.setCreateUserId(query.getCreateUserId()); 
				rpaRecord.setUpdateUserId(query.getUpdateUserId()); 
				RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
				rpaQueryJavaBean.createRPAQuery(rpaRecord, isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
			} catch (Exception e) {
				return "{\"message\": \"couldn't add record. Error: " + e.getMessage() + "\"}";
			} 
			return "{\"message\": \"record with type " + query.getType() + " created\"}";
		}
	
   // Delete record from database using specified ID
	@DELETE
	@Path("delete/{System}/{Tenant}/{Type}/{Process}/{Sequence}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Delete a query record from the RPA Query Service database",
	description = "Delete single query from Query Service Database schema sepcfified by path /{System}/{Tenant}/{Type}/{Process}/{Sequence}")
	public String deleteRecord(
			@PathParam("System") String system,
			@PathParam("Tenant") String tenant,
			@PathParam("Type") String type,
			@PathParam("Process") String process,
			@PathParam("Sequence") Integer sequence) {
	  RPAQueryJavaBean rpaQueryJavaBean = new RPAQueryJavaBean();
	  ArrayList<RPAQueryDTO> rpaArrayList = rpaQueryJavaBean.queryRPA(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
		for (int i = 0; i < rpaArrayList.size(); i++) {
			if (system.equals(rpaArrayList.get(i).getSystem()) && tenant.equals(rpaArrayList.get(i).getTenant()) && type.equals(rpaArrayList.get(i).getType()) && process.equals(rpaArrayList.get(i).getProcess()) && sequence.equals(rpaArrayList.get(i).getSeq())) {
				rpaQueryJavaBean.deleteRPAQuery(rpaArrayList.get(i), isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
				return "{\"message\": \"record successfully deleted\"}";
			}
		}
		return "{\"message\": \"record could not be found\"}";
	}
	
    @PUT
	@Path("/update/{System}/{Tenant}/{Type}/{Process}/{Sequence}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a query record in the RPA Query Service database",
	description = "Update single query from Query Service Database schema sepcfified by path /{System}/{Tenant}/{Type}/{Process}/{Sequence}. JSON object is also needed as input")
	public RPAQueryDTO updateRecord(Query query, @Parameter(description = "Query Json object needed to update specified record in database",required = true) 			
	        @PathParam("System") String system,
			@PathParam("Tenant") String tenant,
			@PathParam("Type") String type,
			@PathParam("Process") String process,
			@PathParam("Sequence") Integer sequence) {
		RPAQueryJavaBean rpaQueryJavaBean  = new RPAQueryJavaBean();
		ArrayList<RPAQueryDTO> rpaArrayList = rpaQueryJavaBean.queryRPA(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
		for (int i = 0; i < rpaArrayList.size(); i++) {
			if (system.equals(rpaArrayList.get(i).getSystem()) && tenant.equals(rpaArrayList.get(i).getTenant()) && type.equals(rpaArrayList.get(i).getType()) && process.equals(rpaArrayList.get(i).getProcess()) && sequence.equals(rpaArrayList.get(i).getSeq())) {
				rpaArrayList.get(i).setSystem(query.getSystem()); 
				rpaArrayList.get(i).setTenant(query.getTenant()); 
				rpaArrayList.get(i).setType(query.getType()); 
				rpaArrayList.get(i).setSeq(query.getSeq()); 
				rpaArrayList.get(i).setProcess(query.getProcess()); 
				rpaArrayList.get(i).setSchema(query.getSchema());
				rpaArrayList.get(i).setQuery(query.getQuery());
				rpaArrayList.get(i).setDescription(query.getDescription());
				rpaArrayList.get(i).setParameter(query.getParameter());
				rpaArrayList.get(i).setFileName(query.getFileName());
				rpaArrayList.get(i).setDBType(query.getDBType());
				rpaArrayList.get(i).setOutputData(query.getOutputData());
				rpaArrayList.get(i).setOutputType(query.getOutputType());
				rpaArrayList.get(i).setOutputEndpoint(query.getOutputEndpoint());
				rpaArrayList.get(i).setActive(query.getActive());
				rpaArrayList.get(i).setCreateUserId(query.getCreateUserId());
				rpaArrayList.get(i).setUpdateUserId(query.getCreateUserId());
				rpaQueryJavaBean.updateRPAQuery(rpaArrayList.get(i), isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema());
				return rpaArrayList.get(i);
			}
		}
		return null;
	}

	
	  
}	  
