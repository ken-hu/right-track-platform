-------------------------------------------------------------
--Default Authorization Server DDL
-------------------------------------------------------------
CREATE TABLE oauth2_authorization_consent
(
    registered_client_id varchar(100)  NOT NULL,
    principal_name       varchar(200)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);
/*
IMPORTANT:
    If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
    as PostgreSQL does not support the 'blob' data type.
*/
CREATE TABLE oauth2_authorization
(
    id                            varchar(100) NOT NULL,
    registered_client_id          varchar(100) NOT NULL,
    principal_name                varchar(200) NOT NULL,
    authorization_grant_type      varchar(100) NOT NULL,
    authorized_scopes             varchar(1000) DEFAULT NULL,
    attributes                    text          DEFAULT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      text          DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT NULL,
    authorization_code_expires_at timestamp     DEFAULT NULL,
    authorization_code_metadata   text          DEFAULT NULL,
    access_token_value            text          DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT NULL,
    access_token_expires_at       timestamp     DEFAULT NULL,
    access_token_metadata         text          DEFAULT NULL,
    access_token_type             varchar(100)  DEFAULT NULL,
    access_token_scopes           varchar(1000) DEFAULT NULL,
    oidc_id_token_value           text          DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT NULL,
    oidc_id_token_expires_at      timestamp     DEFAULT NULL,
    oidc_id_token_metadata        text          DEFAULT NULL,
    refresh_token_value           text          DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT NULL,
    refresh_token_expires_at      timestamp     DEFAULT NULL,
    refresh_token_metadata        text          DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);


-------------------------------------------------------------
--Business table
-------------------------------------------------------------
drop table if exists public.account;
create table public.account
(
    id              serial primary key,
    tenant_code     varchar(64),
    dept_codes      varchar(50)[],
    username        varchar(64),
    password        varchar(200),
    mobile          varchar(50),
    email           varchar(50),
    nickname        varchar(200),
    avatar          varchar(50),
    registered_from varchar(50),
    status          varchar(20)  default 'enabled',
    registered_at   timestamp(6) default CURRENT_TIMESTAMP,
    created_at      timestamp(6) default CURRENT_TIMESTAMP,
    updated_at      timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.account IS '账户信息';

drop table if exists public.third_account;
create table public.third_account
(
    id                     serial primary key,
    account_id             int not null,
    unique_id              varchar(64),
    username               varchar(64),
    registration_id        varchar(50),
    credentials            varchar(200),
    credentials_expires_at timestamp(6),
    type                   varchar(50),
    nickname               varchar(200),
    ext                    text,
    avatar_url             varchar(500),
    registered_at          timestamp(6) default CURRENT_TIMESTAMP,
    created_at             timestamp(6) default CURRENT_TIMESTAMP,
    updated_at             timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.third_account IS '第三方用户信息';


drop table if exists public.role;
create table public.role
(
    id          serial primary key,
    name        varchar(50),
    description text,
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.role IS '角色';

drop table if exists public.tenant;
create table public.tenant
(
    id          serial primary key,
    tenant_code varchar(64) not null unique,
    name        varchar(50),
    status      varchar(20)  default 'enabled',
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.tenant IS '租户信息';

drop table if exists public.department;
create table public.department
(
    id          serial primary key,
    tenant_code varchar(64) not null,
    code        varchar(64),
    parent_code varchar(64),
    name        varchar(64),
    description text,
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.department IS '部门信息';



drop table if exists public.policy;
create table public.policy
(
    id          serial primary key,
    app_code    varchar(200),
    policy_code varchar(200) unique,
    name        varchar(50),
    version     varchar(50),
    content     jsonb,
    description text,
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.policy IS '策略信息';

drop table if exists public.operation_log;
create table public.operation_log
(
    id             serial primary key,
    account_id     int not null,
    action         varchar(50),
    resource_type  varchar(50),
    resource       varchar(50),
    operating_time timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.operation_log IS '操作日志';

drop table if exists public.application;
create table public.application
(
    id          serial primary key,
    app_code    varchar(200) not null unique,
    name        varchar(50),
    description varchar(50),
    index_url   varchar(500),
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.application IS '应用';

drop table if exists public.user_group;
create table public.user_group
(
    id          serial primary key,
    tenant_code varchar(200),
    name        varchar(50),
    description varchar(200),
    created_at  timestamp(6) default CURRENT_TIMESTAMP,
    updated_at  timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.user_group IS '用户组';


drop table if exists public.user_group_rel;
create table public.user_group_rel
(
    id         serial primary key,
    user_id    int not null,
    group_id   int not null,
    created_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.user_group_rel IS '用户和用户组的关系';

drop table if exists public.role_policy_rel;
create table public.role_policy_rel
(
    id         serial primary key,
    role_id    integer,
    policy_id  integer,
    created_at timestamp(6) default CURRENT_TIMESTAMP,
    updated_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.role_policy_rel IS '角色和策略的关系';

drop table if exists public.user_role_rel;
create table public.user_role_rel
(
    id         serial primary key,
    user_id    integer,
    role_id    integer,
    created_at timestamp(6) default CURRENT_TIMESTAMP,
    updated_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.user_role_rel IS '用户和角色的关系';

drop table if exists public.user_policy_rel;
create table public.user_policy_rel
(
    id         serial primary key,
    user_id    int not null,
    policy_id  int not null,
    created_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.user_policy_rel IS '用户和策略的关系';


drop table if exists public.user_group_policy_rel;
create table public.user_group_policy_rel
(
    id         serial primary key,
    user_id    int not null,
    policy_id  int not null,
    created_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.user_group_policy_rel IS '用户组和策略的关系';



drop table if exists public.tenant_application_authorization;
create table public.tenant_application_authorization
(
    id         serial primary key,
    tenant_id  int not null,
    app_id     int not null,
    created_by varchar(50),
    created_at timestamp(6) default CURRENT_TIMESTAMP
);
COMMENT ON TABLE public.tenant_application_authorization IS '租户的应用授权';





