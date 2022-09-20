package org.zerock.controller3.domain;

public class Comment {
	private int num; 		  // ´ñ±Û ¹øÈ£
	private String id; 		  // ´ñ±Û ¾´ È¸¿øid
	private String content;   // ´ñ±Û ³»¿ë
	private String reg_date;  // ´ñ±Û ÀÛ¼º ³¯Â¥ - 2022-09-01 16:08:35
	private int board_num;    // ´ñ±Û ¾´ °Ô½Ã±Û
	
	
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
