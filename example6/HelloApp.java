package org.zerock.controller.example6;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example6/applicationContext.xml");
		MessageBean bean = (MessageBean)gxac.getBean("mbi");
		bean.sayHello();
		gxac.close();
	}
}
