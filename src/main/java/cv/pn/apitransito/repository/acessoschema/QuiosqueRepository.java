package cv.pn.apitransito.repository.acessoschema;

import cv.pn.apitransito.model.acessoschema.Quiosque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuiosqueRepository extends JpaRepository<Quiosque, String> {
  List<Quiosque> findQuiosqueByOrganica(String id);
}
