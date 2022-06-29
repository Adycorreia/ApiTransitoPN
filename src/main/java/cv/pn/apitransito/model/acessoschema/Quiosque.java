package cv.pn.apitransito.model.acessoschema;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Data
public class Quiosque {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    private String IpAddress;

    private String SerialNumber;

    private String description;

    private String organica;

}
