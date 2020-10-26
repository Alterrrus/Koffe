DROP TABLE IF EXISTS coffee;


DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 10000;

CREATE TABLE coffee
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date_time TIMESTAMP           DEFAULT now()       NOT NULL,
    sugar     INTEGER             DEFAULT 1           NOT NULL,
    type      VARCHAR(255)        default 'AMERICANO' NOT NULL

);







