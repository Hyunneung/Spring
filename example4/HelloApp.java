package org.zerock.controller.example4;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example4/applicationContext.xml");
		
		MessageBean bean1 = (MessageBean)gxac.getBean("m1");
		bean1.sayHello("Spring");
		MessageBean bean2 = (MessageBean)gxac.getBean("m2");
		bean2.sayHello("½ºÇÁ¸µ");
		gxac.close();
	}
}
