CREATE TABLE bike_service_request
(
    bike_service_request_id     SERIAL                   NOT NULL,
    bike_service_request_number VARCHAR(32)              NOT NULL,
    received_date_time          TIMESTAMP WITH TIME ZONE NOT NULL,
    completed_date_time         TIMESTAMP WITH TIME ZONE,
    customer_comment            TEXT,
    customer_id                 INT                      NOT NULL,
    bike_to_service_id          INT                      NOT NULL,
    PRIMARY KEY (bike_service_request_id),
    UNIQUE (bike_service_request_number),
    CONSTRAINT fk_bike_service_request_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_bike_service_request_bike
        FOREIGN KEY (bike_to_service_id)
            REFERENCES bike_to_service (bike_to_service_id)
);