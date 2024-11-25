insert into authors(full_name)
values ('Author_100'), ('Author_200'), ('Author_300');

insert into genres(name)
values ('Genre_100'), ('Genre_200'), ('Genre_300');

insert into books(title, author_id, genre_id)
values ('BookTitle_100', 1, 1), ('BookTitle_200', 2, 2), ('BookTitle_300', 3, 3);
