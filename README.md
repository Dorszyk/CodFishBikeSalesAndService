# CodFishBikeSalesAndService

Aplikacja wspomagająca zarządzanie sprzedażą i serwisem rowerów.

## 🚀 Jak uruchomić projekt

### 1. Pobranie repozytorium
Sklonuj repozytorium na swój dysk lokalny:
```bash
git clone https://github.com/twoj-uzytkownik/CodFishBikeSalesAndService.git
cd CodFishBikeSalesAndService
```

### 2. Konfiguracja bazy danych
Projekt wymaga bazy danych **PostgreSQL**.
1. Upewnij się, że masz zainstalowanego PostgreSQL (np. przez Docker lub instalację lokalną).
2. Stwórz bazę danych o nazwie: `cod_fish_bike`.
3. Domyślne dane logowania w `application.yaml`:
   - **Port:** 5432
   - **Database:** `cod_fish_bike`
   - **User:** `postgres`
   - **Password:** `postgres`

*Uwaga: Schemat bazy danych zostanie utworzony automatycznie przy starcie aplikacji dzięki Flyway.*

### 3. Uruchomienie aplikacji w IntelliJ IDEA
1. Otwórz projekt w IntelliJ (wybierz plik `build.gradle` jako projekt).
2. Poczekaj na załadowanie zależności Gradle.
3. Uruchom klasę główną: `BikeSalesAndServiceApplication`.

---

## 🔐 Logowanie i uprawnienia

Aplikacja posiada zaimplementowany system Spring Security. Po uruchomieniu wejdź na:
👉 [http://localhost:8180/codfish-bike/](http://localhost:8180/codfish-bike/)

### Dostępne konta testowe:
| Rola | Login | Hasło |
| :--- | :--- | :--- |
| **Salesman** | `user_1` | `password` |
| **PersonRepairing** | `user_2` | `password` |

*Uprawnienia na stronie zmieniają się dynamicznie w zależności od tego, kto jest zalogowany.*

---

## 🛠 Tryb Administratora (Lokalny bez Security)

Jeśli chcesz przeglądać program z pełnymi uprawnieniami administratora bez konieczności logowania, możesz użyć profilu `local`. 

1. Uruchom aplikację z aktywnym profilem `local`. W IntelliJ możesz to zrobić dodając `-Dspring.profiles.active=local` do VM Options w konfiguracji uruchamiania.
2. W tym trybie Spring Security jest wyłączony (`enabled: false`).
3. Adres dla administratora: [http://localhost:8081/codfish-bike/](http://localhost:8081/codfish-bike/)

---

## 🧪 Testy

Aby uruchomić testy integracyjne, wymagany jest zainstalowany i uruchomiony **Docker** (wykorzystywany przez Testcontainers do postawienia tymczasowej bazy danych).

```bash
./gradlew test
```

---

## 📝 Technologie
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL & Flyway
- Thymeleaf
- Testcontainers (do testów integracyjnych)


