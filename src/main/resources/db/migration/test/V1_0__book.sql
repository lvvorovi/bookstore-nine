CREATE TABLE books (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created TIMESTAMP NOT NULL,

    CONSTRAINT pk_books PRIMARY KEY (id),
    CONSTRAINT uc_books_name UNIQUE (name)
);