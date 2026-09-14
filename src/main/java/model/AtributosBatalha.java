package model;
import java.util.Random;

public class AtributosBatalha{

    private double forca;
    private double agilidade;
    private double stamina;

    private final double forcaBase =  100;
    private final double agilidadeBase =  50;
    private final double staminaBase =  75;

    AtributosBatalha(Gladiador gladiador){
        double randomFactor = gladiador.getTierFactor();
        Random random = new Random();

        forca = (forcaBase + ((1 + (random.nextDouble(50))*randomFactor)));
        agilidade = (agilidadeBase + (1+ (random.nextDouble(50))*randomFactor));
        stamina = (staminaBase + ( 1+(random.nextDouble(50)*randomFactor)));
    }

    //getters and setters

    public double getForca(){return forca;}
    public void setForca(int forca){this.forca = forca;}

    public double getAgilidade(){return agilidade;}
    public void setAgilidade(int agilidade){this.agilidade = agilidade;}

    public double getStamina(){return stamina;}
    public void setStamina(){this.stamina = stamina;};

    //getters atributos resultados de razões

    public double getAtaque(){return (10+(forca*0.10));}
    public double getHealth(){return (500+(3*stamina));}
    public double getCritico(){return (0.10+(0.02*(agilidade/2)));}

}


//Ainda não vou adicionar nada. Mas se a ideia não fugir muito do que combinamos vai ser Força, Agilidade e Defesa