
import os
import mysql_config
import sqoop_tools
from mobula.utils import config_read
from mobula.utils import sql_read
from mobula.utils import common_utils
from mobula.utils import db_toolkit
from datetime import datetime

job_prop_file = os.getenv('JOB_PROP_FILE')
config = config_read.get_config(job_prop_file)

year = config.get('azkaban.flow.start.year')
month = config.get('azkaban.flow.start.month')
day = config.get('azkaban.flow.start.day')
hour = config.get('azkaban.flow.start.hour')
working_directory = config.get('working.dir')
key = config.get('key')

hdfs_dir = config.get('hdfs.dir').replace("\\","")

def create_data(time_day):

    # 导入hive所建外表中
    sql = sql_read.get_sql(working_directory + 'hql文件位置',
                                    {'time_day':time_day})
    print(sql)
    common_utils.execute_sql(sql)

# 导入到MySQL库中
def to_mysql(time_day):

    # 定义MySQL临时表
    tmp_table_name = '临时表_tmp_%s' % time_day
    table_name = '数据表名'

    db = db_toolkit.DBToolkit(mysql_config.MysqlConfig())
    print(mysql_config.MysqlConfig)

    # 删除MySQL中临时表相应的数据
    db.update('drop table if exists %s' % tmp_table_name)
    print('drop table if exists %s' % tmp_table_name)

    # 克隆表结构
    db.clone_table(table_name,tmp_table_name)
    print('clone table done',datetime.now())

    # 删除临时表数据
    db.update('truncate table %s' % tmp_table_name)
    print('truncate tmp table data done',datetime.now())

    # 数据表的字段名
    columns = ('字段名，。。。')
    db.close()

    # 将hdfs文件数据导入到MySQL中
    hdfs_dir_tmp = (hdfs_dir + '外表名字/day=%s' % (time_day))
    print(hdfs_dir_tmp)

    # 用sqoop从hive导出到mysql
    sqoop_export = sqoop_tools.from_hive_export_to_mysql(mysql_config.MysqlConfig(),
            tmp_table_name,hdfs_dir_tmp,columns)
    print(sqoop_export)

    (status, _) = common_utils.execute_shell(sqoop_export)
    if status != 0:
        import sys
        # 退出python程序
        sys.exit(1)
    print('export data from hdfs to MySQL tmp table done ',datetime.now())

    db = db_toolkit.DBToolkit(mysql_config.MysqlConfig())

    # 删除MySQL原表相应的数据
    db.update("delete from %s where data_date = '%s'" %(table_name,common_utils.to_mysql_day(time_day)))
    print("delete from %s where data_date = '%s'" %(table_name,common_utils.to_mysql_day(time_day)))

    # 复制MySQL临时表数据到原表
    copy_sql = ('insert into %s('+columns+') select * from %s ') %(table_name,tmp_table_name)
    db.update(copy_sql)
    print('insert data to MySQL table done ',datetime.now())

    # 删除Mysql临时表
    db.drop_table(tmp_table_name)
    print('drop MySQL tmp table done ',datetime.now())

    db.close()


def main():

    time_day = common_utils.before_day(year,month,day,'1')
    print('time_day: ', time_day)
    create_data(time_day)
    to_mysql(time_day)
    
    time_day_0 = common_utils.before_day(year,month,day,'0')
    print(time_day_0)
    create_data(time_day_0)
    to_mysql(time_day_0)
    

if __name__ == '__main__':
    main()