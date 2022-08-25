package org.zerock.controller.example8;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {
	public static void main(String[] agrs) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext(
				"org/zerock/controller/example8/applicationContext.xml");
		
		MessageMultiple mm = (MessageMultiple)gxac.getBean("mm");
		mm.print();
		gxac.close();
	}
	
}
