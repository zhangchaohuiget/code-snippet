create table user_info
(
    user_id     INTEGER
        constraint user_info_pk
            primary key autoincrement,
    user_name   TEXT,
    create_by   TEXT,
    create_time TEXT,
    update_by   TEXT,
    update_time TEXT
);