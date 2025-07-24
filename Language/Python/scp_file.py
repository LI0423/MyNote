
import re
import time
import paramiko

class UploadFileAndDeploy:
    def __init__(self, host, port, username, password) -> None:
        self.client = None
        self.host = host
        self.port = port
        self.username = username
        self.password = password
        self.sftp_client= None
        self.chan = None

    def connect(self):
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(self.host, self.username, self.username, self.password, 5)
        self.chan = self.client.invoke_shell()

    def close(self):
        self.client.close()

    # 上传文件，本地路径 -> 目标路径
    def uploadFile(self, local_path, dest_path):
        self.sftp_client = self.client.open_sftp()
        self.sftp_client.put(local_path, dest_path)
        self.sftp_client.close()

    # 下载文件，目标路径 -> 本地路径
    def downloadFile(self, dest_path, local_path):
        self.sftp_client = self.client.open_sftp()
        self.sftp_client.get(dest_path, local_path)
        self.sftp_client.close()

    # 执行命令
    def executeCommand(self, command: str):
        try:
            self.sendCommand(command)
            print('执行命令')
        except Exception as e:
            print(e)

    def sendCommand(self, command: str):
        command += command + '\r'
        p = re.compile(r'\[root@.+\s.\*]')
        self.result = ''
        self.chan.send(command)
        while True:
            time.sleep(0.5)
            ret = self.chan.recv(65535).decode("utf-8")
            self.result = self.result + ret
            if p.search(ret):
                break

    def deploy(self, local_path, dest_path):
        self.connect()
        # self.uploadFile(local_path, dest_path)
        self.executeCommand(' cd /opt/deploy_environment/middleExam/server/chengde')
        self.executeCommand(' ps -ef |grep PhysicalMiddleExam')

if __name__ == '__main__':
    deploy = UploadFileAndDeploy('114.215.29.139', 22, 'root', 'ring@clod@11234')
    deploy.deploy('', '')


    




# 下载
# sftp.get('/root/server.log', '/Users/litengjiang/Downloads/server.log')
# sftp.close()
# client.close()



# stdin, stdout, stderr = client.exec_command('pwd')
# res = stdout.read()
# print(res)
# client.close()
