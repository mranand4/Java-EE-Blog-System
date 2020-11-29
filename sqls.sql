/* TABLES */

CREATE TABLE user (
uid INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
fname VARCHAR(50) NOT NULL,
lname VARCHAR(50),
email VARCHAR(50) NOT NULL,
passwd VARCHAR(50) NOT NULL
);

CREATE TABLE post (
post_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
uid INT NOT NULL,
title VARCHAR(255) NOT NULL,
date DATE NOT NULL,
body TEXT
);

CREATE TABLE post_summary(
ps_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
post_id INT NOT NULL,
summary TEXT(400),
poster TEXT(300)
);

CREATE TABLE post_views(
post_id INT NOT NULL UNIQUE,
view_count INT DEFAULT 0,
title VARCHAR(255) NOT NULL
);

CREATE TABLE daily_quotes(
quote_id SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
quote VARCHAR(255) NOT NULL,
author VARCHAR(100) DEFAULT "Anonymous"
);



/* this query gives the size of the database */
SELECT table_schema "db_blog",
        ROUND(SUM(data_length + index_length) / 1024 / 1024, 1) "DB Size in MB" 
FROM information_schema.tables 
GROUP BY table_schema; 