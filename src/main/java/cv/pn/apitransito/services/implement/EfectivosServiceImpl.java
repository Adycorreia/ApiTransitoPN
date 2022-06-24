package cv.pn.apitransito.services.implement;


import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Documents;
import cv.pn.apitransito.repository.EfectivosRepository;
import cv.pn.apitransito.services.EfectivosService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public  class EfectivosServiceImpl implements EfectivosService {

    @Autowired
    EfectivosRepository efectivosRepository;

    @Override
    public APIResponse efectivosAll() {


        List<Agente> listagent = efectivosRepository.findAll();
        try {

            List<EfectivosResponseDTO> efectivosResponseDTOS = listagent.stream()
                    .map(agente -> new EfectivosResponseDTO(
                            agente.getId(),
                            agente.getId_pn(),
                            agente.getNome(),
                            agente.getApelido(),
                            agente.getData_nasc(),
                            agente.getSexo(),
                            agente.getFiliacao(),
                            agente.getIdade(),
                            agente.getCni(),
                            agente.getNif(),
                            agente.getFuncao(),
                            agente.getMorada(),
                            agente.getPosto(),
                            agente.getContacto(),
                            agente.getEmail(),
                            agente.getCreation(),
                            agente.getUpdate(),
                            agente.getObs()))
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(efectivosResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }

    @Override
    public APIResponse listById(Long id) {

        Optional<Agente> agentesOptional = efectivosRepository.findById(id);
        ApiUtilies.checkResource(agentesOptional, MessageState.ID_NAO_EXISTE);
        Agente agente = agentesOptional.get();


        try {
            List<EfectivosResponseDTO> agenteResponseDTO = agentesOptional.stream()
                    .map(efectivoslist -> new EfectivosResponseDTO(
                            efectivoslist.getId(),
                            efectivoslist.getId_pn(),
                            efectivoslist.getNome(),
                            efectivoslist.getApelido(),
                            efectivoslist.getData_nasc(),
                            efectivoslist.getSexo(),
                            efectivoslist.getFiliacao(),
                            efectivoslist.getIdade(),
                            efectivoslist.getCni(),
                            efectivoslist.getNif(),
                            efectivoslist.getFuncao(),
                            efectivoslist.getMorada(),
                            efectivoslist.getPosto(),
                            efectivoslist.getContacto(),
                            efectivoslist.getEmail(),
                            efectivoslist.getCreation(),
                            efectivoslist.getUpdate(),
                            efectivoslist.getObs()
                    ))
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(agenteResponseDTO.toArray())).statusText(MessageState.SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }




    @Override
    public APIResponse insertEfectivos(EfectivosResponseDTO efectivosResponseDTO) {

        Agente agente = new Agente();

        try {
            agente.setId(efectivosResponseDTO.getIdagente());
            agente.setId_pn(efectivosResponseDTO.getId_pn());
            agente.setNome(efectivosResponseDTO.getNome());
            agente.setApelido(efectivosResponseDTO.getApelido());
            agente.setData_nasc(efectivosResponseDTO.getData_nasc());
            agente.setSexo(efectivosResponseDTO.getSexo());
            agente.setFiliacao(efectivosResponseDTO.getFiliacao());
            agente.setIdade(efectivosResponseDTO.getIdade());
            agente.setCni(efectivosResponseDTO.getCni());
            agente.setNif(efectivosResponseDTO.getNif());
            agente.setFuncao(efectivosResponseDTO.getFunção());
            agente.setMorada(efectivosResponseDTO.getMorada());
            agente.setPosto(efectivosResponseDTO.getPosto());
            agente.setContacto(efectivosResponseDTO.getContacto());
            agente.setEmail(efectivosResponseDTO.getEmail());
            agente.setCreation(efectivosResponseDTO.getCreation());
            agente.setUpdate(efectivosResponseDTO.getUpdate());
            agente.setObs(efectivosResponseDTO.getObs());

            efectivosRepository.save(agente);

            return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(l).build();
        }

    }

    @Override
    public APIResponse deleteefect(Long id) {

        Optional<Agente> efectivosOptional = efectivosRepository.findById(id);
        ApiUtilies.checkResource(efectivosOptional, MessageState.ID_NAO_EXISTE);
        Agente agente = efectivosOptional.get();

        try {
            efectivosRepository.delete(agente);
            return APIResponse.builder().status(true).statusText(MessageState.REMOVIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }

    @Override
    public APIResponse updateEfectId(Long id, EfectivosResponseDTO dto) {

        Optional<Agente> efectOptional = efectivosRepository.findById(id);
        ApiUtilies.checkResource(efectOptional, MessageState.ID_NAO_EXISTE);
        Agente agent = efectOptional.get();

        try {
            agent.setId_pn(dto.getId_pn());
            agent.setNome(dto.getNome());
            agent.setApelido(dto.getApelido());
            agent.setData_nasc(dto.getData_nasc());
            agent.setSexo(dto.getSexo());
            agent.setFiliacao(dto.getFiliacao());
            agent.setIdade(dto.getIdade());
            agent.setCni(dto.getCni());
            agent.setNif(dto.getNif());
            agent.setFuncao(dto.getFunção());
            agent.setMorada(dto.getMorada());
            agent.setPosto(dto.getPosto());
            agent.setContacto(dto.getContacto());
            agent.setEmail(dto.getEmail());
            agent.setCreation(dto.getCreation());
            agent.setUpdate(dto.getUpdate());
            agent.setObs(dto.getObs());

            efectivosRepository.save(agent);

            return APIResponse.builder().status(true).statusText(MessageState.ATUALIZADO_COM_SUCESSO).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_ATUALIZAR).details(l).build();

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
