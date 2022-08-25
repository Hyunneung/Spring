package org.zerock.controller.example7;

import java.util.Map;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MapHelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example7/MapApplicationContext.xml");
		MapBean bean = (MapBean)gxac.getBean("mapBean");
		Map<String, String> addressList = bean.getAddressList();
		
		for(String key : addressList.keySet()) {
			System.out.println(key + ":" + addressList.get(key));
		}
		gxac.close();
	}
}
