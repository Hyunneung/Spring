package org.zerock.controller.example6;

public class HelloAppJava {

	public static void main(String[] args) {
		MessageBeanImpl bean = new MessageBeanImpl("Spring");
		FileOutputter outputter = new FileOutputter();
		outputter.setFilePath("out.txt");
		bean.setGreeting("�ȳ��ϼ���!");
		bean.setOutputter(outputter);
		bean.sayHello();
	}
}
