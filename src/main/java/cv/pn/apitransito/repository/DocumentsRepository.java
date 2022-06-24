package cv.pn.apitransito.repository;

import cv.pn.apitransito.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {
    //APIResponse Documents(String tipo_doc);

    List<Documents> findDocumentsByTipodoc(String tipodoc);
    //List<Documents> findById(String id);
    //Optional<Documents> findById(String id);
}
