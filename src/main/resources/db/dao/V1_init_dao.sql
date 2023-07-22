DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

create table users (
id bigserial,
username varchar (255) not null,
password varchar(255) not null,
email varchar(255) not null,
enabled boolean not null,
primary key (id)
);

create table roles(
id bigserial,
name varchar(255) not null,
primary key (id)
);

create table users_roles (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
foreign key (user_id) references users (id),
foreign key (role_id) references roles (id)
);

insert into users (username, password, email, enabled) values
('user1', '$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', 'user1@user.ru', true ),
('user2', '$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', 'user1@user.ru', true ),
('admin1', '$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', 'admin1@admin.com', true),
('admin2', '$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C', 'admin2@admin.com', true);

insert into roles (name) values
('ROLE_ADMIN'),
('ROLE_USER'),
('SOMETHING');

insert into users_roles values
(1, 2),
(2, 2),
(3, 1),
(4, 1);


