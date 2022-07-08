package cv.pn.apitransito.services.implement;

import cv.pn.apitransito.dtos.FeriasResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.model.Ferias;
import cv.pn.apitransito.repository.EfectivosRepository;
import cv.pn.apitransito.repository.FeriasRepository;
import cv.pn.apitransito.services.FeriasService;
import cv.pn.apitransito.utilities.APIResponse;
import cv.pn.apitransito.utilities.ApiUtilies;
import cv.pn.apitransito.utilities.MessageState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeriasServiceImpl implements FeriasService {

    private final FeriasRepository feriasRepository;

    private final EfectivosRepository efectivosRepository;

    public FeriasServiceImpl(FeriasRepository feriasRepository, EfectivosRepository efectivosRepository) {
        this.feriasRepository = feriasRepository;
        this.efectivosRepository = efectivosRepository;
    }

    @Override
    public APIResponse feriasAll() {
        List<Ferias> listArma = feriasRepository.findAll();
        try {

            List<FeriasResponseDTO> feriasResponseDTOS = listArma.stream()
                    .map(ferias -> new FeriasResponseDTO(
                            ferias.getId(),
                            ferias.getData_inicio(),
                            ferias.getData_fim(),
                            ferias.getLocal_feria(),
                            ferias.getEntrega_arma(),
                            ferias.getN_oficio(),
                            ferias.getDespacho(),
                            ferias.getEstado(),
                            ferias.getPass_numero(),
                            ferias.getUser_despacho(),
                            ferias.getAgente().getId(),
                            ferias.getAgente().getNome(),
                            ferias.getAgente().getApelido(),
                            ferias.getCreation(),
                            ferias.getUpdate(),
                            ferias.getObs())).collect(Collectors.toList());


            return APIResponse.builder().status(true).details(Arrays.asList(feriasResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }

    @Override
    public APIResponse insertFerias(FeriasResponseDTO feriasResponseDTO) {
        Ferias ferias = new Ferias();

        Optional<Agente> agenteOptional = efectivosRepository.findById(feriasResponseDTO.getId_agente());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);
        Agente agente = agenteOptional.get();

        try {
            ferias.setId(feriasResponseDTO.getIdferia());
            ferias.setData_inicio(feriasResponseDTO.getData_inicio());
            ferias.setData_fim(feriasResponseDTO.getData_fim());
            ferias.setLocal_feria(feriasResponseDTO.getLocal_feria());
            ferias.setEntrega_arma(feriasResponseDTO.getEntrega_arma());
            ferias.setN_oficio(feriasResponseDTO.getN_oficio());
            ferias.setDespacho(feriasResponseDTO.getDespacho());
            ferias.setEstado(feriasResponseDTO.getEstado());
            ferias.setPass_numero(feriasResponseDTO.getPass_numero());
            ferias.setUser_despacho(feriasResponseDTO.getUser_despacho());
            ferias.setAgente(agente);
            ferias.setCreation(feriasResponseDTO.getCreation());
            ferias.setUpdate(feriasResponseDTO.getUpdate());
            ferias.setObs(feriasResponseDTO.getObs());

            feriasRepository.save(ferias);

            return APIResponse.builder().status(true).statusText(MessageState.INSERIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_DE_INSERCAO).details(l).build();
        }
    }

    @Override
    public APIResponse deleteFeria(Long id) {
        Optional<Ferias> feriasOptional = feriasRepository.findById(id);
        ApiUtilies.checkResource(feriasOptional, MessageState.ID_NAO_EXISTE);
        Ferias ferias = feriasOptional.get();

        try {
            feriasRepository.delete(ferias);
            return APIResponse.builder().status(true).statusText(MessageState.REMOVIDO_COM_SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }

    @Override
    public APIResponse listById(Long Id) {
        Optional<Ferias> feriasOptional = feriasRepository.findById(Id);
        ApiUtilies.checkResource(feriasOptional, MessageState.ID_NAO_EXISTE);
        Ferias ferias1 = feriasOptional.get();
        try {
            List<FeriasResponseDTO> feriasResponseDTOS = feriasOptional.stream()
                    .map(feria -> new FeriasResponseDTO(
                            feria.getId(),
                            feria.getData_inicio(),
                            feria.getData_fim(),
                            feria.getLocal_feria(),
                            feria.getEntrega_arma(),
                            feria.getN_oficio(),
                            feria.getDespacho(),
                            feria.getEstado(),
                            feria.getPass_numero(),
                            feria.getUser_despacho(),
                            feria.getAgente().getId(),
                            feria.getAgente().getNome(),
                            feria.getAgente().getApelido(),
                            feria.getCreation(),
                            feria.getUpdate(),
                            feria.getObs())).collect(Collectors.toList());

            return APIResponse.builder().status(true).details(Arrays.asList(feriasResponseDTOS.toArray())).statusText(MessageState.SUCESSO).build();

        }catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_REMOVER).details(l).build();
        }
    }

    @Override
    public APIResponse updateFeriatId(Long id, FeriasResponseDTO feriasResponseDTO) {
        Optional<Ferias> feriasOptional = feriasRepository.findById(id);
        ApiUtilies.checkResource(feriasOptional, MessageState.ID_NAO_EXISTE);
        Ferias ferias = feriasOptional.get();

        Optional<Agente> agenteOptional = efectivosRepository.findById(ferias.getId());
        ApiUtilies.checkResource(agenteOptional, "Efetivo: " + MessageState.ID_NAO_EXISTE);


        try {
            ferias.setId(feriasResponseDTO.getIdferia());
            ferias.setData_inicio(feriasResponseDTO.getData_inicio());
            ferias.setData_fim(feriasResponseDTO.getData_fim());
            ferias.setLocal_feria(feriasResponseDTO.getLocal_feria());
            ferias.setEntrega_arma(feriasResponseDTO.getEntrega_arma());
            ferias.setN_oficio(feriasResponseDTO.getN_oficio());
            ferias.setDespacho(feriasResponseDTO.getDespacho());
            ferias.setEstado(feriasResponseDTO.getEstado());
            ferias.setPass_numero(feriasResponseDTO.getPass_numero());
            ferias.setUser_despacho(feriasResponseDTO.getUser_despacho());
            ferias.setAgente(agenteOptional.get());
            ferias.setCreation(feriasResponseDTO.getCreation());
            ferias.setUpdate(feriasResponseDTO.getUpdate());
            ferias.setObs(feriasResponseDTO.getObs());

            feriasRepository.save(ferias);

            return APIResponse.builder().status(true).statusText(MessageState.ATUALIZADO_COM_SUCESSO).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO_AO_ATUALIZAR).details(l).build();

        }

    }



}
