package cv.pn.apitransito.repository.acessoschema;

import cv.pn.apitransito.model.acessoschema.Utilizador;
import cv.pn.apitransito.utilities.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, String> {
    Optional<Utilizador> findByUsername(String username);
    List<Utilizador> findByOrganica(String id);

    int countByUsernameAndStatus(String Username, Constants.DMEstado status);

    ///Optional<User> findByUsername(String username);

    Optional<Utilizador> findByToken(String token);

    int countByUsername(String email);
}
