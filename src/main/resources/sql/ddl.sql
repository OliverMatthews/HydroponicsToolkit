CREATE SCHEMA IF NOT EXISTS prod;

DROP SCHEMA IF EXISTS public;

SET search_path TO prod;

CREATE TABLE IF NOT EXISTS prod.plant
(
    uid serial NOT NULL,
    type text NOT NULL,
    plantedDate date NOT NULL,
    harvestDate date NOT NULL,

    PRIMARY KEY (uid),
    CONSTRAINT "unique_plant_id" UNIQUE (uid)
);