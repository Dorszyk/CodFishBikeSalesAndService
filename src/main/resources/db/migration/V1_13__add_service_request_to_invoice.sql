ALTER TABLE invoice
ADD COLUMN bike_service_request_id INT,
ADD CONSTRAINT fk_invoice_service_request
    FOREIGN KEY (bike_service_request_id)
    REFERENCES bike_service_request (bike_service_request_id);

ALTER TABLE invoice ALTER COLUMN bike_to_buy_id DROP NOT NULL;
ALTER TABLE invoice ALTER COLUMN salesman_id DROP NOT NULL;
