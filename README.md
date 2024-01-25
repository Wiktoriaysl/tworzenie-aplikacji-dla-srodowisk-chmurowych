# Tworzenie aplikacji dla środowisk chmurowych

## Technologie:

|Element:| Technologia|
|-|-|
|baza danych:| MySQL|
|Backend:| springboot|
|Frontend:| react.js|

## Uruchomienie:

Projekt trzeba uruchomić za pomocą komendy `docker-compose up`, a następnie wejść na adres `http://localhost:3003`

## Funkcjonalności API:

`GET /users/export` eksportuje listę uzytkowników do pliku .csv

`GET /HealthCheck` funkcjonalność która zwraca status 200 w momencie kiedy aplikacja działa (ma połączenie di bazy danych), jezeli nie działa to zwraca status 500
