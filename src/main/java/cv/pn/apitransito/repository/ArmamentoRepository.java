package cv.pn.apitransito.repository;


import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Armamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArmamentoRepository extends JpaRepository<Armamento, Long>{

    Optional<Armamento> findByAgente_Id(Long id);
}
