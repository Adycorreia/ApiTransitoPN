package cv.pn.apitransito.repositories;

import cv.pn.apitransito.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents, String>{
}
