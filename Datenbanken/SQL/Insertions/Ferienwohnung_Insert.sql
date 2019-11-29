CREATE SEQUENCE WohnungsID INCREMENT BY 1 START WITH 100000;

INSERT INTO Ferienwohnung (AnzahlZimmer, Groesse, Bild, Name, Preis, WohnungsID, AdressID) 
        VALUES ('3', 70.20, 'http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png', 'Seeblick', 130.70, WohnungsID.nextVal, 100000003);
INSERT INTO Ferienwohnung (AnzahlZimmer, Groesse, Bild, Name, Preis, WohnungsID, AdressID) 
        VALUES ('4', 90.80, 'http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png', 'Innsbruck City', 155.90, WohnungsID.nextVal, 100000004);
INSERT INTO Ferienwohnung (AnzahlZimmer, Groesse, Bild, Name, Preis, WohnungsID, AdressID) 
        VALUES ('5', 100.50, 'http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png', 'Boss Appartment', 190.00, WohnungsID.nextVal, 100000020);
INSERT INTO Ferienwohnung (AnzahlZimmer, Groesse, Bild, Name, Preis, WohnungsID, AdressID) 
        VALUES ('2', 49.70, 'http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png', 'Heimat', 79.99, WohnungsID.nextVal, 100000021);
INSERT INTO Ferienwohnung (AnzahlZimmer, Groesse, Bild, Name, Preis, WohnungsID, AdressID) 
        VALUES ('3', 65.90, 'http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png', 'Dubelkaff', 114.90, WohnungsID.nextVal, 100000022);

commit;

        