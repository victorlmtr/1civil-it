-- ------------------------------------------------------------
-- Table: reportType
-- ------------------------------------------------------------

CREATE TABLE reportType (
    typeId SERIAL PRIMARY KEY,
    typeName VARCHAR(50) NOT NULL
);

-- ------------------------------------------------------------
-- Table: city_report
-- ------------------------------------------------------------

CREATE TABLE city_report (
    city_report_id SERIAL PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL,
    postcode VARCHAR(5) NOT NULL,
    insee_code VARCHAR(5) NOT NULL
);

-- ------------------------------------------------------------
-- Table: location
-- ------------------------------------------------------------

CREATE TABLE location (
    locationId SERIAL PRIMARY KEY,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    address VARCHAR(2000),
    city_report_id INT NOT NULL,
    CONSTRAINT location_city_report_FK FOREIGN KEY (city_report_id) REFERENCES city_report(city_report_id)
);

-- ------------------------------------------------------------
-- Table: report
-- ------------------------------------------------------------

CREATE TABLE report (
    reportId SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    comment TEXT NOT NULL,
    addressId INT,
    typeId INT NOT NULL,
    locationId INT NOT NULL,
    CONSTRAINT report_reportType_FK FOREIGN KEY (typeId) REFERENCES reportType(typeId),
    CONSTRAINT report_location0_FK FOREIGN KEY (locationId) REFERENCES location(locationId)
);

-- ------------------------------------------------------------
-- Table: picture
-- ------------------------------------------------------------

CREATE TABLE picture (
    pictureId SERIAL PRIMARY KEY,
    pictureUrl VARCHAR(100) NOT NULL,
    data TEXT,
    reportId INT NOT NULL,
    CONSTRAINT picture_report_FK FOREIGN KEY (reportId) REFERENCES report(reportId)
);
