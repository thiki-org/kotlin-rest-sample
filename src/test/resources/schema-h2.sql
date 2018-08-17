

DROP TABLE IF EXISTS `tms_issue`;

CREATE TABLE `tms_issue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT ,
  `key` varchar(128) NOT NULL DEFAULT '' ,
  `reporter` int(11) unsigned NOT NULL ,
  `summary` varchar(512) NOT NULL DEFAULT '' ,
  `description` text ,
  `assignee` int(11) unsigned DEFAULT NULL ,
  `status` int(11) unsigned DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 ;


DROP TABLE IF EXISTS `tms_user`;

CREATE TABLE `tms_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT ,
  `name` varchar(256) NOT NULL DEFAULT '' ,
  `email` varchar(256) NOT NULL DEFAULT '' ,
  `key` varchar(256) NOT NULL DEFAULT '' ,
  `locale` varchar(128) DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10004 ;

