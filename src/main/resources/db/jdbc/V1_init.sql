DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

create table users (
username varchar (255) not null primary key,
password varchar(255) not null,
enabled boolean not null
);

create table authorities(
username varchar(255) not null,
authority varchar(255) not null,
foreign key (username) references users (username),
unique (username, authority)
);

insert into users values
('user1', '{noop}100', true ),
('admin1', '{bcrypt}$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', true);

insert into authorities values
('admin1', 'ROLE_ADMIN'),
('user1', 'ROLE_USER');

-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS users;
--
-- create table USERS (
--                        username varchar(255) not null primary key,
--                        password varchar(255) not null,
--                        enabled boolean not null
-- );
--
-- create table authorities (
--                              username varchar(255) not null,
--                              authority varchar(255) not null,
--                              foreign key (username) references users (username),
--                              unique (username, authority)
-- );
--
-- insert into users (username, password, enabled) values
--     ('admin1', '{bcrypt}$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', true);
--
-- insert into authorities (username, authority) values ('admin1', 'ROLE_ADMIN');