CREATE TABLE IF NOT EXISTS credentials
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY,
    email    VARCHAR UNIQUE NOT NULL,
    password VARCHAR        NOT NULL,
    user_id  BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id                   BIGINT GENERATED ALWAYS AS IDENTITY,
    first_name           VARCHAR,
    last_name            VARCHAR,
    credential_id        BIGINT,
    birth_date           DATE,
    residential_address  VARCHAR,
    deleted              BOOLEAN,
    sex                  VARCHAR,
    organization_role_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_credential
        FOREIGN KEY (credential_id)
            REFERENCES credentials (id)
);

ALTER TABLE credentials
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR UNIQUE NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT,
    role_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS organizations
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(16) UNIQUE NOT NULL,
    description VARCHAR,
    created_by  BIGINT             NOT NULL,
    deleted_by  BIGINT,
    created_at  DATE,
    updated_at  DATE,
    private     BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id),

    CONSTRAINT fk_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),

    CONSTRAINT fk_deleted_by
        FOREIGN KEY (deleted_by)
            REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS organizations_users
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    organization_id BIGINT,
    user_id         BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_organization_id
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id),

    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS refresh_tokens
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY,
    refresh_token VARCHAR UNIQUE NOT NULL,
    credential_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_credential_id
        FOREIGN KEY (credential_id)
            REFERENCES credentials (id)

);

CREATE TABLE IF NOT EXISTS organization_roles
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    type            VARCHAR NOT NULL,
    organization_id BIGINT,
    user_id         BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_organization_id
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id),

    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS invitations
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    sender_id    BIGINT,
    organization_id BIGINT,
    recipient_id BIGINT,
    state        VARCHAR NOT NULL,
    created_at   VARCHAR,

    PRIMARY KEY (id),

    CONSTRAINT fk_organization_id
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id),

    CONSTRAINT fk_recipient_id
        FOREIGN KEY (recipient_id)
            REFERENCES users (id),

    CONSTRAINT fk_sender_id
        FOREIGN KEY (sender_id)
            REFERENCES users (id)
);

ALTER TABLE users
    ADD CONSTRAINT fk_org_role_id
        FOREIGN KEY (organization_role_id)
            REFERENCES organization_roles (id);