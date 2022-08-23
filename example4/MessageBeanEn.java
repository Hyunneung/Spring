package org.zerock.controller.example4;

public class MessageBeanEn implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello!" + name);
	}
	
	public MessageBeanEn() {
		System.out.println("MessageBeanEn »ý¼ºÀÚ");
	}
}
