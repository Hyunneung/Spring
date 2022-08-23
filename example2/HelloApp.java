package org.zerock.controller.example2;

public class HelloApp {
	public static void main(String[] args) {
		MessageBean bean = new MessageBeanKo();
		bean.sayHello("ȫ�浿");
		
		MessageBean bean1 = new MessageBeanEn();
		bean1.sayHello("Hong gil-dong");
	}
}
