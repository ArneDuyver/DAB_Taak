-- BEGIN;
/*INITIALISE EMPTY TABLES:*/

-- BoerderijTabel
DROP TABLE IF EXISTS "Boerderij";
CREATE TABLE "Boerderij" (
	"boerderijId"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL,
	"adres"	TEXT NOT NULL UNIQUE,
	"email"	TEXT NOT NULL UNIQUE,
	"rekeningnr"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("boerderijId" AUTOINCREMENT)
);

-- KlantTabel
DROP TABLE IF EXISTS "Klant";
CREATE TABLE "Klant" (
	"klantId"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL,
	"adres"	TEXT,
	"telefoonnr"	TEXT,
	"email"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("klantId" AUTOINCREMENT)
);

--PakketbeschrijvingTabel
DROP TABLE IF EXISTS "Pakketbeschrijving";
CREATE TABLE "Pakketbeschrijving" (
	"pakketbeschrijvingId"		INTEGER NOT NULL UNIQUE,
	"naam"			TEXT NOT NULL,
	"kinderen"		INTEGER NOT NULL,
	"volwassenen"	INTEGER NOT NULL,
	PRIMARY KEY("pakketbeschrijvingId" AUTOINCREMENT)
);

--VerkooptTabel
DROP TABLE IF EXISTS "Verkoopt";
CREATE TABLE "Verkoopt" (
	"verkooptId"		INTEGER NOT NULL UNIQUE,
	"boerderijId"	INTEGER NOT NULL,
	"pakketbeschrijvingId"	INTEGER NOT NULL,
	"startdatum"	TEXT NOT NULL DEFAULT '1 januari',
	"prijs"	INTEGER NOT NULL,
	PRIMARY KEY("verkooptId" AUTOINCREMENT)
);

--KooptTabel
DROP TABLE IF EXISTS "koopt";
CREATE TABLE "koopt" (
	"id"	INTEGER NOT NULL UNIQUE,
	"klantId"	INTEGER NOT NULL,
	"verkooptId"	INTEGER NOT NULL,
	"betaald"            BOOLEAN NOT NULL DEFAULT 'FALSE',
	PRIMARY KEY("id" AUTOINCREMENT)
);

--ProductTabel
DROP TABLE IF EXISTS "Product";
CREATE TABLE "Product" (
	"naam"	TEXT NOT NULL UNIQUE,
	"soort"	TEXT,
	"prodId"	INTEGER NOT NULL UNIQUE,
	PRIMARY KEY("prodId" AUTOINCREMENT)
);

--PakketInhoudTabel
DROP TABLE IF EXISTS "PakketInhoud";
CREATE TABLE "PakketInhoud" (
	"PakketInhoudId"	INTEGER NOT NULL UNIQUE,
	"naam"  	TEXT,
	PRIMARY KEY("PakketInhoudId" AUTOINCREMENT)
);

--BevatTabel
DROP TABLE IF EXISTS "bevat";
CREATE TABLE "bevat" (
	"bevatId"	INTEGER NOT NULL UNIQUE,
	"eenheid"	TEXT NOT NULL,
	"hoeveelheid"	INTEGER NOT NULL,
	"productId" INTEGER NOT NULL,
	"pakketInhoudId" INTEGER NOT NULL,
	PRIMARY KEY("bevatId" AUTOINCREMENT)
);

--BehoortTotTabel
DROP TABLE IF EXISTS "BehoortTot";
CREATE TABLE "BehoortTot" (
	"behoortTotId"	INTEGER NOT NULL,
	"weekNr"		INTEGER,
	"verkooptId" INTEGER NOT NULL,
	"pakketInhoudId" INTEGER NOT NULL,
	PRIMARY KEY("behoortTotId" AUTOINCREMENT)
);

--HaaltAfTabel
DROP TABLE IF EXISTS "haaltAf";
CREATE TABLE "haaltAf" (
	"haaltAfId"	INTEGER NOT NULL,
	"afgehaald"	BOOLEAN,
	"behoortTotId"	INTEGER NOT NULL,
	"KlantId"	INTEGER NOT NULL,
	PRIMARY KEY("haaltAfId" AUTOINCREMENT)
);


/*PUT IN EXAMPLE CONTENT:*/

--BoerderijExamples
INSERT INTO Boerderij(naam, adres, email, rekeningnr) VALUES ("Geeritshof","Boshoeve 11, 3900 Peer", "geeritsEnZonen@hotmail.com", "BE37 7390 1252 5428"); -- 1
INSERT INTO Boerderij(naam, adres, email, rekeningnr) VALUES ("Koekoekshof","Laarderweg 9, 3900 Peer", "koekoekshof@hotmail.com", "BE37 6201 5879 2587"); -- 2
INSERT INTO Boerderij(naam, adres, email, rekeningnr) VALUES ("Sezoenshof","apotherkerhendrixstraat 13, 3900 Peer", "sezoenshof@hotmail.com", "BE69 2222 4444 7777"); -- 3
INSERT INTO Boerderij(naam, adres, email, rekeningnr) VALUES ("Woutershof","Universiteitslaan 12, 3650 Hasselt", "woutershof@gmail.com", "BE36 5420 4796 4562"); -- 4

--KlantenExamples
INSERT INTO Klant(naam,adres,telefoonnr,email) VALUES ("Dirk Waterslagers","Boshoeve 14, 3900 Peer ","0486652354","dirk@hotmail.com"); -- 1
INSERT INTO Klant(naam,adres,telefoonnr,email) VALUES ("Jef Maesen","Laarderweg 9, 3900 Peer ","0466956824","jef@hotmail.com"); -- 2
INSERT INTO Klant(naam,adres,telefoonnr,email) VALUES ("Jos Winters","Smeetshofweg z/n, 3900 Peer ","0476585952","jos@gmail.com"); -- 3
INSERT INTO Klant(naam,adres,telefoonnr,email) VALUES ("Thomas Allard","Paardendries 19, 9300 Aalst ","0476569873","thomas@hotmail.com"); -- 4
INSERT INTO Klant(naam,adres,telefoonnr,email) VALUES ("Arne Peeters","Hommelbeek 11, 3980 Tessenderlo ","0472589633","arne@hotmail.com"); -- 5

--ProductExamples
INSERT INTO Product(naam,soort) VALUES ("Broccoli","groenten"); -- 1
INSERT INTO Product(naam,soort) VALUES ("Spruitjes","groenten"); -- 2
INSERT INTO Product(naam,soort) VALUES ("Seranoham","varkensvlees"); -- 3
INSERT INTO Product(naam,soort) VALUES ("Hyacint","bloemen"); -- 4
INSERT INTO Product(naam,soort) VALUES ("Bloedappelsienen","fruit"); -- 5
INSERT INTO Product(naam,soort) VALUES ("Wortelen","groenten"); -- 6
INSERT INTO Product(naam,soort) VALUES ("Kipfilets","kippenvlees"); -- 7
INSERT INTO Product(naam,soort) VALUES ("Appels","groenten"); -- 8
INSERT INTO Product(naam,soort) VALUES ("Filet pur","rundsvlees"); -- 9

--PakketbeschrijvingExamples
INSERT INTO Pakketbeschrijving(naam,kinderen,volwassenen) VALUES ("XS",0,1); -- 1
INSERT INTO Pakketbeschrijving(naam,kinderen,volwassenen) VALUES ("S",0,2); -- 2
INSERT INTO Pakketbeschrijving(naam,kinderen,volwassenen) VALUES ("M",2,2); -- 3
INSERT INTO Pakketbeschrijving(naam,kinderen,volwassenen) VALUES ("L",3,2); -- 4
INSERT INTO Pakketbeschrijving(naam,kinderen,volwassenen) VALUES ("XL",5,2); -- 5

--PakketInhoudExamples
INSERT INTO PakketInhoud(naam)  VALUES (null); -- 1
INSERT INTO PakketInhoud(naam)  VALUES (null); -- 2
INSERT INTO PakketInhoud(naam)  VALUES (null); -- 3
INSERT INTO PakketInhoud(naam)  VALUES (null); -- 4

--BevatExamples
	--Pakket 1:
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (300,"gram",1,1); -- Broccoli
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (2,"stuks",5,1); -- Bloedappelsien
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES ( 200,"gram",9,1); -- Filet Pur
	--Pakket 2:
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (2,"kilogram",2,2); -- Spruitjes
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (1,"kilogram" ,6,2); -- Wortelen
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (400,"gram" ,9,2); --  Filet Pur
	--Pakket 3:
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (100,"gram",3,3); -- Seranoham
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (50,"gram",7,3); -- Kipfilet
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (400,"gram" ,9,3); --  Filet Pur
	--Pakket 4:
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (4,"stuks",4,4); --Hyacint
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (4,"stuks",8,4); -- Appels
INSERT INTO bevat(hoeveelheid,eenheid,productId,pakketInhoudId) VALUES (600,"gram",9,4); --  Filet Pur

--VerkooptExamples
	--Boerderij 1:
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (1,2,360); -- 1
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (1,3,700); -- 2
	--Boerderij 2:
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (2,3,650); -- 3
	--Boerderij 3:
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (3,2,560); -- 4
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (3,3,840); -- 5
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (3,4,1200); -- 6
	--Boerderij 4:
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (4,3,750); -- 7
INSERT INTO verkoopt(boerderijId,pakketbeschrijvingId,prijs) VALUES (4,4,1050); -- 8

--BehoortTotExamples
	--weken 1
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,1,1); -- 1
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,2,2); -- 2
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,3,3); -- 3
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,4,4); -- 4
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,5,1); -- 5
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,6,2); -- 6
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,7,3); -- 7
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (1,8,4); -- 8
	--weken 2
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,1,2); -- 9
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,2,3); -- 10
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,3,4); -- 11
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,4,1); -- 12
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,5,3); -- 13
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,6,4); -- 14
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,7,1); -- 15
INSERT INTO behoortTot(weekNr,verkooptId,pakketInhoudId) VALUES (2,8,2); -- 16

--kooptExamples
INSERT INTO koopt(klantId,verkooptId) VALUES (1,1); -- 1
INSERT INTO koopt(klantId,verkooptId) VALUES (2,3); -- 2
INSERT INTO koopt(klantId,verkooptId) VALUES (3,4); -- 3
INSERT INTO koopt(klantId,verkooptId) VALUES (4,1); -- 4
INSERT INTO koopt(klantId,verkooptId) VALUES (4,7); -- 5

--haaltAfExamples
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (1,1); -- 1
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (1,9); -- 2
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (2,3); -- 3
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (2,11); -- 4
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (3,4); -- 5
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (3,12); -- 6
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (4,1); -- 7
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (4,9); -- 8
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (4,7); -- 9
INSERT INTO haaltAf(klantId,behoortTotId) VALUES (4,15); -- 10

END;
-- ROLLBACK; -- If an error occurred.


