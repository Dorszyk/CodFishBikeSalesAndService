CREATE TABLE bike_to_buy
(
    bike_to_buy_id  SERIAL         NOT NULL,
    category        VARCHAR(32)    NOT NULL,
    subcategory     VARCHAR(32)    NOT NULL,
    serial          VARCHAR(32)    NOT NULL,
    brand           VARCHAR(32)    NOT NULL,
    model           VARCHAR(96)    NOT NULL,
    production_year SMALLINT       NOT NULL,
    color           VARCHAR(32),
    price           NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (bike_to_buy_id),
    UNIQUE (serial)
);