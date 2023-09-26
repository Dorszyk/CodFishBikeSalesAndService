CREATE TABLE person_repairing
(
    person_repairing_id SERIAL      NOT NULL,
    name                VARCHAR(32) NOT NULL,
    surname             VARCHAR(32) NOT NULL,
    code_name_surname   VARCHAR(32) NOT NULL,
    PRIMARY KEY (person_repairing_id)
);