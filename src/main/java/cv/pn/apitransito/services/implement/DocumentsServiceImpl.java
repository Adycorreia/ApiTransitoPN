package cv.pn.def.services.implement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import cv.pn.def.dtos.*;
import cv.pn.def.dtos.notification.SendEmailDTO;
import cv.pn.def.dtos.notification.SendSMSDTO;
import cv.pn.def.dtos.secretary.UpdateStatusRequestDSDTO;
import cv.pn.def.dtos.sniac.*;
import cv.pn.def.entities.*;
import cv.pn.def.entities.acessoschema.Geografia;
import cv.pn.def.repositories.*;
import cv.pn.def.repositories.acessoschema.DomainRepository;
import cv.pn.def.repositories.acessoschema.GeografiaRepository;
import cv.pn.def.services.ForeignService;
import cv.pn.def.services.PhaseExecService;
import cv.pn.def.utilities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public  class PhaseExecServiceImpl implements PhaseExecService {

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private PhaseRequestExecRepository phaseRequestExecRepository;

    @Autowired
    private  RequestRepository requestRepository;

    @Autowired
    private GeografiaRepository geographyRepository;

    @Autowired
    private ForeignService foreignService;

    @Autowired
    private CardNumberRepository cardNumberRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private BiometricRepository biometricRepository;

    @Autowired
    private  CodeCountryRepository codeCountryRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultLink defaultLink;

    @Autowired
    private DigitalSecretaryServiceImpl digitalSecretaryServiceImpl;

    public APIResponse processPhaseExecall(String id) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(id);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        PhaseRequestExec phaseRequestExe = optionalPhaseRequestExec.get();

        System.out.println("phaseExec: " + phaseRequestExe.getId());

        Optional<Phase> optionalPhase = phaseRepository.findById(phaseRequestExe.getIdFase());
        ApiUtilies.checkResource(optionalPhase, MessageState.ID_NAO_EXISTE);
        Phase phase = optionalPhase.get();

        List<Object> detailsPhase = new ArrayList<>();

        try {
            PhaseResponseExexDTO phaseResponseExexDTO = new PhaseResponseExexDTO();

            phaseResponseExexDTO.setIdPdFaseExec(phaseRequestExe.getId());
            phaseResponseExexDTO.setIdtppedido(phaseRequestExe.getIdTpPedido());
            phaseResponseExexDTO.setIncm(phaseRequestExe.getIncm());
            phaseResponseExexDTO.setIdPedido(phaseRequestExe.getIdPedido());
            phaseResponseExexDTO.setFaseCode(phase.getCode());

            detailsPhase.add(phaseResponseExexDTO);

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(detailsPhase).build();

        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }

    }

    @Override
    public APIResponse jsonInputPhaseExec(String id) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(id);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);

        List<Object> objectList = new ArrayList<>();

        try {
            objectList.add(optionalPhaseRequestExec.get().getJson_entrada());
            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(objectList).build();
        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }
    }

    @Override
    public APIResponse refusalProcess(FinalJsonDTO dto) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

        Optional<Request> optionalRequest = requestRepository.findById(dto.getPedidotre().getPedido().getFase().getId_pedido());
        ApiUtilies.checkResource(optionalRequest, MessageState.ID_NAO_EXISTE);
        Request request = optionalRequest.get();


        if(!optionalPhaseRequestExec.get().getEstado().equals("PI")) {

            return APIResponse.builder().status(false).statusText("Contactar Tecnico - Processo não pode ser recusado").build();
        }

        if(dto.getPedidotre().getPedido().getRecusa().getFlag_Biog().equals(false) && dto.getPedidotre().getPedido().getRecusa().getFlag_Biom().equals(false)) {
            System.out.println("Recusa interna");

            String faseAtual = "", faseDestino = "";

            Optional<Phase> optionalPhaseAtual = phaseRepository.findByCode(dto.getPedidotre().getPedido().getFase().getFase_code());
            faseAtual = optionalPhaseAtual.get().getIdPhase();

            if(dto.getPedidotre().getPedido().getFase().getFase_code().equals("AD")) {

                return APIResponse.builder().status(false).statusText("Impossivel realizar recusa interna nessa etapa").build();

            } if(dto.getPedidotre().getPedido().getFase().getFase_code().equals("DEP")) {

                Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("AD");
                faseDestino = optionalPhaseDestino.get().getIdPhase();

            } if(dto.getPedidotre().getPedido().getFase().getFase_code().equals("DP")) {

                Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("DEP");
                faseDestino = optionalPhaseDestino.get().getIdPhase();

            }

            PhaseExecDTO phaseExecDTO = new PhaseExecDTO();
            phaseExecDTO.setIdTipoPedido(dto.getPedidotre().getPedido().getFase().getId_tp_pedido());
            phaseExecDTO.setIdFaseAtual(faseAtual);
            phaseExecDTO.setIdFaseDestino(faseDestino);
            phaseExecDTO.setIdPedido(dto.getPedidotre().getPedido().getFase().getId_pedido());
            phaseExecDTO.setUserUuidFase(dto.getPedidotre().getPedido().getFase().getUser_uuid_fase());
            phaseExecDTO.setDm_estado("PG");
            phaseExecDTO.setEstadoInicial("PI");
            phaseExecDTO.setEstadoFinal("RI");
            phaseExecDTO.setParecer(dto.getPedidotre().getPedido().getFase().getDecisao_final());
            phaseExecDTO.setIncm(dto.getPedidotre().getPedido().getFase().getIncm());
            phaseExecDTO.setObs(dto.getPedidotre().getPedido().getFase().getObservacao());
            phaseExecDTO.setLocalNascimentoDesc(dto.getPedidotre().getPedido().getFase().getLocalNascimentoDesc());
            phaseExecDTO.setJsonEntrada(new Gson().toJson(dto));
            phaseExecDTO.setMotivo(dto.getPedidotre().getPedido().getFase().getMotivo());

            updatePhaseExec(phaseExecDTO, dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
            insertPhaseExec(phaseExecDTO);

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
        }

        try {


            String concatBiog = "", concatBiom = "";
            int erro = 0;

            if (dto.getPedidotre().getPedido().getRecusa().getFlag_Biog().equals(true) && dto.getPedidotre().getPedido().getRecusa().getFlag_Biom().equals(true)) {

                System.out.println("Recusa pelos dados biometricos e biograficos 3");
                erro = 3;
            }
            if (dto.getPedidotre().getPedido().getRecusa().getFlag_Biog().equals(true) && dto.getPedidotre().getPedido().getRecusa().getFlag_Biom().equals(false)) {
                System.out.println("Recusa pelos dados biograficos 1");

                erro = 1;
            }
            if (dto.getPedidotre().getPedido().getRecusa().getFlag_Biog().equals(false) && dto.getPedidotre().getPedido().getRecusa().getFlag_Biom().equals(true)) {
                System.out.println("Recusa pelos dados biometricos 2");

                erro = 2;
            }

            for (FinalJsonDTO.Tipo tipo : dto.getPedidotre().getPedido().getRecusa().getErro().getTipo()) {

                if (tipo.getValue().equals("BIOG")) {

                    concatBiog = tipo.getErro().concat(",").concat(concatBiog);

                }
                if (tipo.getValue().equals("BIOM")) {

                    concatBiom = tipo.getErro().concat(",").concat(concatBiom);
                }

            }

            System.out.println(concatBiog);

            System.out.println(concatBiom);

            String apelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido(), apelido2 = "";

            int lengthApelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido().length();

            if (lengthApelido > 44) {

                apelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido().substring(0, 45);
                apelido2 = dto.getPedidotre().getPedido().getIdentificacao().getApelido().substring(45, lengthApelido);
            }


            RequestSNIACDTO.Row row = new RequestSNIACDTO.Row();
            row.setMimetype("xml");
            row.setEntidade_despacho("DEFSP");
            row.setDespacho("INVALIDO");
            row.setUser_despacho("202020");
            row.setNr_processo(dto.getPedidotre().getPedido().getProcessoINCM());
            //row.setData_emissao(dto.getPedidotre().getPedido().getFase().getData().getEmissao());
            row.setMotivo(dto.getPedidotre().getPedido().getFase().getMotivo());
            //row.setDt_validade(dto.getPedidotre().getPedido().getFase().getData().getValidade());
            row.setId_estrangeiro(dto.getPedidotre().getPedido().getIdEstrangeiros());
            row.setMotivo_biometrico(concatBiom);
            row.setMotivo_biografico(concatBiog);
            row.setErros(erro);
            row.setTipo_documento("TRECV");
            row.setNic(dto.getPedidotre().getPedido().getIdentificacao().getNumeroIdentificacao());
            row.setApelido(apelido);
            row.setApelido2(apelido2);
            row.setNome(dto.getPedidotre().getPedido().getIdentificacao().getNome());
            row.setNacionalidade(dto.getPedidotre().getPedido().getIdentificacao().getNacionalidade());
            row.setNaturalidade(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
            row.setSexo(dto.getPedidotre().getPedido().getIdentificacao().getSexo());
            row.setDt_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
            row.setApelido_pai(dto.getPedidotre().getPedido().getIdentificacao().getApelidoPai());
            row.setNome_pai(dto.getPedidotre().getPedido().getIdentificacao().getNomePai());
            row.setApelido_mae(dto.getPedidotre().getPedido().getIdentificacao().getApelidoMae());
            row.setNome_mae(dto.getPedidotre().getPedido().getIdentificacao().getNomeMae());
            row.setEstado_civil(dto.getPedidotre().getPedido().getIdentificacao().getEstadoCivil());
            row.setAltura(dto.getPedidotre().getPedido().getDadosBiometricos().getAltura());
            row.setTelefone(dto.getPedidotre().getPedido().getMorada().getTelefone());
            row.setTelemovel(dto.getPedidotre().getPedido().getMorada().getTelemovel());
            row.setEmail(dto.getPedidotre().getPedido().getMorada().getEMail());
            row.setConcelho(dto.getPedidotre().getPedido().getMorada().getConcelho());
            row.setFreguesia(dto.getPedidotre().getPedido().getMorada().getFreguesia());
            row.setLocalidade(dto.getPedidotre().getPedido().getMorada().getLocalidade());
            row.setZona(dto.getPedidotre().getPedido().getMorada().getZona());
            row.setIlha(dto.getPedidotre().getPedido().getMorada().getIlha());
            //row.setPan();
            //row.setNumerodocumentovisual(numerovisual);
            //row.setTipo_titulo1(dto.getPedidotre().getPedido().getFase().getTitulo());
            //row.setTipo_titulo2(dto.getPedidotre().getPedido().getFase().getSubTitulo());
            //row.setObservacoes1(dto.getPedidotre().getPedido().getFase().getObs1());
            //row.setObservacoes2(dto.getPedidotre().getPedido().getFase().getObs2());
            // row.setLocal_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
            // row.setPais("238");
            //row.setNacionalidade_desc(nacionalidadeDesc);
            //row.setLocal_nascimento_desc(dto.getPedidotre().getPedido().getFase().getLocalNascimentoDesc());
            //row.setMrz_1(dto.getPedidotre().getPedido().getCodigosMrz().getMrz1());
            //row.setMrz_2(dto.getPedidotre().getPedido().getCodigosMrz().getMrz2());
            //row.setMrz_3(dto.getPedidotre().getPedido().getCodigosMrz().getMrz3());

            RequestSNIACDTO.Rows rows = new RequestSNIACDTO.Rows();
            rows.setRow(row);

            System.out.println("rows: " + rows);

            XmlMapper mapper = new XmlMapper();
            String xmlSniac = mapper.writeValueAsString(rows);

            System.out.println("xmlSniac: " + xmlSniac);

            request.setFinalXml(xmlSniac);
            //request.setNumCartao(numerovisual);
            requestRepository.save(request);

            phaseRequestExec.setObservacao(dto.getPedidotre().getPedido().getFase().getObservacao());
            phaseRequestExec.setUser_uuid_fase(dto.getPedidotre().getPedido().getFase().getUser_uuid_fase());
            phaseRequestExec.setJson_saida(new Gson().toJson(dto));
            phaseRequestExec.setEstado("R");

            phaseRequestExecRepository.save(phaseRequestExec);


            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }

    }

    @Override
    @Transactional
    public APIResponse validateProcess(FinalJsonDTO dto) {

        //System.out.println(dto.getPedidotre().getPedido().getFase().getFase_code());

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);

        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

       // System.out.println("estado: " + optionalPhaseRequestExec.get().getEstado());

        if(!optionalPhaseRequestExec.get().getEstado().equals("PI")) {

            return APIResponse.builder().status(false).statusText("Contactar Tecnico - Processo ja foi Validado").build();
        }

        Optional<Request> optionalRequest = requestRepository.findById(dto.getPedidotre().getPedido().getFase().getId_pedido());
        ApiUtilies.checkResource(optionalRequest, MessageState.ID_NAO_EXISTE);
        Request request = optionalRequest.get();

        String faseAtual = "", faseDestino = "";

        if (dto.getPedidotre().getPedido().getFase().getFase_code().equals("RPT")) {

            Optional<Phase> optionalPhaseAtual = phaseRepository.findByCode("RPT");
            faseAtual = optionalPhaseAtual.get().getIdPhase();

            Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("AD");
            faseDestino = optionalPhaseDestino.get().getIdPhase();
        }

        if (dto.getPedidotre().getPedido().getFase().getFase_code().equals("AD")) {

            Optional<Phase> optionalPhaseAtual = phaseRepository.findByCode("AD");
            faseAtual = optionalPhaseAtual.get().getIdPhase();

            Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("DEP");
            faseDestino = optionalPhaseDestino.get().getIdPhase();
        }

        if (dto.getPedidotre().getPedido().getFase().getFase_code().equals("DEP")) {

            Optional<Phase> optionalPhaseAtual = phaseRepository.findByCode("DEP");
            faseAtual = optionalPhaseAtual.get().getIdPhase();

            Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("DP");
            faseDestino = optionalPhaseDestino.get().getIdPhase();
        }

        if (dto.getPedidotre().getPedido().getFase().getFase_code().equals("DP")) {

            Optional<Phase> optionalPhaseAtual = phaseRepository.findByCode("DP");
            faseAtual = optionalPhaseAtual.get().getIdPhase();

            if (dto.getPedidotre().getPedido().getFase().getDecisao_final().equals("DF")) {

                Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("EC");
                faseDestino = optionalPhaseDestino.get().getIdPhase();

            } else {
                Optional<Phase> optionalPhaseDestino = phaseRepository.findByCode("PIND");
                faseDestino = optionalPhaseDestino.get().getIdPhase();
            }
        }

        PhaseExecDTO phaseExecDTO = new PhaseExecDTO();
        phaseExecDTO.setIdTipoPedido(dto.getPedidotre().getPedido().getFase().getId_tp_pedido());
        phaseExecDTO.setIdFaseAtual(faseAtual);
        phaseExecDTO.setIdFaseDestino(faseDestino);
        phaseExecDTO.setIdPedido(dto.getPedidotre().getPedido().getFase().getId_pedido());
        phaseExecDTO.setUserUuidFase(dto.getPedidotre().getPedido().getFase().getUser_uuid_fase());
        phaseExecDTO.setNumSitre(dto.getPedidotre().getPedido().getNumSITRE());

        phaseExecDTO.setDm_estado("PG");

        if(dto.getPedidotre().getPedido().getFase().getDecisao_final() == null) {

            phaseExecDTO.setEstadoInicial("PI");

        } /*else {

            if (dto.getPedidotre().getPedido().getFase().getDecisao_final().equals("DF")) {
                phaseExecDTO.setEstadoInicial("PI");
            } if (dto.getPedidotre().getPedido().getFase().getDecisao_final().equals("IDF")) {
                phaseExecDTO.setEstadoInicial("C");
            }
        }*/
        phaseExecDTO.setEstadoFinal("C");
        phaseExecDTO.setParecer(dto.getPedidotre().getPedido().getFase().getDecisao_final());
        phaseExecDTO.setIncm(dto.getPedidotre().getPedido().getFase().getIncm());
        phaseExecDTO.setObs(dto.getPedidotre().getPedido().getFase().getObservacao());
        phaseExecDTO.setLocalNascimentoDesc(dto.getPedidotre().getPedido().getFase().getLocalNascimentoDesc());



        switch (dto.getPedidotre().getPedido().getFase().getFase_code()) {

            case "AD":
            case "DEP":
            case "RPT":

                phaseExecDTO.setEstadoInicial("PI");
                phaseExecDTO.setJsonEntrada(new Gson().toJson(dto));

                updatePhaseExec(phaseExecDTO, dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
                insertPhaseExec(phaseExecDTO);

               if(dto.getPedidotre().getPedido().getFase().getFase_code().equals("RPT")) {

                     /* --- atualizar estado secretaria digital envio sniac - despacho --- */

                    digitalSecretaryServiceImpl.updateStatusRequest(new UpdateStatusRequestDSDTO(phaseRequestExec.getId(), dto.getPedidotre().getPedido().getNumeroPedido(),
                    dto.getPedidotre().getPedido().getNumeroRequisicao(), dto.getPedidotre().getPedido().isNumeroRequisicaoOrigem(), null, Constants.UpdateStatusCodeSD.DEF));


                }

                return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();

            case "DP":

                try {



                    if (dto.getPedidotre().getPedido().getFase().getDecisao_final().equals("DF")) {

                        phaseExecDTO.setEstadoInicial("AE");

                        Optional<Geografia> optionalIlha = geographyRepository.findById(dto.getPedidotre().getPedido().getMorada().getIlha());
                        String ilha = optionalIlha.get().getNomeCartao();
                        System.out.println("ilha: " + ilha);

                        Optional<Geografia> optionalConcelho = geographyRepository.findById(dto.getPedidotre().getPedido().getMorada().getConcelho());
                        String concelho = optionalConcelho.get().getNomeCartao();
                        //System.out.println("concelho: " + concelho);

                        Optional<CodeCountry> optionalNacionalidade = codeCountryRepository.findByGeographyID(dto.getPedidotre().getPedido().getIdentificacao().getNacionalidade());
                        String nacionalidadeDesc = optionalNacionalidade.get().getCode3();
                       // System.out.println("nacionalidade: " + nacionalidadeDesc);

                        Optional<CodeCountry> optionalNaturalidade = codeCountryRepository.findByGeographyID(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
                        String naturalidade = optionalNaturalidade.get().getCode3();
                       // System.out.println("naturalidade: " + naturalidade);

                        Optional<CardNumber> optionalNdv = cardNumberRepository.findDistinctFirstByFlagDefTrueAndAndFlagIncmTrue();
                        CardNumber cardNumber = optionalNdv.get();
                        String ndv = Integer.toString(cardNumber.getNdv());

                        Optional<Domain> optionalDomain = domainRepository.findByDominioAndValor("DECISAO_FINAL", dto.getPedidotre().getPedido().getFase().getDecisao_final());
                        ApiUtilies.checkResource(optionalDomain, "Erro: Decisao final nao existe");
                        Domain domain = optionalDomain.get();

                        cardNumber.setFlagDef(false);
                        cardNumberRepository.save(cardNumber);

                      //  System.out.println("ndv: " + ndv);

                        String numerovisual = "TRB".concat(ndv);
                       // System.out.println("numerovisual: " + numerovisual);

                        String residencia = concelho.concat("-").concat(ilha);
                       // System.out.println("residencia: " + residencia);

                        String ano = dto.getPedidotre().getPedido().getFase().getAno();

                        LocalDateTime date = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        String emissao = date.format(formatter);

                       // System.out.println("emissao: " + emissao);

                        LocalDateTime dateValidade = date.minusDays(1).plusYears(Integer.parseInt(ano));
                        String validade = dateValidade.format(formatter);
                        //System.out.println("validade: " + validade);


                        String dateOfBirth = dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento();



                       // System.out.println("dateOfBirth: " + dateOfBirth);

                        MrzDTO mrzDTO = new MrzDTO();

                        mrzDTO.setName(dto.getPedidotre().getPedido().getIdentificacao().getNome());
                        mrzDTO.setDateOfBirth(dateOfBirth);
                        mrzDTO.setExpiry(validade);
                        mrzDTO.setNationalityISO(nacionalidadeDesc);

                        if(dto.getPedidotre().getPedido().getIdentificacao().getNumeroIdentificacao()== null) {

                            mrzDTO.setNic("0");

                        } else {

                            mrzDTO.setNic(dto.getPedidotre().getPedido().getIdentificacao().getNumeroIdentificacao());
                        }

                        mrzDTO.setSex(dto.getPedidotre().getPedido().getIdentificacao().getSexo().toString());
                        mrzDTO.setSurname(dto.getPedidotre().getPedido().getIdentificacao().getApelido());
                        mrzDTO.setNdv(numerovisual);

                        MrzResponseDTO mrzResponseDTO = foreignService.getMRZ(mrzDTO);
                       // System.out.println("mrzResponseDTO: " + new Gson().toJson(mrzResponseDTO));


                        ModelMapper modelMapper = new ModelMapper();
                        FinalJsonDTO.CodigosMrz codigosMrz = modelMapper.map(mrzResponseDTO, FinalJsonDTO.CodigosMrz.class);

                        //System.out.println("codigosMrz: " + codigosMrz.toString());

                        dto.getPedidotre().getPedido().setCodigosMrz(codigosMrz);
                        dto.getPedidotre().getPedido().getFase().setData(new FinalJsonDTO.Datas(emissao, validade, numerovisual, dto.getPedidotre().getPedido().getFase().getDecisao_final()));

                        //System.out.println("Fase: " + dto.getPedidotre().getPedido().getFase().toString());

                       // System.out.println("Mrz: " + dto.getPedidotre().getPedido().getCodigosMrz().toString());

                        String apelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido(), apelido2 = "";

                        int lengthApelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido().length();

                        if (lengthApelido > 44) {

                            apelido = dto.getPedidotre().getPedido().getIdentificacao().getApelido().substring(0, 45);
                            apelido2 = dto.getPedidotre().getPedido().getIdentificacao().getApelido().substring(45, lengthApelido);
                        }

                        String xmlSniac = "";


                        if(request.getOrigem().equals("SNIAC")) {

                            RequestSNIACDTO.Row row = new RequestSNIACDTO.Row();
                            row.setMimetype("xml");
                            row.setEntidade_despacho("DEFSP");
                            row.setDespacho(domain.getDescricao());
                            row.setUser_despacho("202020");
                            row.setNr_processo(dto.getPedidotre().getPedido().getProcessoINCM());
                            row.setData_emissao(dto.getPedidotre().getPedido().getFase().getData().getEmissao());
                            //row.setMotivo(dto.getPedidotre().getPedido().getFase().getMotivo());
                            row.setDt_validade(dto.getPedidotre().getPedido().getFase().getData().getValidade());
                            row.setId_estrangeiro(dto.getPedidotre().getPedido().getIdEstrangeiros());
                            //row.setMotivo_biometrico(dto.getPedidotre().getPedido().getFase().);
                            //row.setMotivo_biografico();
                            //row.setErros();
                            row.setTipo_documento("TRECV");
                            row.setNic(dto.getPedidotre().getPedido().getIdentificacao().getNumeroIdentificacao());
                            row.setApelido(apelido);
                            row.setApelido2(apelido2);
                            row.setNome(dto.getPedidotre().getPedido().getIdentificacao().getNome());
                            row.setNacionalidade(dto.getPedidotre().getPedido().getIdentificacao().getNacionalidade());
                            row.setNaturalidade(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
                            row.setSexo(dto.getPedidotre().getPedido().getIdentificacao().getSexo());
                            row.setDt_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
                            row.setApelido_pai(dto.getPedidotre().getPedido().getIdentificacao().getApelidoPai());
                            row.setNome_pai(dto.getPedidotre().getPedido().getIdentificacao().getNomePai());
                            row.setApelido_mae(dto.getPedidotre().getPedido().getIdentificacao().getApelidoMae());
                            row.setNome_mae(dto.getPedidotre().getPedido().getIdentificacao().getNomeMae());
                            row.setEstado_civil(dto.getPedidotre().getPedido().getIdentificacao().getEstadoCivil());
                            row.setAltura(dto.getPedidotre().getPedido().getDadosBiometricos().getAltura());
                            row.setTelefone(dto.getPedidotre().getPedido().getMorada().getTelefone());
                            row.setTelemovel(dto.getPedidotre().getPedido().getMorada().getTelemovel());
                            row.setEmail(dto.getPedidotre().getPedido().getMorada().getEMail());
                            row.setIlha(dto.getPedidotre().getPedido().getMorada().getIlha());
                            row.setConcelho(dto.getPedidotre().getPedido().getMorada().getConcelho());
                            row.setFreguesia(dto.getPedidotre().getPedido().getMorada().getFreguesia());
                            row.setLocalidade(dto.getPedidotre().getPedido().getMorada().getLocalidade());
                            row.setZona(dto.getPedidotre().getPedido().getMorada().getZona());
                            //row.setPan();
                            row.setNumerodocumentovisual(numerovisual);
                            row.setTipo_titulo1(dto.getPedidotre().getPedido().getFase().getTitulo());
                            row.setTipo_titulo2(dto.getPedidotre().getPedido().getFase().getSubTitulo());
                            row.setObservacoes1(dto.getPedidotre().getPedido().getFase().getObs1());
                            row.setObservacoes2(dto.getPedidotre().getPedido().getFase().getObs2());
                            row.setLocal_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
                            row.setPais("238");
                            row.setResidencia(residencia);
                            row.setNacionalidade_desc(nacionalidadeDesc);
                            row.setLocal_nascimento_desc(naturalidade);
                            row.setMrz_1(dto.getPedidotre().getPedido().getCodigosMrz().getMrz1());
                            row.setMrz_2(dto.getPedidotre().getPedido().getCodigosMrz().getMrz2());
                            row.setMrz_3(dto.getPedidotre().getPedido().getCodigosMrz().getMrz3());

                            RequestSNIACDTO.Rows rows = new RequestSNIACDTO.Rows();
                            rows.setRow(row);

                            XmlMapper mapper = new XmlMapper();
                            xmlSniac = mapper.writeValueAsString(rows);

                        }

                        Formatter fm = new Formatter();
                        fm.format("%.2f", dto.getPedidotre().getPedido().getDadosBiometricos().getAltura());


                        if(request.getOrigem().equals("SD") || request.getOrigem().equals("SITRE")) {

                            SNIACDTO.pedido_info pedido_info = new SNIACDTO.pedido_info();

                            pedido_info.setId_pedido(dto.getPedidotre().getPedido().getNumeroPedido());
                            pedido_info.setId_requisicao(dto.getPedidotre().getPedido().getNumeroRequisicao());

                            if(request.getFinalXml() != null) {

                                XmlMapper xmlMapper = new XmlMapper();
                                SNIACDTO.rows rows = xmlMapper.readValue(request.getFinalXml(), SNIACDTO.rows.class);
                                pedido_info.setId_pedido_pdex(rows.getRow().getPedido_info().getId_pedido_pdex());

                            }


                            pedido_info.setId_pedido_portal(request.getNumSitre());
                            pedido_info.setId_entidade_pedido(dto.getPedidotre().getPedido().getUnidadeOrganicaPedido());
                            pedido_info.setId_utilizador_pedido("manuel.g.nascimento@pn.gov.cv");
                            pedido_info.setDt_pedido(dto.getPedidotre().getPedido().getIGRP_Data_Pedido());
                            pedido_info.setTp_doc("TRE");
                            pedido_info.setTp_tre(dto.getPedidotre().getPedido().getTipotre());
                            pedido_info.setCategoria_tp_tre(dto.getPedidotre().getPedido().getCategoriaTipoTre());
                            pedido_info.setTipo_titulo1(dto.getPedidotre().getPedido().getFase().getTitulo());
                            pedido_info.setTipo_titulo2(dto.getPedidotre().getPedido().getFase().getSubTitulo());
                            //if(dto.getPedidotre().getPedido().getFase().getObs1()!=null) {
                                pedido_info.setObservacoes1(dto.getPedidotre().getPedido().getFase().getObs1());
                            //}
                            //if(dto.getPedidotre().getPedido().getFase().getObs2()!=null) {
                                pedido_info.setObservacoes2(dto.getPedidotre().getPedido().getFase().getObs2());
                           // }
                            pedido_info.setTp_taxa("NORMAL");
                            pedido_info.setPrioridade("NR");
                            pedido_info.setValor(1400);
                            pedido_info.setNum_visual(numerovisual);

                            SNIACDTO.identificacao_info identificacao_info = new SNIACDTO.identificacao_info();

                            identificacao_info.setId_estramgeiro(dto.getPedidotre().getPedido().getIdEstrangeiros());
                            identificacao_info.setNif(dto.getPedidotre().getPedido().getIdentificacao().getNif());
                            identificacao_info.setNum_passaporte(dto.getPedidotre().getPedido().getIdentificacao().getPassaporte().getNumeroPassaporte());
                            identificacao_info.setEntidade_emitente(dto.getPedidotre().getPedido().getIdentificacao().getPassaporte().getEmitidoPor());
                            identificacao_info.setData_emissao(emissao);
                            identificacao_info.setData_validade(validade);
                            identificacao_info.setMrz1(dto.getPedidotre().getPedido().getCodigosMrz().getMrz1());
                            identificacao_info.setMrz2(dto.getPedidotre().getPedido().getCodigosMrz().getMrz2());
                            identificacao_info.setMrz3(dto.getPedidotre().getPedido().getCodigosMrz().getMrz3());
                            identificacao_info.setNome_completo(dto.getPedidotre().getPedido().getIdentificacao().getNome().concat(" ").concat(dto.getPedidotre().getPedido().getIdentificacao().getApelido()));
                            identificacao_info.setNome_proprio(dto.getPedidotre().getPedido().getIdentificacao().getNome());
                            //if(dto.getPedidotre().getPedido().getIdentificacao().getApelido() != null) {
                                identificacao_info.setNome_apelido(dto.getPedidotre().getPedido().getIdentificacao().getApelido());
                            //}
                           // if(dto.getPedidotre().getPedido().getIdentificacao().getNomePai() != null) {
                                identificacao_info.setNome_pai_proprio(dto.getPedidotre().getPedido().getIdentificacao().getNomePai());
                            //}
                           // if(dto.getPedidotre().getPedido().getIdentificacao().getApelidoPai() != null) {
                                identificacao_info.setNome_pai_apelido(dto.getPedidotre().getPedido().getIdentificacao().getApelidoPai());
                            //}
                            //if(dto.getPedidotre().getPedido().getIdentificacao().getNomeMae() != null) {
                                identificacao_info.setNome_mae_proprio(dto.getPedidotre().getPedido().getIdentificacao().getNomeMae());
                           // }
                            //if(dto.getPedidotre().getPedido().getIdentificacao().getApelidoMae() != null) {
                                identificacao_info.setNome_mae_apelido(dto.getPedidotre().getPedido().getIdentificacao().getApelidoMae());
                           // }
                            identificacao_info.setData_nasc(dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
                            identificacao_info.setSexo(dto.getPedidotre().getPedido().getIdentificacao().getSexo());
                            identificacao_info.setEstado_civil(dto.getPedidotre().getPedido().getIdentificacao().getEstadoCivil());

                            identificacao_info.setAltura(fm.toString());
                            identificacao_info.setNaturalidade_id(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
                            identificacao_info.setNacionalidade_id(dto.getPedidotre().getPedido().getIdentificacao().getNacionalidade());
                            identificacao_info.setLocal_nascimento_id(dto.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
                            //if(dto.getPedidotre().getPedido().getIdentificacao().getProfissao() != null) {
                                identificacao_info.setProfissao(dto.getPedidotre().getPedido().getIdentificacao().getProfissao());
                           // }
                            //if(dto.getPedidotre().getPedido().getIdentificacao().getIndicacoesEventuais() != null) {
                                identificacao_info.setIndicacoes_eventuais(dto.getPedidotre().getPedido().getIdentificacao().getIndicacoesEventuais());
                            //}

                            SNIACDTO.endereco_morada_info endereco_morada_info = new SNIACDTO.endereco_morada_info();

                            endereco_morada_info.setTp_morada("P");
                            endereco_morada_info.setDesc_morada(dto.getPedidotre().getPedido().getMorada().getNomeLocalidade());
                            endereco_morada_info.setPais_resid_id("238");
                            endereco_morada_info.setLocalidade_id(dto.getPedidotre().getPedido().getMorada().getLocalidade());
                            endereco_morada_info.setTelefone(dto.getPedidotre().getPedido().getMorada().getTelefone());
                            endereco_morada_info.setTelemovel(dto.getPedidotre().getPedido().getMorada().getTelemovel());
                            endereco_morada_info.setEmail(dto.getPedidotre().getPedido().getMorada().getEMail());
                            //endereco_morada_info.setRua(dto.getPedidotre().getPedido().getMorada().);
                            //endereco_morada_info.setNum_porta();
                            //endereco_morada_info.setPiso();
                            //endereco_morada_info.setCodigo_postal();
                            endereco_morada_info.setResidencia_cartao(residencia);

                            SNIACDTO.local_recebimento_info local_recebimento_info = new SNIACDTO.local_recebimento_info();

                            local_recebimento_info.setId_local_receb(dto.getPedidotre().getPedido().getUnidadeOrganicaPedido());

                            SNIACDTO.biometrico_info biometrico_info = new SNIACDTO.biometrico_info();

                            Optional<BiometricData> biometricDataOptional = biometricRepository.findById(request.getId_biometrico());

                            BiometricData biometricData = biometricDataOptional.get();

                            ObjectMapper objectMapper = new ObjectMapper();
                            KioskResponseDTO kioskResponseDTO = objectMapper.readValue(biometricData.getJson(), KioskResponseDTO.class);

                            List<Object> jsonBiometria = kioskResponseDTO.getGetDataAllResponse().getGetDataAllResult().getBiometricDataList().getItem().stream()
                                    .map(item -> {

                                        if (item.getItemName().equals("Face_jpg_raw")) {

                                            biometrico_info.setFoto_raw(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Face_jp2_normalised")) {

                                            biometrico_info.setFoto_2000(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Face_jpeg_normalised_greyscale")) {

                                            biometrico_info.setFoto_cinza(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Fingerprint_wsq_right")) {

                                            biometrico_info.setWsq_direito(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Fingerprint_wsq_right_CBEFF")) {

                                            biometrico_info.setWsq_direito_cbeff(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Fingerprint_wsq_left")) {

                                            biometrico_info.setWsq_esquerdo(item.getItemData());
                                        }
                                        if (item.getItemName().equals("Fingerprint_wsq_left_CBEFF")) {

                                            biometrico_info.setWsq_esquerdo_cbeff(item.getItemData());
                                        }

                                        return biometrico_info;
                                    }).collect(Collectors.toList());

                            //biometrico_info.setKiosk();
                            biometrico_info.setFoto(biometricData.getFoto());
                            biometrico_info.setJpg_b_remov(biometricData.getFotoGs());
                            biometrico_info.setAssinatura(biometricData.getAssinatura());
                            biometrico_info.setImpressao_digital_direito(dto.getPedidotre().getPedido().getDadosBiometricos().getImpressaoDigitalD());
                            biometrico_info.setPosicao_dedo_dir(biometricData.getDedoDigitalDrt());
                            biometrico_info.setImpressao_digital_esquerdo(biometricData.getDigitalEsq());
                            biometrico_info.setPosicao_dedo_esq(biometricData.getDedoDigitalEsq());
                            biometrico_info.setAltura(fm.toString());
                            biometrico_info.setAltura_kiosk(fm.toString());

                            if(dto.getPedidotre().getPedido().getDadosBiometricos().getCasosEspeciais().equals("1")) {

                                biometrico_info.setCasos_especias("S");
                                biometrico_info.setObservacao(Base64.getEncoder().encodeToString(dto.getPedidotre().getPedido().getDadosBiometricos().getObservacao().getBytes()));
                                biometrico_info.setAusencia_assinatura(dto.getPedidotre().getPedido().getDadosBiometricos().getMotivoAusenciaAssinatura());
                                biometrico_info.setAusenciaImpDigitalD(dto.getPedidotre().getPedido().getDadosBiometricos().getImpressaoDigitalD());
                                biometrico_info.setAusenciaImpDigitalE(dto.getPedidotre().getPedido().getDadosBiometricos().getImpressaoDigitalE());

                            } else {

                                biometrico_info.setCasos_especias("N");

                            }

                            SNIACDTO.pagamento_info pagamento_info = new SNIACDTO.pagamento_info();

                            Date pagamento = new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now()));
                            String pagamentoString = new SimpleDateFormat("dd-mm-yyyy").format(pagamento);

                            pagamento_info.setDt_pagamento(pagamentoString);
                            pagamento_info.setModo_pagamento("");
                            pagamento_info.setValor(1400);
                            pagamento_info.setId_utilizador_pag("manuel.g.nascimento@pn.gov.cv");
                            pagamento_info.setId_entidade_pag("DEFSP");

                            List<SNIACDTO.anexo> anexolist = dto.getPedidotre().getPedido().getDocumentos().getDocumentoType().stream()
                                .map(doc -> {

                                    //System.out.println("DOC" + doc);

                                    SNIACDTO.anexo anexo= new SNIACDTO.anexo();
                                    anexo.setTipo_documento(doc.getTipoDocumento());
                                    anexo.setCodeDoc(doc.getCodeDoc());
                                    anexo.setConteudo(doc.getConteudo());
                                    return anexo;
                                })
                                .collect(Collectors.toList());

                            SNIACDTO.row row = new SNIACDTO.row();

                            row.setMimetype("xml");
                            row.setPedido_info(pedido_info);
                            row.setIdentificacao_info(identificacao_info);
                            row.setEndereco_morada_info(endereco_morada_info);
                            row.setLocal_recebimento_info(local_recebimento_info);
                            row.setBiometrico_info(biometrico_info);
                            row.setPagamento_info(pagamento_info);
                            row.setAnexo(anexolist);

                            SNIACDTO.rows rows = new SNIACDTO.rows();
                            rows.setRow(row);

                            //System.out.println("ROWS" + new Gson().toJson(rows));
                            XmlMapper mapper = new XmlMapper();
                            xmlSniac = mapper.writeValueAsString(rows);

                        }
                        request.setFinalXml(xmlSniac);
                        request.setNumCartao(numerovisual);
                        request.setDecisao_final(domain.getDescricao());
                        request.setData_final(LocalDate.now());

                        requestRepository.save(request);

                        phaseExecDTO.setJsonEntrada(new Gson().toJson(dto));

                        updatePhaseExec(phaseExecDTO, dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
                        insertPhaseExec(phaseExecDTO);

                        return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
                    }

                    else {
                        phaseExecDTO.setEstadoInicial("C");
                        phaseExecDTO.setJsonEntrada(new Gson().toJson(dto));
                        updatePhaseExec(phaseExecDTO, dto.getPedidotre().getPedido().getFase().getId_pd_fase_exec());
                        insertPhaseExec(phaseExecDTO);

                        return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
                    }

                } catch (Exception e) {

                    List<Object> l = new ArrayList<>();
                    l.add(e.getMessage());

                    return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();

                }

            default: return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(Collections.singletonList(new
                    Exception().getMessage())).build();
        }


    }

    @Override
    public APIResponse updatePhaseExec(PhaseExecDTO dto, String id) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(id);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);

        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

        try {

            phaseRequestExec.setObservacao(dto.getObs());
            phaseRequestExec.setUser_uuid_fase(dto.getUserUuidFase());
            phaseRequestExec.setJson_saida(dto.getJsonEntrada());
            phaseRequestExec.setMotivo(dto.getMotivo());
            phaseRequestExec.setEstado(dto.getEstadoFinal());
            phaseRequestExec.setNumSitre(dto.getNumSitre());

            phaseRequestExecRepository.save(phaseRequestExec);

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();

        }
    }

    @Override
    public APIResponse insertPhaseExec(PhaseExecDTO dto) {

        try {

            PhaseRequestExec phaseRequestExec = new PhaseRequestExec();

            phaseRequestExec.setIdTpPedido(dto.getIdTipoPedido());
            phaseRequestExec.setIdFase(dto.getIdFaseDestino());
            phaseRequestExec.setIdPedido(dto.getIdPedido());
            phaseRequestExec.setUser_uuid(dto.getUserUuidFase());
            phaseRequestExec.setIncm(dto.getIncm());
            phaseRequestExec.setJson_entrada(dto.getJsonEntrada());
            phaseRequestExec.setEstado(dto.getEstadoInicial());
            phaseRequestExec.setDmEstado(dto.getDm_estado());
            phaseRequestExec.setParecer(dto.getParecer());
            phaseRequestExec.setNumSitre(dto.getNumSitre());


            phaseRequestExecRepository.save(phaseRequestExec);

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();

        }
    }

    @Override
    public APIResponse listProcessSendSniac() {

        List<PhaseExecParcialData> requestExecList = phaseRequestExecRepository.findPhaseRequestExecByEstadoOrEstado("AE", "R");

        try {

            if(requestExecList.isEmpty()) {

                return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
            }

            List<ProcessListResponseDTO> searchResponseDTOList = requestExecList.stream()
                    .map(phaseRequestExec -> {
                        Optional<Request> optionalRequest = requestRepository.findById(phaseRequestExec.getIdPedido());
                        Optional<Domain> optionalDomain = domainRepository.findByDominioAndValor("ESTADO_FASE", phaseRequestExec.getEstado());
                        return new ProcessListResponseDTO(optionalRequest.get().getNome(), optionalRequest.get().getApelido(),
                                optionalRequest.get().getNumCartao(), optionalRequest.get().getIncm(),
                                optionalRequest.get().getDt_nascimento(), optionalRequest.get().getNumeroIgrp(), optionalRequest.get().getNic(),
                                optionalRequest.get().getId_pedido(), phaseRequestExec.getId(), optionalDomain.get().getDescricao(),
                                optionalRequest.get().getCreated_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    })
                    .collect(Collectors.toList());
            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(Collections.singletonList(searchResponseDTOList)).build();

        } catch (Exception e) {

            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();

        }

    }

    @Override
    public APIResponse detailsCard(String idPhaseExec) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(idPhaseExec);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            FinalJsonDTO jsonEntrada = objectMapper.readValue(optionalPhaseRequestExec.get().getJson_entrada(), FinalJsonDTO.class);

            System.out.println("jsonEntrada: " +jsonEntrada);

            String apelido = jsonEntrada.getPedidotre().getPedido().getIdentificacao().getApelido(), apelido2 = "";

            int lengthApelido = jsonEntrada.getPedidotre().getPedido().getIdentificacao().getApelido().length();

            if (lengthApelido > 44) {

                apelido = jsonEntrada.getPedidotre().getPedido().getIdentificacao().getApelido().substring(0, 45);
                apelido2 = jsonEntrada.getPedidotre().getPedido().getIdentificacao().getApelido().substring(45, lengthApelido);
            }

            Optional<Geografia> optionalIlha = geographyRepository.findById(jsonEntrada.getPedidotre().getPedido().getMorada().getIlha());
            String ilha = optionalIlha.get().getNomeCartao();
            System.out.println("ilha: " + ilha);

            Optional<Geografia> optionalConcelho = geographyRepository.findById(jsonEntrada.getPedidotre().getPedido().getMorada().getConcelho());
            String concelho = optionalConcelho.get().getNomeCartao();
            System.out.println("concelho: " + concelho);

            Optional<Geografia> optionalNacionalidade = geographyRepository.findById(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getNacionalidade());
            String nacionalidadeDesc = optionalNacionalidade.get().getNomeCartao();
            System.out.println("nacionalidade: " + nacionalidadeDesc);

            Optional<Geografia> optionalNaturalidade = geographyRepository.findById(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getNaturalidade());
            String naturalidade = optionalNaturalidade.get().getNomeCartao();
            System.out.println("naturalidade: " + nacionalidadeDesc);

            String residencia = concelho.concat("-").concat(ilha);
            System.out.println("residencia: " + residencia);

            DataCardDTO dataCardDTO = new DataCardDTO();

            dataCardDTO.setApelido1(apelido);
            dataCardDTO.setApelido2(apelido2);
            dataCardDTO.setNome(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getNome());

            if(jsonEntrada.getPedidotre().getPedido().getFase() != null) {

                dataCardDTO.setObs1(jsonEntrada.getPedidotre().getPedido().getFase().getObs1());
                dataCardDTO.setObs2(jsonEntrada.getPedidotre().getPedido().getFase().getObs2());
                dataCardDTO.setTitulo(jsonEntrada.getPedidotre().getPedido().getFase().getTitulo());
                dataCardDTO.setLei(jsonEntrada.getPedidotre().getPedido().getFase().getSubTitulo());

                if(jsonEntrada.getPedidotre().getPedido().getFase().getData() != null) {

                    dataCardDTO.setValidade(jsonEntrada.getPedidotre().getPedido().getFase().getData().getValidade());
                    dataCardDTO.setNumeroDoc(jsonEntrada.getPedidotre().getPedido().getFase().getData().getNumeroDocumentoVisual());
                }
            }

            dataCardDTO.setNic(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getNumeroIdentificacao());
            dataCardDTO.setFoto(jsonEntrada.getPedidotre().getPedido().getDadosBiometricos().getFotografia());
            dataCardDTO.setAssinatura(jsonEntrada.getPedidotre().getPedido().getDadosBiometricos().getAssinatura());

            dataCardDTO.setNascimento(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
            dataCardDTO.setNaturalidade(naturalidade);
            dataCardDTO.setNacionalidade(nacionalidadeDesc);
            dataCardDTO.setSexo(jsonEntrada.getPedidotre().getPedido().getIdentificacao().getSexo());
            dataCardDTO.setResidencia(residencia);

            if(jsonEntrada.getPedidotre().getPedido().getCodigosMrz() != null) {

                dataCardDTO.setMrz1(jsonEntrada.getPedidotre().getPedido().getCodigosMrz().getMrz1());
                dataCardDTO.setMrz2(jsonEntrada.getPedidotre().getPedido().getCodigosMrz().getMrz2());
                dataCardDTO.setMrz3(jsonEntrada.getPedidotre().getPedido().getCodigosMrz().getMrz3());

            }


            List<Object> objectList = new ArrayList<>();
            objectList.add(dataCardDTO);

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(objectList).build();
        } catch (Exception e) {
            List<Object> l = new ArrayList<>();
            l.add(e.getMessage());

            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(l).build();
        }

    }

    @Override
    public String xmlDataSniac(String idpedido) {

        System.out.println(idpedido);
        Optional<Request> optionalRequest = requestRepository.findById(idpedido);
        ApiUtilies.checkResource(optionalRequest, MessageState.ID_NAO_EXISTE);


        if (optionalRequest.get().getFinalXml()== null) {

            return "Erro";
        }

        return optionalRequest.get().getFinalXml();


    }

    @Override
    public APIResponse shippingDataSniac(ShippingRequestSniacDTO dto) throws JsonProcessingException {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(dto.getIdPhaseExec());
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

        Optional<Request> optionalRequest = requestRepository.findById(phaseRequestExec.getIdPedido());
        ApiUtilies.checkResource(optionalRequest, MessageState.ID_NAO_EXISTE);
        Request request = optionalRequest.get();


        String xmlSniac = xmlDataSniac(phaseRequestExec.getIdPedido());

        System.out.println("xmlSniac: " + xmlSniac);

        APIResponse response = null;

        List<Object> objectList = new ArrayList<>();

        if(xmlSniac.equals("Erro")) {

            //return new ResponseEntity<>("Processo nao possui dados para envio - contactar administrador", HttpStatus.OK);
            objectList.add("Processo nao possui dados para envio - contactar administrador");
            response = APIResponse.builder().status(false).statusText(MessageState.ERRO).details(objectList).build();
        }
        else {

            XmlMapper mapper = new XmlMapper();

            if(request.getOrigem().equals("SNIAC")) {

                RequestSNIACDTO.Rows rows = mapper.readValue(xmlSniac, RequestSNIACDTO.Rows.class);

                APIResponseSNIAC apiResponseSNIAC = restTemplate.postForObject(defaultLink.getSniac(), rows, APIResponseSNIAC.class);

                System.out.println(apiResponseSNIAC.getStatus());

                if(apiResponseSNIAC.getStatus().equals(true)) {

                    if(phaseRequestExec.getEstado().equals("AE")) {

                        phaseRequestExec.setEstado("PI");
                        request.setPan(apiResponseSNIAC.getPan());
                        request.setDecisao_final("DEFERIDO");
                        request.setData_final(LocalDate.now());

                        phaseRequestExecRepository.save(phaseRequestExec);
                        requestRepository.save(request);

                        objectList.add(apiResponseSNIAC.getStatus_text());
                        return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(objectList).build();
                    }
                    if(phaseRequestExec.getEstado().equals("R")) {

                        phaseRequestExec.setEstado("RS");
                        phaseRequestExec.setMotivo(rows.getRow().getMotivo_biografico() + "," + rows.getRow().getMotivo_biometrico());
                        phaseRequestExecRepository.save(phaseRequestExec);

                        objectList.add(apiResponseSNIAC.getStatus_text());
                        return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(objectList).build() ;

                    }
                }
                if(apiResponseSNIAC.getStatus().equals(false))
                {
                    objectList.add(apiResponseSNIAC.getStatus_text());
                    return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(objectList).build();
                }

            }

            if(request.getOrigem().equals("SITRE") || request.getOrigem().equals("SD")) {

                HttpHeaders headerToken = new HttpHeaders();
                headerToken.setBasicAuth(defaultLink.getBaseAuth());
                headerToken.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<String> entityToken = new HttpEntity<>(headerToken);

                AcessToken acessToken = restTemplate.postForObject(defaultLink.getAcessToken(), entityToken, AcessToken.class);

                HttpHeaders headerPedex = new HttpHeaders();
                headerPedex.setContentType(MediaType.APPLICATION_JSON);
                headerPedex.setBearerAuth(acessToken.access_token);

                RequestPedex.PedidoTrePortal pedidoTrePortal = new RequestPedex.PedidoTrePortal();

                String xml = xmlSniac.replaceAll("<([a-zA-Z][a-zA-Z0-9]*)[^>]*/>", "").replaceAll("<([a-zA-Z][a-zA-Z0-9]*)[^>]*>\\s*</\\1>", "");

                System.out.println("xml" + xml);

                String pArgs = Base64.getEncoder().encodeToString(xml.getBytes(StandardCharsets.ISO_8859_1));

                pedidoTrePortal.setP_ARGS(pArgs);

                RequestPedex.Root root = new RequestPedex.Root();

                root.setPedido_tre_portal(pedidoTrePortal);

                HttpEntity<RequestPedex.Root> entityPedex = new HttpEntity<>(root, headerPedex);

                ResponseEntity<String> responsePedex = restTemplate.postForEntity(defaultLink.getPedex(), entityPedex, String.class);

                System.out.println("responsePedex: " + responsePedex.getBody());

                if(responsePedex.getBody().contains("success")) {

                    if(phaseRequestExec.getEstado().equals("AE")) {

                        ObjectMapper objectMapper = new ObjectMapper();
                        ResponsePedex.Root pedex = objectMapper.readValue(responsePedex.getBody(), ResponsePedex.Root.class);


                        XmlMapper xmlMapper = new XmlMapper();
                        SNIACDTO.rows sniacdto = xmlMapper.readValue(xmlSniac, SNIACDTO.rows.class);

                        sniacdto.getRow().getPedido_info().setId_pedido_pdex(String.valueOf(pedex.getEntries().getEntry().getId_pedido()));

                        phaseRequestExec.setEstado("PI");
                        //request.setPan(apiResponseSNIAC.getPan());
                        request.setDecisao_final("DEFERIDO");
                        request.setData_final(LocalDate.now());
                        request.setFinalXml(mapper.writeValueAsString(sniacdto));

                        phaseRequestExecRepository.save(phaseRequestExec);
                        requestRepository.save(request);

                        /* --- atualizar estado secretaria digital envio sniac - emissao --- */

                       /* FinalJsonDTO finalJsonDTO = new Gson().fromJson(phaseRequestExec.getJson_entrada(), FinalJsonDTO.class);

                        digitalSecretaryServiceImpl.updateStatusRequest(new UpdateStatusRequestDSDTO(phaseRequestExec.getId(), finalJsonDTO.getPedidotre().getPedido().getNumeroPedido(),
                                finalJsonDTO.getPedidotre().getPedido().getNumeroRequisicao(), finalJsonDTO.getPedidotre().getPedido().isNumeroRequisicaoOrigem(), null, Constants.UpdateStatusCodeSD.AED));
*/
                        return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
                    }
                    /*if(phaseRequestExec.getEstado().equals("R")) {

                        phaseRequestExec.setEstado("RS");
                        phaseRequestExec.setMotivo(rows.getRow().getMotivo_biografico() + "," + rows.getRow().getMotivo_biometrico());
                        phaseRequestExecRepository.save(phaseRequestExec);

                    }*/

                }
                if(responsePedex.getBody().contains("error"))
                {
                    return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(Arrays.asList(responsePedex.getBody())).build();
                }

            }
        }
        return response;
    }

    @Override
    public APIResponse updateJson(String id, String idPedido, FinalJsonDTO dto) {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(id);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

        Optional<Request> optionalRequest = requestRepository.findById(idPedido);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);

        Request request = optionalRequest.get();

        try {

            request.setDt_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
            request.setNome(dto.getPedidotre().getPedido().getIdentificacao().getNome());
            request.setApelido(dto.getPedidotre().getPedido().getIdentificacao().getApelido());

            requestRepository.save(request);

            phaseRequestExec.setJson_entrada(new Gson().toJson(dto));
            phaseRequestExecRepository.saveAndFlush(phaseRequestExec);

            return  APIResponse.builder().status(true).statusText(MessageState.SUCESSO).details(Collections.singletonList(phaseRequestExec.getJson_entrada())).build();
        } catch (Exception e) {
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(Collections.singletonList(e.getMessage())).build();
        }

    }

    @Override
    public APIResponse validatePreAnalise(String idPhaseExec, FinalJsonDTO dto) throws JsonProcessingException {

        Optional<PhaseRequestExec> optionalPhaseRequestExec = phaseRequestExecRepository.findById(idPhaseExec);
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        PhaseRequestExec phaseRequestExec = optionalPhaseRequestExec.get();

        Optional<Request> optionalRequest = requestRepository.findById(phaseRequestExec.getIdPedido());
        ApiUtilies.checkResource(optionalPhaseRequestExec, MessageState.ID_NAO_EXISTE);
        Request request = optionalRequest.get();

        try {

            HttpHeaders header = new HttpHeaders();
            header.setBasicAuth(defaultLink.getBaseAuthNotification());
            header.set("X-API-Version", "1");
            header.setContentType(MediaType.APPLICATION_JSON);

            if(!request.getOrganica().equals("CRSVSVFI")) {

                if (dto.getPedidotre().getPedido().getMorada().getEMail() != null) {

                    SendEmailDTO sendEmailDTO = new SendEmailDTO();

                    sendEmailDTO.setFrom("def@policianacional.cv");
                    sendEmailDTO.setOrigin("PN_FRONTEIRA");
                    sendEmailDTO.setSubject("Regularização Extraordinária");
                    sendEmailDTO.setMsg("Caro Cidadão, dirija-se ao posto " + dto.getPedidotre().getPedido().getNomeUnidadeOrganicaPedido() + " para efetuar a recolha dados biométricos. Leve consigo os documentos do seu pedido.");
                    sendEmailDTO.setMyto(dto.getPedidotre().getPedido().getMorada().getEMail());

                    HttpEntity<SendEmailDTO> entity = new HttpEntity<>(sendEmailDTO, header);

                    ResponseEntity<String> response = restTemplate.postForEntity(defaultLink.getEmailSend(), entity, String.class);
                }

                if (dto.getPedidotre().getPedido().getMorada().getTelemovel() != null) {

                    SendSMSDTO sendSMSDTO = new SendSMSDTO();

                    sendSMSDTO.setFrom("PN_DEF");
                    sendSMSDTO.setOrigin("PN_FRONTEIRA");
                    sendSMSDTO.setNumber(dto.getPedidotre().getPedido().getMorada().getTelemovel());
                    sendSMSDTO.setMessage("Caro Cidadão, dirija-se ao posto " + dto.getPedidotre().getPedido().getNomeUnidadeOrganicaPedido() + " para efetuar a recolha dados biométricos. Leve consigo os documentos do seu pedido.");

                    HttpEntity<SendSMSDTO> entity = new HttpEntity<>(sendSMSDTO, header);

                    ResponseEntity<String> response = restTemplate.postForEntity(defaultLink.getSmsSend(), entity, String.class);
                }

            }

            request.setDt_nascimento(dto.getPedidotre().getPedido().getIdentificacao().getDataNascimento());
            request.setNome(dto.getPedidotre().getPedido().getIdentificacao().getNome());
            request.setApelido(dto.getPedidotre().getPedido().getIdentificacao().getApelido());

            requestRepository.save(request);

            phaseRequestExec.setJson_entrada(new Gson().toJson(dto));
            optionalPhaseRequestExec.get().setEstado("PI");
            phaseRequestExecRepository.save(optionalPhaseRequestExec.get());

            /* --- atualizar estado secretaria digital envio sniac - emissao ---

            digitalSecretaryServiceImpl.updateStatusRequest(new UpdateStatusRequestDSDTO(phaseRequestExec.getId(), dto.getPedidotre().getPedido().getNumeroPedido(),
                    dto.getPedidotre().getPedido().getNumeroRequisicao(), dto.getPedidotre().getPedido().isNumeroRequisicaoOrigem(), null, Constants.UpdateStatusCodeSD.DEF));
*/

            return APIResponse.builder().status(true).statusText(MessageState.SUCESSO).build();
        } catch (Exception e) {
            return APIResponse.builder().status(false).statusText(MessageState.ERRO).details(Arrays.asList(e.getMessage())).build();
        }
    }

}
