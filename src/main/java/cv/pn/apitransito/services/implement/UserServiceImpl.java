package cv.pn.apitransito.services.implement;

import cv.pn.apitransito.dtos.UserResponseDTO;
import cv.pn.apitransito.model.acessoschema.Organica;
import cv.pn.apitransito.model.acessoschema.Utilizador;
import cv.pn.apitransito.repository.acessoschema.OrganicaRepository;
import cv.pn.apitransito.repository.acessoschema.QuiosqueRepository;
import cv.pn.apitransito.repository.acessoschema.UtilizadorRepository;
import cv.pn.apitransito.services.UserService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.Constants;
import cv.pn.apitransito.utilities.MessageState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UtilizadorRepository userRepository;


	@Autowired
	private OrganicaRepository organicaRepository;

	@Autowired
	private QuiosqueRepository quiosqueRepository;

	@Override
	public APIResponse insertUser(String username) {

		Utilizador user = new Utilizador();

		try {

			user.setUsername(username);
			user.setPassword("$iTrE2020");
			user.setStatus(Constants.DMEstado.A);

			userRepository.saveAndFlush(user);

			user.setToken(user.getId_user());

			userRepository.save(user);

			return APIResponse.builder().status(true).statusText(user.getId_user()).build();

		} catch (Exception e) {
			List<Object> errors = new ArrayList<>();
			errors.add(e.getMessage());
			return APIResponse.builder()
					.status(false).statusText("Erro")
					.details(errors).build();
		}
	}


	@Override
	public APIResponse allUser() {

		List<Utilizador> allUser = userRepository.findAll();

		try {

			List<Object> objectList = new ArrayList<>();

			for (Utilizador utilizador : allUser) {

				String organica = "", idOrganica = "";
				if (utilizador.getOrganica() != null) {

					Optional<Organica> optionalOrganica = organicaRepository.findById(utilizador.getOrganica());
					ApiUtilies.checkResource(optionalOrganica, MessageState.ID_NAO_EXISTE);

					organica = optionalOrganica.get().getName();
					idOrganica = optionalOrganica.get().getId();
				}

				UserResponseDTO userResponseDTO = new UserResponseDTO();

				userResponseDTO.setId_user(utilizador.getId_user());
				userResponseDTO.setName(utilizador.getName());
				userResponseDTO.setUsername(utilizador.getUsername());
				userResponseDTO.setOrganica(organica);
				userResponseDTO.setIdOrganica(idOrganica);

				objectList.add(userResponseDTO);
			}


			return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(objectList).build();

		} catch (Exception e) {
			List<Object> l = new ArrayList<>();
			l.add(e.getMessage());

			return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
		}


	}

/*
	@Override
	public APIResponse connectUserToOrganic(String user, String idOrganica) {


		Optional<Organica> optionalOrganica = organicaRepository.findById(idOrganica);
		ApiUtilies.checkResource(optionalOrganica, MessageState.ID_NAO_EXISTE);

		Optional<Utilizador> optionalUtilizador = userRepository.findByUsername(user);
		ApiUtilies.checkResource(optionalOrganica, MessageState.ID_NAO_EXISTE);
		Utilizador utilizador = optionalUtilizador.get();

		try {

			utilizador.setOrganica(idOrganica);

			userRepository.save(utilizador);


			return APIResponse.builder().status(true).statusText(MessageState.ASSOCIAR).details(Collections.singletonList(utilizador)).build();
		} catch (Exception e) {

			List<Object> l = new ArrayList<>();
			l.add(e.getMessage());
			return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
		}
	}



	@Override
	public APIResponse insertUtilizador(UtilizadorDTO dto) {

		int countUser = userRepository.countByUsername(dto.getEmail());

		Optional<Organica> optionalOrganica = organicaRepository.findById(dto.getOrganica());
		ApiUtilies.checkResource(optionalOrganica, "Oraganica " + MessageState.ID_NAO_EXISTE);

		if(countUser != 0) {

			return  APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(Arrays.asList("Ja existe um utilizador com mesmo email")).build();
		}

		Utilizador utilizador = new Utilizador();

		try {

			utilizador.setUsername(dto.getEmail());
			utilizador.setOrganica(dto.getOrganica());
			utilizador.setPassword("$iTrE2020");
			utilizador.setStatus(DMEstado.A);
			utilizador.setName(dto.getName());

			userRepository.save(utilizador);

			return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();
		} catch (Exception e) {

			return  APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(Arrays.asList(e.getMessage())).build();
		}
	}
*/
}
