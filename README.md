#Добро пожаловать в проект Финансовый Менеджер! 
Это приложение позволяет пользователям управлять своими финансами, создавая пользователей, добавляя расходы и доходы, а также создавая группы пользователей с установленными лимитами расходов.

#Приложение предоставляет следующие функции:
  1. Создание пользователей: Пользователи могут регистрироваться и управлять своими данными. 
  2. Добавление расходов и доходов: Пользователи могут добавлять свои финансовые операции, отслеживая свои доходы и расходы. 
  3. Создание групп пользователей: Администратор может объединять в группы пользователей для совместного управления финансами. 
  4. Установка лимитов расходов: Для групп пользователей можно устанавливать лимиты на расходы.

#Использование
После запуска приложения вы можете использовать одни из некоторых конечных точек API:
1. Создание пользователя: POST /api/users/create 
2. Добавление дохода: POST /api/incomes/create 
3. Добавление расхода: POST /api/expenses/create 
4. Создание группы пользователей: POST /user-groups 
5. Установка лимита расходов для группы: PUT /user-groups/{groupId}/expense-limit

#Технологии
Java 21, Spring Boot, Spring Web, Hibernate ,PostgreSQL 

#Планируемые изменения
1. Swagger
2. ExceptionHandler

