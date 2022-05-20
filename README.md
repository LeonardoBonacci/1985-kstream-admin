# heroes-admin

```
curl -X DELETE http://localhost:8083/connectors/heroes-connector
curl -i http://localhost:8083/connectors/heroes-connector/

curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @register-mysql-heroes.json

docker-compose exec mysql bash -c 'mysql -u $MYSQL_USER -p$MYSQL_PASSWORD heroes'


```


```
curl -d '{"name": "foo", "description":"bar"}' -H "Content-Type: application/json" -X POST localhost:8080/users
curl localhost:8080/users/1

curl -X POST localhost:8080/admins/1

curl -d '{"name": "coro", "type":"sardex"}' -H "Content-Type: application/json" -X POST localhost:8080/pools/admins/1
curl localhost:8080/pool/1

curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST localhost:8080/accounts/pools/1/users/1


kubectl port-forward service/heroes-admin-app 8080:8080
```