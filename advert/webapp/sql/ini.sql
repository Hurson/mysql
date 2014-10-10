CREATE TABLE TBL_USER  (
   ID              NUMBER                     	   NOT NULL,
   USERNAME        VARCHAR2(254)                   NOT NULL,
   PASSWD          VARCHAR2(254)                   NOT NULL,
   ADDRESS         VARCHAR2(254)                   NOT NULL,
   EMAIL		   VARCHAR2(254)                   NOT NULL,
   CONSTRAINT PK_USER PRIMARY KEY (ID)
);

create sequence TBL_USER_SEQ increment by 1 start with 1 nomaxvalue;

create or replace trigger TBL_USER_TRIG
  before insert on TBL_USER
  for each row
when (new.id is null)
declare
  -- local variables here
begin
  select TBL_USER_SEQ.nextval into:new.id from dual;
end TBL_USER_TRIG;
/