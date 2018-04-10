CREATE TABLE sitebuilt.database_user_info
(
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    account VARCHAR(20) DEFAULT '' COMMENT '用户名',
    password VARCHAR(100) DEFAULT '' COMMENT '密码'
);
ALTER TABLE sitebuilt.database_user_info ADD last_login_time DATETIME DEFAULT current_timestamp NULL;
ALTER TABLE sitebuilt.database_user_info ADD create_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL;
ALTER TABLE sitebuilt.database_user_info ADD update_time DATETIME DEFAULT current_timestamp NULL;
ALTER TABLE sitebuilt.database_user_info ADD level INT(4) DEFAULT 0 NULL;
ALTER TABLE sitebuilt.database_user_info ADD last_login_ip VARCHAR(40) DEFAULT '' COMMENT '最后登录的机器ip';

CREATE UNIQUE INDEX database_user_info_account_uindex ON sitebuilt.database_user_info (account);

CREATE TABLE sitebuilt.database_user_sql_history
(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    user_id INT(11) DEFAULT 0 COMMENT 'database_user_info表对应的id',
    sql_detail VARCHAR(2000) DEFAULT '' COMMENT '执行的SQL详情',
    create_time DATETIME DEFAULT current_timestamp COMMENT '记录创建时间即执行SQL时间',
    client_ip VARCHAR(40) DEFAULT '' COMMENT '执行sql时登录的客户端ip',
    server_ip VARCHAR(40) DEFAULT '' COMMENT '执行SQL的服务端ip',
    env VARCHAR(20) DEFAULT '' COMMENT '执行的数据库环境：test、stage、production'
) DEFAULT CHARSET=utf8;

CREATE TABLE sitebuilt.database_user_favorite
(
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    user_id INT(11) DEFAULT 0 COMMENT 'database_user_info表对应的id',
    favorite_sql_detail VARCHAR(2000) DEFAULT '' COMMENT '收藏的sql',
    name VARCHAR(100) DEFAULT '' COMMENT '给收藏sql取个名字吧',
    create_time DATETIME DEFAULT current_timestamp COMMENT '创建时间',
    update_time DATETIME DEFAULT current_timestamp COMMENT '更新时间',
    delete_flag INT(2) DEFAULT 0 COMMENT '是否删除,0:否，1：是',
    client_ip VARCHAR(40) DEFAULT '' COMMENT '客户端ip',
    order_sort INT(4) DEFAULT 0 COMMENT '排序'
) DEFAULT CHARSET=utf8;

ALTER TABLE sitebuilt.database_user_info ADD last_update_password_time DATETIME DEFAULT current_timestamp COMMENT '最后一次更新密码时间';

ALTER TABLE sitebuilt.database_user_info ADD version INT DEFAULT 0 COMMENT '修改版本,亦可用于乐观锁';

ALTER TABLE sitebuilt.database_user_info ADD email VARCHAR(40) DEFAULT '' COMMENT '个人邮箱';

ALTER TABLE database_user_info ADD `online_level` int(4) default '0' null;

UPDATE database_user_info SET online_level = `level`;

ALTER TABLE database_user_info ADD `status` int(4) default '1' null;

ALTER TABLE database_user_favorite MODIFY COLUMN favorite_sql_detail VARCHAR(5000) default '' null;

ALTER TABLE database_user_info ADD `default_schema` VARCHAR(20) default 'sitebuilt' null;

ALTER  TABLE  database_user_sql_history  ADD  INDEX(user_id);