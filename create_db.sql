-- Database: examinator_db

-- DROP DATABASE IF EXISTS examinator_db;

CREATE DATABASE examinator_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username character varying(128) COLLATE pg_catalog."default" NOT NULL,
    name character varying(128) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(128) COLLATE pg_catalog."default" NOT NULL,
    password character varying(128) COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL DEFAULT true,
    email character varying(128) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;
	
-- Table: public.roles

-- DROP TABLE IF EXISTS public.roles;

CREATE TABLE IF NOT EXISTS public.roles
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.roles
    OWNER to postgres;

-- Table: public.user_role

-- DROP TABLE IF EXISTS public.user_role;

CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT role_fk FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_role
    OWNER to postgres;
	
-- Table: public.pesistent_logins

-- DROP TABLE IF EXISTS public.pesistent_logins;

CREATE TABLE IF NOT EXISTS public.persistent_logins
(
    username character varying(64) COLLATE pg_catalog."default" NOT NULL,
    series character varying(64) COLLATE pg_catalog."default" NOT NULL,
    token character varying(64) COLLATE pg_catalog."default" NOT NULL,
    last_used time without time zone NOT NULL,
    CONSTRAINT pesistent_logins_pkey PRIMARY KEY (series)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.pesistent_logins
    OWNER to postgres;

INSERT INTO public.roles(name)
	VALUES ('ROLE_STUDENT'), ('ROLE_TEACHER'), ('ROLE_ADMIN');

INSERT INTO public.users(username, name, surname, password, active, email)
	VALUES ('admin', 'admin', 'admin', '$2a$12$U/X7w3x8Kmow7L22p7hDu.gpRDGnH3TtG5U6zfK96sTeXea.0R53K', true, 'admin@admin.com');
	
INSERT INTO public.user_role(
	user_id, role_id)
	VALUES (1, 3);
	
-- Table: public.exams

-- DROP TABLE IF EXISTS public.exams;

CREATE TABLE IF NOT EXISTS public.exams
(
    exam_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    exam_name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    exam_subject character varying(30) COLLATE pg_catalog."default" NOT NULL,
    exam_difficulty character varying(15) COLLATE pg_catalog."default" NOT NULL,
    exam_duration integer NOT NULL,
    exam_shuffle boolean NOT NULL DEFAULT false,
    CONSTRAINT exams_pkey PRIMARY KEY (exam_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.exams
    OWNER to postgres;
	
-- Table: public.questions

-- DROP TABLE IF EXISTS public.questions;

CREATE TABLE IF NOT EXISTS public.questions
(
    question_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    question_content text COLLATE pg_catalog."default" NOT NULL,
    question_examid integer NOT NULL,
    CONSTRAINT questions_pkey PRIMARY KEY (question_id),
    CONSTRAINT question_exam_fk FOREIGN KEY (question_examid)
        REFERENCES public.exams (exam_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.questions
    OWNER to postgres;
	
-- Table: public.answers

-- DROP TABLE IF EXISTS public.answers;

CREATE TABLE IF NOT EXISTS public.answers
(
    answer_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    answer_text text COLLATE pg_catalog."default" NOT NULL,
    answer_iscorrect boolean NOT NULL,
    answer_questionid integer NOT NULL,
    CONSTRAINT answers_pkey PRIMARY KEY (answer_id),
    CONSTRAINT answer_question_fk FOREIGN KEY (answer_questionid)
        REFERENCES public.questions (question_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.answers
    OWNER to postgres;
	
-- Table: public.assignments

-- DROP TABLE IF EXISTS public.assignments;

CREATE TABLE IF NOT EXISTS public.assignments
(
    user_id bigint NOT NULL,
    exam_id integer NOT NULL,
    description character varying(256) COLLATE pg_catalog."default",
    CONSTRAINT exam_fk FOREIGN KEY (exam_id)
        REFERENCES public.exams (exam_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.assignments
    OWNER to postgres;
	
-- Table: public.results_exam

-- DROP TABLE IF EXISTS public.results_exam;

CREATE TABLE IF NOT EXISTS public.results_exam
(
    user_id bigint NOT NULL,
    exam_id integer NOT NULL,
    mark integer NOT NULL,
    when_pass character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT exam_fk FOREIGN KEY (exam_id)
        REFERENCES public.exams (exam_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.results_exam
    OWNER to postgres;
	
-- Table: public.posts

-- DROP TABLE IF EXISTS public.posts;

CREATE TABLE IF NOT EXISTS public.posts
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title text COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    creator_id bigint NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    "from" timestamp without time zone,
    "to" timestamp without time zone,
    restriction text COLLATE pg_catalog."default",
    image_id bigint,
    CONSTRAINT posts_pkey PRIMARY KEY (id),
    CONSTRAINT image_post_fk FOREIGN KEY (image_id)
        REFERENCES public.medias (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT post_creator_id_fk FOREIGN KEY (creator_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.posts
    OWNER to postgres;
	
-- Table: public.medias

-- DROP TABLE IF EXISTS public.medias;

CREATE TABLE IF NOT EXISTS public.medias
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    url text COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    width integer NOT NULL,
    height integer NOT NULL,
    CONSTRAINT medias_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.medias
    OWNER to postgres;