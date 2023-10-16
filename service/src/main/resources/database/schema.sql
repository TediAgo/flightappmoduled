CREATE SEQUENCE IF NOT EXISTS public.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY users.id;

ALTER SEQUENCE public.users_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.token_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.token_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.flight_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY flight.id;

ALTER SEQUENCE public.flight_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.trip_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY trip.id;

ALTER SEQUENCE public.trip_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    roles character varying(255) COLLATE pg_catalog."default",
    validity boolean,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT users_roles_check CHECK (roles::text = ANY (ARRAY['USER'::character varying, 'ADMIN'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.token
(
    id integer NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    token_type character varying(255) COLLATE pg_catalog."default",
    user_id integer,
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token),
    CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT token_token_type_check CHECK (token_type::text = 'BEARER'::text)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.flight
(
    id integer NOT NULL DEFAULT nextval('flight_id_seq'::regclass),
    arrival_date timestamp(6) without time zone,
    departure_date timestamp(6) without time zone,
    flight_number bigint,
    from_airport character varying(255) COLLATE pg_catalog."default",
    to_airport character varying(255) COLLATE pg_catalog."default",
    validity boolean,
    CONSTRAINT flight_pkey PRIMARY KEY (id),
    CONSTRAINT unique_flight_number UNIQUE (flight_number),
    CONSTRAINT flight_from_airport_check CHECK (from_airport::text = ANY (ARRAY['TIRANA_AIRPORT'::character varying, 'FRANKFURT_AIRPORT'::character varying, 'EINDHOVEN_AIRPORT'::character varying, 'VIENNA_AIRPORT'::character varying]::text[])),
    CONSTRAINT flight_to_airport_check CHECK (to_airport::text = ANY (ARRAY['TIRANA_AIRPORT'::character varying, 'FRANKFURT_AIRPORT'::character varying, 'EINDHOVEN_AIRPORT'::character varying, 'VIENNA_AIRPORT'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.flight
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.trip
(
    id integer NOT NULL DEFAULT nextval('trip_id_seq'::regclass),
    arrival_date timestamp(6) without time zone,
    departure_date timestamp(6) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    from_country character varying(255) COLLATE pg_catalog."default",
    to_country character varying(255) COLLATE pg_catalog."default",
    trip_reason character varying(255) COLLATE pg_catalog."default",
    trip_status character varying(255) COLLATE pg_catalog."default",
    validity boolean,
    flight_id integer,
    user_id integer,
    CONSTRAINT trip_pkey PRIMARY KEY (id),
    CONSTRAINT fk8qawa54m8w5olt1mrn50re3yw FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
                              ON UPDATE NO ACTION
                              ON DELETE NO ACTION,
    CONSTRAINT fkuafk9kpdml3h3my9dv9usjh1 FOREIGN KEY (flight_id)
    REFERENCES public.flight (id) MATCH SIMPLE
                              ON UPDATE NO ACTION
                              ON DELETE NO ACTION,
    CONSTRAINT trip_from_country_check CHECK (from_country::text = ANY (ARRAY['ALBANIA'::character varying, 'GERMANY'::character varying, 'NETHERLANDS'::character varying, 'AUSTRIA'::character varying]::text[])),
    CONSTRAINT trip_to_country_check CHECK (to_country::text = ANY (ARRAY['ALBANIA'::character varying, 'GERMANY'::character varying, 'NETHERLANDS'::character varying, 'AUSTRIA'::character varying]::text[])),
    CONSTRAINT trip_trip_reason_check CHECK (trip_reason::text = ANY (ARRAY['MEETING'::character varying, 'TRAINING'::character varying, 'PROJECT'::character varying, 'WORKSHOP'::character varying, 'EVENT'::character varying, 'OTHER'::character varying]::text[])),
    CONSTRAINT trip_trip_status_check CHECK (trip_status::text = ANY (ARRAY['CREATED'::character varying, 'WAITING_FOR_APPROVAL'::character varying, 'APPROVED'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.trip
    OWNER to postgres;