package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Random;

@Embeddable
public class AtributosBatalha{

    @Column(nullable = false)
    private double forca;

    @Column(nullable = false)
    private double agilidade;

    @Column(nullable = false)
    private double stamina;

    private final double forcaBase =  100;
    private final double agilidadeBase =  50;
    private final double staminaBase =  75;

    //JPA precisa do construtor sem argumentos para recriar o objeto com os valores do banco
    public AtributosBatalha() {
    }

    AtributosBatalha(Gladiador gladiador){
        double randomFactor = gladiador.getTierFactor();
        Random random = new Random();

        forca = (forcaBase + ((1 + (random.nextDouble(50))*randomFactor)));
        agilidade = (agilidadeBase + (1+ (random.nextDouble(50))*randomFactor));
        stamina = (staminaBase + ( 1+(random.nextDouble(50)*randomFactor)));
    }

    //getters and setters

    public double getForca(){return forca;}
    public void setForca(double forca){this.forca = forca;}

    public double getAgilidade(){return agilidade;}
    public void setAgilidade(double agilidade){this.agilidade = agilidade;}

    public double getStamina(){return stamina;}
    public void setStamina(double stamina){this.stamina = stamina;}

    //getters atributos resultados de razões

    public double getAtaque(){return (10+(forca*0.10));}
    public double getHealth(){return (500+(3*stamina));}
    public double getCritico(){return (0.10+(0.02*(agilidade/2)));}

}


//Os atributos são sorteados UMA vez, na criação do gladiador (tier define o fator).
//Depois disso o banco é a fonte da verdade: colunas forca/agilidade/stamina da tabela gladiador.