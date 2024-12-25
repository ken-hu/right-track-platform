-- 随机生成uuid
select gen_random_uuid();

-- 清理数据
select *
from oauth2_authorization_consent;
select *
from oauth2_authorization;
truncate table oauth2_authorization_consent;
truncate table oauth2_authorization;


-- 初始化一个admin账号
INSERT INTO public.account (id, username, password, mobile, email, nickname, avatar, registered_from, status,
                            registered_at, created_at, updated_at)
VALUES (-1, 'admin', '$2a$10$cmRLXexu5oSoX2PsDKUB9eoAnG5dIAU3OVBqYF/1N4NtKuGHeCZv6', null, null, null, null, null,
        'enabled', now(), now(), now());

-- 初始化一些policy测试
insert into policy(app_code, policy_code, name, version, content, description)
values ('channel', 'default-user', '默认测试策略', '2024-12-11', '[
  {
    "id": "id_90ca5bd38344",
    "version": "version_f35013c94d48",
    "name": "name_5fe91a556fdf",
    "description": "description_f03b843b1832",
    "statements": [
      {
        "id": "test",
        "effect": "Deny",
        "actions": [
          "rt:UsesListV1"
        ],
        "resources": [
          "*"
        ]
      }
    ]
  },
  {
    "id": "id_90ca5bd383442",
    "version": null,
    "name": "name_5fe91a556fdf",
    "description": "description_f03b843b1832",
    "statements": [
      {
        "id": "test",
        "effect": "Deny",
        "actions": [
          "rt:TestGet"
        ],
        "resources": [
          "rt:category/123",
          "rt:category/124"
        ]
      }
    ]
  },
  {
    "id": "id_90ca5bd383443",
    "version": null,
    "name": "name_5fe91a556fdf",
    "description": "description_f03b843b1832",
    "statements": [
      {
        "id": "test",
        "effect": "Deny",
        "actions": [
          "map:GetCityV1",
          "map:GetCityCategoryV1",
          "map:GetRegionPoisV1",
          "map:ListRegionCitiesV1"
        ],
        "resources": [
          "rt:map:region/${authority_city}",
          "rt:map:category/11111",
          "rt:map:category/2"
        ]
      }
    ]
  }
]', 'nothing'),
       ('channel', 'admin', '超级管理员', '2024-12-25', '[
         {
           "id": "id_90ca5bd38344",
           "version": "version_f35013c94d48",
           "name": "admin_policy",
           "description": "description",
           "statements": [
             {
               "id": "admin",
               "effect": "Allow",
               "actions": [
                 "rt:*"
               ],
               "resources": [
                 "*"
               ]
             }
           ]
         }
       ]', 'admin');


insert into user_policy_rel(user_id, policy_id)
values (-1, 1),
       (-1, 2);

-- 初始化应用
INSERT INTO public.application (id, app_code, name, description, index_url, created_at, updated_at)
VALUES (1, 'channel', '数说睿见', '测试', null, '2024-12-11 13:18:52.455140', '2024-12-11 13:18:52.455140');
INSERT INTO public.application (id, app_code, name, description, index_url, created_at, updated_at)
VALUES (2, 'channel-admin', '数说睿见管理后台', null, null, '2024-12-11 13:19:11.883068', '2024-12-11 13:19:11.883068');

