package cv.pn.apitransito.repository;

import cv.pn.apitransito.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface DocumentsRepository extends JpaRepository<Documents, String> {
    //APIResponse Documents(String tipo_doc);

    List<Documents> findDocumentsByTipodoc(String tipodoc);

}