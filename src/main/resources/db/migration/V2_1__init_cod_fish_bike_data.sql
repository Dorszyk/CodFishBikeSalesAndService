insert into SALESMAN (name, surname, code_name_surname)
values ('Piotr', 'Korba', 'SAL99436PK'),
       ('Marcin', 'Siodełko', 'SAL99437MS'),
       ('Tomasz', 'Opona', 'SAL99438TO'),
       ('Rafał', 'Przerzutka', 'SAL99439RP');

insert into PERSON_REPAIRING (name, surname, code_name_surname)
values ('Adam', 'Regeneracja', 'REP17123AR'),
       ('Jakub', 'Kontrola', 'REP17124JK'),
       ('Sebastian ', 'Eliminacja', 'REP17125SE'),
       ('Szymon ', 'Poprawa', 'REP17126SP');

insert into BIKE_TO_BUY (category, subcategory, serial, brand, model, production_year, color, price)
values ('Rower E-Bike', 'MTB', 'CUBE633101', 'Cube', 'REACTION HYBRID PERFORMANCE 500', '2023', 'Black', '13699.00'),
       ('Rower E-Bike', 'MTB', 'CUBE633111', 'Cube ', 'REACTION HYBRID PERFORMANCE 500', '2023', 'Metallic Brown',
        '13699.00'),
       ('Rower E-Bike', 'MTB', 'CUBE634222', 'Cube ', 'REACTION HYBRID RACE 625 EASY ENTRY', '2023', 'Switch Blue',
        '19259.00'),
       ('Rower Mtb', 'MTB SPORT', '9030202422', 'Unibike', 'EXPERT 29', '2023', 'Red', '6899.00'),
       ('Rower Mtb', 'MTB SPORT', '9030095022', 'Unibike', 'LINK 29', '2023', 'Blue', '4199.00'),
       ('Rower Mtb', 'MTB SPORT', '9030093022', 'Unibike', 'EVO 29', '2023', 'Yellow', '5399.00'),
       ('Rower Szosowy', 'Racing', 'Model 5282901', 'Trek', 'Madone SLR 9 AXS Gen 7', '2023', 'Blue', '81699.00'),
       ('Rower Szosowy', 'Racing', 'Model 5304010', 'Trek', 'Madone SLR 7 AXS Gen 7', '2023', 'Red', '54499.00'),
       ('Rower Szosowy', 'Racing', 'Model 5280930', 'Trek', 'Domane SLR 6 Gen 4', '2023', 'Crystal White', '38999.00'),
       ('Rower Trekkingowy', 'Freeway', 'PR-MD0107', 'Merida ', 'FREEWAY 9700 LADY DISC', '2023', 'Gray', '4899.00'),
       ('Rower Trekkingowy', 'Freeway', 'PR-MD0071', 'Merida ', 'FREEWAY 9300 MAN DISC', '2023', 'Gray', '3299.00'),
       ('Rower Trekkingowy', 'Freeway', 'PR-MD0065', 'Merida ', 'FREEWAY 9300 LADY DISC', '2023', 'White-Pink',
        '3299.00'),
       ('Rower Miejski', 'City', 'UA#42949802', 'Author', 'Gloria', '2023', 'Green', '3399.00'),
       ('Rower Miejski', 'City', 'UA#42928202', 'Author', 'Gloria', '2020', 'Beige', '2499.00'),
       ('Rower Miejski', 'City', 'UA#42949902', 'Author', 'SIMPLEX', '2023', 'Blue', '3699.00'),

       ('Rower E-Bike', 'Urban', 'KB_71669', 'Kellys', 'ESTIMA 40 SH Black 504Wh', '2023', 'Black', '10999.00'),
       ('Rower E-Bike', 'Tour', 'KB_71549', 'Kellys', 'E-CARSON 90 P ATB 725Wh', '2023', 'Gray', '18999.00'),
       ('Rower E-Bike', 'Junior', 'KB_76218', 'Kellys', 'eMARC SH 24" 418Wh', '2023', 'Yellow', '8499.00');

insert into SERVICE (service_code, description, price)
values ('CENT-KOL-001', 'Centrowanie koła', '50.00'),
       ('REG-LUZ-002', 'Regulacja przerzutki', '30.00'),
       ('WYM-OKL-003', 'Wymiana pary okładzin hamulcowych', '20.00'),
       ('WYM-OPO-004', 'Wymiana opony', '30'),
       ('PAK-POD-005', 'Pakiet przeglądowy podstawowy', '150.00'),
       ('PAK-KOM-006', 'Pakiet kompleksowy roweru', '300.00'),
       ('REG-HAM-007', 'Regulacja hamulców', '30.00'),
       ('WYM-SZP-008', 'Wymiana szprychy w kole', '80.00'),
       ('WYM-KOL-009', 'Wymiana koła przedniego lub tylnego', '50.00'),
       ('WYM-PLY-010', 'Wymiana płynu hamulcowego ', '60.00'),
       ('WYM-PED-011', 'Wymiana dwóch pedałów ', '10.00'),
       ('WYM-RAM-012', 'Wymiana ramy', '350.00'),
       ('WYM-OSW-013', 'Wymiana oświetlenia', '50.00'),
       ('MON-AKC-014', 'Montaż akcesoriów', '10.00');


insert into PART (serial_number, description, price)
values ('Prz-tyl-slx', 'Przerzutka tylna SLX', '239.99'),
       ('Prz-prz-slx', 'Przerzutka przednia SLX', '118.99'),
       ('Kas-zeb-slx', 'Kaseta zębatek MTB SLX', '589.99'),
       ('Mec-kor-mtb', 'Mechanizm korbowy MTB Carbon', '1609.99'),
       ('Lan-mtb-slx', 'Łańcuch MTB SLX', '90.19'),
       ('Zas-ham-slx', 'Zacisk hamulca tarczowego SLX', '299.99'),
       ('Tar-ham-shi', 'Tarcza hamulcowa SHIMANO CENTER', '111.99'),
       ('Pia-prz-slx', 'Piasta przednia SLX', '318.99'),
       ('Kol-prz-shi', 'Koło przednie', '164.99'),
       ('Kol-tyl-shi', 'Koło tylne', '143.99'),
       ('Dwu-ped-spd', 'Dwustronne pedały SPD', '178.99'),
       ('Ply-ham-shi', 'Płyn hamulcowy', '14.99'),
       ('Osw-prz-shi', 'Oświetlenie przód', '150.99');
