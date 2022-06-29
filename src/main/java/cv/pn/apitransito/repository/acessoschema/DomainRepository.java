package cv.pn.apitransito.repository.acessoschema;


import cv.pn.apitransito.model.Domain;
import cv.pn.apitransito.utilities.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, String> {

	List<Domain> findByDominioAndEstado(String dominio, Constants.DMEstado estado);

	Optional<Domain> findByDominioAndValor(String dominio, String valor);


}
