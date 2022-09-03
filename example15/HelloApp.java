package org.zerock.controller.example15;

import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext gxac = new GenericXmlApplicationContext("org/zerock/controller/example15/applicationContext.xml");
		MessageBeanImpl mbi = (MessageBeanImpl)gxac.getBean("mbi");
		mbi.setGreeting("æ»≥Á«œººø‰!");
		FileOutputter fo = gxac.getBean(FileOutputter.class);
		fo.setFilePath("out15.txt");
		mbi.sayHello();
		gxac.close();
	}

}
