package cv.pn.apitransito.utilities;

import lombok.Data;

@Data
public class  Constants {

	public enum TipoDoc {
		CARTA, LIVRETE


	}

	public enum EstadoCivil {
	S, C, V, U, D, O, X


    }

	public enum Dominio {

		DM_ESTADO_CIVIL, DM_TIPO_SEXO;

	}

	public enum DMEstado {

		A, I

    }

	public enum Sexo {

		M, F, X

    }

	public enum DocType {

		TRECV, TRECVESP
	}

	public enum RequestTypeMethod {

		GET, PUT, DELETE, POST
	}

	public  enum StatusPhase {

		PI, AE, E, C, R, PA
	}

	public  enum UpdateStatusCodeSD {
		S, DEF, IND, PE, ENT, AED
	}

	public enum RelationTable {

		TRE_ADDRESS("tre_endereco"), TRE_ARCHIVE("tre_arquivo"), TRE_FILE("tre_file"), TRE_REQUEST("tre_pedido"), TRE_IDENTIFICATION("tre_identificacao"), TRE_FOREIGN("Tre_estrangeiro");

			private final String table;

			RelationTable (String table){

				this.table = table;

			}

			public String getType() {

				return this.table;
			}
		}
	}




