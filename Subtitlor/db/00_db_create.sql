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


## NEW VERSION ##
CREATE TABLE file_translate (
	id serial PRIMARY KEY,
	file_name VARCHAR(100) NOT NULL,
	date_file DATE NOT NULL 
) ;

CREATE TABLE sequence_translate (
	id serial PRIMARY KEY,
	sequence_details VARCHAR(100) NOT NULL,
	file_id integer NOT NULL,
CONSTRAINT sequence_id_fkey FOREIGN KEY (file_id)
      REFERENCES file_translate (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
) ;

CREATE TABLE string_translate (
	
	sequence_id integer NOT NULL,
	language_string VARCHAR(100) NOT NULL,
	content_string VARCHAR(250) NOT NULL,
	PRIMARY KEY (sequence_id,language_string),
CONSTRAINT translate_id_fkey FOREIGN KEY (sequence_id)
      REFERENCES sequence_translate (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
) ;

