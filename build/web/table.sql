CREATE TABLE Produit(
    id_produit VARCHAR(10) DEFAULT ('PRD') || LPAD(nextval('form_sequence')::TEXT,4,'0'),
    val VARCHAR(50) ,
    consommation DOUBLE PRECISION,
    PRIMARY KEY(id_produit)
);

CREATE TABLE machine(
    id_machine VARCHAR(10) DEFAULT ('MAC') || LPAD(nextval('mac_sequence')::TEXT,2,'0'),
    val VARCHAR(50) ,
    PRIMARY KEY(id_machine)
);

CREATE TABLE Achat(
    id_achat VARCHAR(15) DEFAULT ('ACH') || LPAD(nextval('achat_sequence')::TEXT,12,'0'),
    id_produit VARCHAR(10),
    quantite DOUBLE PRECISION,
    PU DOUBLE PRECISION,
    daty_achat TIMESTAMP,
    reste INTEGER,
    PRIMARY KEY(id_achat),
    FOREIGN KEY(id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE bloc(
    id_bloc VARCHAR(15) DEFAULT ('BLC') || LPAD(nextval('bloc_sequence')::TEXT,12,'0'),
    val VARCHAR(50) ,
    longueur DOUBLE PRECISION,
    largeur DOUBLE PRECISION,
    epaisseur DOUBLE PRECISION,
    id_machine VARCHAR(10) NOT NULL,
    prix_revient_pratique DOUBLE PRECISION,
    prix_revient_theorique DOUBLE PRECISION,
    daty_creation TIMESTAMP,
    PRIMARY KEY(id_bloc),
    FOREIGN KEY(id_machine) REFERENCES machine(id_machine)
);