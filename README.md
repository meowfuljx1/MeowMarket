# ПРИЛОЖЕНИЕ ИСПОЛЬЗУЕТ:
- Java 17, Oracle OpenJDK 23.0.1
- Spring Boot 3.2.5 (Web, JPA, Security, Mail)
- MySQL
- Lombok
- Jakarta Activation API
- Maven
- Freemarker

# ЗАПУСК:
Создайте базу данных в MySQL.  
`CREATE DATABASE meowmarket;`

Откройте каталог `\MeowMarket` в IntelliJ IDEA.

Настройте SDK:  
File -> Project Structure -> SDK: Oracle OpenJDK 23.0.1  
https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html

Указажите следующие environment variables:
- DB_URL  
Например: `jdbc:mysql://localhost:3306/meowmarket`
- DB_USER
- DB_PASSWORD

Запустите метод main в классе MeowMarketApplication.  
Зайдите на `http://localhost:8080/`
