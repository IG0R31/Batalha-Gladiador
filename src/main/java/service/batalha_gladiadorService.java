package service;

import org.springframework.stereotype.Service;
import model.Gladiador;
import model.Usuario;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//EXCLUIR SERVICE

@Service
public class batalha_gladiadorService {
    private final Map<String, Usuario> usuarios = new LinkedHashMap<>();
    private final repository.GladiadorRepository gladiadorRepository;

    public batalha_gladiadorService(repository.GladiadorRepository gladiadorRepository) {
        this.gladiadorRepository = gladiadorRepository;
    }
    //---Usuário---
    public Usuario criarUsuario(String nome, String email){

        if (nome == null || nome.equals("")){
            throw new IllegalArgumentException("Nome do  usuário é obrigatório.");
        }
        if (email == null || email.equals("")){
            throw new IllegalArgumentException("Email do usuário é obrigatório.");
        }
        if (usuarios.containsKey(email)){
            throw new IllegalArgumentException("Email do usuário já existe.");
        }
        Usuario usuario = new Usuario(nome, email);
        usuarios.put(email, usuario);
        return usuario;
    }

    //---Pesquisa---
    //Busca gladiadores por parte do nome (global, todos os usuários)
    public List<Gladiador> pesquisarPorNome(String nome){
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Digite um nome para pesquisar.");
        }
        return gladiadorRepository.findByNomeContainingIgnoreCase(nome.trim());
    }

}
