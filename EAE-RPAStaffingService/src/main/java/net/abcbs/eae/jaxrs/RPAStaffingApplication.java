package net.abcbs.eae.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/***********************************************************************************************************************************************************************
 * @Author ABCBS Resource
 * 
 * Description: RPAStaffingApplication class will be used as the application driver
 * 
 * Project: Staffing Service
 ***********************************************************************************************************************************************************************/
@ApplicationPath("resources")
public class RPAStaffingApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RPAStaffingResource.class);
		return classes;	
	}
}