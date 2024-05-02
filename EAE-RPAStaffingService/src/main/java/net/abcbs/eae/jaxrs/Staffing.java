package net.abcbs.eae.jaxrs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Name: Staffing object 
 * Purpose: POJO class assigns the data for POST and PUT request
 * Annotations: Used to avoid null values and others
 * 
 * */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Staffing {
	private String orgRegionName;
	private String entityType;
	private String userType;
	private String userName;
	private String firstName;
	private String lastName;
	private int maxCases;
	private int maxDailyCases;
	private int maxOverallCases;
	private int maxAssignments;
	private String active;
	private Date createTs;
	private String createUserId;
	private Date updateTs;
	private String updateUserId;
	private String type;
	private int ruleId;
	private int rulePriority;
	private String ruleName;
	private String ruleDescription;
	private int maxPercent;
	private int maxMember;
	
	public Staffing () {
		//Default Constructor
	}
	
	//Assigned Constructor
	public Staffing(String orgRegionName,String entityType, String userType, String userName, String firstName, String lastName, int maxCases, 
			int maxDailyCases, int maxOverallCases, int maxAssignments, String active, Date createTs, String createUserId, Date updateTs, String updateUserId, String type,
			int ruleId, int rulePriority, String ruleName, String ruleDescription, int maxPercent, int maxMember) {
		
		super();
		this.orgRegionName = orgRegionName;
		this.entityType = entityType;
		this.userType = userType;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.maxCases = maxCases;
		this.maxDailyCases = maxOverallCases;
		this.maxAssignments = maxAssignments;
		this.active = active;
		this.createTs = createTs;
		this.createUserId = createUserId;
		this.updateTs = updateTs;
		this.updateUserId = updateUserId;
		this.type = type;
		this.ruleId = ruleId;
		this.rulePriority = rulePriority;
		this.ruleName = ruleName;
		this.ruleDescription = ruleDescription;
		this.maxPercent = maxPercent;
		this.maxMember = maxMember;
	}
	
	
	//Getters and setters 
	public String getOrgRegionName() {
		return orgRegionName;
	}

	public void setOrgRegionName(String orgRegionName) {
		this.orgRegionName = orgRegionName;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getMaxCases() {
		return maxCases;
	}

	public void setMaxCases(int maxCases) {
		this.maxCases = maxCases;
	}

	public int getMaxDailyCases() {
		return maxDailyCases;
	}

	public void setMaxDailyCases(int maxDailyCases) {
		this.maxDailyCases = maxDailyCases;
	}

	public int getMaxOverallCases() {
		return maxOverallCases;
	}

	public void setMaxOverallCases(int maxOverallCases) {
		this.maxOverallCases = maxOverallCases;
	}

	public int getMaxAssignments() {
		return maxAssignments;
	}

	public void setMaxAssignments(int maxAssignments) {
		this.maxAssignments = maxAssignments;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getRulePriority() {
		return rulePriority;
	}

	public void setRulePriority(int rulePriority) {
		this.rulePriority = rulePriority;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public int getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(int maxPercent) {
		this.maxPercent = maxPercent;
	}

	public int getMaxMember() {
		return maxMember;
	}

	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}

}
