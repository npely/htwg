CREATE SEQUENCE AnzahlungsID INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Anzahlung (Betrag, Datum, AnzahlungsID, Rechnungsnr) 
        VALUES (100.00, '01.12.19', AnzahlungsID.nextVal, 100000000060);
        
commit;