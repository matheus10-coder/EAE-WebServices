package net.abcbs.eae.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AffiniteAssignment {
	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("Name")
	private String username;
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("EntityType")
	private String entityType;
	
	@JsonProperty("UserType")
	private String userType;
	
	@JsonProperty("OrgTeam")
	private String orgTeam;
	
	@JsonProperty("LicenseType")
	private String licenseType;
	
	@JsonProperty("State")
	private String state;
	
	@JsonProperty("County")
	private String county;
	
	@JsonProperty("CountOfAssignments")
	private int numberOfAssignments;
	
	@JsonProperty("CountOfCases")
	private int numberOfCases;
	
	@JsonProperty("CountOfAssignmentsToday")
	private int numberOfAssignmentsToday;
	
	@JsonProperty("IsNew")
	private boolean isNew;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getOrgTeam() {
		return orgTeam;
	}

	public void setOrgTeam(String orgTeam) {
		this.orgTeam = orgTeam;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public int getNumberOfAssignments() {
		return numberOfAssignments;
	}

	public void setNumberOfAssignments(int numberOfAssignments) {
		this.numberOfAssignments = numberOfAssignments;
	}

	public int getNumberOfCases() {
		return numberOfCases;
	}

	public void setNumberOfCases(int numberOfCases) {
		this.numberOfCases = numberOfCases;
	}

	public int getNumberOfAssignmentsToday() {
		return numberOfAssignmentsToday;
	}

	public void setNumberOfAssignmentsToday(int numberOfAssignmentsToday) {
		this.numberOfAssignmentsToday = numberOfAssignmentsToday;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	public String toString() {
		return "AffiniteAssignment [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", entityType=" + entityType + ", userType=" + userType + ", orgTeam=" + orgTeam + ", licenseType="
				+ licenseType + ", state=" + state + ", county=" + county + ", numberOfAssignments="
				+ numberOfAssignments + ", numberOfCases=" + numberOfCases + ", numberOfAssignmentsToday="
				+ numberOfAssignmentsToday + ", isNew=" + isNew + "]";
	}
	
}
