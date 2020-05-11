package br.com.evoluum.estados_api.aplicacao.servicos;

import br.com.evoluum.estados_api.aplicacao.cliente_ibge.IBGELocalidadesCliente;
import br.com.evoluum.estados_api.dominio.localidades.Municipio;
import br.com.evoluum.estados_api.dominio.arquivos.FabricaDeArquivosCSV;
import br.com.evoluum.estados_api.dominio.dtos.MunicipioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstadosServico {
    private IBGELocalidadesCliente clienteIBGELocalidades;

    public EstadosServico(IBGELocalidadesCliente clienteIBGELocalidades) {
        this.clienteIBGELocalidades = clienteIBGELocalidades;
    }

    public Mono<List<Municipio>> buscaTodosOsEstadosESeusMunicipios() {
        final ObjectMapper mapper = new ObjectMapper();

        return (this.clienteIBGELocalidades.buscaTodosOsEstadosEMunicipios()
                .map(municipio -> mapper.convertValue(municipio, Municipio.class))
                .collectList());
    }

    public Mono<Void> preparaEDevolveEstadosEMunicipiosEmCSV() {
        return (Mono<Void>) this.buscaTodosOsEstadosESeusMunicipios()
                .map(this::montaMunicipiosDTO)
                .flatMap(municipiosDTO -> this.montaDadosCSVDe(municipiosDTO));
    }

    public Mono<List<MunicipioDTO>> preparaEDevolveEstadosEMunicipiosEmJSON() {
        return this.buscaTodosOsEstadosESeusMunicipios()
                .map(this::montaMunicipiosDTO);
    }

    public Flux<String> buscaIdDeCidadeAtravesDo(String nome) {
        return this.clienteIBGELocalidades.buscaIdDeCidadeAtravesDo(nome);
    }

    private Mono<Void> montaDadosCSVDe(List<MunicipioDTO> municipiosDTO) {
        try {
            FabricaDeArquivosCSV fabricaCSV = new FabricaDeArquivosCSV();

            fabricaCSV.adicionaCabecalhoE(
                    "idEstado",
                    "siglaEstado",
                    "regiaoNome",
                    "nomeCidade",
                    "nomeMesorregiao",
                    "nomeFormatado"
            ).criaEEscreveArquivoAPartirDe(municipiosDTO, "municipios");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Mono.empty();
    }

    private List<MunicipioDTO> montaMunicipiosDTO(List<Municipio> municipios) {
        List<MunicipioDTO> municipiosDTOLista = new ArrayList<>();
        municipios.forEach(municipio -> {
            municipiosDTOLista.add(new MunicipioDTO(municipio));
        });

        return municipiosDTOLista;
    }
}
