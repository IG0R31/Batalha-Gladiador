package model;

public class StatusGladiador {
   private boolean VIVO;
   private boolean MORTO;

    public boolean StatusGladiador() {
        this.VIVO = false;
        this.MORTO = false;
        return this.VIVO;
    }
    public boolean MORTO() {
        this.VIVO = false;
    }
    public boolean VIVO() {
        this.VIVO = true;
    }

}
//Aqui o Status do Gladiador se define por ele estar vivo ou morto.
//Vou definir depois se o Gladiador vai possuir algum tipo de Status de vida, caso não