package model;

public class Gladiador {
    private String nome;
    private String descricao;
    private int batalhasVencidas;
    private StatusGladiador status;

    public Gladiador(String nome, String descricao, int batalhasVencidas, StatusGladiador status) {
        this.nome = nome;
        this.descricao = descricao;
        this.batalhasVencidas = 0;
        this.status = status;
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

    @Override
    public String toString(){
        return "Gladiador{nome:'" + nome + ", descrição:" + descricao + ", batalhas Vencidas(Total):" + batalhasVencidas + ", status:" + status + "}";
    }
}
