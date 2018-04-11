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
	maanedsloenn DECIMAL(10,2),
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
	timer INTEGER,
	rolle VARCHAR(30),
	
	CONSTRAINT deltakelsePK PRIMARY KEY(ansattid, prosjektid),
	CONSTRAINT ansattFK FOREIGN KEY(ansattid) REFERENCES ansatt(ansattid),
	CONSTRAINT prosjektFK FOREIGN KEY(prosjektid) REFERENCES prosjekt(prosjektid)

);

ALTER TABLE ansatt ADD CONSTRAINT avdelingFK FOREIGN KEY (avdelingsID) REFERENCES avdeling(avdelingsID);


	
INSERT INTO avdeling
VALUES
	(DEFAULT, 'Administrasjon'),
	(DEFAULT, 'Produksjon'),
	(DEFAULT, 'Markedsføring');
	


INSERT INTO ansatt
VALUES
	(DEFAULT, 'ansi', 'Anders', 'Simonsen', '2017-08-01', 'Avdelingssjef', 500000, 1),
	(DEFAULT, 'brn2', 'Ola', 'Nordmann', '2017-12-31', 'Medarbeider', 400000, 1),
	(DEFAULT, 'brn3', 'Kari', 'Nordmann', '2017-06-01', 'Medarbeider', 600000, 2),
	(DEFAULT, 'brn4', 'Per', 'Persen', '2018-03-03', 'Tekniker', 450000, 2),
	(DEFAULT, 'brn5', 'Kristin', 'Kristensen', '2016-01-01', 'Medarbeider', 600000, 3),
	(DEFAULT, 'admo', 'Adrian', 'Mortensen', '2017-08-01', 'Avdelingssjef', 500000, 2),
	(DEFAULT, 'krno', 'Kristoffer', 'Nome', '2017-08-01', 'Avdelingssjef', 500000, 3),
	(DEFAULT, 'brn8', 'Mari', 'Mikkelsen', '2017-02-03', 'Medarbeider', 400000, 2),
	(DEFAULT, 'brn9', 'Erik', 'Eriksen', '2018-02-01', 'Tekniker', 300000, 1),
	(DEFAULT, 'br10', 'Fredrik', 'Fredriksen', '2018-03-05', 'Medarbeider', 400000, 3),
	(DEFAULT, 'br11', 'Line', 'Larsen', '2016-04-26', 'Ass. Avd. Sjef', 500000,1),
	(DEFAULT, 'br12', 'Ida', 'Iversen', '2017-06-25', 'Tekniker', 400000, 3);
	

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