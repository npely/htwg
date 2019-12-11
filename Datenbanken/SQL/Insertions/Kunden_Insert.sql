CREATE SEQUENCE KundeID INCREMENT BY 1 START WITH 100000050;

INSERT INTO Kunde (NAME, IBAN, NEWSLETTER, MAILADRESSE, PASSWORT, KUNDENID, ADRESSID)
            VALUES ('Torben Kleinkopf', 'DE324112393', 'N', 'torbenkopf@hotmail.de', '4213489fd', KundeID.nextVal, 100000023);
INSERT INTO Kunde (NAME, IBAN, NEWSLETTER, MAILADRESSE, PASSWORT, KUNDENID, ADRESSID)
            VALUES ('Carlos Rodrigez', 'SP5434112393', 'Y', 'carrod@hotmail.de', '4asfadsf489fd', KundeID.nextVal, 100000042);
INSERT INTO Kunde (NAME, IBAN, NEWSLETTER, MAILADRESSE, PASSWORT, KUNDENID, ADRESSID)
            VALUES ('Tortilus Maximus', 'SP431242393', 'N', 'tortmax@hotmail.de', 'asdf1234', KundeID.nextVal, 100000043);
INSERT INTO Kunde (NAME, IBAN, NEWSLETTER, MAILADRESSE, PASSWORT, KUNDENID, ADRESSID)
            VALUES ('Pablo Escobar', 'SP1234112393', 'N', 'pabesc@yahoo.de', 'abcdefghijkl', KundeID.nextVal, 100000040);
            
commit;