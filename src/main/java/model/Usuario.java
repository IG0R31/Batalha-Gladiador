package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    //não sei se vai querer manter 1000 ou mais como default, depende muito como vai querer deixar para a montagem de Gladiadores.
    //A tela do usuário mostra "Montar personagem (250 Créditos)", então o início precisa cobrir o custo.
    public static final int CREDITOS_INICIAIS = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String senha;

    @Column(nullable = false)
    private int creditos;

    //Construtor sem argumentos: usado pelo Thymeleaf (formulário) e pelo JPA (carregar do banco)
    public Usuario() {
        this.creditos = CREDITOS_INICIAIS;
    }

    public Usuario(String nome, String email, String senha) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    //getters e setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getSenha(){ return senha; }
    public void setSenha(String senha){ this.senha = senha; }

    public int getCreditos(){ return creditos; }
    public void setCreditos(int creditos){ this.creditos = creditos; }
}