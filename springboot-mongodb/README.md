## 集成MongoDB

SpringBoot集成MongoDB

## 安装MongoDB数据库

[官网安装](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-red-hat/)

安装完成后，配置数据库的账号和密码：

```
mongo --port 27017
use test
db.createUser(
   {
     user: "river",
     pwd: "123456",
     roles: [ { role: "readWrite", db: "test" } ]
   }
 )
```