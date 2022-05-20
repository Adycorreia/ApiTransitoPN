package cv.pn.apitransito.services.implement;


import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.model.Documents;
import cv.pn.apitransito.repository.DocumentsRepository;
import cv.pn.apitransito.services.DocumentsService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.Constants;
import cv.pn.apitransito.utilities.MessageState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public  class DocumentsServiceImpl implements DocumentsService {

    @Autowired
    DocumentsRepository documentsRepository;

    @Override
    public APIResponse documentsAll() {


        List<Documents> listdoc = documentsRepository.findAll();
        try {

            List<DocumentsResponseDTO> documentsResponseDTOS = listdoc.stream()
                    .map(document -> new DocumentsResponseDTO(
                            document.getId(),
                            document.getMatricula(),
                            document.getN_carta(),
                            document.getCondutor(),
                            document.getProprietario(),
                            document.getMotivo(),
                            document.getDestino(),
                            document.getN_oficio(),
                            document.getData_apreensao(),
                            document.getData_entrega(),
                            document.getCap(),
                            document.getTipodoc(),
                            document.getV_apreendido(),
                            document.getObs()))
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(documentsResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }




    @Override
    public APIResponse insertDocuments(DocumentsResponseDTO documentsResponseDTO) {

        Documents documents = new Documents();

        try {
            documents.setId(documentsResponseDTO.getIddoc());
            documents.setMatricula(documentsResponseDTO.getMatricula());
            documents.setN_carta(documentsResponseDTO.getN_carta());
            documents.setCondutor(documentsResponseDTO.getCondutor());
            documents.setProprietario(documentsResponseDTO.getProprietario());
            documents.setMotivo(documentsResponseDTO.getMotivo());
            documents.setDestino(documentsResponseDTO.getDestino());
            documents.setN_oficio(documentsResponseDTO.getN_oficio());
            documents.setData_apreensao(documentsResponseDTO.getData_apreensao());
            documents.setData_entrega(documentsResponseDTO.getData_entrega());
            documents.setV_apreendido(documentsResponseDTO.getV_apreendido());
            documents.setTipodoc(documentsResponseDTO.getTipodoc());
            documents.setObs(documentsResponseDTO.getObs());
            documents.setCap(documentsResponseDTO.getCap());

            documentsRepository.save(documents);

            return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(l).build();
        }

    }

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




}
