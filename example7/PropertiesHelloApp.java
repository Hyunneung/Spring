package org.zerock.controller.example7;

import java.util.Properties;

import org.springframework.context.support.GenericXmlApplicationContext;

public class PropertiesHelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example7/PropertiesApplicationContext.xml");
		PropertiesBean bean = (PropertiesBean)gxac.getBean("propertiesBean");
		Properties addressList = bean.getAddressList();
		
		for(String key : addressList.stringPropertyNames()) {
			System.out.println(key + ":" + addressList.getProperty(key));
		}
		gxac.close();
	}
}
