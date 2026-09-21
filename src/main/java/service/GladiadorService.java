package service;


import model.Gladiador;
import model.Usuario;
import repository.UsuarioRepository;
import repository.GladiadorRepository;

import java.util.List;
import java.util.Optional;


public class GladiadorService {

    private final UsuarioRepository usuarioRepository;
    private final GladiadorRepository gladiadorRepository;

    public GladiadorService(
            UsuarioRepository usuarioRepository,
            GladiadorRepository gladiadorRepository){

        this.usuarioRepository = usuarioRepository;
        this.gladiadorRepository = gladiadorRepository;
    }


    public Gladiador criarGladiador(Long usuarioId, Gladiador gladiador){

        Optional <Usuario> usuario = usuarioRepository.findById(usuarioId);

        return null;
    } // desconta 250 créditos

    public List<Gladiador> listarGladiadores(Long usuarioId){
        return null;
    }

    public List<Gladiador> listarVivos(){
        return null;
    }
    public Gladiador pesquisarGladiador(Long id) {
        return null;
    }
    public void atualizarDescricao(Long id, String descricao){

    }
    public void deletarGladiador(Long id){

    }
    // Random decide;
    public Gladiador batalhar(Long idA, Long idB){
     return null;
    }
    //vencedor +1 vitória, perdedor MORTO

    public List<Gladiador> ranking() {
        return null;
    }
}
