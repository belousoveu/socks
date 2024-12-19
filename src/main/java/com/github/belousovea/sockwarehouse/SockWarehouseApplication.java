package com.github.belousovea.sockwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SockWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SockWarehouseApplication.class, args);
    }

}


/* TODO:
1. Методы контроллера
2. Загрузка файла Excel/CSV во временную директорию на диске
3. Кэширование запросов
4. Логирование
5. Гибкие критерии поиска
6. Централизованная обработка исключений
7. Swagger
8. Тестирование



 */