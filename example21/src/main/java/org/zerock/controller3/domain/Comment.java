package org.zerock.controller3.domain;

public class Comment {
	private int num; 		  // ��� ��ȣ
	private String id; 		  // ��� �� ȸ��id
	private String content;   // ��� ����
	private String reg_date;  // ��� �ۼ� ��¥ - 2022-09-01 16:08:35
	private int board_num;    // ��� �� �Խñ�
	
	
	// getter, setter
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	
	
}
