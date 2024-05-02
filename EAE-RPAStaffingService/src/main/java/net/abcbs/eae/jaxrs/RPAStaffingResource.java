package net.abcbs.eae.jaxrs;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;
import net.abcbs.rpa.dto.OrgRegionDTO;
import net.abcbs.rpa.dto.RefRulesDTO;
import net.abcbs.rpa.dto.StaffWorkloadDTO;
import net.abcbs.rpa.javabeans.OrgRegionJavaBean;
import net.abcbs.rpa.javabeans.RefRulesJavaBean;
import net.abcbs.rpa.javabeans.StaffWorkloadJavaBean;



/***********************************************************************************************************************************************************************
 * @Author mfribeiro
 * 
 * Body for all the methods used for the REST Web service
 * 
 * Description: StaffingResource class is the application resource level which the main methods will be called in order to return the message to the user 
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/

@Path("/cmwa")
@OpenAPIDefinition(
		servers = {
				@Server(
					description = "localhost",
					url = "localhost:9080/RPAStaffingService/resources"),
				@Server(
					description = "development",
					url = "https://isshareddev.abcbs.net/RPAStaffingService/resources"),
				@Server(
						description = "test",
						url = "https://issharedtst.abcbs.net/RPAStaffingService/resources"),
				@Server(
					description = "stage",
					url = "https://issharedstg.abcbs.net/RPAStaffingService/resources"),
				@Server(
					description = "production",
					url = "https://isshared.abcbs.net/RPAStaffingService/resources")
		})
@SecurityScheme(name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		description = "Username and Password are used for authorization")
public class RPAStaffingResource {
	
    /**
     * Private method
     * 
     * Data object to get database information
     * 
     * Utilizing isSharedApplication class
     * 
     */
	private static Logger logger = LogManager.getLogger(RPAStaffingResource.class);
	private static IsSharedApplicationDataObject isSharedApplicationDataObject = null;
	private static String jndi = "db2NodeDB";
	private static String schema = "BCBSDB26";

	static {
		try {
			isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	/**
     * Public method
     * 
     * Method to output successful message from the server
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Main service base endpoint that you can hit to get a response from the server",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "A simple base endpoint that you can hit to get a response from the server to verify the main resource",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)	
	public String cmwaMessage(){
		return "{\"message\": \"CMWA - RPA Staffing Service will accept in a request and respond with the appropriate information queried from the DB2 database. Db2jndiName: " + isSharedApplicationDataObject.getDb2JndiName() + ". Schema: " + isSharedApplicationDataObject.getDb2Schema() + ". Please refer to your specific endpoint. \"}";
	}
	/**
     * Public method
     * 
     * STAFF WORKDLOAD ENDPOINT
     * 
     * @return 
     */
	@GET
	@Path("/staffworkload")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Staffworkload base endpoint",
			description = "Return a successful string as the endpoint is requested",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") })
	public String staffworkloadBaseEndpoint() {
		return  "{\"message\": \"CMWA - StaffWorkload base endpoint. Please append your request to this endpoint to fetch information related to staff workload table. \"}";
	}
	/**
     * Public method
     * 
     * REF RULES ENDPOINT
     * 
     * @return 
     */
	@GET
	@Path("/ref-rules")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Ref Rules base endpoint",
	description = "Return a successful string as the endpoint is requested",
	responses = {
			@ApiResponse(
					description = "JSON response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") })
	public String refRulesBaseEndpoint() {
		return  "{\"message\": \"CMWA - RefRules base endpoint. Please append your request to this endpoint to fetch information related to org region table. \"}";
	}
	/**
     * Public method
     * 
     * ORG REGION ENDPOINT
     * 
     * @return 
     */
	@GET
	@Path("/org-region")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Org Region base endpoint",
	description = "Return a successful string as the endpoint is requested",
	responses = {
			@ApiResponse(
					description = "JSON response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") })
	public String orgRegionBaseEndpoint() {
		return  "{\"message\": \"CMWA - OrgRegion base endpoint. Please append your request to this endpoint to fetch information related to org region table. \"}";
	}
	
	/**
     * GET Request 
     * 
     * Method to retrieve a single row from the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Path("/staffworkload/get/{userName}/{entityType}/{userType}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve a single record from the staffworkload table based on the username, entitytype and userytype",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "A single record will return if the user has passed a valid input and valid parameters that matches in the db ",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = StaffWorkloadDTO.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public List<StaffWorkloadDTO> swGetRecords(
			@Parameter(description = "Please insert username, entitytype and usertype strings respectively, the service will perform input validation if succeed it will return the values from db",
			required = true)
			@PathParam("userName") String user,
			@PathParam("entityType") String entityType,
			@PathParam("userType") String userType) {
		StaffWorkloadJavaBean staffworkloadJavabean = new StaffWorkloadJavaBean();
		//return staffworkloadJavabean.queryStaffWorkload(jndi, schema,user,entityType,userType);
		return staffworkloadJavabean.queryStaffWorkload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(),user,entityType,userType);
	
	}
	/**
     * GET ALL Request 
     * 
     * Method to retrieve all the rows from the table associated with the staffworkload
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Path("/staffworkload/get/{all}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve multiple records from the staffworkload table",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Fetch all the records in the staffworkload db",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = StaffWorkloadDTO.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public List<StaffWorkloadDTO> swGetAllRecords(
			@Parameter(description = "Please insert all to fetch all the staffworkload in the db",
			required = true)
			@PathParam("all") String allUserEntries) {
		StaffWorkloadJavaBean staffworkloadJavabean = new StaffWorkloadJavaBean();
		//return staffworkloadJavabean.queryStaffWorkload(jndi, schema,allUserEntries,"","");
		return staffworkloadJavabean.queryStaffWorkload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(),allUserEntries,"","");
	
	}
	/**
     * GET Request 
     * 
     * Method to retrieve a single row from the table associated with the referral rule id
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Path("/ref-rules/get/{ruleId}/{ruleType}/{orgRegionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve a single record from the ref rules table based on the ruleId, ruleType and orgRegionName",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Please insert rule-id, ruletype and org-region-name strings respectively, the service will perform input validation if succeed it will return the values from db",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = RefRulesDTO.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public List<RefRulesDTO> refRulesGetRecords(
			@Parameter(description = "Please insert Rule ID, ruleType and org region name strings and the service will perform input validation if succeed it will return the values",
			required = true)
			@PathParam("ruleId") String id,
			@PathParam("ruleType") String ruleType,
			@PathParam("orgRegionName") String orgRegionName) {
		RefRulesJavaBean refRulesJavaBean = new RefRulesJavaBean();
		//return refRulesJavaBean.queryRefRules(jndi,schema,id,ruleType,orgRegionName);
		return refRulesJavaBean.queryRefRules(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(),id,ruleType,orgRegionName);
		
	}
	/**
     * GET ALL Request 
     * 
     * Method to retrieve all the rows from the table associated with the ref rules
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Path("/ref-rules/get/{all}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve multiple records from the ref rules table ",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Fetch all the records in the ref rules db",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = StaffWorkloadDTO.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public List<RefRulesDTO> refRulesGetAllRecords(
			@Parameter(description = "Please append the word all to fetch all the ref rules in the db",
			required = true)
			@PathParam("all") String allUserEntries) {
		RefRulesJavaBean refRulesJavaBean = new RefRulesJavaBean();
		//return refRulesJavaBean.queryRefRules(jndi, schema,allUserEntries,"","");
		return refRulesJavaBean.queryRefRules(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(),allUserEntries,"","");
	
	}
	/**
     * GET Request 
     * 
     * Method to retrieve a single row from the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@GET
	@Path("/org-region/region-name/{orgRegionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retrieve a single or multiple records from the org region table based on the org-region-name or append the word all to retrive multiple records",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Retrieve a single or multiple records from the org region table based on the org-region-name or append the word all to retrive multiple records",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = OrgRegionDTO.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public List<OrgRegionDTO> orgGetRecords(
			@Parameter(description = "Please insert org-region-name string and the service will perform input validation if succeed it will return the values",
			required = true)
			@PathParam("orgRegionName") String orgRegionName) {
		OrgRegionJavaBean orgRegionJavaBean = new OrgRegionJavaBean();
		//return orgRegionJavaBean.queryOrgRegion(jndi,schema,orgRegionName);
		return orgRegionJavaBean.queryOrgRegion(isSharedApplicationDataObject.getDb2JndiName(),isSharedApplicationDataObject.getDb2Schema(),orgRegionName);
		
	}	
	
	/**
     * POST Request 
     * 
     * Method to create a row to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@POST
	@Path("/staffworkload/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a record into staffworkload based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Create a record into staffworkload based on the body json given",
	responses = {
			@ApiResponse(
					description = "String response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String createStaffWorkloadRecord (
			@RequestBody (
					description = "Staffworkload Bean request body type. Create userID is required to complete this request successfully",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = StaffWorkloadDTO.class)))
			Staffing staffWorkload) {
		StaffWorkloadDTO staffRecord = new StaffWorkloadDTO();
		String responseJson = null;
			
		try {
			
			staffRecord.setEntityType(staffWorkload.getEntityType());
			staffRecord.setUserName(staffWorkload.getUserName());
			staffRecord.setOrgRegionName(staffWorkload.getOrgRegionName());
			staffRecord.setUserType(staffWorkload.getUserType());
			staffRecord.setFirstName(staffWorkload.getFirstName());
			staffRecord.setLastName(staffWorkload.getLastName());
			staffRecord.setMaxCases(staffWorkload.getMaxCases());
			staffRecord.setMaxDailyCases(staffWorkload.getMaxDailyCases());
			staffRecord.setMaxOverallCases(staffWorkload.getMaxOverallCases());
			staffRecord.setMaxAssignments(staffWorkload.getMaxAssignments());
			staffRecord.setActive(staffWorkload.getActive());
			staffRecord.setMaxDailyCases(staffWorkload.getMaxDailyCases());
			staffRecord.setCreateUserId(staffWorkload.getCreateUserId());
		
			StaffWorkloadJavaBean staffJavaBean = new StaffWorkloadJavaBean();
			//responseJson = staffJavaBean.createStaffWorkload(jndi, schema, staffRecord);
			responseJson = staffJavaBean.createStaffWorkload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), staffRecord);
		
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be created. Reason: " + e.getMessage() + "\"}";
		}
		if (!responseJson.toLowerCase().contains("exception")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
	}
	/**
     * POST Request 
     * 
     * Method to create a row to the table associated with the json body
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@POST
	@Path("/ref-rules/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a record into referral rule table based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Create a record into referral rule table based on the body json given",
	responses = {
			@ApiResponse(
					description = "String response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String createReferralRRecord (
			@RequestBody (
					description = "Referral Rules Bean request body type. Create userID is required to complete this request successfully",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = RefRulesDTO.class)))
			Staffing referralRule) {
		RefRulesDTO refRulesRecord = new RefRulesDTO();
		String responseJson = null;
		
		
		try {
			
			refRulesRecord.setOrgRegionName(referralRule.getOrgRegionName());
			refRulesRecord.setType(referralRule.getType());
			refRulesRecord.setRuleId(referralRule.getRuleId());
			refRulesRecord.setRulePriority(referralRule.getRulePriority());
			refRulesRecord.setRuleName(referralRule.getRuleName());
			refRulesRecord.setRuleDescription(referralRule.getRuleDescription());
			refRulesRecord.setUserType(referralRule.getUserType());
			refRulesRecord.setActive(referralRule.getActive());
			refRulesRecord.setMaxPercent(referralRule.getMaxPercent());
			refRulesRecord.setMaxMember(referralRule.getMaxMember());
			refRulesRecord.setCreateUserId(referralRule.getCreateUserId());
			refRulesRecord.setUpdateUserId(referralRule.getUpdateUserId());
			
			RefRulesJavaBean refRulesJavaBean = new RefRulesJavaBean();
			
			responseJson = refRulesJavaBean.createRefRules(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), refRulesRecord);
			
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be created. Reason: " + e.getMessage() + "\"}";
		}
		
		if (!responseJson.toLowerCase().contains("exception")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
	}
	/**
     * POST Request 
     * 
     * Method to create a row to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@POST
	@Path("/org-region/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a record into org region table based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Create a record into org region table based on  the body json given",
	responses = {
			@ApiResponse(
					description = "String response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String createOrgRegionRecord (
			@RequestBody (
					description = "OrgRegion Bean request body type. Create userID is required to complete this request successfully",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = OrgRegionDTO.class)))
			Staffing orgRegion) {
		OrgRegionDTO orgRecord = new OrgRegionDTO();
		String responseJson = null;
		
		try {
			
			orgRecord.setOrgRegionName(orgRegion.getOrgRegionName());
			orgRecord.setActive(orgRegion.getActive());
			orgRecord.setCreateUserId(orgRegion.getCreateUserId());
			OrgRegionJavaBean orgRegionJaveBean = new OrgRegionJavaBean();
			//responseJson = orgRegionJaveBean.createOrgRegion(jndi, schema, orgRecord);
			responseJson = orgRegionJaveBean.createOrgRegion(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), orgRecord);
			
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be created. Reason: " + e.getMessage() + "\"}";
		}
		
		if (!responseJson.toLowerCase().contains("exception")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
	}
	
	/**
     * UPDATE Request 
     * 
     * Method to update/modify a specific record to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@PUT
	@Path("/staffworkload/update/{username}/{entityType}/{userType}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update a record into staffworkload based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Update a record into staffworkload based on the body json given",
	responses = {
			@ApiResponse(
					description = "String response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String updateStaffWorkloadRecord (@RequestBody (
			description = "Staffworkload Bean request type. Update userID is required to complete this request successfully",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = StaffWorkloadDTO.class)))
			Staffing staffWorkload, 
			@Parameter(
					description = "Please insert the username and org-region-name to update the desired staff record ",required = true,
					content = @Content(mediaType = "application/json"))
			@PathParam("username") String user, 
			@PathParam("entityType") String entityType, 
			@PathParam("userType") String userType) {
		StaffWorkloadDTO staffRecord = new StaffWorkloadDTO();
		String responseJson = null;
			
		try {
			
			staffRecord.setEntityType(staffWorkload.getEntityType());
			staffRecord.setUserName(staffWorkload.getUserName());
			staffRecord.setOrgRegionName(staffWorkload.getOrgRegionName());
			staffRecord.setUserType(staffWorkload.getUserType());
			staffRecord.setFirstName(staffWorkload.getFirstName());
			staffRecord.setLastName(staffWorkload.getLastName());
			staffRecord.setMaxCases(staffWorkload.getMaxCases());
			staffRecord.setMaxDailyCases(staffWorkload.getMaxDailyCases());
			staffRecord.setMaxOverallCases(staffWorkload.getMaxOverallCases());
			staffRecord.setMaxAssignments(staffWorkload.getMaxAssignments());
			staffRecord.setActive(staffWorkload.getActive());
			staffRecord.setMaxDailyCases(staffWorkload.getMaxDailyCases());
			staffRecord.setUpdateUserId(staffWorkload.getUpdateUserId());
			
			
			StaffWorkloadJavaBean staffJavaBean = new StaffWorkloadJavaBean();
			//responseJson = staffJavaBean.updateStaffWorkload(jndi, schema, staffRecord, user, entityType, userType);
			responseJson = staffJavaBean.updateStaffWorkload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), staffRecord, user, entityType, userType);
		
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be updated. Reason: " + e.getMessage() + "\"}";
		}
		if (!responseJson.toLowerCase().contains("exception") && !responseJson.toLowerCase().contains("has not been updated")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
	}
	
	/**
     * UPDATE Request 
     * 
     * Method to update/modify a specific record to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@PUT
	@Path("/ref-rules/update/{ruleId}/{ruleType}/{orgRegionName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update a record into referral rule table based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Update a record into referral rule table based on the body json given",
	responses = {
			@ApiResponse(
					description = "Successful JSON response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String updateReferralRRecord (
			@RequestBody (
					description = "Referral Rules Bean request type. Update userID is required to complete this request successfully",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = RefRulesDTO.class)))
			Staffing referralRule, 
			@Parameter(description = "Please insert the the rule-id, rule-type and the org-region-name in order to update the specific referral rule record ",required = true)
			@PathParam("ruleId") String ruleId,
			@PathParam("ruleType") String ruleType,
			@PathParam("orgRegionName") String orgRegionName) {
		RefRulesDTO refRulesRecord = new RefRulesDTO();
		String responseJson = null;
		
		
		try {
			
			refRulesRecord.setOrgRegionName(referralRule.getOrgRegionName());
			refRulesRecord.setType(referralRule.getType());
			refRulesRecord.setRuleId(referralRule.getRuleId());
			refRulesRecord.setRulePriority(referralRule.getRulePriority());
			refRulesRecord.setRuleName(referralRule.getRuleName());
			refRulesRecord.setRuleDescription(referralRule.getRuleDescription());
			refRulesRecord.setUserType(referralRule.getUserType());
			refRulesRecord.setActive(referralRule.getActive());
			refRulesRecord.setMaxPercent(referralRule.getMaxPercent());
			refRulesRecord.setMaxMember(referralRule.getMaxMember());
			refRulesRecord.setUpdateUserId(referralRule.getUpdateUserId());
			
			RefRulesJavaBean refRulesJavaBean = new RefRulesJavaBean();
			//responseJson = refRulesJavaBean.updateRefRules(jndi, schema, refRulesRecord,ruleId, ruleType, orgRegionName );
			responseJson = refRulesJavaBean.updateRefRules(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), refRulesRecord, ruleId, ruleType, orgRegionName);
			
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be updated. Reason: " + e.getMessage() + "\"}";
		}
		
		if (!responseJson.toLowerCase().contains("exception") && !responseJson.toLowerCase().contains("has not been updated")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
	}
	
	/**
     * UPDATE Request 
     * 
     * Method to update/modify a specific record to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */

	@PUT
	@Path("/org-region/update/{orgRegionName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update a record into Org Region table based on the body json given",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Update a record into Org Region table based on the body json given",
	responses = {
			@ApiResponse(
					description = "String response",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = JsonPayload.class))),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	) public String updateOrgRegion (
			@RequestBody (
					description = "Org Region Bean request type. Update UserID is required to complete this request successfully",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = OrgRegionDTO.class)))
			Staffing orgRegionRecord, 
			@Parameter(description = "Please insert the the org-region-name to update the specific org-region record ",required = true)
			@PathParam("orgRegionName") String orgRegName) {
		OrgRegionDTO orgRegionUpdate = new OrgRegionDTO();
		String responseJson = null;
			
		try {
			
			orgRegionUpdate.setOrgRegionName(orgRegionRecord.getOrgRegionName());
			orgRegionUpdate.setActive(orgRegionRecord.getActive());
			orgRegionUpdate.setUpdateUserId(orgRegionRecord.getUpdateUserId());
			
			
			OrgRegionJavaBean orgRegJavaBean = new OrgRegionJavaBean();
			//responseJson = orgRegJavaBean.updateOrgRegionRecord(jndi, schema, orgRegionUpdate, orgRegName);
			responseJson = orgRegJavaBean.updateOrgRegionRecord(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), orgRegionUpdate, orgRegName);
		
		} catch (Exception e) {
			return "{\"Failure\": \"Record couldn't be updated. Reason: " + e.getMessage() + "\"}";
		}
		if (!responseJson.toLowerCase().contains("exception") && !responseJson.toLowerCase().contains("has not been updated")) {
			return "{\"Success\": \"" + responseJson + "\"}";
		}
		else {
			return "{\"Failure\": \"" + responseJson + "\"}";
		}
		
		
		
	}
	
	
	/**
     * DELETE Request 
     * 
     * Method to drop a whole row or a specific record to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return List value
     * 
     */
	@DELETE
	@Path("/staffworkload/delete/{username}/{entityType}/{userType}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Delete a single record from the staffworkload table based on username, entity-type and user-type",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Drop an single record from the staffworkload",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized")}
	)public String staffWorkloadDeleteRecord(
			@Parameter(description = "Please insert username, entity-type and user-type to delete the record",
			required = true)
			@PathParam("username") String username,
			@PathParam("entityType") String entityType,
			@PathParam("userType") String userType) {
		
		String responseJson = null;	
		try {
			
			StaffWorkloadJavaBean staffworkloadJavaBean = new StaffWorkloadJavaBean();
			//responseJson = staffworkloadJavaBean.deleteStaffWorkload(jndi, schema, username, entityType,userType );
			responseJson = staffworkloadJavaBean.deleteStaffWorkload(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), username, entityType,userType );
					
			} catch (Exception e) {
					return "{\"Failure\": \"Record couldn't be deleted. Reason: " + e.getMessage() + "\"}";
			}
				
			if (!responseJson.toLowerCase().contains("exception")) {
				return "{\"Success\": \"" + responseJson + "\"}";
			}
			else {
				return "{\"Failure\": \"" + responseJson + "\"}";
			}
		}
	
	/**
     * DELETE Request 
     * 
     * Method to drop a whole row or a specific record to the table associated with the referral rule id
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return Successful or faulted string
     * 
     */
	@DELETE
	@Path("/ref-rules/delete/{refRuleId}/{ruleType}/{orgRegionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Delete a single record from the Referral Rules table based on referral rule id",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Delete a single record from the Referral Rules table based on referral rule id. Please append the specfic referral rule id to the endpoint",
			responses = {
					@ApiResponse(
							description = "Successful JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public String refRuleDeleteRecord(
			@Parameter(description = "Please insert ref rule-id, rule-type and org-region-name to delete the record",
			required = true)
			@PathParam("refRuleId") String refRuleId,
			@PathParam("ruleType") String ruleType,
			@PathParam("orgRegionName") String orgRegionName) {
		
		String responseJson = null;	
		try {
			RefRulesJavaBean refRulesJavaBean = new RefRulesJavaBean();
			//responseJson = refRulesJavaBean.deleteRefRule(jndi, schema, refRuleId, ruleType, orgRegionName);
			responseJson = refRulesJavaBean.deleteRefRule(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), refRuleId, ruleType, orgRegionName);
					
			} catch (Exception e) {
					return "{\"Failure\": \"Record couldn't be deleted. Reason: " + e.getMessage() + "\"}";
			}
				
			if (!responseJson.toLowerCase().contains("exception")) {
				return "{\"Success\": \"" + responseJson + "\"}";
			}
			else {
				return "{\"Failure\": \"" + responseJson + "\"}";
			}
		}
	
	/**
     * DELETE Request 
     * 
     * Method to drop a whole row or a specific record to the table associated with the userName
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return Successful or faulted string
     * 
     */
	@DELETE
	@Path("/org-region/delete/{orgRegionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Delete a single record from the Org Region table based on Org Region Name",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "Delete a single record from the org-region table based on org-region-name. Please append append to specific org-region-name to the endpoint ",
			responses = {
					@ApiResponse(
							description = "Successful JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = JsonPayload.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)public String orgDeleteRecord(
			@Parameter(description = "Please insert org region name to delete the record",
			required = true)
			@PathParam("orgRegionName") String orgRegionName) {
		
		String responseJson = null;	
		try {
			OrgRegionJavaBean orgRegionJaveBean = new OrgRegionJavaBean();
			//responseJson = orgRegionJaveBean.deleteOrgRegion(jndi, schema, orgRegionName);
			responseJson = orgRegionJaveBean.deleteOrgRegion(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), orgRegionName);
					
			} catch (Exception e) {
					return "{\"Failure\": \"Record couldn't be created. Reason: " + e.getMessage() + "\"}";
			}
				
			if (!responseJson.toLowerCase().contains("exception")) {
				return "{\"Success\": \"" + responseJson + "\"}";
			}
			else {
				return "{\"Failure\": \"" + responseJson + "\"}";
			}
		}
	
	/**
     * Public method
     * 
     * Performs credential validation 
     * 
     * If user gets a 401 they are unauthorized
     * 
     * @return JsonPayLoad type message 
     * 
     */
	@GET
	@Path("/test/authorization") 
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Simple test authorization method",
		security = @SecurityRequirement(name = "basicAuth"),
		description = "Test your credentials",
		responses = {
			@ApiResponse(
					description = "A method to simply test your credentials against the web service to see if you are authorized",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized")}
	)
	public JsonPayload testAuthorization() {
		JsonPayload payload = new JsonPayload();
		payload.setMessage("authorization valid");
		return payload;
	}
	
	
	public JsonNode jsonReader(String jsonFile) throws JsonMappingException, JsonProcessingException {
		
		//Create ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		//Parse JSON file into a JsonNode
		JsonNode jsonNode = objectMapper.readTree(jsonFile);
		
		return jsonNode;
	}
	
	
	
	public List<String> createUniqueIdList (JsonNode uniqueIdNode){
		List<String> uniqueIdList = new ArrayList<String>();
		
		//Assign Unique List (example: sccfId(s) or hostClaimId(s))
		if (uniqueIdNode.isArray()) {
			
			
			for (int i = 0; i < uniqueIdNode.size(); i++) {
				uniqueIdList.add(uniqueIdNode.get(i).asText());
			}
			
		}
		return uniqueIdList;
	}
	
	
	
}
