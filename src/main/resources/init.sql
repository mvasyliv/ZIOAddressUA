
CREATE TABLE countries(
    id uuid NOT NULL PRIMARY KEY,
    full_name VARCHAR NOT NULL, 
    short_name VARCHAR NOT NULL,
    description_short VARCHAR NULL
);

CREATE TABLE postcodes(zip_code VARCHAR NOT NULL PRIMARY KEY);

CREATE TABLE IF NOT EXISTS areas(
    id uuid NOT NULL PRIMARY KEY,
    country_id uuid NOT NULL,
    full_name VARCHAR NOT NULL,
    description_short VARCHAR NULL
);

CREATE TABLE IF NOT EXISTS regions(
    id uuid NOT NULL PRIMARY KEY,
    area_id uuid NOT NULL,
    full_name VARCHAR NOT NULL,
    description_short VARCHAR NULL
);

INSERT INTO countries(id, full_name, short_name, description_short)
VALUES
    ('97f25d3f-1f81-43b1-8a9f-7133cf0a4121', 'Ukraine', 'UA', 'Ukraine'),
    ('477eb31a-a967-4c10-bfba-749e3a62eec5', 'Germany', 'GR', NULL),
    ('c47f8d70-bbd2-4676-a3df-225142e13e79', 'Bretain', 'BD', NULL),
    ('777ec8d9-ebf6-4906-a27f-262e0d8aca52', 'USA', 'USA', NULL);

INSERT INTO postcodes(zip_code) 
VALUES
    ('46011'),
    ('46010'),
    ('46012');


INSERT INTO areas(id, country_id, full_name, description_short)
VALUES ('6ddca3ce-0efd-4a67-b43e-65c5862b0081', '97f25d3f-1f81-43b1-8a9f-7133cf0a4121', 'Тернопільська', NULL),
('97ed6175-2502-4287-993e-ca258aac745d', '97f25d3f-1f81-43b1-8a9f-7133cf0a4121', 'Львівська', NULL );

INSERT INTO regions(id, area_id, full_name, description_short)
VALUES ('0f616b78-6850-4002-9b2e-cd538780062b', '6ddca3ce-0efd-4a67-b43e-65c5862b0081','Тернопільський', NULL),
('a0844283-5ad2-440b-91bd-c908e66b6a6a', '6ddca3ce-0efd-4a67-b43e-65c5862b0081', 'Чортківський', NULL );