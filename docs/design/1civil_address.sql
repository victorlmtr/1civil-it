------------------------------------------------------------
-- Table: address
------------------------------------------------------------
CREATE TABLE public.address(
	addressId   SERIAL NOT NULL ,
	latitude    DECIMAL (9, 6) NOT NULL ,  -- Adjusted precision for latitude
	longitude   DECIMAL (9, 6) NOT NULL ,  -- Adjusted precision for longitude
	address     VARCHAR (2000)  NOT NULL ,
	postcode    VARCHAR (5) NOT NULL  ,
	CONSTRAINT address_PK PRIMARY KEY (addressId)
)WITHOUT OIDS;
