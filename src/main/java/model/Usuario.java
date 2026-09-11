package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private int creditos;
    private final List<Gladiador> gladiadores;

    public static final int CREDITOS_INICIAIS = 100; //não sei se vai querer manter 100 ou mais como default, depende muito como vai querer deixar para a montagem de Gladiadores.

    public Usuario(String nome, String email){
        this.nome = nome;
        this.email = email;
        this.creditos = CREDITOS_INICIAIS;
        this.gladiadores = new ArrayList<>();
    }

    //getters e setters
    public String getNome(){ return nome; }
    public String setNome(){ this.nome = nome; }

    public String getEmail(){ return email; }
    public String setEmail(){ this.email = email; }

    public int getCreditos(){ return creditos; }
    public int setCreditos(){  this.creditos = CREDITOS_INICIAIS; }

    public List<Gladiador> getGladiadores(){ return gladiadores; }
    public void addGladiador(Gladiador gladiador){
        this.gladiadores.add(gladiador);
    }

    public void removerGladiador(Gladiador gladiador){
        this.gladiadores.remove(gladiador);
    }
}
