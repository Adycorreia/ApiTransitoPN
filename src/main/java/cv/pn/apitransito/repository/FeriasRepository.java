package cv.pn.apitransito.repository;

import cv.pn.apitransito.model.Ferias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeriasRepository  extends JpaRepository<Ferias, Long> {

    List<Ferias> findByAgente_Id(Long id);
    Optional<Ferias> findById(Long id);

}
