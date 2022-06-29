package cv.pn.apitransito.model.acessoschema;

import cv.pn.apitransito.utilities.Constants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Utilizador {

	@Id
	@GeneratedValue(generator = "UUID")
	private String id_user;

	@Column(name = "username", nullable = true, columnDefinition = "character varying(50)")
	private String username;

	@Column(name = "password", nullable = true, columnDefinition = "character varying(50)")
	private String password;

	@Column(name = "token", nullable = true, columnDefinition = "character varying(40)")
	private String token;

	@Column(name = "flag_user")
	private boolean flag_user;

	@Column(name = "name", nullable = true, columnDefinition = "character varying(200)")
	private String name;

	@Column(name = "status", nullable = true, columnDefinition = "character varying(50)")
	@Enumerated(EnumType.STRING)
	private Constants.DMEstado status;

	@Column(name = "estado", nullable = true, columnDefinition = "character varying(50)")
	private String estado;

	@Column(name = "titulo", nullable = true, columnDefinition = "character varying(50)")
	private String titulo;

	@Column(name = "id_organica", nullable = false, columnDefinition = "character varying(40) ")
	private String organica;
}
