CREATE TABLE service_person
(
    service_person_id       SERIAL       NOT NULL,
    hours                   INT          NOT NULL,
    comment                 VARCHAR(256) NOT NULL,
    bike_service_request_id INT          NOT NULL,
    person_repairing_id     INT          NOT NULL,
    service_id              INT          NOT NULL,
    PRIMARY KEY (service_person_id),
    CONSTRAINT fk_service_person_bike_service_request
        FOREIGN KEY (bike_service_request_id)
            REFERENCES bike_service_request (bike_service_request_id),
    CONSTRAINT fk_service_person_repairing_person_repairing
        FOREIGN KEY (person_repairing_id)
            REFERENCES person_repairing (person_repairing_id),
    CONSTRAINT fk_service_person_bike_service
        FOREIGN KEY (service_id)
            REFERENCES service (service_id)
);