package org.zerock.controller.example7;

import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

public class SetHelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example7/SetApplicationContext.xml");
		SetBean bean = (SetBean)gxac.getBean("setBean");
		Set<String> addressList = bean.getAddressList();
		for(String address : addressList) {
			System.out.println(address);
		}
		gxac.close();
	}
}
