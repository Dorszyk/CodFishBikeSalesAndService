ALTER TABLE salesman
    ADD COLUMN user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES codfish_bike_user(user_id);

ALTER TABLE person_repairing
    ADD COLUMN  user_id INT,
    ADD FOREIGN KEY (user_id) REFERENCES codfish_bike_user (user_id);

insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES (1,'piotr_korba','piotr.korba@codfishbike.com','$2a$12$RjvQfMhEUoFgaaxGdkN/7OcPT./K6lzmvqql4oOQdkLNcYpOntz4O',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES (2,'marcin_siodelko','marcin.siodelko@codfishbike.com','$2a$12$S84L7I.avv17PYSFIS.1Z.wNFHpHLFrf08yTCMKiAhqBr0nsakk3G',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES (3,'tomasz_opona','tomasz.opona@codfishbike.com','$2a$12$I9GqfAU4jk8KRPeu9YThkOsbpFlbtb7wvsj10MfYPJyybS7p7Wthm',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(4,'rafal_przerzutka','rafal.przerzutka@codfishbike.com','$2a$12$3GjswT7X4oZZj5DV8M.4XOPAYlhZERRzSmcwJPQg9mSCBB5utIdt2',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(5,'adam_regeneracja','adam.regeneracja@codfishbike.com','$2a$12$YwJqmE9D1yKmn4iLa8YVCexVRfIq80IDxsROJHIHPjwz9zh/8q6Ve',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(6,'jakub_kontrola','jakub.kontrola@codfishbike.com','$2a$12$bsm6SOSB/4WgFZUt/xVUrOpsEqM4GHVJpdCTziYVVM8jWNAGSk79i',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(7,'sebastian_eliminacja','sebastian.eliminacja@codfishbike.com','$2a$12$FypTJD09e8i18CmA5oSuoOwPDH1AOxpgJVzu48MUHSz2WeC.bP.0O',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(8,'szymon_poprawa','szymon.poprawa@codfishbike.com','$2a$12$JmsPNJDQvw6OGvNl8n6.ceXH3HGiWiiXgfUfKihNxW3utECLw74be',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(9,'user_1','user_1@codfishbike.com','$2a$12$MeEU6azjsFAshmxn4b.V8eVy1NhcWnYwZH95j.GLoabz7DSZCLmg.',true);
insert into codfish_bike_user (user_id, user_name, email, password, active) VALUES(10,'user_2','user_2@codfishbike.com','$2a$12$MeEU6azjsFAshmxn4b.V8eVy1NhcWnYwZH95j.GLoabz7DSZCLmg.',true);


UPDATE salesman SET user_id = 1 WHERE code_name_surname ='SAL99436PK';
UPDATE salesman SET user_id = 2 WHERE code_name_surname ='SAL99437MS';
UPDATE salesman SET user_id = 3 WHERE code_name_surname ='SAL99438TO';
UPDATE salesman SET user_id = 4 WHERE code_name_surname ='SAL99439RP';
UPDATE salesman SET user_id = 9 WHERE code_name_surname ='SAL99440US';

UPDATE person_repairing  SET user_id = 5 WHERE code_name_surname = 'REP17123AR';
UPDATE person_repairing  SET user_id = 6 WHERE code_name_surname = 'REP17124JK';
UPDATE person_repairing  SET user_id = 7 WHERE code_name_surname = 'REP17125SE';
UPDATE person_repairing  SET user_id = 8 WHERE code_name_surname = 'REP17126SP';
UPDATE person_repairing  SET user_id = 10 WHERE code_name_surname = 'REP17220US';

insert into codfish_bike_role(role_id, role) VALUES (1,'SALESMAN'), (2, 'PERSON_REPAIRING');

insert into codfish_bike_user_role (user_id, role_id) VALUES (1,1),(2,1),(3,1),(4,1),(9,1);
insert into codfish_bike_user_role (user_id, role_id) VALUES (5,2),(6,2),(7,2),(8,2),(10,2);

ALTER TABLE salesman
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE person_repairing
    ALTER COLUMN user_id SET NOT NULL;