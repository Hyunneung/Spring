select * from tab
select * from comments;
delete from comments;

create table comments(
  num          number       primary key,
  id           varchar2(30) references member(id),
  content      varchar2(200),
  reg_date     date,
  board_num    number references board(board_num) 
               on delete cascade 
);

-- ½ÃÄö½º »ı¼º
create sequence com_seq;