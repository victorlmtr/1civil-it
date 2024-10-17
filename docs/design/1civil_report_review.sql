------------------------------------------------------------
-- Table: reportStatus
------------------------------------------------------------
CREATE TABLE public.reportStatus(
	statusId     SERIAL NOT NULL ,
	statusName   VARCHAR (40) NOT NULL  ,
	CONSTRAINT reportStatus_PK PRIMARY KEY (statusId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: reportReview
------------------------------------------------------------
CREATE TABLE public.reportReview(
	reportReviewId    SERIAL NOT NULL ,
	reportId          INT  NOT NULL ,        -- Foreign key to the Report microservice
	enforcerId        INT  NOT NULL ,        -- Assumed to link to a user (admin or enforcer)
	reviewComment     VARCHAR (2000)   ,
	reviewTimestamp   TIMESTAMP  NOT NULL ,
	statusId          INT  NOT NULL  ,
	CONSTRAINT reportReview_PK PRIMARY KEY (reportReviewId),
	CONSTRAINT reportReview_reportStatus_FK FOREIGN KEY (statusId) REFERENCES public.reportStatus(statusId)
)WITHOUT OIDS;
