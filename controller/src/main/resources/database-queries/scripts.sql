CREATE TABLE IF NOT EXISTS public.products
(
    id bigserial NOT NULL,
    type text,
    company text,
    name text,
    PRIMARY KEY (id)
);

ALTER TABLE public.products
    OWNER to postgres;