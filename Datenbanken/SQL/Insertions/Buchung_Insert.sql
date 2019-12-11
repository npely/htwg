CREATE SEQUENCE Buchungsnr INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('28.11.19', '1.12.19', '2.12.19', Buchungsnr.nextVal, 110000000, 100081);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('12.6.19', '14.6.19', '22.6.19', Buchungsnr.nextVal, 110000000, 100100);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('2.2.20', '5.3.20', '10.4.20', Buchungsnr.nextVal, 10000050, 100100);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('17.8.19', '20.8.19', '10.9.19', Buchungsnr.nextVal, 100000050, 100101);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('3.10.19', '5.10.19', '30.10.19', Buchungsnr.nextVal, 100000051, 100102);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('1.4.20', '10.4.20', '10.5.20', Buchungsnr.nextVal, 100000052, 100103);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('1.4.20', '10.4.20', '5.5.20', Buchungsnr.nextVal, 100000052, 100104);
INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('28.11.20', '1.12.20', '2.12.20', Buchungsnr.nextVal, 100000053, 100081);
        
commit;