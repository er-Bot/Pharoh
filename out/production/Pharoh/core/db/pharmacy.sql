--------------------------------------------------------
--  File created - Saturday-January-30-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence CCL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."CCL_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence CCMD_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."CCMD_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence CLIENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."CLIENT_SEQ"  MINVALUE 1 MAXVALUE 10000000 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence CSL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."CSL_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence DOCT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."DOCT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence EMPL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."EMPL_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence MEDI_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."MEDI_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence MGRP_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."MGRP_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence PHARMACIE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."PHARMACIE_SEQ"  MINVALUE 1 MAXVALUE 1000000 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence SCL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."SCL_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence SCMD_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."SCMD_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence SUPPLIER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."SUPPLIER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Sequence USERS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ERBO"."USERS_SEQ"  MINVALUE 1 MAXVALUE 1000000 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  DDL for Table BELONG_TO
--------------------------------------------------------

  CREATE TABLE "ERBO"."BELONG_TO" 
   (	"MGRP_ID" NUMBER, 
	"MEDI_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CLIENT
--------------------------------------------------------

  CREATE TABLE "ERBO"."CLIENT" 
   (	"CLT_ID" NUMBER, 
	"CLT_FNAME" VARCHAR2(32 BYTE), 
	"CLT_LNAME" VARCHAR2(32 BYTE), 
	"CLT_ADDRESS" VARCHAR2(128 BYTE), 
	"CLT_TEL" VARCHAR2(16 BYTE), 
	"CLT_ORDER_DOCT" VARCHAR2(32 BYTE), 
	"CLT_ORDER_NUM" NUMBER, 
	"CLT_GENDER" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CLIENT_COMMAND
--------------------------------------------------------

  CREATE TABLE "ERBO"."CLIENT_COMMAND" 
   (	"CCMD_ID" NUMBER, 
	"CCMD_DATE" DATE, 
	"CCMD_TOTAL" NUMBER, 
	"PHAR_ID" NUMBER, 
	"CLT_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CLIENT_COMMAND_LINE
--------------------------------------------------------

  CREATE TABLE "ERBO"."CLIENT_COMMAND_LINE" 
   (	"CCL_ID" NUMBER, 
	"CCL_QTE" NUMBER, 
	"CCMD_ID" NUMBER, 
	"MEDI_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CREDENTIAL
--------------------------------------------------------

  CREATE TABLE "ERBO"."CREDENTIAL" 
   (	"USR_NAME" VARCHAR2(32 BYTE), 
	"USR_PASS" VARCHAR2(64 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table DOCTOR
--------------------------------------------------------

  CREATE TABLE "ERBO"."DOCTOR" 
   (	"DOCT_ID" NUMBER, 
	"DOCT_FNAME" VARCHAR2(32 BYTE), 
	"DOCT_LNAME" VARCHAR2(32 BYTE), 
	"DOCT_BDAY" DATE, 
	"DOCT_GENDER" VARCHAR2(8 BYTE), 
	"PHAR_ID" NUMBER, 
	"DOCT_EMAIL" VARCHAR2(64 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table EMPLOYEE
--------------------------------------------------------

  CREATE TABLE "ERBO"."EMPLOYEE" 
   (	"EMPL_ID" NUMBER, 
	"EMPL_FNAME" VARCHAR2(32 BYTE), 
	"EMPL_LNAME" VARCHAR2(32 BYTE), 
	"EMPL_BDAY" DATE, 
	"EMPL_GENDER" VARCHAR2(8 BYTE), 
	"EMPL_SALARY" NUMBER, 
	"EMPL_START" DATE, 
	"EMPL_END" DATE, 
	"PHAR_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table MEDICINE
--------------------------------------------------------

  CREATE TABLE "ERBO"."MEDICINE" 
   (	"MEDI_ID" NUMBER, 
	"MEDI_NAME" VARCHAR2(32 BYTE), 
	"MEDI_EXPIRE_DATE" DATE, 
	"MEDI_PU" NUMBER, 
	"MEDI_STOCK_QTE" NUMBER, 
	"MEDI_DESC" VARCHAR2(128 BYTE), 
	"MEDI_DCI" VARCHAR2(32 BYTE), 
	"MEDI_FORM" VARCHAR2(32 BYTE), 
	"MEDI_DOSE" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table MEDICINE_GROUP
--------------------------------------------------------

  CREATE TABLE "ERBO"."MEDICINE_GROUP" 
   (	"MGRP_ID" NUMBER, 
	"MGRP_NAME" VARCHAR2(32 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table PHARMACY
--------------------------------------------------------

  CREATE TABLE "ERBO"."PHARMACY" 
   (	"PHAR_ID" NUMBER, 
	"PHAR_NAME" VARCHAR2(32 BYTE), 
	"PHAR_ADDRESS" VARCHAR2(128 BYTE), 
	"PHAR_RS" VARCHAR2(32 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SUPPLIER
--------------------------------------------------------

  CREATE TABLE "ERBO"."SUPPLIER" 
   (	"SUPL_ID" NUMBER, 
	"SUPL_NAME" VARCHAR2(32 BYTE), 
	"SUPL_ADDRESS" VARCHAR2(128 BYTE), 
	"SUPL_TEL" VARCHAR2(16 BYTE), 
	"SUPL_EMAIL" VARCHAR2(32 BYTE), 
	"SUPL_RS" VARCHAR2(32 BYTE), 
	"SUPL_CIVIL_ID" VARCHAR2(32 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SUPPLIER_COMMAND
--------------------------------------------------------

  CREATE TABLE "ERBO"."SUPPLIER_COMMAND" 
   (	"SCMD_ID" NUMBER, 
	"SCMD_DATE" DATE, 
	"SCMD_TOTAL" NUMBER, 
	"PHAR_ID" NUMBER, 
	"SUPL_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SUPPLIER_COMMAND_LINE
--------------------------------------------------------

  CREATE TABLE "ERBO"."SUPPLIER_COMMAND_LINE" 
   (	"SCL_ID" NUMBER, 
	"SCL_QTE" NUMBER, 
	"SCL_PU" NUMBER, 
	"SCMD_ID" NUMBER, 
	"MEDI_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "ERBO"."USERS" 
   (	"USR_ID" NUMBER, 
	"USR_NAME" VARCHAR2(32 BYTE), 
	"USR_PASS" VARCHAR2(64 BYTE), 
	"USR_TYPE" VARCHAR2(20 BYTE) DEFAULT 'EMPLOYEE'
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into ERBO.BELONG_TO
SET DEFINE OFF;
REM INSERTING into ERBO.CLIENT
SET DEFINE OFF;
REM INSERTING into ERBO.CLIENT_COMMAND
SET DEFINE OFF;
REM INSERTING into ERBO.CLIENT_COMMAND_LINE
SET DEFINE OFF;
REM INSERTING into ERBO.CREDENTIAL
SET DEFINE OFF;
Insert into ERBO.CREDENTIAL (USR_NAME,USR_PASS) values ('jam','14561');
REM INSERTING into ERBO.DOCTOR
SET DEFINE OFF;
REM INSERTING into ERBO.EMPLOYEE
SET DEFINE OFF;
REM INSERTING into ERBO.MEDICINE
SET DEFINE OFF;
REM INSERTING into ERBO.MEDICINE_GROUP
SET DEFINE OFF;
REM INSERTING into ERBO.PHARMACY
SET DEFINE OFF;
Insert into ERBO.PHARMACY (PHAR_ID,PHAR_NAME,PHAR_ADDRESS,PHAR_RS) values (1,'ensias','ensais rabat','58008');
Insert into ERBO.PHARMACY (PHAR_ID,PHAR_NAME,PHAR_ADDRESS,PHAR_RS) values (22,'homa homa','lhoma 7da 3mi 3ziz','RC457');
REM INSERTING into ERBO.SUPPLIER
SET DEFINE OFF;
REM INSERTING into ERBO.SUPPLIER_COMMAND
SET DEFINE OFF;
REM INSERTING into ERBO.SUPPLIER_COMMAND_LINE
SET DEFINE OFF;
REM INSERTING into ERBO.USERS
SET DEFINE OFF;
Insert into ERBO.USERS (USR_ID,USR_NAME,USR_PASS,USR_TYPE) values (1,'jam','14561','ADMIN');
Insert into ERBO.USERS (USR_ID,USR_NAME,USR_PASS,USR_TYPE) values (2,'erbo','789','EMPLOYEE');
--------------------------------------------------------
--  DDL for Index CLIENT_COMMAND_LINE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."CLIENT_COMMAND_LINE_PK" ON "ERBO"."CLIENT_COMMAND_LINE" ("CCL_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index CLIENT_COMMAND_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."CLIENT_COMMAND_PK" ON "ERBO"."CLIENT_COMMAND" ("CCMD_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index CLIENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."CLIENT_PK" ON "ERBO"."CLIENT" ("CLT_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index DOCTOR_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."DOCTOR_PK" ON "ERBO"."DOCTOR" ("DOCT_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index EMPLOYEE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."EMPLOYEE_PK" ON "ERBO"."EMPLOYEE" ("EMPL_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index MEDICINE_GROUP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."MEDICINE_GROUP_PK" ON "ERBO"."MEDICINE_GROUP" ("MGRP_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index MEDICINE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."MEDICINE_PK" ON "ERBO"."MEDICINE" ("MEDI_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index MED_NAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."MED_NAME_UNIQUE" ON "ERBO"."MEDICINE" ("MEDI_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index MGRP_MED_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."MGRP_MED_PK" ON "ERBO"."BELONG_TO" ("MGRP_ID", "MEDI_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PHARMACIE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."PHARMACIE_PK" ON "ERBO"."PHARMACY" ("PHAR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SUPLIER_COMMAND_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."SUPLIER_COMMAND_PK" ON "ERBO"."SUPPLIER_COMMAND" ("SCMD_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SUPLIER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."SUPLIER_PK" ON "ERBO"."SUPPLIER" ("SUPL_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SUPPLIER_COMMAND_LINE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."SUPPLIER_COMMAND_LINE_PK" ON "ERBO"."SUPPLIER_COMMAND_LINE" ("SCL_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index USERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ERBO"."USERS_PK" ON "ERBO"."USERS" ("USR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Trigger CCL_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."CCL_ID_TRIG" 
   before insert on "ERBO"."CLIENT_COMMAND_LINE" 
   for each row 
begin  
   if inserting then 
      if :NEW."CCL_ID" is null then 
         select CCL_SEQ.nextval into :NEW."CCL_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."CCL_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CCMD_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."CCMD_ID_TRIG" 
   before insert on "ERBO"."CLIENT_COMMAND" 
   for each row 
begin  
   if inserting then 
      if :NEW."CCMD_ID" is null then 
         select CCMD_SEQ.nextval into :NEW."CCMD_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."CCMD_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CLT_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."CLT_ID_TRIG" 
   before insert on "ERBO"."CLIENT" 
   for each row 
begin  
   if inserting then 
      if :NEW."CLT_ID" is null then 
         select CLIENT_SEQ.nextval into :NEW."CLT_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."CLT_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DCTR_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."DCTR_ID_TRIG" 
   before insert on "ERBO"."DOCTOR" 
   for each row 
begin  
   if inserting then 
      if :NEW."DOCT_ID" is null then 
         select DOCT_SEQ.nextval into :NEW."DOCT_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."DCTR_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger EMPL_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."EMPL_ID_TRIG" 
   before insert on "ERBO"."EMPLOYEE" 
   for each row 
begin  
   if inserting then 
      if :NEW."EMPL_ID" is null then 
         select EMPL_SEQ.nextval into :NEW."EMPL_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."EMPL_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger MEDI_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."MEDI_ID_TRIG" 
   before insert on "ERBO"."MEDICINE" 
   for each row 
begin  
   if inserting then 
      if :NEW."MEDI_ID" is null then 
         select MEDI_SEQ.nextval into :NEW."MEDI_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."MEDI_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger MGRP_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."MGRP_ID_TRIG" 
   before insert on "ERBO"."MEDICINE_GROUP" 
   for each row 
begin  
   if inserting then 
      if :NEW."MGRP_ID" is null then 
         select MGRP_SEQ.nextval into :NEW."MGRP_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."MGRP_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger PHAR_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."PHAR_ID_TRIG" 
   before insert on "ERBO"."PHARMACY" 
   for each row 
begin  
   if inserting then 
      if :NEW."PHAR_ID" is null then 
         select PHARMACIE_SEQ.nextval into :NEW."PHAR_ID" from dual; 
      end if; 
   end if; 
end;
/
ALTER TRIGGER "ERBO"."PHAR_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger PHAR_ID_TRIGGER
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."PHAR_ID_TRIGGER" 
   before insert on "ERBO"."PHARMACY" 
   for each row 
begin  
   if inserting then 
      if :NEW."PHAR_ID" is null then 
         select PHARMACIE_SEQ.nextval into :NEW."PHAR_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."PHAR_ID_TRIGGER" ENABLE;
--------------------------------------------------------
--  DDL for Trigger SCL_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."SCL_ID_TRIG" 
   before insert on "ERBO"."SUPPLIER_COMMAND_LINE" 
   for each row 
begin  
   if inserting then 
      if :NEW."SCL_ID" is null then 
         select SCL_SEQ.nextval into :NEW."SCL_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."SCL_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger SCMD_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."SCMD_ID_TRIG" 
   before insert on "ERBO"."SUPPLIER_COMMAND" 
   for each row 
begin  
   if inserting then 
      if :NEW."SCMD_ID" is null then 
         select SCMD_SEQ.nextval into :NEW."SCMD_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."SCMD_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger SUPP_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."SUPP_ID_TRIG" 
   before insert on "ERBO"."SUPPLIER" 
   for each row 
begin  
   if inserting then 
      if :NEW."SUPL_ID" is null then 
         select SUPPLIER_SEQ.nextval into :NEW."SUPL_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."SUPP_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger USR_ID_SEQ
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE TRIGGER "ERBO"."USR_ID_SEQ" 
   before insert on "ERBO"."USERS" 
   for each row 
begin  
   if inserting then 
      if :NEW."USR_ID" is null then 
         select USERS_SEQ.nextval into :NEW."USR_ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "ERBO"."USR_ID_SEQ" ENABLE;
--------------------------------------------------------
--  Constraints for Table EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("EMPL_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("EMPL_FNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("EMPL_LNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" ADD CONSTRAINT "EMPLOYEE_PK" PRIMARY KEY ("EMPL_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("EMPL_SALARY" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("EMPL_START" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" MODIFY ("PHAR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."EMPLOYEE" ADD CONSTRAINT "EMPL_GNDR_CHECK" CHECK (empl_gender in ('MALE', 'FEMALE')) ENABLE;
--------------------------------------------------------
--  Constraints for Table DOCTOR
--------------------------------------------------------

  ALTER TABLE "ERBO"."DOCTOR" MODIFY ("DOCT_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."DOCTOR" MODIFY ("DOCT_FNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."DOCTOR" MODIFY ("DOCT_LNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."DOCTOR" MODIFY ("PHAR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."DOCTOR" ADD CONSTRAINT "DOCTOR_PK" PRIMARY KEY ("DOCT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."DOCTOR" MODIFY ("DOCT_GENDER" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."DOCTOR" ADD CONSTRAINT "GNDR_CHECK" CHECK (doct_gender in ('MALE', 'FEMALE')) ENABLE;
--------------------------------------------------------
--  Constraints for Table SUPPLIER_COMMAND
--------------------------------------------------------

  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" MODIFY ("SCMD_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" MODIFY ("SCMD_DATE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" MODIFY ("PHAR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" MODIFY ("SUPL_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" ADD CONSTRAINT "SUPLIER_COMMAND_PK" PRIMARY KEY ("SCMD_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SUPPLIER
--------------------------------------------------------

  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" ADD CONSTRAINT "SUPLIER_PK" PRIMARY KEY ("SUPL_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_NAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_TEL" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_RS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER" MODIFY ("SUPL_CIVIL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MEDICINE_GROUP
--------------------------------------------------------

  ALTER TABLE "ERBO"."MEDICINE_GROUP" MODIFY ("MGRP_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE_GROUP" ADD CONSTRAINT "MEDICINE_GROUP_PK" PRIMARY KEY ("MGRP_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."MEDICINE_GROUP" MODIFY ("MGRP_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLIENT_COMMAND_LINE
--------------------------------------------------------

  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" MODIFY ("CCL_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" MODIFY ("CCL_QTE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" MODIFY ("CCMD_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" MODIFY ("MEDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" ADD CONSTRAINT "CLIENT_COMMAND_LINE_PK" PRIMARY KEY ("CCL_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table CREDENTIAL
--------------------------------------------------------

  ALTER TABLE "ERBO"."CREDENTIAL" MODIFY ("USR_NAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CREDENTIAL" MODIFY ("USR_PASS" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLIENT
--------------------------------------------------------

  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_FNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_LNAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_ORDER_DOCT" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_ORDER_NUM" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" ADD CONSTRAINT "CLIENT_PK" PRIMARY KEY ("CLT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."CLIENT" MODIFY ("CLT_GENDER" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT" ADD CONSTRAINT "GENDER_CHECK" CHECK (clt_gender in ('MALE', 'FEMALE')) ENABLE;
--------------------------------------------------------
--  Constraints for Table BELONG_TO
--------------------------------------------------------

  ALTER TABLE "ERBO"."BELONG_TO" MODIFY ("MGRP_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."BELONG_TO" MODIFY ("MEDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."BELONG_TO" ADD CONSTRAINT "MGRP_MED_PK" PRIMARY KEY ("MGRP_ID", "MEDI_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table MEDICINE
--------------------------------------------------------

  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" ADD CONSTRAINT "MEDICINE_PK" PRIMARY KEY ("MEDI_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."MEDICINE" ADD CONSTRAINT "MED_NAME_UNIQUE" UNIQUE ("MEDI_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_NAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_EXPIRE_DATE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_PU" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_STOCK_QTE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_DCI" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_FORM" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."MEDICINE" MODIFY ("MEDI_DOSE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SUPPLIER_COMMAND_LINE
--------------------------------------------------------

  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" MODIFY ("SCL_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" ADD CONSTRAINT "SUPPLIER_COMMAND_LINE_PK" PRIMARY KEY ("SCL_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" MODIFY ("SCL_QTE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" MODIFY ("SCL_PU" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" MODIFY ("SCMD_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" MODIFY ("MEDI_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "ERBO"."USERS" MODIFY ("USR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."USERS" MODIFY ("USR_NAME" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."USERS" MODIFY ("USR_PASS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."USERS" ADD CONSTRAINT "USERS_PK" PRIMARY KEY ("USR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."USERS" ADD CONSTRAINT "USR_TYPE_CHK" CHECK (USR_TYPE IN ('EMPLOYEE', 'ADMIN')) ENABLE;
  ALTER TABLE "ERBO"."USERS" MODIFY ("USR_TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLIENT_COMMAND
--------------------------------------------------------

  ALTER TABLE "ERBO"."CLIENT_COMMAND" MODIFY ("CCMD_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND" MODIFY ("CCMD_DATE" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND" MODIFY ("PHAR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."CLIENT_COMMAND" ADD CONSTRAINT "CLIENT_COMMAND_PK" PRIMARY KEY ("CCMD_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table PHARMACY
--------------------------------------------------------

  ALTER TABLE "ERBO"."PHARMACY" MODIFY ("PHAR_ID" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."PHARMACY" MODIFY ("PHAR_ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."PHARMACY" MODIFY ("PHAR_RS" NOT NULL ENABLE);
  ALTER TABLE "ERBO"."PHARMACY" ADD CONSTRAINT "PHARMACIE_PK" PRIMARY KEY ("PHAR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ERBO"."PHARMACY" MODIFY ("PHAR_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table BELONG_TO
--------------------------------------------------------

  ALTER TABLE "ERBO"."BELONG_TO" ADD CONSTRAINT "MEDI_ID_KEY" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "ERBO"."MEDICINE" ("MEDI_ID") ENABLE;
  ALTER TABLE "ERBO"."BELONG_TO" ADD CONSTRAINT "MGRP_ID_FK" FOREIGN KEY ("MGRP_ID")
	  REFERENCES "ERBO"."MEDICINE_GROUP" ("MGRP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CLIENT_COMMAND
--------------------------------------------------------

  ALTER TABLE "ERBO"."CLIENT_COMMAND" ADD CONSTRAINT "PHAR_CCMD_FK" FOREIGN KEY ("PHAR_ID")
	  REFERENCES "ERBO"."PHARMACY" ("PHAR_ID") ENABLE;
  ALTER TABLE "ERBO"."CLIENT_COMMAND" ADD CONSTRAINT "CCMD_CLT_FK" FOREIGN KEY ("CLT_ID")
	  REFERENCES "ERBO"."CLIENT" ("CLT_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CLIENT_COMMAND_LINE
--------------------------------------------------------

  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" ADD CONSTRAINT "CCL_MEDI_ID" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "ERBO"."MEDICINE" ("MEDI_ID") ENABLE;
  ALTER TABLE "ERBO"."CLIENT_COMMAND_LINE" ADD CONSTRAINT "CCMD_CCL_FK" FOREIGN KEY ("CCMD_ID")
	  REFERENCES "ERBO"."CLIENT_COMMAND" ("CCMD_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DOCTOR
--------------------------------------------------------

  ALTER TABLE "ERBO"."DOCTOR" ADD CONSTRAINT "DOCT_PHAR_KEY" FOREIGN KEY ("PHAR_ID")
	  REFERENCES "ERBO"."PHARMACY" ("PHAR_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "ERBO"."EMPLOYEE" ADD CONSTRAINT "EMPL_PHAR_KEY" FOREIGN KEY ("PHAR_ID")
	  REFERENCES "ERBO"."PHARMACY" ("PHAR_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SUPPLIER_COMMAND
--------------------------------------------------------

  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" ADD CONSTRAINT "PHAR_SCMD_KEY" FOREIGN KEY ("PHAR_ID")
	  REFERENCES "ERBO"."PHARMACY" ("PHAR_ID") ENABLE;
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND" ADD CONSTRAINT "SUPL_CSMD_KEY" FOREIGN KEY ("SUPL_ID")
	  REFERENCES "ERBO"."SUPPLIER" ("SUPL_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SUPPLIER_COMMAND_LINE
--------------------------------------------------------

  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" ADD CONSTRAINT "SCMD_SCL_KEY" FOREIGN KEY ("SCMD_ID")
	  REFERENCES "ERBO"."SUPPLIER_COMMAND" ("SCMD_ID") ENABLE;
  ALTER TABLE "ERBO"."SUPPLIER_COMMAND_LINE" ADD CONSTRAINT "MEDI_SCL_KEY" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "ERBO"."MEDICINE" ("MEDI_ID") ENABLE;
