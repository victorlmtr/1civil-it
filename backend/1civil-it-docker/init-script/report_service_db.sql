-- Création de la base de données : report_service_db
CREATE DATABASE report_service_db;

\c report_service_db;

------------------------------------------------------------
-- Table: reportType
------------------------------------------------------------
CREATE TABLE public.reportType(
	typeId     SERIAL NOT NULL ,
	typeName   VARCHAR (50) NOT NULL  ,
	CONSTRAINT reportType_PK PRIMARY KEY (typeId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: cityReport
------------------------------------------------------------
CREATE TABLE public.cityReport(
	cityReportId   SERIAL NOT NULL ,
	cityName       VARCHAR (100) NOT NULL ,
	postCode       VARCHAR (5) NOT NULL ,
	inseeCode      VARCHAR (5) NOT NULL  ,
	CONSTRAINT cityReport_PK PRIMARY KEY (cityReportId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: localisation
------------------------------------------------------------
CREATE TABLE public.localisation(
	localisation_id   SERIAL NOT NULL ,
	latitude          DECIMAL (9, 6)  NOT NULL ,
	longitude         DECIMAL (9, 6)  NOT NULL ,
	address           VARCHAR (100) NOT NULL ,
	cityReportId      INT  NOT NULL  ,
	CONSTRAINT localisation_PK PRIMARY KEY (localisation_id) ,
    CONSTRAINT localisation_cityReport_FK FOREIGN KEY (cityReportId) REFERENCES public.cityReport(cityReportId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: report
------------------------------------------------------------
CREATE TABLE public.report(
	reportId          SERIAL NOT NULL ,
	userId            INT  NOT NULL ,
	creationDate      TIMESTAMP  NOT NULL ,
	comment           VARCHAR (2000)  NOT NULL ,
	typeId            INT  NOT NULL ,
	localisation_id   INT  NOT NULL  ,
	CONSTRAINT report_PK PRIMARY KEY (reportId) ,
    CONSTRAINT report_reportType_FK FOREIGN KEY (typeId) REFERENCES public.reportType(typeId) ,
    CONSTRAINT report_localisation0_FK FOREIGN KEY (localisation_id) REFERENCES public.localisation(localisation_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: picture
------------------------------------------------------------
CREATE TABLE public.picture(
	pictureId    SERIAL NOT NULL ,
	pictureUrl   VARCHAR (100) NOT NULL ,
	data         VARCHAR (2000)   ,
	reportId     INT  NOT NULL  ,
	CONSTRAINT picture_PK PRIMARY KEY (pictureId) ,
    CONSTRAINT picture_report_FK FOREIGN KEY (reportId) REFERENCES public.report(reportId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Insertion des données factices pour la table reportType
------------------------------------------------------------
INSERT INTO public.reportType (typeName) VALUES 
('Déchet sauvage'),
('Graffiti'),
('Dégradation de mobilier urbain'),
('Nuisance sonore'),
('Stationnement');


------------------------------------------------------------
-- Insertion des données factices pour la table cityReport
------------------------------------------------------------
INSERT INTO public.cityReport (cityName, postCode, inseeCode) VALUES 
('Paris', '75001', '75101'),
('Lyon', '69001', '69101'),
('Marseille', '13001', '13101'),
('Toulouse', '31000', '31555'),
('Roubaix', '59100', '59512');


------------------------------------------------------------
-- Insertion des données factices pour la table localisation
------------------------------------------------------------
INSERT INTO public.localisation (latitude, longitude, address, cityReportId) VALUES 
(48.8566, 2.3522, 'Rue de Rivoli', 1),
(45.7640, 4.8357, 'Rue de la République', 2),
(43.2965, 5.3698, 'Vieux-Port', 3),
(43.6047, 1.4442, 'Place du Capitole', 4),
(38.4567, 3.8536, 'Grand place', 5);


------------------------------------------------------------
-- Insertion des données factices pour la table report
------------------------------------------------------------
INSERT INTO public.report (userId, creationDate, comment, typeId, localisation_id) VALUES 
(1, NOW(), 'Déchets laissés dans le parc.', 1, 1),
(2, NOW(), 'Graffiti sur les murs de l\''immeuble.', 2, 2),
(3, NOW(), 'Banc public endommagé à côté de l\''arrêt de bus.', 3, 3),
(4, NOW(), 'Musique très forte à 2h du matin.', 4, 4),
(5, NOW(), 'Stationnement interdit et gênant', 5, 5);


------------------------------------------------------------
-- Insertion des données factices pour la table picture
------------------------------------------------------------
INSERT INTO public.picture (pictureUrl, data, reportId) VALUES 
('https://example.com/picture1.jpg', 'Image de déchets dans le parc.', 1),
('https://example.com/picture2.jpg', 'Photo de graffiti sur les murs.', 2),
('https://example.com/picture3.jpg', 'Photo du banc endommagé.', 3),
('https://example.com/picture4.jpg', 'Photo du lieu avec nuisance sonore.', 4),
('https://example.com/picture5.jpg', 'Photo du véhicule mal stationné', 5);