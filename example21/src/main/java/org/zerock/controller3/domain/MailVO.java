package org.zerock.controller3.domain;

public class MailVO {
	private String from = "bhn5050@naver.com"; // mail.properties에서 작성한 아이디에 해당하는 이메일 주소 작성
	private String to;
	private String subject = "회원가입을 축하드립니다."; // 제목
	private String content = "안녕하세요! 회원가입을 축하드립니다."; // 내용
	
	
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
