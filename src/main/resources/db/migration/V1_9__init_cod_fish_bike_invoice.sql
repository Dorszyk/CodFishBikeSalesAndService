CREATE TABLE invoice
(
    invoice_id     SERIAL                   NOT NULL,
    invoice_number VARCHAR(64)              NOT NULL,
    date_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    bike_to_buy_id  INT                      NOT NULL,
    customer_id    INT                      NOT NULL,
    salesman_id    INT                      NOT NULL,
    PRIMARY KEY (invoice_id),
    UNIQUE (invoice_number),
    CONSTRAINT fk_invoice_bike
        FOREIGN KEY (bike_to_buy_id)
            REFERENCES bike_to_buy (bike_to_buy_id),
    CONSTRAINT fk_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_invoice_salesman
        FOREIGN KEY (salesman_id)
            REFERENCES salesman (salesman_id)
);