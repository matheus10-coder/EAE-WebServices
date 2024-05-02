package net.abcbs.eae.pojo;

public class RPAMDMDataServiceResponse {
	private String id;
	private String username;
	private String firstName;
	private String lastName;
	private String userType;
	private String orgTeam;
	private String licenseType;
	private String state;
	private int numberOfAssignments;
	private int numberOfCases;
	private int numberOfAssignmentsToday;
	private String onHold;
	
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
	public String getOnHold() {
		return onHold;
	}
	public void setOnHold(String onHold) {
		this.onHold = onHold;
	}
	@Override
	public String toString() {
		return "RPAMDMDataServiceResponse [id=" + id + ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", userType=" + userType + ", orgTeam=" + orgTeam + ", licenseType="
				+ licenseType + ", state=" + state + ", numberOfAssignments=" + numberOfAssignments + ", numberOfCases="
				+ numberOfCases + ", numberOfAssignmentsToday=" + numberOfAssignmentsToday + ", affiniteStatus=" + onHold + "]";
	}
}
