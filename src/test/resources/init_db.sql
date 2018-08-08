

DROP TABLE IF EXISTS `tms_issue`;

CREATE TABLE `tms_issue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `key` varchar(128) NOT NULL DEFAULT '' COMMENT 'human-readable-key',
  `reporter` int(11) unsigned NOT NULL COMMENT 'reporter''s user-id',
  `summary` varchar(512) NOT NULL DEFAULT '' COMMENT 'short summary which will be displayed in cards',
  `description` text COMMENT 'detail content',
  `assignee` int(11) unsigned DEFAULT NULL COMMENT 'user-id who will work for this issue',
  `status` int(11) unsigned DEFAULT NULL COMMENT 'status-id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='an issue is a task.';

INSERT INTO `tms_issue` (`id`, `key`, `reporter`, `summary`, `description`, `assignee`, `status`)
VALUES
	(1, 'test-1', 10001, 'a test task1', NULL, NULL, 10002),
	(2, 'test-2', 10003, 'a test task2', 'contents', 10001, 10002);


DROP TABLE IF EXISTS `tms_user`;

CREATE TABLE `tms_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT 'display name',
  `email` varchar(256) NOT NULL DEFAULT '' COMMENT 'email',
  `key` varchar(256) NOT NULL DEFAULT '' COMMENT 'human-readable-key',
  `locale` varchar(128) DEFAULT NULL COMMENT 'for i18n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8mb4 COMMENT='user';

INSERT INTO `tms_user` (`id`, `name`, `email`, `key`, `locale`)
VALUES
	(10001, 'zhangshan', 'zhangshan@thiki.net', 'zhangshan', 'zh_CN'),
	(10003, 'lisi', 'lisi@thiki.net', 'lisi', 'zh_CN');
