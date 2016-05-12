CREATE TABLE `timer_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `cmd_text` text NOT NULL,
  `timer` varchar(255) DEFAULT NULL,
  `cmd_format` text NOT NULL,
  `rundir` varchar(255) DEFAULT NULL,
  `depends` varchar(255) DEFAULT NULL,
  `dscrption` text,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `run_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_key` varchar(255) DEFAULT NULL,
  `cmd_text` text NOT NULL,
  `cmd_format` text NOT NULL,
  `rundir` varchar(255) DEFAULT NULL,
  `batch` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `taskKey` (`task_key`),
  KEY `batch` (`batch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
