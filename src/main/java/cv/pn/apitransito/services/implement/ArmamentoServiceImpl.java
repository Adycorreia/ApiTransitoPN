package cv.pn.apitransito.services.implement;



import cv.pn.apitransito.dtos.ArmamentoResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Armamento;
import cv.pn.apitransito.repository.ArmamentoRepository;
import cv.pn.apitransito.repository.EfectivosRepository;
import cv.pn.apitransito.services.ArmamentoService;

import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.MessageState;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.util.*;
import java.util.stream.Collectors;


@Service
public  class ArmamentoServiceImpl implements ArmamentoService {


    private final ArmamentoRepository armamentoRepository;

    private final EfectivosRepository efectivosRepository;

    public ArmamentoServiceImpl(ArmamentoRepository armamentoRepository, EfectivosRepository efectivosRepository) {
        this.armamentoRepository = armamentoRepository;
        this.efectivosRepository = efectivosRepository;
    }

    @Override
    public APIResponse armamentoAll() {

        List<Armamento> listArma = armamentoRepository.findAll();
        try {

            List<ArmamentoResponseDTO> armamentoResponseDTOS = listArma.stream()
                    .map(arma -> new ArmamentoResponseDTO(
                            arma.getId(),
                            arma.getNumero(),
                            arma.getMarca(),
                            arma.getModelo(),
                            arma.getCalibre(),
                            arma.getN_carregador(),
                            arma.getN_municoes(),
                            arma.getEstado(),
                            arma.getFotografia(),
                            arma.getAgente().getId(),
                            arma.getAgente().getNome(),
                            arma.getAgente().getApelido(),
                            arma.getCreation(),
                            arma.getUpdate(),
                            arma.getObs())).collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(armamentoResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }

    @Override
    public APIResponse insertArmamentoAll(ArmamentoResponseDTO armamentoResponseDTO) {

        Armamento armamento = new Armamento();

        Optional<Agente> agenteOptional = efectivosRepository.findById(armamentoResponseDTO.getId_agente());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);
        Agente agente = agenteOptional.get();

        try {
            /* 
            armamento.setId(armamentoResponseDTO.getIdarma());
            armamento.setNumero(armamentoResponseDTO.getNumero());
            armamento.setMarca(armamentoResponseDTO.getMarca());
            armamento.setModelo(armamentoResponseDTO.getModelo());
            armamento.setCalibre(armamentoResponseDTO.getCalibre());
            armamento.setN_carregador(armamentoResponseDTO.getN_carregador());
            armamento.setN_municoes(armamentoResponseDTO.getN_municoes());
            armamento.setEstado(armamentoResponseDTO.getEstado());
            armamento.setFotografia(armamentoResponseDTO.getFotografia());
            armamento.setAgente(agente);
            armamento.setCreation(armamentoResponseDTO.getCreation());
            armamento.setUpdate(armamentoResponseDTO.getUpdate());
            armamento.setObs(armamentoResponseDTO.getObs());*/

            BeanUtils.copyProperties(armamentoResponseDTO, armamento);
        
            armamentoRepository.save(armamento);

            return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(l).build();
        }
    }

    @Override
    public APIResponse deleteArma(Long id) {
        Optional<Armamento> armamentoOptional = armamentoRepository.findById(id);
        ApiUtilies.checkResource(armamentoOptional, MessageState.ID_NAO_EXISTE);
        Armamento armamento = armamentoOptional.get();

        try {
            armamentoRepository.delete(armamento);
            return APIResponse.builder().status(true).statusText(MessageState.REMOVIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }

    @Override
    public APIResponse listById(Long id) {
        Optional<Armamento> armamentoOptional = armamentoRepository.findById(id);
        ApiUtilies.checkResource(armamentoOptional, MessageState.ID_NAO_EXISTE);
        Armamento armamento1 = armamentoOptional.get();
/*
        Optional<Agente> agenteOptional = efectivosRepository.findById(Armamento.getId());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);
        Agente agente = agenteOptional.get();
*/
        try {
            List<ArmamentoResponseDTO> armamentoResponseDTO = armamentoOptional.stream()
                    .map(armamento -> new ArmamentoResponseDTO(

                            armamento.getId(),
                            armamento.getNumero(),
                            armamento.getMarca(),
                            armamento.getModelo(),
                            armamento.getCalibre(),
                            armamento.getN_carregador(),
                            armamento.getN_municoes(),
                            armamento.getEstado(),
                            armamento.getFotografia(),
                            armamento.getAgente().getId(),
                            armamento.getAgente().getNome(),
                            armamento.getAgente().getApelido(),
                           // agente.getId(),
                            armamento.getCreation(),
                            armamento.getUpdate(),
                            armamento.getObs()) )
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(armamentoResponseDTO.toArray())).statusText(MessageState.SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }


    @Override
    public APIResponse listByIdAgentId(Long id) {
        Optional<Armamento> armamentoOptional = armamentoRepository.findByAgente_Id(id);
        ApiUtilies.checkResource(armamentoOptional, MessageState.ID_NAO_EXISTE);
        Armamento Armamento = armamentoOptional.get();
/*
        Optional<Agente> agenteOptional = efectivosRepository.findById(Armamento.getId());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);
        Agente agente = agenteOptional.get();
*/
        try {
            List<ArmamentoResponseDTO> armamentoResponseDTO = armamentoOptional.stream()
                    .map(armamento -> new ArmamentoResponseDTO(

                            armamento.getId(),
                            armamento.getNumero(),
                            armamento.getMarca(),
                            armamento.getModelo(),
                            armamento.getCalibre(),
                            armamento.getN_carregador(),
                            armamento.getN_municoes(),
                            armamento.getEstado(),
                            armamento.getFotografia(),
                            armamento.getAgente().getId(),
                            armamento.getAgente().getNome(),
                            armamento.getAgente().getApelido(),
                            // agente.getId(),
                            armamento.getCreation(),
                            armamento.getUpdate(),
                            armamento.getObs()) )
                    .collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(armamentoResponseDTO.toArray())).statusText(MessageState.SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }


    @Override
    public APIResponse updateArmaId(Long id, ArmamentoResponseDTO dto) {
        Optional<Armamento> armamentoOptional = armamentoRepository.findById(id);
        ApiUtilies.checkResource(armamentoOptional, MessageState.ID_NAO_EXISTE);
        Armamento armamento = armamentoOptional.get();
//
        Optional<Agente> agenteOptional = efectivosRepository.findById(armamento.getId());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);
         armamento = new Armamento();
        //

        try {

            BeanUtils.copyProperties(dto, armamento);
            armamento.setId(armamentoOptional.get().getId());

            /* 
            armamento.setId(dto.getIdarma());
            armamento.setNumero(dto.getNumero());
            armamento.setMarca(dto.getMarca());
            armamento.setModelo(dto.getModelo());
            armamento.setCalibre(dto.getCalibre());
            armamento.setN_carregador(dto.getN_carregador());
            armamento.setN_municoes(dto.getN_municoes());
            armamento.setEstado(dto.getEstado());
            armamento.setFotografia(dto.getFotografia());
            armamento.setAgente(agenteOptional.get());
            armamento.setCreation(dto.getCreation());
            armamento.setUpdate(dto.getUpdate());
            armamento.setObs(dto.getObs());
          */

            armamentoRepository.save(armamento);

            return APIResponse.builder().status(true).statusText(MessageState.ATUALIZADO_COM_SUCESSO).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_ATUALIZAR).details(l).build();

        }
    }
}
