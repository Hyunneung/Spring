package org.zerock.controller3.domain;

public class MailVO {
	private String from = "bhn5050@naver.com"; // mail.properties���� �ۼ��� ���̵� �ش��ϴ� �̸��� �ּ� �ۼ�
	private String to;
	private String subject = "ȸ�������� ���ϵ帳�ϴ�."; // ����
	private String content = "�ȳ��ϼ���! ȸ�������� ���ϵ帳�ϴ�."; // ����
	
	
	// getter, setter
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
