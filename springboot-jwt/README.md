## 基于Shiro+JWT可实现Token认证方式


## demo:

启动应用后

1. 先访问登录接口/login
```
curl -X POST http://localhost:9095/login -H 'Content-Type: application/json' -d '
{
    "username": "admin",
    "password": "12345678"
}
'
```

返回值：

``` json
{
    "success": true,
    "msg": "Login success",
    "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBpZCI6IjExMSIsImltZWkiOiJpbWVpIiwiZXhwIjoxNTM2NDg3NTM1LCJ1c2VybmFtZSI6ImFkbWluIn0.uat7rvVLwC7bcM-jRs329RWdHIFC6P-YN7YdJrdRUHE"
}
```

2. 使用token再去访问接口
```
curl -X GET http://localhost:9095/api/v1/join?imei=imei -H 'Content-Type: application/json' -H 'Authorization: 上面拿到的token值'
```