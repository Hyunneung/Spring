package org.zerock.controller.example7;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ListHelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example7/ListApplicationContext.xml");
		ListBean bean = (ListBean)gxac.getBean("listBean");
		List<String> addressList = bean.getAddressList();
		for(String address : addressList) {
			System.out.println(address);
		}
		gxac.close();
	}
}
