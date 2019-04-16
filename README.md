# proz_banda_imbecyli

projekt na proz - aplikacja testowa

serwer - łączy się z bazą danych - wysyła testy do klientów

klienci - wyświetlają testy i wysyłają wyniki do serwera

wykładowca czy coś - tworzy nowe testy i wysyła do serwera, który dodaje do bazy danych

zbigniew stonoga - gnije w pierdlu

## Server install

```bash
git clone https://github.com/Daraniel1000/proz_banda_imbecyli.git
cd proz_banda_imbecyli/server
mvn test
mvn tomcat7:run
```

## Server docs

```
> GET /tests HTTP/1.1
> Host: localhost:8080
> 
< HTTP/1.1 200 OK
< Content-Type: application/json
< 
[ {
  "id" : 1,
  "name" : "Test pierwszy"
}, {
  "id" : 2,
  "name" : "Test drugi"
} ]

> POST /tests/1/solve HTTP/1.1
> Host: localhost:8080
> 
< HTTP/1.1 200 OK
< Content-Type: application/json
< 
{
  "id" : 1,
  "testId" : 1,
  "someMsg" : "bla bla bla something TODO"
}

> POST /tests/1/solve HTTP/1.1
> Host: localhost:8080
> 
< HTTP/1.1 200 OK
< Content-Type: application/json
< 
{
  "id" : 2,
  "testId" : 1,
  "someMsg" : "bla bla bla something TODO"
}

> POST /tests/1/solve/2/submit HTTP/1.1
> Host: localhost:8080
> Content-Type: application/json
> 
["1", "3", "zielony"]
< HTTP/1.1 200 OK
< Content-Type: application/json
< 
{
  "generatedTestId" : 2,
  "someMsg" : "Dobra robota, 2/10"
}

> POST /tests/1/solve/13/submit HTTP/1.1
> Host: localhost:8080
> Content-Type: application/json
> 
["1", "3", "zielony"]
< HTTP/1.1 404 Not Found
< Content-Type: application/json
< 
No such generated test found
```
