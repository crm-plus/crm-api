CREATE TABLE IF NOT EXISTS category
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(37) UNIQUE NOT NULL,
    description VARCHAR,
    organization_id BIGINT,

    PRIMARY KEY(id),

    CONSTRAINT fk_organization_id
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id)
);

CREATE TABLE IF NOT EXISTS custom_parameters
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(37) NOT NULL,
    units VARCHAR(37) NOT NULL,
    parameter_value VARCHAR(37) NOT NULL,
    product_id BIGINT,

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS products
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(37) NOT NULL,
    description VARCHAR,
    organization_id BIGINT,
    price NUMERIC NOT NULL,
    prime_cost NUMERIC NOT NULL,
    category_id BIGINT,
    available_quantity BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    deleted_by BIGINT,

    PRIMARY KEY(id),

    CONSTRAINT fk_organization_id
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id),

    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
            REFERENCES category (id),

    CONSTRAINT fk_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id)
);

ALTER TABLE custom_parameters
    ADD CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES products (id);

CREATE TABLE IF NOT EXISTS tags
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(37) UNIQUE NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS products_tags
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    product_id BIGINT,
    product_tag_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT fk_product_tag_id
        FOREIGN KEY (product_tag_id)
            REFERENCES tags (id)
);