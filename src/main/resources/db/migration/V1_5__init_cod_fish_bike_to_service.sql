CREATE TABLE bike_to_service
(
    bike_to_service_id SERIAL      NOT NULL,
    serial             VARCHAR(20) NOT NULL,
    brand              VARCHAR(32) NOT NULL,
    model              VARCHAR(32) NOT NULL,
    production_year    SMALLINT    NOT NULL,
    PRIMARY KEY (bike_to_service_id),
    UNIQUE (serial)
);