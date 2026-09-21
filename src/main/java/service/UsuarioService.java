package service;


import model.Usuario;
import repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    public Usuario criarUsuario(Usuario usuario){
        Usuario novoUsuario = new Usuario(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha());
        return repository.save(novoUsuario);
    }


    public Usuario autenticar(String email, String senha){
        Optional <Usuario> usuario = repository.findByEmail(email);

        if(usuario.isEmpty()){
            throw new IllegalArgumentException("e-mail ou senha inválidos");
        }
        Usuario encontrado = usuario.get();
        if (!encontrado.getSenha().equals(senha)){
            throw new IllegalArgumentException("e-mail ou senha inválidos");
        }
        return encontrado;
        }

    public Usuario buscarUsuario(Long id){
        Optional <Usuario> usuario = repository.findById(id);
        if(usuario.isEmpty()){throw new IllegalArgumentException("nao encontrado");}
        return usuario.get();
    }

    public int subtrairCreditos(Usuario usuario,int valor){
        if(usuario.getCreditos()<valor){throw new IllegalArgumentException("SALDO INSUFICIENTE");}
        usuario.setCreditos(usuario.getCreditos()-valor);
        repository.save(usuario);
        return usuario.getCreditos();
    }
}
