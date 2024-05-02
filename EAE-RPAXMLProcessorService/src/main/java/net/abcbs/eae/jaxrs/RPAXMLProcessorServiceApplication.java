package net.abcbs.eae.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/***********************************************************************************************************************************************************************
 * @Author ABCBS Resource
 * 
 * Description: RPAXMLProcessorServiceApplication class will be used as the application driver
 * 
 * Project: Bluecard Host Adjustment 
 ***********************************************************************************************************************************************************************/
@ApplicationPath("resources")
public class RPAXMLProcessorServiceApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RPAXMLProcessorServiceResource.class);
		return classes;	
	}
}