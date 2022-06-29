package cv.pn.apitransito.model.acessoschema;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter @Getter
public class Geografia {

	@Id
	@Column(name = "id", nullable = false, columnDefinition = "Character varying(40)")
	private String id;

	@Column(name = "nome", nullable = false, columnDefinition = "Character varying(150)")
	private String nome;

	@Column(name = "nivel", nullable = true, columnDefinition = "integer")
	private Integer nivel;

	@Column(name = "latitude", nullable = true, columnDefinition = "Character varying(30)")
	private String latitude;

	@Column(name = "longitude", nullable = true, columnDefinition = "Character varying(30)")
	private String longitude;

	@Column(name = "self_id", nullable = false, columnDefinition = "Character varying(32)")
	private String selfId;

	@Column(name = "nome_cartao", nullable = true, columnDefinition = "Character varying(16)")
	private String nomeCartao;

	@Column(name = "code_2", nullable = true, columnDefinition = "Character varying(3)")
	private String code2;

	@Column(name = "code_3", nullable = true, columnDefinition = "Character varying(3)")
	private String code3;

	@Column(name = "num_icao", nullable = true, columnDefinition = "Character varying(5)")
	private String numIcao;
}
