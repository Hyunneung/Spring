create table member(
	id 		  	varchar2(12),
	password    varchar2(10),
	name	    varchar2(15), -- �ѱ� 5���ڱ��� ����
	age		    number(2),
	gender	    varchar2(3),
	email		varchar2(30),
	PRIMARY KEY(id)
);

alter table member modify password varchar2(60);

select * from member;

delete from member 

ALTER TABLE member ADD auth VARCHAR(50) not null

-- admin�� ������ �������� ������Ʈ
update member
set AUTH = 'ROLE_ADMIN'
where id = 'admin'
