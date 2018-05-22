# basic 根据自己需要修改Dockerfile
# 命令范例 docker build -t jdkhome/basic:0.0.1 ./

FROM daocloud.io/library/java:openjdk-8u40
MAINTAINER main@jdkhome.com

# 拷贝可执行程序
COPY build/libs /var/app

# 端口
EXPOSE 7769

CMD ["java", "-jar", "-Dfile.encoding=UTF8", "-Duser.timezone=GMT+08", "-Dfastjson.parser.autoTypeSupport=true", "/var/app/jvmc-0.0.1-SNAPSHOT.jar"]

# docker run -d --name "basic" -e SPRING_PROFILES_ACTIVE=develop -p 8080:8080 -v /data/basic:/tmp -v /data/basic/logs:/tmp/logs jdkhome/basic:latest
