package br.unirio.kipao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.unirio.kipao.helper.CustomRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories (repositoryBaseClass = CustomRepositoryImpl.class)
public class KipaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KipaoApplication.class, args);
	}

}
