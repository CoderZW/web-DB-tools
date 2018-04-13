# web-DB-tools

# 简介
* 一个web版数据库执行工具,而且写sql有提示,关键字和表名提示,没有字段提示.
* 支持多条sql执行
* 单条SQL执行table形式返回，多条SQL已JSON并格式化好返回展示.
* 常用SQL收藏,编辑,删除,以及SQL分享给其他用户
* 支持添加用户,权限配置,只读,读写,dml等
* 支持定期密码更新提醒,只需一个配置设置密码必须更新的周期
* 附带一些常用的开发工具,JSON格式化,freemarker语法测试,域名whois查询,正则表达式测试,分片上传文件至项目所在服务器.
* 还有加了百度统计统计各个页面及用户分析,如需修改成自己,修改tj.ftl内容为自己的百度统计代码即可

# 技术框架及插件
* spring boot,spring mvc
* java,mysql
* freemarker,html,js,jquery,css
* bootstrap,layui

# 使用截图
![单条SQL执行](https://github.com/hammerLei/web-DB-tools/blob/master/photo/database1.png)
***
![多条SQL执行](https://github.com/hammerLei/web-DB-tools/blob/master/photo/database2.png)
***
![收藏](https://github.com/hammerLei/web-DB-tools/blob/master/photo/database3.png)
***
![导出结果](https://github.com/hammerLei/web-DB-tools/blob/master/photo/database5.png)
***
![导出结果](https://github.com/hammerLei/web-DB-tools/blob/master/photo/baidutj.png)

# 部署说明
* 服务器下基础java环境请自行配置
* deploy 成jar包
* 项目目录下主要包含这些文件和目录 conf  homethy-site-database-1.0-SNAPSHOT.jar  log  logs  restart.sh
* conf目录对象项目中的conf目录,里面文件内容打开看便知
* restart.sh 脚本如下,自行修改路径和端口
``` 
#!/bin/bash

jar_file=homethy-site-database-1.0-SNAPSHOT.jar
prot_file='Dserver.port=20101'

kill -9 `ps aux|grep $jar_file | grep $prot_file | grep java | grep -v grep | awk '{print $2}'`

echo "---------------------------kill done--------------------------------"

java -jar -Dserver.port=20101 -Dchime.application.name=site -Dspring.profiles.active=test homethy-site-database-1.0-SNAPSHOT.jar >> $(pwd)/log/catalina-$(date +%Y-%m-%d).log &

echo "restart success!"
``` 
