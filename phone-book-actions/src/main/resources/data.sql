create table if not exists contacts (
    id int not null auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    phone_number int,
    primary key (id)
);