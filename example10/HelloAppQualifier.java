package org.zerock.controller.example10;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloAppQualifier {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example10/applicationContext.xml");
		MessageMultiple mm = (MessageMultiple)gxac.getBean("mm");
		mm.print();
		gxac.close();
	}

}
