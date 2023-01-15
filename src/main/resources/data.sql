INSERT INTO car(id, license_plate, serial_number, make, type, horsepower, fuel_type, oil_type)
VALUES (1000, 'NK-418-G', '4247411', 'Mazda', 'RX-7', 310, 'Ongelood 98', '20W-50'),
       (1001, 'NZ-320-W', '1234567', 'Skoda', 'Octavia', 140, 'Ongelood 95', '5W-30'),
       (1002, 'YP-35-HK', '4623897', 'Toyota', 'Celica', 95, 'Ongelood 95', '10W-40'),
       (1003, 'SN-616-P', '9758613', 'Citroën', 'C1', 54, 'Ongelood 95', '0W-20'),
       (1004, 'DL-3B-NU', '1354975883', 'Mercedes', 'E300', 180, 'Diesel', '20W-50'),
       (1005, 'PV-44-LY', '465268', 'Porsche', 'Carrera 911 GT3RS', 556, 'Ongelood 98', '5W-30'),
       (1006, 'DT-69-AU', '67983154', 'Audi', 'A6', 326, 'Ongelood 98', '10W-40'),
       (1007, 'VW-272-D', '15684762', 'Volkswagen', 'Golf', 121, 'Ongelood 98', '0W-20'),
       (1008, 'P-689-RW', '918738135', 'Peugeot', '107', 42, 'Ongelood 95', '5W-30'),
       (1009, 'FHK-500-A', '5978631269', 'Fiat', '500', 28, 'Ongelood 95', '10W-40');

INSERT INTO storage(id, name, type, price)
VALUES (2000, 'Basic', 'Onverwarmd', 150),
       (2001, 'Basic', 'Onverwarmd', 150),
       (2002, 'Basic', 'Onverwarmd', 150),
       (2003, 'Basic+', 'Climate control', 225),
       (2004, 'Basic+', 'Climate control', 225),
       (2005, 'Basic+', 'Climate control', 225),
       (2006, 'Basic+', 'Climate control', 225),
       (2007, 'Premium', 'Climate control & Luchtcirculatie', 400),
       (2008, 'Premium', 'Climate control & Luchtcirculatie', 400),
       (2009, 'Premium', 'Climate control & Luchtcirculatie', 400);

INSERT INTO customer(id, name, address, date_of_birth, gender)
VALUES (3000, 'Stefan Stefansson', 'Monopolylaan 12, Amersfoort, 6421AK', '2000-11-11', 'M'),
       (3001, 'Stefanie Stef', 'Laan van Puntenburg 120, Utrecht, 4455BK', '1980-12-01', 'F'),
       (3002, 'Collin van Leeuw', 'Horizonstraat 27, Nuth, 7368CZ', '1995-02-19', 'M'),
       (3003, 'Swel Leeuws', 'Nolenlaan 634, Bussum, 6146HA', '1975-02-17', 'F'),
       (3004, 'Jan Döner', 'Wellerlaan 109, Heerle, 2896XZ', '1992-07-14', 'M'),
       (3005, 'Thirza Gaar', 'Humorstraat 1, Rotterdam, 9876JK', '1995-04-01', 'F'),
       (3006, 'Melvin Motzkussen', 'Monsigneur Nolstraat, Roermond, 8752VC', '1996-08-15', 'M'),
       (3007, 'Niek Willemijns', 'Jozefstraat 2, Nus, 3554AP', '1964-09-12', 'F'),
       (3008, 'Frans Misschien', 'Pastorijlaan 166, Neersekom, 6731DJ', '1994-06-05', 'M'),
       (3009, 'Lino Savels', 'Kampstraat 87, Stein, 6920HN', '1990-08-06', 'F');

INSERT INTO subscription(id, price, type)
VALUES (4000, 10, 'Druppellader'),
       (4001, 15, 'Ontvochtiger'),
       (4002, 20, 'Ventilator'),
       (4003, 45, 'Camera'),
       (4004, 50, 'Controle'),
       (4005, 55, 'Oliewissel');

INSERT INTO roles(rolename)
VALUES ('user'), ('admin');

