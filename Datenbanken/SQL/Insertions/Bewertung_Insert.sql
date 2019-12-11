CREATE SEQUENCE BewertungsID INCREMENT BY 1 START WITH 100000000000;

INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (5, 'Eine sehr sch�ne und saubere Wohnung. Ich komme gerne wieder.', '01.12.19', BewertungsID.nextVal, 100000000141);
INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (4, 'Für den Preis vollkommen in Ordnung!', '02.12.19', BewertungsID.nextVal, 100000000140);
INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (3, 'Not great, not bad.', '01.12.19', BewertungsID.nextVal, 100000000143);
INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (2, 'IST OK.', '01.12.19', BewertungsID.nextVal, 100000000144);
INSERT INTO Bewertung (Sterne, Kommentar, Datum, BewertungsID, Buchungsnr) 
        VALUES (1, 'Drecks Wohung!!!', '11.5.20', BewertungsID.nextVal, 100000000145);
        
commit;