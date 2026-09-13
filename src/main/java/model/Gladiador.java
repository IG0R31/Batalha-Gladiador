package model;
import java.util.Random;

public class Gladiador {
    private String nome;
    private String descricao;
    private int batalhasVencidas;
    private StatusGladiador status;
    private int aparencia;
    private String tier;

    public Gladiador(String nome, String descricao, int batalhasVencidas, StatusGladiador status, String tier) {
        this.nome = nome;
        this.descricao = descricao;
        this.batalhasVencidas = 0;
        this.status = status;
        aparencia = new Random().nextInt(10); // Numero random de 1 a 10 para definir a aparencia do gladiador
        this.tier = tier;
    }

    private double getRandomFactor(String tier){
    //Esse método é utilizado pela classe de maneira privada apenas para converter o Tier em um número que define o fator aleatório
        double randomFactor;
        switch (tier){
            case "bronze"-> randomFactor= .05;
            case "prata"-> randomFactor =  0.10;
            case "ouro"-> randomFactor = 0.20;
            case "platina"-> randomFactor = 0.30;
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

    public String getTier(){return tier;}
    public void setTier(String tier){this.tier = tier;}
    public double getTierFactor(){return getRandomFactor(tier);} //usado para outras somas


    @Override
    public String toString(){
        return "Gladiador{nome:'" + nome + ", descrição:" + descricao + ", batalhas Vencidas(Total):" + batalhasVencidas + ", status:" + status + "}";
    }
}
