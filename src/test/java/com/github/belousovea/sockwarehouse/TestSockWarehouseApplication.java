package com.github.belousovea.sockwarehouse;

import org.springframework.boot.SpringApplication;

public class TestSockWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.from(SockWarehouseApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
