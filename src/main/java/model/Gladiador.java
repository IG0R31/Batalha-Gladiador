package model;
import java.util.Random;

//Adicionando as dependecias para o Banco de Dados Feito.

import jakarta.persistence.*;

@Entity
@Table(name="gladiador")
public class Gladiador {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String nome;

    @Column(length = 150)
    private String descricao;

    @Column(name = "batalhas_vencidas", nullable = false)
    private int batalhasVencidas;

    @Column(name = "aparencia", nullable = false)
    private int aparencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tier tier;

    //"VIVO" ou "MORTO" — mapeia o ENUM do banco
    @Column(nullable = false)
    private String status;

    @Embedded
    private AtributosBatalha atributos;

    //Construtor sem argumentos: usado pelo Thymeleaf (formulário) e pelo JPA (carregar do banco)
    public Gladiador() {
        this.batalhasVencidas = 0;
        this.tier = Tier.BRONZE;
        this.status = "VIVO";
        aparencia = new Random().nextInt(11) + 1; // Numero random de 1 a 11 para definir a aparencia do gladiador
        atributos = new AtributosBatalha(this);
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario = usuario; }



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

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public int getAparencia(){return aparencia;}
    public void setAparencia(int aparencia){this.aparencia = aparencia;}

    public Tier getTier(){return tier;}
    public void setTier(Tier tier){this.tier = tier;}
    public double getTierFactor(){return getRandomFactor(tier);} //usado para outras somas

    public AtributosBatalha getAtributos() {
        return atributos;
    }
    public void setAtributos(AtributosBatalha atributos) {
        this.atributos = atributos;
    }

    @Override
    public String toString(){
        return "Gladiador{nome:'" + nome + ", descrição:" + descricao + ", batalhas Vencidas(Total):" + batalhasVencidas + ", status:" + status + ", tier:" + tier + "}";
    }
}
