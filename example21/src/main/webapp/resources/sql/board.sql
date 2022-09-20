select *
from (select rownum rnum, b.*
	  from (select *
	  		from board
	 	 	order by BOARD_RE_REF desc, BOARD_RE_SEQ asc) b
	  where rownum = #{endrow}
	 )
where rnum = #{startrow} and rnum = #{endrow}	



select * from board;

-- board 테이블 생성
CREATE TABLE BOARD(
	BOARD_NUM		NUMBER,			-- 글 번호
	BOARD_NAME		VARCHAR2(30),	-- 작성자
	BOARD_PASS		VARCHAR2(30),	-- 비밀번호
	BOARD_SUBJECT	VARCHAR2(300),	-- 제목
	BOARD_CONTENT	VARCHAR2(4000), -- 내용
	BOARD_FILE		VARCHAR2(50),	-- 첨부될 파일명 (가공)
	BOARD_ORIGINAL	VARCHAR2(50),	-- 첨부될 파일명
	BOARD_RE_REF	NUMBER,			-- 답변 글 작성 시 참조되는 글의 번호
	BOARD_RE_LEV	NUMBER,			-- 답변 글의 깊이
	BOARD_RE_SEQ	NUMBER,			-- 답변 글의 순서
	BOARD_READCOUNT NUMBER,			-- 글의 조회수
	BOARD_DATE DATE,                -- 글의 작성 날짜
	PRIMARY KEY(BOARD_NUM)
);



delete from board;
delete board where board_num=12;



-- 원문글의 경우 BOARD_RE_REF 필드의 값이 될 쿼리
select nvl(max(board_num),0)+1 from board


-- board 테이블 조회
select * from board;
select * from board order by BOARD_DATE;
select * from board order by board_re_ref, board_num;
-- board 테이블 모든 데이터 삭제
delete from board;


-- ***** 게시판 테스트 위해 임시로 데이터 추가 *****
insert into board (BOARD_NUM, BOARD_SUBJECT, BOARD_NAME, BOARD_RE_REF) values(1, '처음', 'admin', 1);
insert into board (BOARD_NUM, BOARD_SUBJECT, BOARD_NAME, BOARD_RE_REF) values(2, '둘째', 'admin', 2);
insert into board (BOARD_NUM, BOARD_SUBJECT, BOARD_NAME, BOARD_RE_REF) values(3, '셋째', 'admin', 3);
insert into comm (num, id, comment_board_num) values (1, 'admin', 1);
insert into comm (num, id, comment_board_num) values (2, 'admin', 1);
insert into comm (num, id, comment_board_num) values (3, 'admin', 2);
insert into comm (num, id, comment_board_num) values (4, 'admin', 2);


-- ***** 사용할 sql 쿼리 *****
-- 1. comm 테이블에서 comment_board_num별 갯수를 구합니다.
select comment_board_num, count(*) CNT
from comm
group by comment_board_num;


-- 2. board와 조인합니다
select board_num, board_subject, cnt
from board join (select comment_board_num, count(*) cnt
			     from comm
				 group by comment_board_num)
on board_num = comment_board_num;	

			 
-- 문제점) 만약 board 테이블에는 글이 있지만 댓글이 없는 경우 조회가 되지 않습니다.
-- 3. outer join을 이용해서 board의 글이 모두 표시되고 cnt가 null인 경우 0으로 표시되도록 합니다.
select board_num, board_subject, nvl(cnt,0) cnt
from board left outer join (select comment_board_num, count(*) cnt
			     from comm
				 group by comment_board_num)
on board_num = comment_board_num
order by board_re_ref desc, board_re_seq asc;	


-- 4. 인라인뷰를 이용한 쿼리문 작성
select * 
from ( select rownum rnum, j.*
	   from ( select board_num, board_subject, nvl(cnt,0) cnt
			  from board left outer join (select comment_board_num, count(*) cnt
			  					   		  from comm
				 						  group by comment_board_num)
			  on board_num = comment_board_num
			  order by board_re_ref desc, board_re_seq asc) j
		where rownum <= 20 )
where rnum >= 11 and rnum <= 20;

-- 5.
select * 
from ( select rownum rnum, j.*
	   from ( select board_num, board_subject, nvl(cnt,0) cnt
			  from board left outer join (select comment_board_num, count(*) cnt
			  					   		  from comm
				 						  group by comment_board_num)
			  on board_num = comment_board_num
			  order by board_re_ref desc, board_re_seq asc) j
		where rownum <= 20 )
where rnum >= 1 and rnum <=10;


-- 데이터 삭제
delete from board where BOARD_NUM=0;

select * from board order by board_re_ref, board_num;
select * from comm;

