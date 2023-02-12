create table role(
    id SERIAL,
    name varchar(255),
    constraint role_name_unique unique (name)
);

alter table role add constraint role_pk_id primary key (id);


