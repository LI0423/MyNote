import mysql.connector
from mysql.connector import Error


def export_data_to_file(file_path):
    try:
        # 连接数据库
        connection = mysql.connector.connect(
            host='localhost',
            database='mydatabase',
            user='myusername',
            password='mypassword'
        )

        if connection.is_connected():
            cursor = connection.cursor()
            # 查询表数据
            cursor.execute("SELECT * FROM mytable")
            rows = cursor.fetchall()

            # 写入数据到文件中
            with open(file_path, 'w') as file:
                for row in rows:
                    file.write(','.join(str(value) for value in row) + '\n')

            print("成功导出数据到文件：" + file_path)

    except Error as e:
        print("导出数据失败：", e)

    finally:
        # 关闭连接
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("数据库连接已关闭。")


if __name__ == '__main__':
    # 导出数据到文件夹
    export_data_to_file('data.txt')