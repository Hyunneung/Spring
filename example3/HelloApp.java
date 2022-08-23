package org.zerock.controller.example3;

public class HelloApp {
	public static void main(String[] args) {
		BeanFactory factory = new BeanFactory();
		MessageBean bean = (MessageBean)factory.getBean("MessageBeanEn");
		bean.sayHello("spring");
	}
}
