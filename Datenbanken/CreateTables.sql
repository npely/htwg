create table Land(
    Name VARCHAR2(74) NOT NULL,
    CONSTRAINT pk_Land PRIMARY KEY(Name)
    );

create table Ausstattung(
    Name VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_Ausstattung PRIMARY KEY(Name)
    );
    
create table Adresse(
    PLZ VARCHAR2(10) NOT NULL,
    Ort VARCHAR2(50) NOT NULL,
    Straße VARCHAR2(50) NOT NULL,
    Hausnummer VARCHAR2(4) NOT NULL,
    AdressID Char(9) NOT NULL, 
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
    WohnungsID Char(6) NOT NULL,
    AdressID Char(9) NOT NULL,
    CONSTRAINT pk_Ferienwohnung PRIMARY KEY(WohnungsID),
    CONSTRAINT fk_FerienWohnung FOREIGN KEY(AdressID)
                REFERENCES Adresse(AdressID));

create table FW_hat_AUS(
    AusstattungsName VARCHAR2(30) NOT NULL,
    WohnungsID Char(6) NOT NULL,
    CONSTRAINT fk1_FW_hat_AUS FOREIGN KEY(AusstattungsName)
                REFERENCES Ausstattung(Name)
                ON DELETE CASCADE,
    CONSTRAINT fk2_FW_hat_AUS FOREIGN KEY(WohnungsID)
                REFERENCES Ferienwohnung(WohnungsID)
                ON DELETE CASCADE,
    CONSTRAINT pk_FW_hat_AUS PRIMARY KEY(AusstattungsName, WohnungsID)
);