create table IF NOT EXISTS authors (
    id BIGINT AUTO_INCREMENT,
    full_name varchar(255),
    primary key (id)
);

create table IF NOT EXISTS genres (
    id BIGINT AUTO_INCREMENT,
    name varchar(255),
    primary key (id)
);

create table books (
    id BIGINT AUTO_INCREMENT,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    book_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);