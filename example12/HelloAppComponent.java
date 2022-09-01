package org.zerock.controller.example12;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloAppComponent {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example12/applicationContext.xml");
		MessageMultiple mm = gxac.getBean(MessageMultiple.class);
		mm.print();
		gxac.close();
	}

}
