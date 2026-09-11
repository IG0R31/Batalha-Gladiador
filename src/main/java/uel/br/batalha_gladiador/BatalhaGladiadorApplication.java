package uel.br.batalha_gladiador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// scanBasePackages inclui os pacotes controller e service,
// que ficam fora da sub-árvore deste pacote (uel.br.batalha_gladiador).
@SpringBootApplication(scanBasePackages = {"uel.br.batalha_gladiador", "controller", "service"})
public class BatalhaGladiadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatalhaGladiadorApplication.class, args);
    }
}