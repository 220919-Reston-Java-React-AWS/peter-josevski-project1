drop table if exists users;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    role VARCHAR(20) NOT NULL
);

create table roles (
	employee varchar(10) not null,
	manager varchar(10) not null,

)