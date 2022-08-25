package org.zerock.controller.example9;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloAppAutowired {
	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext(
				"org/zerock/controller/example9/applicationContext.xml");
		
		MessageMultiple mm = (MessageMultiple)gxac.getBean("mm");
		mm.print();
		gxac.close();
	}
	
}
