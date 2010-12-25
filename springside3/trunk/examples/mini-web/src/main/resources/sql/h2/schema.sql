
    drop table if exists acct_authority;

    drop table if exists acct_role;

    drop table if exists acct_role_authority;

    drop table if exists acct_user;

    drop table if exists acct_user_role;

    create table acct_authority (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    ) ;

    create table acct_role (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        authorities varchar(255) not null,
        primary key (id)
    ) ;

    create table acct_role_authority (
        role_id bigint not null,
        authority_id bigint not null,
        foreign key(role_id) references acct_role(id),
        foreign key(authority_id) references acct_authority(id)
    ) ;

    create table acct_user (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ;

    create table acct_user_role (
        user_id bigint not null,
        role_id bigint not null,
        foreign key(user_id) references acct_user(id),
        foreign key(role_id) references acct_role(id)
    ) ;

