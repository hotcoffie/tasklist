create schema if not exists tasklist collate utf8mb4_0900_ai_ci;

create table if not exists security_role
(
    id int auto_increment
        primary key,
    name varchar(64) not null,
    name_zh varchar(64) not null,
    constraint security_role_name_uindex
        unique (name)
);

create table if not exists security_user
(
    id int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(64) not null,
    name varchar(20) not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint user_account_uindex
        unique (username)
);

create table if not exists security_user_role
(
    id int auto_increment
        primary key,
    user_id int not null,
    role_id int not null
);

create table if not exists task
(
    id int auto_increment
        primary key,
    user_id int not null,
    this_week varchar(500) not null,
    after_week varchar(500) not null,
    checked char default '0' not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

