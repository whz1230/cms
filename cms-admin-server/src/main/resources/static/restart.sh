#!/bin/bash
#在执行shell输入框中加入BUILD_ID=dontKillMe即可防止jenkins杀死启动的进程
export BUILD_ID=dontkillme
jar_name="cms-admin-server-1.0-SNAPSHOT"
echo "stop服务开始"
pid_list=`ps -ef|grep ${jar_name} | grep -v "grep"|awk '{print $2}'`
echo dir=${pid_list}
if [ "${pid_list}" = "" ]; then
    echo "no ${jar_name} pid alive"
else
    echo "${jar_name} Id list :${pid_list}"
    for pid in ${pid_list}
    { kill -9 ${pid}
      echo "KILL ${pid}:"
      echo "service stop success"
    }
fi
echo "stop服务脚本结束"
echo "start服务脚本开始"
JAVA_HOME=/usr/local/java/jdk1.8.0_241
dir=/root/.jenkins/workspace/cms/cms-admin-server/target
cd ${dir}
echo dir=${dir}
jar=$(find /lib -type f -name *.jar)
classpath=${dir}/*:${dir}/lib/*:${JAVA_HOME}/lib/tools.jar:${JAVA_HOME}/lib/dt.jar
echo classpath=${classpath}
echo ---------------------------------------------
nohup ${JAVA_HOME}/bin/java -classpath ${classpath} -XX:-UseGCOverheadLimit -Xms1024m -Xmx2048m -jar ${dir}/${jar_name}.jar  > ${dir}/$(date +'%Y%m%d').log &