
Docker 运行mongo容器的命令
docker run --name mongo -p 27017:27017 -v ~/docker-data/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin -d mongo

通过一些命令登录到容器当中
登录容器获得mongo bash，在bash当中直接使用mongo命令
docker exec -it mongo bash

mongo -u admin -p admin

--创建库
use springbucks
--创建用户
db.createUser({user:"springbucks",pwd:"springbucks",roles:[{role:"readWrite",db:"springbucks"}]})

show users

show collections

db.coffee.find()