package cv.pn.apitransito.model;

import cv.pn.apitransito.utilities.Constants;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Domain {

	@Id
	@GeneratedValue(generator = "UUID")
	private String id;

	private String dominio;

	private String valor;

	private String descricao;

	private int ordem;

	private Constants.DMEstado estado;

	private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
