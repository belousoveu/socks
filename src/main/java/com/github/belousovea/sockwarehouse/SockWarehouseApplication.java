package com.github.belousovea.sockwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.github.belousovea.sockwarehouse.repository")
public class SockWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SockWarehouseApplication.class, args);
    }

}


/* TODO:
4. Логирование
6. Централизованная обработка исключений
8. Тестирование



 */