# heroes-admin

```
curl -i http://localhost:8083/connectors/

curl -i -X DELETE http://localhost:8083/connectors/account-source && curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @kafka-connect/account-source-connector-kube.json

kubectl -n kafka run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.29.0-kafka-3.2.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic account --from-beginning

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

```
kubectl config set-context --current --namespace=kafka

kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka

kubectl apply -f kafka-persistent-single.yaml -n kafka
kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka




kubectl port-forward service/connect-service 8083:8083
kubectl port-forward service/heroes-admin-service 8080:8080

```
