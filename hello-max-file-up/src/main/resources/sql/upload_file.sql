CREATE TABLE `upload_file` (
    `file_id` VARCHAR(32) NOT NULL,
    `file_path` VARCHAR(128) NOT NULL COMMENT '文件存储路径',
    `file_size` VARCHAR(32) NOT NULL COMMENT '文件大小',
    `file_suffix` VARCHAR(25) NOT NULL COMMENT '文件后缀',
    `file_name` VARCHAR(32) NOT NULL COMMENT '文件名',
    `file_md5` VARCHAR(32) NOT NULL COMMENT '文件md5值',
    `create_time` TIMESTAMP DEFAULT '0000-00-00 00:00:00',
    `update_time` TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
	`file_status` INT NOT NULL COMMENT '文件状态',
    PRIMARY KEY (`file_id`)
)COMMENT '文件存储表';
