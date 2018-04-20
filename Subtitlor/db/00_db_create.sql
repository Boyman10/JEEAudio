# MYSQL VERSION :

CREATE TABLE traduction (
	id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
	filename VARCHAR(100) UNIQUE NOT NULL,
	sequence VARCHAR(150) NOT NULL,
	language_str VARCHAR(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


CREATE TABLE strings (
	id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	translated_str TINYTEXT,	
	traduction_id BIGINT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# POSTGRESQL VERSION :

CREATE TABLE traduction (
	id serial PRIMARY KEY,
	filename VARCHAR(100) NOT NULL,
	sequence VARCHAR(150) NOT NULL,
	language_str VARCHAR(20) NOT NULL
) ;


CREATE TABLE strings (
	id serial PRIMARY KEY,
	translated_str VARCHAR(250),	
	traduction_id integer NOT NULL,
	UNIQUE(translated_str,traduction_id),
	  CONSTRAINT traduction_id_fkey FOREIGN KEY (traduction_id)
      REFERENCES traduction (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
	
) ;