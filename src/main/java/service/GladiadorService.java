package service;


import model.Gladiador;
import model.Usuario;
import repository.UsuarioRepository;
import repository.GladiadorRepository;
import service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.Random;


public class GladiadorService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final GladiadorRepository gladiadorRepository;


    //Depois retirar a "usuarioRepository se não for usada nenhuma vez"
    public GladiadorService(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            GladiadorRepository gladiadorRepository){
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.gladiadorRepository = gladiadorRepository;
    }

    Random random = new Random();

    public Gladiador criarGladiador(Long usuarioId, Gladiador gladiador){
        Optional <Usuario> usuario = usuarioRepository.findById(usuarioId);
        if(usuario.isEmpty()){throw new IllegalArgumentException("USUARIO NAO ENCONTRADO - ERRO");}
        Usuario encontrado = usuario.get();
        int valorGladiador = calcularValorGladiador(gladiador.getTier());
        usuarioService.subtrairCreditos(encontrado,valorGladiador); //Esse método da Service calcula se há saldo suficiente no usuario, se negativo, joga erro;
        gladiador.setUsuario(encontrado);
        return gladiadorRepository.save(gladiador);
    }

    public List<Gladiador> listarGladiadores(Long usuarioId){
        return null;
    }

    public List<Gladiador> listarVivos(){
        return null;
    }
    public Gladiador pesquisarGladiador(Long id) {
        Optional<Gladiador> gladiador = gladiadorRepository.findById(id);
        if(gladiador.isEmpty()){throw new IllegalArgumentException("GLADIADOR NAO ENCONTRADO");}
        return gladiador.get();
    }

    public void atualizarDescricao(Long id, String descricao){
        Gladiador gladiador = pesquisarGladiador(id);
        gladiador.setDescricao(descricao);
        gladiadorRepository.save(gladiador);
    }

    public void deletarGladiador(Long id){
    Gladiador gladiador = pesquisarGladiador(id);
    gladiadorRepository.deleteById(id);
    }

    // Random decide;
    public Gladiador batalhar(Long idA, Long idB){
     Gladiador Glad1 = pesquisarGladiador(idA);
     Gladiador Glad2 = pesquisarGladiador(idB);
     double glad1Range = (random.nextDouble(Glad1.getAtributos().getStatSum()))*(1+Glad1.getTierFactor());
     double glad2Range = (random.nextDouble(Glad2.getAtributos().getStatSum()))*(1+Glad2.getTierFactor());

     Gladiador vencedor=null;
     Gladiador perdedor=null;
    if (glad1Range > glad2Range){
         vencedor = Glad1;
         perdedor = Glad2;
     }
    else{//glad2 venceu:
        vencedor = Glad2;
        perdedor = Glad1;
     }
        vencedor.setBatalhasVencidas(vencedor.getBatalhasVencidas()+1);
        perdedor.setStatus("MORTO");

        gladiadorRepository.save(vencedor);
        gladiadorRepository.save(perdedor);

        return vencedor;
    }
    //vencedor +1 vitória, perdedor MORTO

    public List<Gladiador> ranking() {
        return null;
    }

    public int calcularValorGladiador (Gladiador.Tier tier){
        int valorCreditos;
        switch (tier){
            case PRATA-> valorCreditos =  350;
            case OURO-> valorCreditos = 600;
            case PLATINA-> valorCreditos = 1000;
            default-> valorCreditos = 250;
    }return valorCreditos;
}
}
