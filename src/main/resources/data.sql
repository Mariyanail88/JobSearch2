create table if not exists users(
    id int auto_increment primary key,
    name varchar(45),
    password varchar(45)
);

insert into users(name, password)
values ('John Doe', '123456'),
       ('Jane Doe', 'qwerty');