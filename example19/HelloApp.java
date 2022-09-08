package org.zerock.controller.example19;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example19/applicationContext.xml");
		MessageMultiple mm = (MessageMultiple)gxac.getBean("mm");
		mm.print();
		gxac.close();
	}

}
 
