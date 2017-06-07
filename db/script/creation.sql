DROP TABLE IF EXISTS AUTHORITY;
DROP TABLE IF EXISTS AUTH_USER;
DROP TABLE IF EXISTS AUTH_USER_AUTHORITY;
DROP TABLE IF EXISTS CRON;
--=========================================================================================================
--============================================AUTHORITY====================================================
--=========================================================================================================
CREATE TABLE AUTHORITY(
  CODE 						            VARCHAR(50) 		PRIMARY KEY,
  DESCRIPTION 				        VARCHAR(200)		DEFAULT NULL
);
--=========================================================================================================
--============================================AUTH_USER====================================================
--=========================================================================================================
CREATE TABLE AUTH_USER(
  AUTH_USER_ID 				        BIGINT 				  PRIMARY KEY AUTO_INCREMENT,
  EMAIL 						          VARCHAR(200)		NOT NULL,
  FIRST_NAME 					        VARCHAR(200)		DEFAULT NULL,
  LAST_NAME 					        VARCHAR(200)		DEFAULT NULL,
  PASSWORD 					          VARCHAR(200)		NOT NULL,
  ACTIVE 						          BOOLEAN				  DEFAULT NULL,
  LAST_PASSWORD_RESET_DATE 	  TIMESTAMP			  NOT NULL,
);
CREATE UNIQUE INDEX IDX_AUTH_USER ON AUTH_USER(EMAIL);
--=========================================================================================================
--======================================AUTH_USER_AUTHORITY================================================
--=========================================================================================================
CREATE TABLE AUTH_USER_AUTHORITY(
  AUTH_USER 					        BIGINT				  NOT NULL,
  AUTHORITY 					        VARCHAR(50)			NOT NULL
);
CREATE UNIQUE INDEX IDX_AUTH_USER_AUTHORITY ON AUTH_USER_AUTHORITY (AUTH_USER, AUTHORITY);
--FK AUTH_USER_AUTHORITY.AUTH_USER -> AUTH_USER.AUTH_USER_ID
ALTER TABLE AUTH_USER_AUTHORITY
  ADD CONSTRAINT FK_AUTH_USER_AUTHORITY_ON_AUTH_USER FOREIGN KEY (AUTH_USER) REFERENCES AUTH_USER (AUTH_USER_ID);
--FK AUTH_USER_AUTHORITY.AUTHORITY -> AUTHORITY.CODE
ALTER TABLE AUTH_USER_AUTHORITY
  ADD CONSTRAINT FK_AUTH_USER_AUTHORITY_ON_AUTHORITY FOREIGN KEY (AUTHORITY) REFERENCES AUTHORITY (CODE);
--=========================================================================================================
--==============================================CRON=======================================================
--=========================================================================================================
CREATE TABLE CRON(
  CRON_ID 					        BIGINT				  PRIMARY KEY,
  CRON 						          VARCHAR(50)			DEFAULT NULL,
  DESCRIPTION 				      VARCHAR(300)		DEFAULT NULL,
  AUTH_USER 					      BIGINT				  NOT NULL
);
--FK CRON.AUTH_USER -> AUTH_USER.AUTH_USER_ID
ALTER TABLE CRON
  ADD CONSTRAINT FK_CRON_ON_AUTH_USER FOREIGN KEY (AUTH_USER) REFERENCES AUTH_USER(AUTH_USER_ID);

INSERT INTO AUTHORITY VALUES(
  'ROLE_ADMIN', 'Администратор приложения'
);

INSERT INTO AUTH_USER VALUES(
  1, 'sasha361322@gmail.com', 'Александр', 'Шипилов', '$2y$10$rdO4GaqZ.K3I8JZRpM6I3.Xy7mxqk/KMljGpNnVhm55l80MD5aPCq', TRUE, PARSEDATETIME('01-01-2017','dd-MM-yyyy')
);

INSERT INTO AUTH_USER_AUTHORITY VALUES(
  1, 'ROLE_ADMIN'
);

INSERT INTO CRON VALUES(
  1, '* * * * * * *', 'Нерабочий крон', 1
);