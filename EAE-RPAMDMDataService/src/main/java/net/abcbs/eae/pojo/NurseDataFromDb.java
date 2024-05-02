package net.abcbs.eae.pojo;

public class NurseDataFromDb {
	private String licenseType;
	private String state;
	private String firstName;
	private String lastName;
	
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

	@Override
	public String toString() {
		return "NurseDataFromDb [licenseType=" + licenseType + ", state=" + state + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
