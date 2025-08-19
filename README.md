# User Management Service with Kafka Events

Микросервис для управления пользователями с event-driven архитектурой на Spring Boot 3 и Apache Kafka.

## Особенности

- **Полный CRUD API** для управления пользователями
- **Event-sourcing** через Kafka (события CREATE/UPDATE/DELETE)
- **Готовый Docker-стек** (Kafka + Zookeeper + PostgreSQL)
- **Логирование операций**
- **Чистая архитектура** (Controller-Service-Repository)

## Технологии

| Компонент       | Технологии                          |
|-----------------|-------------------------------------|
| Backend         | Kotlin, Spring Boot 3 (Web, Data JPA) |
| База данных     | PostgreSQL, Hibernate               |
| Месседжинг     | Apache Kafka, Spring Kafka          |
| Инфраструктура  | Docker, Docker Compose              |

## Запуск проекта
1. Запустить docker daemon
2. Клонировать репозиторий и запустить:  
   ```bash
   https://github.com/Mis0in/CRUD-microservice.git
   cd CRUD-microservice
   docker compose -f docker-compose.yml up --build
   ```
3. API будет доступно на: `http://localhost:8080/api/users`

## API Endpoints

| Метод | Путь            | Описание                  | Пример тела запроса            |
|-------|-----------------|---------------------------|--------------------------------|
| POST  | /api/users      | Создать пользователя      | `{"name":"John", "email":"john@example.com"}` |
| GET   | /api/users      | Получить всех пользователей | -                              |
| GET   | /api/users/{id} | Получить пользователя по ID | -                              |
| PUT   | /api/users/{id} | Обновить пользователя     | `{"name":"John Updated", "email":"new@example.com"}` |
| DELETE| /api/users/{id} | Удалить пользователя      | -                              |

## Примеры событий Kafka

Событие создания пользователя:
```json
{
  "eventId": "550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2023-07-28T12:00:00Z",
  "userId": 1,
  "eventType": "user.created"
}
```
