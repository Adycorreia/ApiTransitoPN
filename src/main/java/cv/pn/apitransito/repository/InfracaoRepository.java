package cv.pn.apitransito.repository;

import cv.pn.apitransito.model.Infracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfracaoRepository extends JpaRepository<Infracao, String>{
    //APIResponse Documents(String tipo_doc);

    //List<Infracao> findDocumentsByTipodoc(String tipodoc);

}
