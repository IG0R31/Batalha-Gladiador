package model;
import java.util.Random;

public class Gladiador {
    private Long id;
    private String nome;
    private String descricao;
    private int batalhasVencidas;
    private int aparencia;
    private Tier tier;
    private AtributosBatalha atributos;

    public Gladiador(String nome, String descricao, int batalhasVencidas, StatusGladiador status, Tier tier) {
        this.nome = nome;
        this.descricao = descricao;
        this.batalhasVencidas = 0;
        this.status = status;
        this.tier = tier;
        aparencia = new Random().nextInt(10); // Numero random de 1 a 10 para definir a aparencia do gladiador
        atributos = new AtributosBatalha(this);
    }

    //enum utilizado para manter padrão nos tiers, se fosse utilizado String, valores inválidos poderiam ser inseridos.
    public enum Tier{BRONZE, PRATA, OURO, PLATINA}

    private double getRandomFactor(Tier tier){
    //Esse método é utilizado pela classe de maneira privada apenas para converter o Tier em um número que define o fator aleatório
        double randomFactor;
        switch (tier){
            case BRONZE-> randomFactor= .05;
            case PRATA-> randomFactor =  0.10;
            case OURO-> randomFactor = 0.20;
            case PLATINA-> randomFactor = 0.30;
            default-> randomFactor = 0.01;
     }
     return randomFactor;
    }

    //getters e setters
    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getDescricao(){ return descricao; }
    public void setDescricao(String descricao){ this.descricao = descricao; }

    public int getBatalhasVencidas(){ return batalhasVencidas; }
    public void setBatalhasVencidas(int batalhasVencidas){ this.batalhasVencidas = batalhasVencidas; }

    public StatusGladiador getStatus(){ return status; }
    public void setStatus(StatusGladiador status){ this.status = status; }

    public int getAparencia(){return aparencia;}
    public void setAparencia(int aparencia){this.aparencia = aparencia;}

    public Tier getTier(){return tier;}
    public void setTier(Tier tier){this.tier = tier;}
    public double getTierFactor(){return getRandomFactor(tier);} //usado para outras somas

    public AtributosBatalha getAtributos() {
        return atributos;
    }

    @Override
    public String toString(){
        return "Gladiador{nome:'" + nome + ", descrição:" + descricao + ", batalhas Vencidas(Total):" + batalhasVencidas + ", status:" + status + "}";
    }
}
