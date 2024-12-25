create table if not exists public.region
(
    adcode        int primary key not null,
    name          varchar(50),
    province_code int,
    province_name varchar(50),
    city_code     int,
    city_name     varchar(50),
    district_code int,
    district_name varchar(50),
    adlevel       varchar(20),
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);
