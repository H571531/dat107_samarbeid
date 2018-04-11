DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;
SET search_path TO oblig3;

CREATE TABLE ansatt(
	ansattID SERIAL,
	brukernavn CHAR(4) UNIQUE,
	fornavn VARCHAR(30),
	etternavn VARCHAR(30),
	ansettelsesDato DATE,
	stilling VARCHAR(30),
	maanedsloenn DECIMAL,
	avdelingsID INTEGER NOT NULL,
	
	CONSTRAINT ansattPK PRIMARY KEY(ansattID)
	--CONSTRAINT avdelingFK FOREIGN KEY (avdelingsID) REFERENCES avdeling(avdelingsID)
	);
	
CREATE TABLE avdeling(
	avdelingsID SERIAL,
	navn VARCHAR(30),
	sjef INTEGER,
	

	CONSTRAINT avdelingPK PRIMARY KEY(avdelingsID),
	CONSTRAINT sjefFK FOREIGN KEY (sjef) REFERENCES ansatt(ansattID)
	

);

CREATE TABLE prosjekt(
	prosjektID SERIAL,
	prosjektnavn VARCHAR(30),
	beskrivelse VARCHAR(40),
	
	CONSTRAINT prosjektPK PRIMARY KEY(prosjektID)

);

CREATE TABLE prosjektdeltakelse(
	--lager surrogatnøkkel for lettere JPA
	ansattid INTEGER,
	prosjektid INTEGER,
	antallTimer INTEGER,
	rolle VARCHAR(30),
	
	CONSTRAINT deltakelsePK PRIMARY KEY(ansattid, prosjektid),
	CONSTRAINT ansattFK FOREIGN KEY(ansattid) REFERENCES ansatt(ansattid),
	CONSTRAINT prosjektFK FOREIGN KEY(prosjektid) REFERENCES prosjekt(prosjektid)

);

ALTER TABLE ansatt ADD CONSTRAINT avdelingFK FOREIGN KEY (avdelingsID) REFERENCES avdeling(avdelingsID);


	
INSERT INTO avdeling
VALUES
	(DEFAULT, 'Avdeling 1'),
	(DEFAULT, 'Avdeling 2'),
	(DEFAULT, 'Avdeling 3');
	


INSERT INTO ansatt
VALUES
	(DEFAULT, 'brn1', 'Fornavn 1', 'Etternavn 1', '2018-01-01', 'Avdelingssjef', 500000, 1),
	(DEFAULT, 'brn2', 'Fornavn 2', 'Etternavn 2', '2017-12-31', 'Stilling 2', 400000, 1),
	(DEFAULT, 'brn3', 'Fornavn 3', 'Etternavn 3', '2017-06-01', 'Stilling 3', 600000, 2),
	(DEFAULT, 'brn4', 'Fornavn 4', 'Etternavn 4', '2018-03-03', 'Stilling 4', 450000, 2),
	(DEFAULT, 'brn5', 'Fornavn 5', 'Etternavn 5', '2016-01-01', 'Stilling 3', 600000, 3),
	(DEFAULT, 'brn6', 'Fornavn 6', 'Etternavn 6', '2016-02-05', 'Avdelingssjef', 500000, 2),
	(DEFAULT, 'brn7', 'Fornavn 7', 'Etternavn 7', '2016-06-10', 'Avdelingssjef', 500000, 3),
	(DEFAULT, 'brn8', 'Fornavn 8', 'Etternavn 8', '2017-02-03', 'Stilling 3', 400000, 2),
	(DEFAULT, 'brn9', 'Fornavn 9', 'Etternavn 9', '2018-02-01', 'Stilling 4', 300000, 1),
	(DEFAULT, 'br10', 'Fornavn 10', 'Etternavn 10', '2018-03-05', 'Stilling 3', 400000, 3),
	(DEFAULT, 'br11', 'Fornavn 11', 'Etternavn 11', '2016-04-26', 'Stilling 2', 500000,1),
	(DEFAULT, 'br12', 'Fornavn 12', 'Etternavn 12', '2017-06-25', 'Stilling 4', 400000, 3);
	

UPDATE avdeling SET sjef=1 WHERE avdelingsID = 1;
UPDATE avdeling SET sjef=6 WHERE avdelingsID = 2;
UPDATE avdeling SET sjef=7 WHERE avdelingsID = 3;

ALTER TABLE avdeling ALTER COLUMN sjef SET NOT NULL;

--ALTER TABLE avdeling ADD CONSTRAINT sjefMaaVaereIAvdeling CHECK(sjef IN(SELECT ansattid FROM ansatt WHERE ansatt.avdeling = avdelingsid));

INSERT INTO prosjekt
VALUES
	(DEFAULT, 'Lage hjemmeside', 'Lage hjemmeside for firma'),
	(DEFAULT, 'Lage database', 'Lage database med SQL'),
	(DEFAULT, 'Lage JPA', 'Lage grensesnitt for database'),
	(DEFAULT, 'Kundeprosjekt', 'Prosjekt for Kunde Kundesen');
	
--Prosjektdeltakelse: (Ansattid, prosjektid, antall timer)
INSERT INTO prosjektdeltakelse
VALUES
	(2, 1, 20, 'Prosjektleder'),
	(3, 2, 15, 'Prosjektleder'),
	(4, 3, 16, 'Prosjektleder'),
	(5, 4, 10, 'Prosjektleder'),
	(8, 4, 30, 'Prosjektmedarbeider'),
	(9, 4, 30, 'Prosjektmedarbeider');
	
--SELECT * FROM ansatt;