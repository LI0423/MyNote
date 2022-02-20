# -*- coding:utf-8 -*-
"""
mysql_config utils
"""
import os

from mobula.utils import config_read
job_prop_file = os.getenv('JOB_PROP_FILE')
config = config_read.get_config(job_prop_file)

class MysqlConfig(object):
    """wallpaper的mysql配置"""
    def __init__(self):
        super(WallPaperMysqlConfig,self).__init__()
        self.mysql_host = config.get('mysql.host')
        self.mysql_port = config.get('mysql.port')
        self.mysql_user = config.get('mysql.user')
        self.mysql_password = config.get('mysql.password').replace("\\","")
        self.mysql_db = config.get('mysql.db')

    def __str__(self):
        return 'mysql_host' + self.mysql_host + \
               'mysql_port' + self.mysql_port + \
               'mysql_user' + self.mysql_user + \
               'mysql_password' + self.mysql_password + \
               'mysql_db' + self.mysql_db

