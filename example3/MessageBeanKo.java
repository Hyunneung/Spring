package org.zerock.controller.example3;

public class MessageBeanKo implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("�ȳ��ϼ���!" + name);
	}
}
