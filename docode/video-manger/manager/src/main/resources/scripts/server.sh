#!/bin/bash

source /home/work/.bash_profile

# 设置脚本路径

DEPLOY_DIR="/home/work/video-manager/@artifactId@"
cd $DEPLOY_DIR
JAR_NAME=@artifactId@.jar
ACTION=$1
ENV=$2

if [ -z ${ENV} ]; then
    ENV="prod"
fi

echo "env is $ENV"

#if [ "prod" = "${ENV}" -o "stress" = "${ENV}" ]; then
#    VM_OPTION="-XX:+UseG1GC -Xmx4g -Xms4g -Xss256k -XX:MaxGCPauseMillis=200\
# -verbose:gc\
# -XX:+HeapDumpOnOutOfMemoryError\
# -XX:+PrintGCDetails\
# -XX:+PrintGCTimeStamps\
# -XX:+PrintGCDateStamps\
# -Xloggc:${DEPLOY_DIR}/gc.log"
#fi

# 读取服务pid
get_pid()
{
        pid=`jps -l | grep ${JAR_NAME} | grep -v grep | awk '{print $1}'`
}

start()
{
    get_pid
    if [ ${pid} ]; then
	    echo "Process is already running..."
	    exit
    fi


    nohup java ${VM_OPTION} -Dspring.profiles.active=${ENV} -jar ${DEPLOY_DIR}/${JAR_NAME} >/dev/null 2>&1 &

    echo "Start success, pid is: "$!
}

stop()
{
    get_pid
    while [ -n "$pid" ]
    do
    echo "Stop process..."
        kill $pid
        sleep 2
        get_pid
    done

    echo "Stop success"
}

case ${ACTION} in
    'start')
        start
        ;;

    'stop')
        stop
        ;;

    'restart')
        stop && start
        ;;

    *)
        echo "Usage: server.sh start|stop|restart [env]";;
esac
