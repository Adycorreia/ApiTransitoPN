package cv.pn.apitransito.services.implement;



import cv.pn.apitransito.dtos.InfracaoResponseDTO;
import cv.pn.apitransito.model.Infracao;
import cv.pn.apitransito.repository.InfracaoRepository;
import cv.pn.apitransito.services.InfracaoService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public  class InfracaoServiceImpl implements InfracaoService {

    @Autowired
    InfracaoRepository infracaoRepository;

    @Override
    public APIResponse infracaoAll() {


        List<Infracao> listInfrac = infracaoRepository.findAll();
        try {

            List<InfracaoResponseDTO> infracaoResponseDTOS = listInfrac.stream()
                    .map(infracao -> new InfracaoResponseDTO(
                            infracao.getId(),
                            infracao.getArtigo(),
                            infracao.getValor(),
                            infracao.getDescricao(),
                            infracao.getPrevisto(),
                            infracao.getCont_orden(),
                            infracao.getCreation(),
                            infracao.getUpdate(),
                            infracao.getObs()))
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(infracaoResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }




    @Override
    public APIResponse insertInfracao(InfracaoResponseDTO infracaoResponseDTO) {

        Infracao infracao = new Infracao();

        try {
            infracao.setId(infracaoResponseDTO.getIdinfracao());
            infracao.setArtigo(infracaoResponseDTO.getArtigo());
            infracao.setDescricao(infracaoResponseDTO.getDescricao());
            infracao.setValor(infracaoResponseDTO.getValor());
            infracao.setPrevisto(infracaoResponseDTO.getPrevisto());
            infracao.setCont_orden(infracaoResponseDTO.getCont_orden());
            infracao.setCreation(infracaoResponseDTO.getCreation());
            infracao.setUpdate(infracaoResponseDTO.getUpdate());
            infracao.setObs(infracaoResponseDTO.getObs());


            infracaoRepository.save(infracao);

            return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(l).build();
        }

    }
/*
    @Override
    public APIResponse findTipodoc(String tipodoc) {

        try {

            List<Documents> listDocuments = documentsRepository.findDocumentsByTipodoc(tipodoc.toUpperCase());

            List<DocumentsResponseDTO> documentsResponseDTO = listDocuments.stream()
                    .map(documents -> new DocumentsResponseDTO(
                            documents.getId(),
                            documents.getMatricula(),
                            documents.getN_carta(),
                            documents.getCondutor(),
                            documents.getProprietario(),
                            documents.getMotivo(),
                            documents.getDestino(),
                            documents.getN_oficio(),
                            documents.getData_apreensao(),
                            documents.getData_entrega(),
                            documents.getCap(),
                            documents.getTipodoc(),
                            documents.getV_apreendido(),
                            documents.getObs()

                    ))
                    .collect(Collectors.toList());


            return APIResponse.builder().status(true).details(Arrays.asList(documentsResponseDTO.toArray())).statusText(MessageState.SUCESSO).build();
        } catch (Exception e) {
            List<Object> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return APIResponse.builder()
                    .status(false).statusText(MessageState.ERRO)
                    .details(errors).build();
        }

    }

*/


}
