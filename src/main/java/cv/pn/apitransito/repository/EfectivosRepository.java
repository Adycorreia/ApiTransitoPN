package cv.pn.apitransito.repository;

import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EfectivosRepository extends JpaRepository<Agente, Long>{
    //APIResponse Documents(String tipo_doc);


}
