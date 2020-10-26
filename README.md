# readme

нет встроенного tomcat.
___
тест запросов: /swagger-ui.html
___
настройки postgres: resources/db
___

curl -X POST "http://localhost:8080/" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"coffeeType\": \"CAPPUCCINO\",  \"sugar\": 0}"
___
curl -X GET "http://localhost:8080/coffee/10003" -H  "accept: application/json"
___
curl -X GET "http://localhost:8080/" -H  "accept: application/json"
___