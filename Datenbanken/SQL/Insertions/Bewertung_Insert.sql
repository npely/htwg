CREATE SEQUENCE BewertungsID INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (5, 'Eine sehr schöne und saubere Wohnung. Ich komme gerne wieder.', '01.12.19', BewertungsID.nextVal, 100000000001);
        
commit;