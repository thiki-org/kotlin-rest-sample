
INSERT INTO `tms_issue` (`id`, `key`, `reporter`, `summary`, `description`, `assignee`, `status`)
VALUES
	(1, 'test-1', 10001, 'a test task1', NULL, NULL, 10002),
	(2, 'test-2', 10003, 'a test task2', 'contents', 10001, 10002);


INSERT INTO `tms_user` (`id`, `name`, `email`, `key`, `locale`)
VALUES
	(10001, 'zhangshan', 'zhangshan@thiki.net', 'zhangshan', 'zh_CN'),
	(10003, 'lisi', 'lisi@thiki.net', 'lisi', 'zh_CN');
