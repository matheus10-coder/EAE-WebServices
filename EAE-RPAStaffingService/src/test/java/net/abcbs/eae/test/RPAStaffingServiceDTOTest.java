package net.abcbs.eae.test;

import static org.junit.Assert.*;
import net.abcbs.eae.jaxrs.*;
import org.junit.Test;
import net.abcbs.rpa.dto.RefRulesDTO;
import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;

/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: RPAStaffingServiceDTOTest class used to provide and perform unit test on the core functions of the application
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/
public class RPAStaffingServiceDTOTest {
	
	/**
	 * IsSharedApplicationDataObject isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
	 * 
	 * JUnit test cases
	 * 
	 * Testing sccfID getter and setter
	 * Testing database connection and manager properties 
	 */
	
	
	/**SCCF ID*/
	/*@Test
	public void testSccfId() {
		RefRulesDTO sccfId = new RefRulesDTO();
		sccfId.setSccf("1234567SCCFTEST");
		String result = sccfId.getSccf();
		assertEquals("1234567SCCFTEST", result);
	}*/
	
	/**Patient First Name*/
	/*@Test
	public void testpatFirstName() {
		RefRulesDTO patFirstName = new RefRulesDTO();
		patFirstName.setPatFirstName("Adam");
		String result = patFirstName.getPatFirstName();
		assertEquals("Adam", result);
	}*/
	
	/**Patient Last Name*/
	/*@Test
	public void testpatLastName() {
		RefRulesDTO patLastName = new RefRulesDTO();
		patLastName.setPatFirstName("Smith");
		String result = patLastName.getPatFirstName();
		assertEquals("Smith", result);
	}*/
	
	
	/**Other Carrier*/
	/*@Test
	public void testotherCarrier() {
		RefRulesDTO otherCarrier = new RefRulesDTO();
		otherCarrier.setOtherCarrier("Y");
		String result = otherCarrier.getOtherCarrier();
		assertEquals("Y", result);
	}*/
	
	/**Patient Paid Amount
	@Test
	public void testpatPaidAmt() {
		double delta = 1e-15;
		RefRulesDTO paidAmt = new RefRulesDTO();
		paidAmt.setPatPaidAmt(22000.00);
		double result = paidAmt.getPatPaidAmt();
		assertEquals(22000.00, result, delta);
	}*/
	/**
	@Test
	public void testIsSharedAppPropDataSource() {
		String dataSource = isSharedApplicationDataObject.getDb2JndiName();
		assertNotNull(dataSource);
	}
	
	@Test
	public void testIsSharedAppPropDbScheme() {
		String scheme = isSharedApplicationDataObject.getDb2Schema();
		assertNotNull(scheme);
	}**/
	

}
