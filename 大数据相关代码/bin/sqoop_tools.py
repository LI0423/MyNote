#!/usr/bin/env python3
# -*- coding:utf-8 -*-

"""sqoop工具类
"""


def from_hive_export_to_mysql(mysql_config, table, hdfs_dir,columns):
    """把hive中的数据导出到mysql中
    """
    if len(mysql_config.mysql_password) == 0:
        sqoop_export = ("sqoop export "
                        "--connect jdbc:mysql://%s:%s/%s  "
                        "--username %s "
                        "--table %s "
                        "--input-null-string '\\\\N' "
                        "--input-null-non-string '\\\\N' "
                        "--input-fields-terminated-by '\\t' "
                        "--input-lines-terminated-by '\\n' "
                        "--export-dir %s "
                        "--num-mappers 3 ") % \
                       (mysql_config.mysql_host, mysql_config.mysql_port,
                        mysql_config.mysql_db, mysql_config.mysql_user,
                        table, hdfs_dir)
        return sqoop_export
    else:
        sqoop_export = ("sqoop export "
                        "--connect jdbc:mysql://%s:%s/%s  "
                        "--username %s "
                        "--password '%s' "
                        "--table %s "
                        "--input-null-string '\\\\N' "
                        "--input-null-non-string '\\\\N' "
                        "--input-fields-terminated-by '\\t' "
                        "--input-lines-terminated-by '\\n' "
                        "--export-dir %s "
                        "--columns '%s' "
                        "--num-mappers 3 ") % \
                       (mysql_config.mysql_host, mysql_config.mysql_port,
                        mysql_config.mysql_db, mysql_config.mysql_user,
                        mysql_config.mysql_password, table, hdfs_dir, columns)
        return sqoop_export



def from_mysql_import_to_hive(mysql_config, table=None,hive_table=None, query=None, maps_size=1):
    """
    # mysql中导出数据到hive中
    :param mysql_config:
    :param table:
    :param query:
    :param maps_size:
    :return:
    """
    if table and query:
        sqoop_import = ("sqoop import  "
                        "-D mapreduce.map.memory.mb=6144 "
                        "-D mapreduce.reduce.memory.mb=6144 "
                        "--connect jdbc:mysql://%s:%s/%s "
                        "--driver com.mysql.jdbc.Driver "
                        "--username %s "
                        "--password '%s' "
                        "--split-by id "
                        "--hive-import "
                        "--hive-database "
                        "--num-mappers %s "
                        "--fields-terminated-by '\\t' "
                        "--query '%s' "
                        "--hive-table %s "
                        "--null-string '\\\\N' "
                        "--null-non-string '\\\\N' "
                        "--target-dir %s "
                        "--hive-overwrite") % \
                       (mysql_config.mysql_host, mysql_config.mysql_port,
                        mysql_config.mysql_db, mysql_config.mysql_user,
                        mysql_config.mysql_password, maps_size, query, hive_table, table)
    elif table:
        sqoop_import = ("sqoop import  "
                        "-D mapreduce.map.memory.mb=6144 "
                        "-D mapreduce.reduce.memory.mb=6144 "
                        "--connect jdbc:mysql://%s:%s/%s "
                        "--driver com.mysql.jdbc.Driver "
                        "--username %s "
                        "--password '%s' "
                        "--split-by id "
                        "--hive-import "
                        "--hive-database "
                        "--num-mappers %s "
                        "--fields-terminated-by '\\t' "
                        "--table %s "
                        "--null-string '\\\\N' "
                        "--null-non-string '\\\\N' "
                        "--hive-overwrite") % \
                       (mysql_config.mysql_host, mysql_config.mysql_port, mysql_config.mysql_db,
                        mysql_config.mysql_user, mysql_config.mysql_password, maps_size, table)

    return sqoop_import
