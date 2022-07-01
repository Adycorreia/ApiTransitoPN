package cv.pn.apitransito.services.implement;

import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.repository.EfectivosRepository;
import cv.pn.apitransito.services.EfectivosService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
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

                            agente.getObs(),

                            agente.getFotografia(),

                            agente.getAssinatura(),

                            agente.getEstado_civil(),

                            agente.getIlha_id(),

                            agente.getConcelho_id(),

                            agente.getFreguesia_id(),

                            agente.getLocalidade_id(),

                            agente.getZona_id(),

                            agente.getNacionalidade_id(),

                            agente.getCv_nacionalidade(),

                            agente.getNivel_instrucao(),

                            agente.getLocal_nascimento(),

                            agente.getNaturalidade()

                    ))
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

                            efectivoslist.getObs(),

                            efectivoslist.getFotografia(),

                            efectivoslist.getAssinatura(),

                            efectivoslist.getEstado_civil(),

                            efectivoslist.getIlha_id(),

                            efectivoslist.getConcelho_id(),

                            efectivoslist.getFreguesia_id(),

                            efectivoslist.getLocalidade_id(),

                            efectivoslist.getZona_id(),

                            efectivoslist.getNacionalidade_id(),

                            efectivoslist.getCv_nacionalidade(),

                            efectivoslist.getNivel_instrucao(),

                            efectivoslist.getLocal_nascimento(),

                            efectivoslist.getNaturalidade()


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

        Calendar dataAtual = Calendar.getInstance();
        LocalDate nascimento = efectivosResponseDTO.getData_nasc();
        int ano1 = nascimento.getYear();
        int ano2 = dataAtual.get(Calendar.YEAR);
        int idade = ano2 - ano1;

        try {
            agente.setId(efectivosResponseDTO.getIdagente());

            agente.setId_pn(efectivosResponseDTO.getId_pn());

            agente.setNome(efectivosResponseDTO.getNome());

            agente.setApelido(efectivosResponseDTO.getApelido());

            agente.setData_nasc(efectivosResponseDTO.getData_nasc());

            agente.setSexo(efectivosResponseDTO.getSexo());

            agente.setFiliacao(efectivosResponseDTO.getFiliacao());

            agente.setIdade(idade);

            agente.setCni(efectivosResponseDTO.getCni());

            agente.setNif(efectivosResponseDTO.getNif());

            agente.setFuncao(efectivosResponseDTO.getFuncao());

            agente.setMorada(efectivosResponseDTO.getMorada());

            agente.setPosto(efectivosResponseDTO.getPosto());

            agente.setContacto(efectivosResponseDTO.getContacto());

            agente.setEmail(efectivosResponseDTO.getEmail());

            agente.setCreation(efectivosResponseDTO.getCreation());

            agente.setUpdate(efectivosResponseDTO.getUpdate());

            agente.setObs(efectivosResponseDTO.getObs());

            agente.setFotografia(efectivosResponseDTO.getFotografia());

            agente.setAssinatura(efectivosResponseDTO.getAssinatura());

            agente.setEstado_civil(efectivosResponseDTO.getEstado_civil());

            agente.setIlha_id(efectivosResponseDTO.getIlha_id());

            agente.setConcelho_id(efectivosResponseDTO.getConcelho_id());

            agente.setFreguesia_id(efectivosResponseDTO.getFreguesia_id());

            agente.setLocalidade_id(efectivosResponseDTO.getLocalidade_id());

            agente.setZona_id(efectivosResponseDTO.getZona_id());

            agente.setNacionalidade_id(efectivosResponseDTO.getNacionalidade_id());

            agente.setCv_nacionalidade(efectivosResponseDTO.getCv_nacionalidade());

            agente.setNivel_instrucao(efectivosResponseDTO.getNivel_instrucao());

            agente.setNaturalidade(efectivosResponseDTO.getNaturalidade());


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

            agent.setFuncao(dto.getFuncao());

            agent.setMorada(dto.getMorada());

            agent.setPosto(dto.getPosto());

            agent.setContacto(dto.getContacto());

            agent.setEmail(dto.getEmail());

            agent.setCreation(dto.getCreation());

            agent.setUpdate(dto.getUpdate());

            agent.setObs(dto.getObs());

            agent.setFotografia(dto.getFotografia());

            agent.setAssinatura(dto.getAssinatura());


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
