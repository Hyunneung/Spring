package org.zerock.controller.example11;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloAppResource {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example11/applicationContext.xml");
		MessageMultiple mm = (MessageMultiple)gxac.getBean("mm");
		mm.print();
		gxac.close();
	}

}
