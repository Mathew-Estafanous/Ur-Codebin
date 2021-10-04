CREATE TABLE user_accounts (
    account_id bigint PRIMARY KEY NOT NULL,
    account_username varchar(255) NOT NULL,
    account_email varchar(255) NOT NULL,
    account_password varchar(255) NOT NULL
);

CREATE TABLE code_paste (
    paste_id binary(16) not null,
    paste_expiration timestamp,
    paste_title varchar(255),
    paste_syntax varchar(255),
    paste_visibility varchar(255),
    account_id bigint,
    primary key (paste_id),
    foreign key (account_id) REFERENCES user_accounts(account_id)
);

CREATE TABLE source_table (
    source_id binary(16) not null,
    source_code MEDIUMTEXT,
    primary key (source_id)
);