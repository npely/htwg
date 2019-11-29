CREATE SEQUENCE AdressID INCREMENT BY 1 START WITH 100000000; 
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Eisenbahnstraﬂe', '15', AdressID.nextVal, 'Deutschland');
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78476', 'Allensbach', 'Radolfzellerstraﬂe', '119', AdressID.nextVal, 'Deutschland');        
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78479', 'Reichenau', 'Fischergasse', '4', AdressID.nextVal, 'Deutschland');        
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('8593', 'Kesswil', 'Seetalstraﬂe', '2', AdressID.nextVal, 'Schweiz');        
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('6020', 'Innsbruck', 'Rudolf-Greinz-Straﬂe', '1', AdressID.nextVal, 'Oesterreich');
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Bodanstraﬂe', '1', AdressID.nextVal, 'Deutschland'); 
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Am Z‰hringerplatz', '10', AdressID.nextVal, 'Deutschland');   
INSERT INTO Adresse (PLZ, Ort, Straﬂe, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Seestraﬂe', '8', AdressID.nextVal, 'Deutschland');        
        
commit;        
        
        
        
        