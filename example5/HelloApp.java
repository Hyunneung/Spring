package org.zerock.controller.example5;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {
	public static void main(String args[]) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example5/applicationContext.xml");
		MessageBean bean = (MessageBean)gxac.getBean("m");
		bean.sayHello();
		gxac.close();
		
	}
	
}
