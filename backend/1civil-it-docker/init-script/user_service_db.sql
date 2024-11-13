-- Création de la base de données : user_service_db
CREATE DATABASE user_service_db;

\c user_service_db;

------------------------------------------------------------
-- Table: city
------------------------------------------------------------
CREATE TABLE public.city(
	cityId      SERIAL NOT NULL ,
	cityName    VARCHAR (100) NOT NULL ,
	inseeCode   VARCHAR (5) NOT NULL ,
	postCode    VARCHAR (5) NOT NULL  ,
	CONSTRAINT city_PK PRIMARY KEY (cityId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: role
------------------------------------------------------------
CREATE TABLE public.role(
	roleId     SERIAL NOT NULL ,
	roleName   VARCHAR (100) NOT NULL  ,
	CONSTRAINT role_PK PRIMARY KEY (roleId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: users
------------------------------------------------------------
CREATE TABLE public.users(
	userId         SERIAL NOT NULL ,
	firstName      VARCHAR (100) NOT NULL ,
	lastName       VARCHAR (100) NOT NULL ,
	passwordHash   VARCHAR (225) NOT NULL ,
	creationDate   TIMESTAMP  NOT NULL ,
	email          VARCHAR (120) NOT NULL ,
	phoneNumber    VARCHAR (10) NOT NULL ,
	isVerified     BOOL  NOT NULL ,
	isEnabled      BOOL  NOT NULL ,
	roleId         INT  NOT NULL ,
	cityId         INT    ,
	CONSTRAINT users_PK PRIMARY KEY (userId)

	,CONSTRAINT users_role_FK FOREIGN KEY (roleId) REFERENCES public.role(roleId)
	,CONSTRAINT users_city0_FK FOREIGN KEY (cityId) REFERENCES public.city(cityId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: address
------------------------------------------------------------
CREATE TABLE public.address(
	address_id      SERIAL NOT NULL ,
	streetNumber    VARCHAR (5) NOT NULL ,
	streetName      VARCHAR (100) NOT NULL ,
	adressDetails   VARCHAR (225) NOT NULL ,
	cityId          INT  NOT NULL ,
	userId          INT  NOT NULL  ,
	CONSTRAINT address_PK PRIMARY KEY (address_id)

	,CONSTRAINT address_city_FK FOREIGN KEY (cityId) REFERENCES public.city(cityId)
	,CONSTRAINT address_users0_FK FOREIGN KEY (userId) REFERENCES public.users(userId)
	,CONSTRAINT address_users_AK UNIQUE (userId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: pictureCard
------------------------------------------------------------
CREATE TABLE public.pictureCard(
	pictureCardId   SERIAL NOT NULL ,
	pictureUrl      VARCHAR (100) NOT NULL ,
	isValid         BOOL  NOT NULL ,
	userId          INT  NOT NULL  ,
	CONSTRAINT pictureCard_PK PRIMARY KEY (pictureCardId)

	,CONSTRAINT pictureCard_users_FK FOREIGN KEY (userId) REFERENCES public.users(userId)
)WITHOUT OIDS;


------------------------------------------------------------
-- Insertion des données factices pour la table city
------------------------------------------------------------
INSERT INTO public.city (cityName, inseeCode, postCode) VALUES 
('Paris', '75056', '75001'),
('Lyon', '69123', '69001'),
('Marseille', '13055', '13001');

------------------------------------------------------------
-- Insertion des données factices pour la table role
------------------------------------------------------------
INSERT INTO public.role (roleName) VALUES 
('Admin'),
('Citoyen'),
('SuperAdmin');

------------------------------------------------------------
-- Insertion des données factices pour la table users
------------------------------------------------------------
INSERT INTO public.users (firstName, lastName, passwordHash, creationDate, email, phoneNumber, isVerified, isEnabled, roleId, cityId) VALUES 
('John', 'Doe', 'hash_password_john', NOW(), 'john.doe@example.com', '0123456789', true, true, 1, 1),
('Jane', 'Smith', 'hash_password_jane', NOW(), 'jane.smith@example.com', '0987654321', true, true, 2, 2),
('Alice', 'Johnson', 'hash_password_alice', NOW(), 'alice.johnson@example.com', '0567891234', false, true, 2, 3);

------------------------------------------------------------
-- Insertion des données factices pour la table address
------------------------------------------------------------
INSERT INTO public.address (streetNumber, streetName, adressDetails, cityId, userId) VALUES 
('12', 'Rue de Rivoli', 'Apartment 34', 1, 1),
('45', 'Rue de la République', 'Building A', 2, 2),
('78', 'Avenue du Prado', 'Floor 2', 3, 3);

------------------------------------------------------------
-- Insertion des données factices pour la table pictureCard
------------------------------------------------------------
INSERT INTO public.pictureCard (pictureUrl, isValid, userId) VALUES 
('https://example.com/john_profile.jpg', true, 1),
('https://example.com/jane_profile.jpg', false, 2),
('https://example.com/alice_profile.jpg', true, 3);