package cv.pn.apitransito.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Data
public class Documents  extends  AbstractEntity{

    private String matricula;
    private String n_carta;
    private String condutor;
    private String proprietario;
    private String motivo;
    private String destino;
    private String n_oficio;
    private String data_apreensao;
    private String data_entrega;
    private String n_cap;
    private String tipodoc;
    private String V_apreendido;
    private String obs;

}
