create table authors (
    id BIGINT AUTO_INCREMENT,
    full_name varchar(255),
    primary key (id)
);

create table genres (
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