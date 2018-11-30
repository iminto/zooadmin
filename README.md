# Zoo Admin
Zoo Admin - 在线 zookeeper管理工具，基于Jfinal+Beetl开发。

------

您可以使用 Zoo Admin：
==

> * 方便的查看zookeeper的节点和节点数据
> * 快捷的添加、删除和级联删除节点
> * 快捷的编辑节点数据，目前只支持文本数据
> * 登陆权限控制
> * zookeeper 四字命令的支持
> * zookeeper 服务端日志查看（待完善）
> * 灵活的配置和启动方式

Quick Start
==

直接执行以下命令运行项目即可：

    chmod u+x ./start.sh
    ./start.sh
也可以使用mvn clean install && mvn jetty:run 运行

项目启动后，访问 [http://localhost:9000][1]，显示如下
![主页][2]
![日志查看][3]
默认用户名和密码均是root,密码可到user.properties文件中配置,默认启动端口也可到里面配置
------

问题和建议
==

waitfox@qq.com

------

  [1]: http://localhost/index.html
  [2]: ./doc/img/index.png
  [3]: ./doc/img/zoo-log.png
