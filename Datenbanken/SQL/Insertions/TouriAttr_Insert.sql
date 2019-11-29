CREATE SEQUENCE TouriAttrID INCREMENT BY 1 START WITH 100000000;

INSERT INTO TouriAttr (Beschreibung, Name, TouriAttrID) 
        VALUES ('Kino', 'CineStar Konstanz', TouriAttrID.nextVal);
        
commit;