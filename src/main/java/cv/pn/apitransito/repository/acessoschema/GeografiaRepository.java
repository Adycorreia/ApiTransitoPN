package cv.pn.apitransito.repository.acessoschema;

import cv.pn.apitransito.model.acessoschema.Geografia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeografiaRepository extends JpaRepository<Geografia, String> {



    List<Geografia> findGeographyByNivelAndAndSelfId(int nivel, String selfId);

}
