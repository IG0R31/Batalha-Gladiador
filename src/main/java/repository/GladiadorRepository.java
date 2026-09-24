package repository;

import model.Gladiador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GladiadorRepository extends JpaRepository<Gladiador, Long> {

    //Gladiadores de um usuário (FK usuario_id)
    List<Gladiador> findByUsuarioId(Long usuarioId);

    //Apenas os aptos a lutar
    List<Gladiador> findByStatus(String status);

    //Vivos do usuário (select "Seu Gladiador")
    List<Gladiador> findByUsuarioIdAndStatus(Long usuarioId, String status);

    //Vivos de outros usuários (select "Gladiador a Enfrentar")
    List<Gladiador> findByStatusAndUsuarioIdNot(String status, Long usuarioId);

    //Ranking: maior número de batalhas vencidas primeiro
    List<Gladiador> findByOrderByBatalhasVencidasDesc();

    //Busca por parte do nome, ignorando maiúsculas/minúsculas
    List<Gladiador> findByNomeContainingIgnoreCase(String nome);
}