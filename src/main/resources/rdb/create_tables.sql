create table if not exists director
(
    id          int auto_increment primary key,
    name        varchar(255) not null
);

create table if not exists movie
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    director_id int not null
);

