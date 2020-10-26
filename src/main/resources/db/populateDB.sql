DELETE
FROM coffee;



ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO coffee (date_time, sugar,type)
VALUES ('2020-01-30 10:00:00', 1,'AMERICANO'),
       ('2020-01-30 10:00:01', 6,'CAPPUCCINO'),
       ('2020-01-30 10:00:05', 5,'LATTE'),
       ('2020-01-30 10:00:10', 10,'ESPRESSO');




