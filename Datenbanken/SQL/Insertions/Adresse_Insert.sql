CREATE SEQUENCE AdressID INCREMENT BY 1 START WITH 100000000; 
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Eisenbahnstra�e', '15', AdressID.nextVal, 'Deutschland');
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78476', 'Allensbach', 'Radolfzellerstra�e', '119', AdressID.nextVal, 'Deutschland');        
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78479', 'Reichenau', 'Fischergasse', '4', AdressID.nextVal, 'Deutschland');        
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('8593', 'Kesswil', 'Seetalstra�e', '2', AdressID.nextVal, 'Schweiz');        
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('6020', 'Innsbruck', 'Rudolf-Greinz-Stra�e', '1', AdressID.nextVal, 'Oesterreich');
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Bodanstra�e', '1', AdressID.nextVal, 'Deutschland'); 
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Am Z�hringerplatz', '10', AdressID.nextVal, 'Deutschland');   
INSERT INTO Adresse (PLZ, Ort, Stra�e, Hausnummer, AdressID, LandName) 
        VALUES ('78467', 'Konstanz', 'Seestra�e', '8', AdressID.nextVal, 'Deutschland');        
        
commit;        
        
        
        
        