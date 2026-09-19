package repository;

import model.Gladiador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GladiadorRepository extends JpaRepository<Gladiador, Long> {

    //Gladiadores de um usuário (FK usuario_id)
    List<Gladiador> findByUsuarioId(Long usuarioId);

    //Apenas os aptos a lutar
    List<Gladiador> findByStatus(String status);

    //Ranking: maior número de batalhas vencidas primeiro
    List<Gladiador> findByOrderByBatalhasVencidasDesc();
}