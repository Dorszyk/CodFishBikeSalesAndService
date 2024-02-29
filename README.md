# CodFishBikeSalesAndService

# Jak uruchomić projekt

1. Pobierz repozytorium
2. Otwórz projekt w IntelliJ

# Jak połączyć się z bazą danych

1. Dodaj nowe łączenie z Bazą Danych
2. Wybierz Typ Bazy Danych: "PostgreSQL" z listy dostępnych typów.
3. Konfiguracja Połączenia: Podaj szczegóły połączenia do swojej bazy danych PostgreSQL:

Port: Domyślny port dla PostgreSQL to 5432.
Database: cod_fish_bike
User: postgres
Password: postgres
Testuj Połączenie: Po wprowadzeniu wszystkich danych kliknij przycisk "Test Connection".

# Szczegóły połączenia znajdują się w application.yaml

Następnie uruchom program:
BikeSalesAndServiceApplication #RUN

dostępy dla Salesman:
login: user_1
hasło: password

dostępy dla PersonRepairing:
login: user_2
hasło: password

# wybierz przeglądarkę i wpisz adres:
http://localhost:8180/codfish-bike/ dla user_1 lub user_2

# w zależności, jaką osobą się zalogujesz, masz swoje uprawnienia w wyświetlaniu danych na stronie.
Jeżeli chcesz przeglądać program jako administrator bez użycia security (logowania, dostęp do wszystkich uprawnień), to w pliku application-local.yaml
masz wyłączony spring:security:enabled:false, oraz możesz uruchomić go lokalnie na innym localhost.
Szczegóły w pliku "active=local.png" znajdziesz jego lokalizację w commit "Starting the CodFishBikes Sales&Service software"

# wybierz przeglądarkę i wpisz adres:
http://localhost:8081/codfish-bike/ dla administratora

# jeżeli, chcesz sprawdzić napisane testy to w niektórych przypadkach testowych, potrzebny jest Docker-Container.  


