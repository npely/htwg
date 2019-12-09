CREATE SEQUENCE Buchungsnr INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) 
        VALUES ('28.11.19', '29.11.19', '30.11.19', Buchungsnr.nextVal, 110000000, 100081);
        
commit;