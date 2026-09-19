package uel.br.batalha_gladiador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// scanBasePackages inclui os pacotes controller e service, que ficam fora
// da sub-árvore deste pacote (uel.br.batalha_gladiador).
// EntityScan aponta onde estão as entidades (model) e EnableJpaRepositories onde ficam os repositories.
@SpringBootApplication(scanBasePackages = {"uel.br.batalha_gladiador", "controller", "service"})
@EntityScan("model")
@EnableJpaRepositories("repository")
public class BatalhaGladiadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatalhaGladiadorApplication.class, args);
    }
}