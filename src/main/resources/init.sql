
CREATE TABLE countries(
    id uuid not null primary key,
    full_name varchar not null, 
    short_name varchar not null,
    description_short varchar NULL
);

INSERT INTO countries(id, full_name, short_name, description_short)
VALUES
    ('97f25d3f-1f81-43b1-8a9f-7133cf0a4121', 'Ukraine', 'UA', 'Ukraine'),
    ('477eb31a-a967-4c10-bfba-749e3a62eec5', 'Germany', 'GR', NULL),
    ('c47f8d70-bbd2-4676-a3df-225142e13e79', 'Bretain', 'BD', NULL),
    ('777ec8d9-ebf6-4906-a27f-262e0d8aca52', 'USA', 'USA', NULL);