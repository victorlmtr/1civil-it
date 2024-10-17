------------------------------------------------------------
-- Table: reportType
------------------------------------------------------------
CREATE TABLE public.reportType(
	typeId     SERIAL NOT NULL ,
	typeName   VARCHAR (50) NOT NULL  ,
	CONSTRAINT reportType_PK PRIMARY KEY (typeId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: report
------------------------------------------------------------
CREATE TABLE public.report(
	reportId       SERIAL NOT NULL ,
	userId         INT  NOT NULL ,          -- Foreign key to the User microservice
	creationDate   TIMESTAMP  NOT NULL ,
	comment        VARCHAR (2000)  NOT NULL ,
	addressId      INT   ,                  -- Foreign key to the Address microservice (optional for report's location)
	typeId         INT  NOT NULL  ,
	CONSTRAINT report_PK PRIMARY KEY (reportId),
	CONSTRAINT report_reportType_FK FOREIGN KEY (typeId) REFERENCES public.reportType(typeId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: picture
------------------------------------------------------------
CREATE TABLE public.picture(
	pictureId    SERIAL NOT NULL ,
	pictureUrl   VARCHAR (100) NOT NULL ,
	data         VARCHAR (2000)   ,
	reportId     INT  NOT NULL  ,
	CONSTRAINT picture_PK PRIMARY KEY (pictureId),
	CONSTRAINT picture_report_FK FOREIGN KEY (reportId) REFERENCES public.report(reportId)
)WITHOUT OIDS;
