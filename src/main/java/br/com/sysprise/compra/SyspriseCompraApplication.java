package br.com.sysprise.compra;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class SyspriseCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyspriseCompraApplication.class, args);
	}

}
