# heroes-admin

```
docker-compose exec mysql bash -c 'mysql -u $MYSQL_USER -p$MYSQL_PASSWORD heroes'

```

https://www.cloudskillsboost.google/focuses/22790?parent=catalog
```
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
export CLOUDSQL_SERVICE_ACCOUNT=cloudsql-service-account
gcloud iam service-accounts create $CLOUDSQL_SERVICE_ACCOUNT --project=$PROJECT_ID
gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:$CLOUDSQL_SERVICE_ACCOUNT@$PROJECT_ID.iam.gserviceaccount.com"  \
    --role="roles/cloudsql.admin"

gcloud iam service-accounts keys create $CLOUDSQL_SERVICE_ACCOUNT.json \
    --iam-account=$CLOUDSQL_SERVICE_ACCOUNT@$PROJECT_ID.iam.gserviceaccount.com \
    --project=$PROJECT_ID

kubectl create secret generic cloudsql-instance-credentials \
    --from-file=credentials.json=$CLOUDSQL_SERVICE_ACCOUNT.json

kubectl create secret generic cloudsql-db-credentials \
    --from-literal=username=mysqluser \
    --from-literal=password=mysqlpw \
    --from-literal=dbname=heroes

kubectl apply -f heroes-admin.yaml


kubectl expose deployment heroes-admin \
    --type "LoadBalancer" \
    --port 80 --target-port 8080


export LOAD_BALANCER_IP=$(kubectl get svc heroes-admin \
    -o=jsonpath='{.status.loadBalancer.ingress[0].ip}' -n default)
echo heroes-admin Load Balancer Ingress IP: http://$LOAD_BALANCER_IP

curl -d '{"name": "foo", "description":"bar"}' -H "Content-Type: application/json" -X POST localhost:8080/users
curl -X POST localhost:8080/admins/1
curl -d '{"name": "coro1", "type":"sardex"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/pools/admins/1
curl -d '{"name": "coro2", "type":"sardex"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/pools/admins/1
curl -d '{"name": "coro3", "type":"sardex"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/pools/admins/1
curl -d '{"name": "foooo", "type":"sardex"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/pools/admins/1

curl -d '{"name": "foooooo", "description":"bla"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/accounts/pools/1/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/accounts/pools/2/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/accounts/pools/3/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST http://$LOAD_BALANCER_IP/accounts/pools/4/users/1
```

```
curl -d '{"name": "foo", "description":"bar"}' -H "Content-Type: application/json" -X POST localhost:8080/users
curl -X POST localhost:8080/admins/1
curl -d '{"name": "coro1", "type":"sardex"}' -H "Content-Type: application/json" -X POST localhost:8080/pools/admins/1
curl -d '{"name": "coro2", "type":"sardex"}' -H "Content-Type: application/json" -X POST localhost:8080/pools/admins/1
curl -d '{"name": "coro3", "type":"sardex"}' -H "Content-Type: application/json" -X POST localhost:8080/pools/admins/1
curl -d '{"name": "foooo", "type":"sardex"}' -H "Content-Type: application/json" -X POST localhost:8080/pools/admins/1

curl -d '{"name": "foooooo", "description":"bla"}' -H "Content-Type: application/json" -X POST localhost:8080/accounts/pools/1/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST localhost:8080/accounts/pools/2/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST localhost:8080/accounts/pools/3/users/1
curl -d '{"name": "accc", "description":"bla"}' -H "Content-Type: application/json" -X POST localhost:8080/accounts/pools/4/users/1


kubectl port-forward service/heroes-admin-app 8080:8080
```
