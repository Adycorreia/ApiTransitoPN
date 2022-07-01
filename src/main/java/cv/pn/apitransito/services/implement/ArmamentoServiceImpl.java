package cv.pn.apitransito.services.implement;



import cv.pn.apitransito.dtos.ArmamentoResponseDTO;
import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.dtos.InfracaoResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Armamento;
import cv.pn.apitransito.model.Documents;
import cv.pn.apitransito.model.Infracao;
import cv.pn.apitransito.repository.ArmamentoRepository;
import cv.pn.apitransito.repository.EfectivosRepository;
import cv.pn.apitransito.services.ArmamentoService;

import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public  class ArmamentoServiceImpl implements ArmamentoService {

    @Autowired
    ArmamentoRepository armamentoRepository;

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
                            arma.getId_agente(),
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

        try {
            armamento.setId(armamentoResponseDTO.getIdarma());
            armamento.setNumero(armamentoResponseDTO.getNumero());
            armamento.setMarca(armamentoResponseDTO.getMarca());
            armamento.setModelo(armamentoResponseDTO.getModelo());
            armamento.setCalibre(armamentoResponseDTO.getCalibre());
            armamento.setN_carregador(armamentoResponseDTO.getN_carregador());
            armamento.setN_municoes(armamentoResponseDTO.getN_municoes());
            armamento.setEstado(armamentoResponseDTO.getEstado());
            armamento.setFotografia(armamentoResponseDTO.getFotografia());
            armamento.setId_agente(armamentoResponseDTO.getId_agente());
            armamento.setCreation(armamentoResponseDTO.getCreation());
            armamento.setUpdate(armamentoResponseDTO.getUpdate());
            armamento.setObs(armamentoResponseDTO.getObs());

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
        Armamento Armamento = armamentoOptional.get();


        try {
            List<ArmamentoResponseDTO> armamentoResponseDTOS = armamentoOptional.stream()
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
                            arma.getId_agente(),
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
    public APIResponse updateArmaId(Long id, ArmamentoResponseDTO dto) {
        Optional<Armamento> armamentoOptional = armamentoRepository.findById(id);
        ApiUtilies.checkResource(armamentoOptional, MessageState.ID_NAO_EXISTE);
        Armamento armamento = armamentoOptional.get();

        try {
            armamento.setId(dto.getIdarma());
            armamento.setNumero(dto.getNumero());
            armamento.setMarca(dto.getMarca());
            armamento.setModelo(dto.getModelo());
            armamento.setCalibre(dto.getCalibre());
            armamento.setN_carregador(dto.getN_carregador());
            armamento.setN_municoes(dto.getN_municoes());
            armamento.setEstado(dto.getEstado());
            armamento.setFotografia(dto.getFotografia());
            armamento.setId_agente(dto.getId_agente());
            armamento.setCreation(dto.getCreation());
            armamento.setUpdate(dto.getUpdate());
            armamento.setObs(dto.getObs());


            armamentoRepository.save(armamento);

            return APIResponse.builder().status(true).statusText(MessageState.ATUALIZADO_COM_SUCESSO).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_ATUALIZAR).details(l).build();

        }
    }
}
