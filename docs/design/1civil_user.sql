------------------------------------------------------------
-- Table: role
------------------------------------------------------------
CREATE TABLE public.role(
	roleId     SERIAL NOT NULL ,
	roleName   VARCHAR (50) NOT NULL  ,
	CONSTRAINT role_PK PRIMARY KEY (roleId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: users
------------------------------------------------------------
CREATE TABLE public.users(
	userId         SERIAL NOT NULL ,
	firstname      VARCHAR (50) NOT NULL ,
	lastname       VARCHAR (70) NOT NULL ,
	passwordHash   VARCHAR (255) NOT NULL ,
	creationDate   TIMESTAMP  NOT NULL ,
	email          VARCHAR (120) NOT NULL ,
	phoneNumber    VARCHAR (15) NOT NULL ,
	isVerified     BOOL  NOT NULL ,
	isEnabled      BOOL  NOT NULL ,
	addressId      INT   ,    -- Foreign key to the Address microservice (optional)
	roleId         INT  NOT NULL  ,
	CONSTRAINT users_PK PRIMARY KEY (userId),
	CONSTRAINT users_role_FK FOREIGN KEY (roleId) REFERENCES public.role(roleId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: pictureCard
------------------------------------------------------------
CREATE TABLE public.pictureCard(
	pictureCardId   SERIAL NOT NULL ,
	pictureUrl      VARCHAR (100) NOT NULL ,
	isValid         BOOL  NOT NULL ,
	userId          INT  NOT NULL  ,
	CONSTRAINT pictureCard_PK PRIMARY KEY (pictureCardId),
	CONSTRAINT pictureCard_users_FK FOREIGN KEY (userId) REFERENCES public.users(userId)
)WITHOUT OIDS;
