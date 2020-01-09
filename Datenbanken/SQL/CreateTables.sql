create table Land(
    Name VARCHAR2(74) NOT NULL,
    CONSTRAINT pk_Land PRIMARY KEY(Name));

create table Ausstattung(
    Name VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_Ausstattung PRIMARY KEY(Name));
    
create table Adresse(
    PLZ VARCHAR2(10) NOT NULL,
    Ort VARCHAR2(50) NOT NULL,
    Stra�e VARCHAR2(50) NOT NULL,
    Hausnummer VARCHAR2(4) NOT NULL,
    AdressID CHAR(9) NOT NULL check (REGEXP_LIKE ( AdressID, '^[0-9]{9}$' )), 
    LandName VARCHAR2(74) NOT NULL,
    CONSTRAINT pk_Adresse PRIMARY KEY(AdressID),
    CONSTRAINT fk_Adresse FOREIGN KEY(LandName)
                REFERENCES Land(Name));    

create table Ferienwohnung(
    AnzahlZimmer Integer NOT NULL check (AnzahlZimmer > 0),
    Groesse Number(6,2) NOT NULL check (Groesse > 0),
    Bild VARCHAR2(200),
    Name VARCHAR2(30) NOT NULL,
    Preis Number(7,2) NOT NULL check (Preis > 0),
    WohnungsID CHAR(6) NOT NULL check (REGEXP_LIKE ( WohnungsID, '^[0-9]{6}$' )),
    AdressID CHAR(9) NOT NULL check (REGEXP_LIKE ( AdressID, '^[0-9]{9}$' )),
    CONSTRAINT pk_Ferienwohnung PRIMARY KEY(WohnungsID),
    CONSTRAINT fk_FerienWohnung FOREIGN KEY(AdressID)
                REFERENCES Adresse(AdressID));

create table FW_hat_AUS(
    AusstattungsName VARCHAR2(30) NOT NULL,
    WohnungsID CHAR(6) NOT NULL check (REGEXP_LIKE ( WohnungsID, '^[0-9]{6}$' )),
    CONSTRAINT fk1_FW_hat_AUS FOREIGN KEY(AusstattungsName)
                REFERENCES Ausstattung(Name)
                ON DELETE CASCADE,
    CONSTRAINT fk2_FW_hat_AUS FOREIGN KEY(WohnungsID)
                REFERENCES Ferienwohnung(WohnungsID)
                ON DELETE CASCADE,
    CONSTRAINT pk_FW_hat_AUS PRIMARY KEY(AusstattungsName, WohnungsID));

create table TouriAttr(
    Beschreibung VARCHAR2(500) NOT NULL,
    Name VARCHAR2(30) NOT NULL,
    TouriAttrID Char(9) NOT NULL check (REGEXP_LIKE ( TouriAttrID, '^[0-9]{9}$' )),
    CONSTRAINT pk_TouriAttr PRIMARY KEY(TouriAttrID));

create table liegtImUmkreis(
    Entfernung Number(4,1) NOT NULL check (Entfernung > 0),
    WohnungsID CHAR(6) NOT NULL check (REGEXP_LIKE ( WohnungsID, '^[0-9]{6}$' )),
    TouriAttrID CHAR(9) NOT NULL check (REGEXP_LIKE ( TouriAttrID, '^[0-9]{9}$' )),
    CONSTRAINT fk1_liegtImUmkreis FOREIGN KEY(WohnungsID)
                REFERENCES Ferienwohnung(WohnungsID)
                ON DELETE CASCADE,
    CONSTRAINT fk2_liegtImUmkreis FOREIGN KEY(TouriAttrID)
                REFERENCES TouriAttr(TouriAttrID)
                ON DELETE CASCADE,
    CONSTRAINT pk_liegtImUmkreis PRIMARY KEY(WohnungsID, TouriAttrID));

create table Kunde(
    Name VARCHAR2(30) NOT NULL,
    IBAN VARCHAR2(32) NOT NULL,
    Newsletter CHAR(1) NOT NULL check (Newsletter in ( 'Y', 'N' )),
    Mailadresse VARCHAR2(345) NOT NULL UNIQUE check (REGEXP_LIKE ( Mailadresse, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' )),
    Passwort VARCHAR2(30) NOT NULL check (REGEXP_LIKE ( Passwort, '^.{6,30}$' )),
    KundenID CHAR(9) NOT NULL check (REGEXP_LIKE ( KundenID, '^[0-9]{9}$' )),
    AdressID CHAR(9) NOT NULL check (REGEXP_LIKE ( AdressID, '^[0-9]{9}$' )), 
    CONSTRAINT pk_Kunde PRIMARY KEY(KundenID),
    CONSTRAINT fk_Kunde FOREIGN KEY(AdressID)
                REFERENCES Adresse(AdressID));

create table Buchung(
    Datum DATE NOT NULL,
    Anfangsdatum DATE NOT NULL,
    Enddatum DATE NOT NULL,
    Buchungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Buchungsnr, '^[0-9]{12}$' )),
    KundenID CHAR(9) NOT NULL check (REGEXP_LIKE ( KundenID, '^[0-9]{9}$' )),
    WohnungsID CHAR(6) NOT NULL check (REGEXP_LIKE ( WohnungsID, '^[0-9]{6}$' )),
    CONSTRAINT pk_Buchung PRIMARY KEY(Buchungsnr),
    CONSTRAINT fk1_Buchung FOREIGN KEY(KundenID)
                REFERENCES Kunde(KundenID),
    CONSTRAINT fk2_Buchung FOREIGN KEY(WohnungsID)
                REFERENCES Ferienwohnung(WohnungsID));
    

create table Rechnung(
    Betrag Number(7,2) NOT NULL check (Betrag > 0),
    Datum DATE NOT NULL,
    Rechnungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Rechnungsnr, '^[0-9]{12}$' )),
    Buchungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Buchungsnr, '^[0-9]{12}$' )),
    CONSTRAINT pk_Rechnung PRIMARY KEY(Rechnungsnr),
    CONSTRAINT fk_Rechnung FOREIGN KEY(Buchungsnr)
                REFERENCES Buchung(Buchungsnr));

create table Anzahlung(
    Betrag Number(7,2) NOT NULL check (Betrag > 0),
    Datum DATE NOT NULL,
    AnzahlungsID CHAR(12) NOT NULL check (REGEXP_LIKE ( AnzahlungsID, '^[0-9]{12}$' )),
    Rechnungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Rechnungsnr, '^[0-9]{12}$' )),
    CONSTRAINT pk_Anzahlung PRIMARY KEY(AnzahlungsID),
    CONSTRAINT fk_Anzahlung FOREIGN KEY(Rechnungsnr)
                REFERENCES Rechnung(Rechnungsnr));

create table Bewertung(
    Sterne Integer NOT NULL check ( 1 <= Sterne AND Sterne <= 5),
    Kommentar VARCHAR2(500),
    Datum DATE NOT NULL,
    BewertungsID CHAR(12) NOT NULL check (REGEXP_LIKE ( BewertungsID, '^[0-9]{12}$' )),
    Buchungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Buchungsnr, '^[0-9]{12}$' )),
    CONSTRAINT pk_Bewertung PRIMARY KEY(BewertungsID),
    CONSTRAINT fk_Bewertung FOREIGN KEY(Buchungsnr)
                REFERENCES Buchung(Buchungsnr));
                
CREATE TABLE STORNIERUNG(
    StornierungsDatum DATE NOT NULL,
    BuchungsDatum DATE NOT NULL,
    Anfangsdatum DATE NOT NULL,
    Enddatum DATE NOT NULL,
    Buchungsnr CHAR(12) NOT NULL check (REGEXP_LIKE ( Buchungsnr, '^[0-9]{12}$' )),
    KundenID CHAR(9) NOT NULL check (REGEXP_LIKE ( KundenID, '^[0-9]{9}$' )),
    WohnungsID CHAR(6) NOT NULL check (REGEXP_LIKE ( WohnungsID, '^[0-9]{6}$' )),
    CONSTRAINT pk_Stornierung PRIMARY KEY(Buchungsnr),
    CONSTRAINT fk1_Stornierung FOREIGN KEY(KundenID)
                REFERENCES Kunde(KundenID),
    CONSTRAINT fk2_Stornierung FOREIGN KEY(WohnungsID)
                REFERENCES Ferienwohnung(WohnungsID));


commit;








    

