create table member(
	id 		  	varchar2(12),
	password    varchar2(10),
	name	    varchar2(15), -- 한글 5글자까지 가능
	age		    number(2),
	gender	    varchar2(3),
	email		varchar2(30),
	PRIMARY KEY(id)
);

alter table member modify password varchar2(60);

select * from member;

delete from member 

ALTER TABLE member ADD auth VARCHAR(50) not null

-- admin을 관리자 계정으로 업데이트
update member
set AUTH = 'ROLE_ADMIN'
where id = 'admin'
