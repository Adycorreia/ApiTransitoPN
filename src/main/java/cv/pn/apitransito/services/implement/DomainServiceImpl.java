package cv.pn.apitransito.services.implement;

import cv.pn.apitransito.dtos.DomainDTO;
import cv.pn.apitransito.dtos.DomainResponseDTO;
import cv.pn.apitransito.model.Domain;
import cv.pn.apitransito.repository.acessoschema.DomainRepository;
import cv.pn.apitransito.services.DomainService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.Constants;
import cv.pn.apitransito.utilities.MessageState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainServiceImpl implements DomainService {

	@Autowired
	private DomainRepository domainRepository;

	@Override
	public APIResponse findDomain(String dominio) {

		try {

			List<Domain> listDomain = domainRepository.findByDominioAndEstado(dominio.toUpperCase(), Constants.DMEstado.A);

			List<DomainResponseDTO> domainResponseDTOS = listDomain.stream()
					.map(domain -> new DomainResponseDTO(domain.getId(), domain.getValor(), domain.getDescricao()))
					.collect(Collectors.toList());


			return APIResponse.builder().status(true).details(Arrays.asList(domainResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();
		} catch (Exception e) {
			List<Object> errors = new ArrayList<>();
	          errors.add(e.getMessage());
	          return APIResponse.builder()
	        		  .status(false).statusText(MessageState.ERRO)
	        		  .details(errors).build();
		}

	}

	@Override
	public APIResponse insertDomain(DomainDTO dto) {

		try {

			dto.getDomain().stream()
					.map(dR -> {
						Domain domain = new Domain();
						domain.setDominio(dR.getDominio().toUpperCase());
						domain.setValor(dR.getValor().toUpperCase());
						domain.setOrdem(dR.getOrder());
						domain.setDescricao(dR.getDescricao());
						domain.setEstado(Constants.DMEstado.A);
						return domain;})
					.forEach(domainRepository::save);

			return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

		} catch (Exception e) {
			List<Object> errors = new ArrayList<>();
	          errors.add(e.getMessage());
	          return APIResponse.builder()
	        		  .status(false).statusText(MessageState.REMOVIDO_COM_SUCESSO)
	        		  .details(errors).build();
		}
	}

	@Override
	public APIResponse updateDomain(DomainDTO.DomainRequest dto, String id) {

		Optional<Domain> opt = domainRepository.findById(id);
		ApiUtilies.checkResource(opt, MessageState.ID_NAO_EXISTE);
		Domain domain = opt.get();

		try {

				domain.setDominio(dto.getDominio().toUpperCase());
				domain.setValor(dto.getValor().toUpperCase());
				domain.setOrdem(dto.getOrder());
				domain.setDescricao(dto.getDescricao());
				domain.setEstado(Constants.DMEstado.A);

				domainRepository.save(domain);

				return APIResponse.builder().status(true).statusText(MessageState.ATUALIZADO_COM_SUCESSO).build();

		} catch (Exception e) {
			List<Object> errors = new ArrayList<>();
	          errors.add(e.getMessage());
	          return APIResponse.builder()
	        		  .status(false).statusText(MessageState.ERRO_AO_ATUALIZAR)
	        		  .details(errors).build();
		}
	}

	@Override
	public APIResponse swicthEstadoDomain(String id) {

		Optional<Domain> opt = domainRepository.findById(id);
		ApiUtilies.checkResource(opt, MessageState.ID_NAO_EXISTE);
		Domain domain = opt.get();

		try {
				if(domain.getEstado().equals(Constants.DMEstado.A)) {

					domain.setEstado(Constants.DMEstado.I);

				} else {

					domain.setEstado(Constants.DMEstado.A);
				}


				domainRepository.save(domain);

				return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();

		} catch (Exception e) {
			List<Object> errors = new ArrayList<>();
	          errors.add(e.getMessage());
	          return APIResponse.builder()
	        		  .status(false).statusText(MessageState.ERRO)
	        		  .details(errors).build();
	}

	}



}
