CREATE SEQUENCE Rechnungsnr INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Rechnung (Betrag, Datum, Rechnungsnr, Buchungsnr) 
        VALUES (190.00, '30.11.19', Rechnungsnr.nextVal, 100000000120);
        
commit;