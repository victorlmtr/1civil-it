-- Création de la base de données : report_service_db
CREATE DATABASE report_service_db;

\c report_service_db;

------------------------------------------------------------
-- Table: reportType
------------------------------------------------------------
CREATE TABLE public.reporttype(
                                  typeid SERIAL NOT NULL,
                                  typename VARCHAR(100) NOT NULL,
                                  CONSTRAINT reporttype_pkey PRIMARY KEY (typeid)
) WITHOUT OIDS;

------------------------------------------------------------
-- Table: city_report
------------------------------------------------------------
CREATE TABLE public.city_report(
                                   city_report_id SERIAL NOT NULL,
                                   city_name VARCHAR(100) NOT NULL,
                                   postcode VARCHAR(5) NOT NULL,
                                   insee_code VARCHAR(5) NOT NULL,
                                   CONSTRAINT city_report_pkey PRIMARY KEY (city_report_id)
) WITHOUT OIDS;

------------------------------------------------------------
-- Table: location
------------------------------------------------------------
CREATE TABLE public.location(
                                locationid SERIAL NOT NULL,
                                latitude DECIMAL(38, 2) NOT NULL,
                                longitude DECIMAL(38, 2) NOT NULL,
                                address VARCHAR(2000),
                                city_report_id INT NOT NULL,
                                CONSTRAINT location_pkey PRIMARY KEY (locationid),
                                CONSTRAINT location_city_report_fk FOREIGN KEY (city_report_id) REFERENCES public.city_report(city_report_id)
) WITHOUT OIDS;

------------------------------------------------------------
-- Table: report
------------------------------------------------------------
CREATE TABLE public.report(
                              reportid SERIAL NOT NULL,
                              userid INT NOT NULL,
                              creationdate TIMESTAMP NOT NULL,
                              comment TEXT,
                              typeid INT NOT NULL,
                              locationid INT NOT NULL,
                              CONSTRAINT report_pkey PRIMARY KEY (reportid),
                              CONSTRAINT report_reporttype_fk FOREIGN KEY (typeid) REFERENCES public.reporttype(typeid),
                              CONSTRAINT report_location_fk FOREIGN KEY (locationid) REFERENCES public.location(locationid)
) WITHOUT OIDS;

------------------------------------------------------------
-- Table: picture
------------------------------------------------------------
CREATE TABLE public.picture(
                               pictureid SERIAL NOT NULL,
                               pictureurl VARCHAR(100) NOT NULL,
                               data TEXT,
                               reportid INT NOT NULL,
                               CONSTRAINT picture_pkey PRIMARY KEY (pictureid),
                               CONSTRAINT picture_report_fk FOREIGN KEY (reportid) REFERENCES public.report(reportid)
) WITHOUT OIDS;

------------------------------------------------------------
-- Insertion des données factices pour la table reportType
------------------------------------------------------------
INSERT INTO public.reporttype (typename) VALUES
                                             ('Stationnement interdit'),
                                             ('Dépôt sauvage d''ordures');

------------------------------------------------------------
-- Insertion des données factices pour la table city_report
------------------------------------------------------------
INSERT INTO public.city_report (city_name, postcode, insee_code) VALUES
                                                                     ('Paris', '75001', '75056'),
                                                                     ('Lyon', '69001', '69123'),
                                                                     ('Marseille', '13001', '13206'),
                                                                     ('Bordeaux', '33000', '33063'),
                                                                     ('Lille', '59000', '59350'),
                                                                     ('Toulouse', '31000', '31555'),
                                                                     ('Nice', '06000', '06088'),
                                                                     ('Nantes', '44000', '44109'),
                                                                     ('Strasbourg', '67000', '67482'),
                                                                     ('Montpellier', '34000', '34172');

------------------------------------------------------------
-- Insertion des données factices pour la table location
------------------------------------------------------------
INSERT INTO public.location (latitude, longitude, address, city_report_id) VALUES
                                                                               (48.86, 2.35, '10 Rue de Rivoli', 1),
                                                                               (45.76, 4.84, '25 Avenue Jean Jaurès', 2),
                                                                               (43.30, 5.37, '5 Rue de la République', 3),
                                                                               (44.84, -0.58, '15 Place de la Bourse', 4),
                                                                               (50.63, 3.06, '8 Place du Général de Gaulle', 5),
                                                                               (43.60, 1.44, '20 Boulevard Lazare Carnot', 6),
                                                                               (43.71, 7.26, '12 Promenade des Anglais', 7),
                                                                               (47.22, -1.55, '18 Rue de Strasbourg', 8),
                                                                               (48.57, 7.75, '3 Place Kléber', 9),
                                                                               (43.61, 3.88, '22 Avenue Georges Clemenceau', 10);

------------------------------------------------------------
-- Insertion des données factices pour la table report
------------------------------------------------------------
-- Insert data into the report table first
INSERT INTO public.report (userid, creationdate, comment, typeid, locationid) VALUES
                                                                                  (2, '2024-10-22 00:49:26', 'Voiture garée devant mon garage', 1, 1),
                                                                                  (2, '2024-10-23 00:49:26', 'La voiture est là depuis deux semaines', 1, 2),
                                                                                  (3, '2024-10-24 00:49:26', NULL, 1, 3);

------------------------------------------------------------
-- Insertion des données factices pour la table picture
------------------------------------------------------------
-- Insert into picture after report data is added
INSERT INTO public.picture (pictureurl, data, reportid) VALUES
                                                            ('https://images.victorl.xyz/stationnement1.jpg', NULL, 1),
                                                            ('https://images.victorl.xyz/stationnement2.jpg', NULL, 2),
                                                            ('https://images.victorl.xyz/stationnement3.jpg', NULL, 3);

------------------------------------------------------------
-- Réinitialisation des séquences
------------------------------------------------------------
SELECT pg_catalog.setval('public.city_report_city_report_id_seq', 10, true);
SELECT pg_catalog.setval('public.location_locationid_seq', 10, true);
SELECT pg_catalog.setval('public.picture_pictureid_seq', 6, true);
SELECT pg_catalog.setval('public.report_reportid_seq', 6, true);
SELECT pg_catalog.setval('public.reporttype_typeid_seq', 2, true);