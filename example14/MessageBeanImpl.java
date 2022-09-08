package org.zerock.controller.example14;

import java.io.IOException;

import javax.annotation.Resource;

public class MessageBeanImpl implements MessageBean {
	private String name;
	private String greeting;
	private Outputter outputter;
	
	// ������
	public MessageBeanImpl(String name) {
		this.name = name;
		System.out.println("MessageBeanImpl�� ������");
	}
	
	// �������̵�
	@Override
	public void sayHello() {
		String message = greeting + name + "!";
		System.out.println("MessageBeanImpl�� sayHello() - �������̵� : " + message);
		try {
			outputter.output(message);
		} catch(IOException ie) {
			ie.printStackTrace();
		}
	}
	
	// setter
	public void setGreeting(String greeting) {
		this.greeting = greeting;
		System.out.println("MessageBeanImpl�� setGreeting()");
	}
	
	@Resource
	public void setOutputter(Outputter outputter) {
		this.outputter = outputter;
		System.out.println("MessageBeanImpl�� setOutputter()");
	}
	
	
}