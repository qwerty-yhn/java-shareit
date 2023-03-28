DROP TABLE IF EXISTS users,
    requests,
    items,
    bookings,
    comments;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_name  VARCHAR(50)                             NOT NULL,
    email      VARCHAR(40)                             NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description  VARCHAR(100)                            NOT NULL,
    requestor_id BIGINT,
    created      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT fk_requestor_id FOREIGN KEY (requestor_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS items
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    owner_id     BIGINT                                  NOT NULL,
    item_name    VARCHAR(50)                             NOT NULL,
    description  VARCHAR(100)                            NOT NULL,
    available    BOOLEAN                                 NOT NULL,
    request_id   BIGINT,
    CONSTRAINT pk_items PRIMARY KEY (id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_items UNIQUE (owner_id, item_name, description)
    );

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text      VARCHAR(100)                            NOT NULL,
    item_id   BIGINT,
    author_id BIGINT,
    created   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_item_id FOREIGN KEY (item_id) REFERENCES items (id)   ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS bookings
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date   TIMESTAMP WITHOUT TIME ZONE,
    item_id    BIGINT                                  NOT NULL,
    booker_id  BIGINT                                  NOT NULL,
    status     VARCHAR(50)                             NOT NULL,
    CONSTRAINT pk_bookings PRIMARY KEY (id),
    CONSTRAINT fk_bookings_item_id FOREIGN KEY (item_id) REFERENCES items (id)   ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_booker_id FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_bookings UNIQUE (start_date, end_date, item_id, booker_id)
    );