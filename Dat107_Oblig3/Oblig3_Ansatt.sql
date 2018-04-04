DROP SCHEMA IF EXISTS Oblig3 CASCADE;

CREATE SCHEMA Oblig3;
SET search_path TO Oblig3;
    
-- Ikke nødvendig å sletter tabellen(e) siden vi har tomt skjema, men ...
-- DROP TABLE person;

CREATE TABLE Ansatt
(
    AnsattId SERIAL,
	brukernavn VARCHAR(6),
	fornavn VARCHAR(30),
	etternavn VARCHAR(30),
    AnsettelsesDato DATE,
	Stilling VARCHAR(30) NOT NULL,
	manedslonn DECIMAL(10, 2),
    CONSTRAINT Ansatt_PK PRIMARY KEY (AnsattId)
);

INSERT INTO
  Ansatt(brukernavn,fornavn,etternavn,AnsettelsesDato,Stilling,manedslonn)
VALUES
    ('adrmor','Adrian','Mortensen','2008-11-11','Teknikker','32000.2'),
    ('adrjor','Adrian','Johnsen','2008-11-11','Selger','38000.2'),
    ('adrren','Adrian','Rene','2008-11-11','Sjef','38000.2');
