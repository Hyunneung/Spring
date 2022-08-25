package org.zerock.controller.example8;

public class HelloAppJava {

	public static void main(String[] args) {
		MessageBean bean = new MessageBeanEn();
		MessageMultiple mm = new MessageMultiple();
		mm.setMessagebean(bean);
		mm.print();
	}
}
