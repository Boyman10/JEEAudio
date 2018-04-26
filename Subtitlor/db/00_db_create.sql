
CREATE TABLE public.file_translate (
                file_id INTEGER NOT NULL,
                date_file DATE NOT NULL,
                file_name VARCHAR(100) NOT NULL,
                CONSTRAINT file_id PRIMARY KEY (file_id)
);


CREATE SEQUENCE public.sequence_translate_id_seq;

CREATE TABLE public.sequence_translate (
                sequence_id INTEGER NOT NULL DEFAULT nextval('public.sequence_translate_id_seq'),
                sequence_details VARCHAR(100) NOT NULL,
                file_id INTEGER NOT NULL,
                CONSTRAINT sequence_id PRIMARY KEY (sequence_id)
);


ALTER SEQUENCE public.sequence_translate_id_seq OWNED BY public.sequence_translate.sequence_id;

CREATE TABLE public.string_translate (
                sequence_id INTEGER NOT NULL,
                language_string VARCHAR NOT NULL,
                content_string VARCHAR(250) NOT NULL,
                CONSTRAINT string_translate_id PRIMARY KEY (sequence_id, language_string)
);


ALTER TABLE public.sequence_translate ADD CONSTRAINT file_translate_sequence_translate_fk
FOREIGN KEY (file_id)
REFERENCES public.file_translate (file_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.string_translate ADD CONSTRAINT sequence_translate_string_translate_fk
FOREIGN KEY (sequence_id)
REFERENCES public.sequence_translate (sequence_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
