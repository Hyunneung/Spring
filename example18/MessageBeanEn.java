package org.zerock.controller.example18;

public class MessageBeanEn implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello!" + name);
	}
	
	public MessageBeanEn() {
		System.out.println("MessageBeanEn »ý¼ºÀÚ");
	}
	
	public void destroyMethod() {
		System.out.println("MessageBeanEnÀÇ destroyMethod() ¸Þ¼Òµå");
	}
}
 
