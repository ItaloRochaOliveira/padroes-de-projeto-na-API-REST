package edu.padraodeprojeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan("edu.padraodeprojeto")
public class PadraoDeProjetoNaApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadraoDeProjetoNaApiRestApplication.class, args);
	}

}
