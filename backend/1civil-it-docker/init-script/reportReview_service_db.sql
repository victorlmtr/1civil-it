-- Création de la base de données : reportReview_service_db
CREATE DATABASE reportReview_service_db;

\c reportreview_service_db;

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
	reportId          INT  NOT NULL ,
	enforcerId        INT  NOT NULL ,
	reviewComment     VARCHAR (2000)   ,
	reviewTimestamp   TIMESTAMP  NOT NULL ,
	statusId          INT  NOT NULL  ,
	CONSTRAINT reportReview_PK PRIMARY KEY (reportReviewId) ,
    CONSTRAINT reportReview_reportStatus_FK FOREIGN KEY (statusId) REFERENCES public.reportStatus(statusId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Insertion des données factices pour la table reportStatus
------------------------------------------------------------
INSERT INTO public.reportStatus (statusName) VALUES 
('En cours de traitement'),
('Rejeté'),
('Résolu');


------------------------------------------------------------
-- Insertion des données factices pour la table reportReview
------------------------------------------------------------
INSERT INTO public.reportReview (reportId, enforcerId, reviewComment, reviewTimestamp, statusId) VALUES 
(1, 1, 'Examen initial du rapport, en attente de recherches supplémentaires.', NOW(), 1),
(2, 1, 'Suivi : informations supplémentaires requises.', NOW(), 2),
(1, 1, 'Problème résolu. Rapport marqué comme fermé.', NOW(), 3),
(3, 1, 'Rapport examiné et clôturé par l’agent d’exécution.', NOW(), 3);