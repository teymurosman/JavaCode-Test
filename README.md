# JavaCode-Test
Проект выполнен в рамках тестового задания для компании JavaCode

<details>
  <summary><strong>Техническое задание</strong></summary>

Напишите приложение, которое по REST принимает запрос вида

POST api/v1/wallet
{
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000 }

после выполнять логику по изменению счета в базе данных также есть возможность получить баланс кошелька

GET api/v1/wallets/{WALLET_UUID}

стек:
java 8-17
Spring 3
Postgresql

Должны быть написаны миграции для базы данных с помощью liquibase

Обратите особое внимание проблемам при работе в конкурентной среде (1000 RPS по одному кошельку). Ни один запрос не должен быть не обработан (50Х error)

Предусмотрите соблюдение формата ответа для заведомо неверных запросов, когда кошелька не существует, не валидный json, или недостаточно средств.

приложение должно запускаться в докер контейнере, база данных тоже, вся система должна подниматься с помощью docker-compose

предусмотрите возможность настраивать различные параметры как на стороне приложения так и базы данных без пересборки контейнеров.

эндпоинты должны быть покрыты тестами.
</details>

## Требования
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop/)

## Как запустить проект
1) Склонировать проект
```bash
$ git clone https://github.com/teymurosman/JavaCode-Test.git
```
2) Конфигурация

В файле '.env' можно указать переменные окружения

3) Сборка maven
```
mvn clean package -DskipTests
```
4) Развёртывание docker контейнеров
```
docker-compose up
```
*Основной сервис поднимется на 8080 порту*
## Стек
Java 17, Spring Boot 3, Maven, Spring Data JPA, PostgreSQL, Docker, Mapstruct, Lombok
