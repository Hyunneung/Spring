package org.zerock.controller.example16;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ReadMain {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example16/applicationContext.xml");
		ReadTest rt = gxac.getBean(ReadTest.class);
		rt.print();
		gxac.close();
	}

}
