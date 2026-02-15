package com.ufpa.simupay; // O endereço do seu projeto

// Os dois imports que estavam faltando!
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimupayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimupayApplication.class, args);
    }
}