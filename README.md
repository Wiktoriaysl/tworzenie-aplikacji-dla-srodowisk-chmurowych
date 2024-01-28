# Tworzenie aplikacji dla środowisk chmurowych

## Temat projketu:

Aplikacja typu CRUD do zarządzania listą uzytkowników

## Autor:

Wiktoria Szendzielorz, numer indeksu: 38012

## Technologie:

|Element:| Technologia|
|-|-|
|baza danych:| MySQL|
|Backend:| springboot|
|Frontend:| react.js|

## Uruchomienie:

Projekt trzeba uruchomić za pomocą komendy `docker compose up`, a następnie wejść na adres `http://localhost:3003`.
API dostępne jest pod adresem `http://localhost:8083`

## Funkcjonalności API:

`GET /users/export` eksportuje listę uzytkowników do pliku .csv

`GET /HealthCheck` funkcjonalność która zwraca status 200 w momencie kiedy aplikacja działa (ma połączenie do bazy danych), jezeli nie działa to zwraca status 500
