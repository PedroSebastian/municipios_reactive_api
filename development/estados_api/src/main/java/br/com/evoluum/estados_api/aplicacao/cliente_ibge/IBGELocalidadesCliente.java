package br.com.evoluum.estados_api.aplicacao.cliente_ibge;

import br.com.evoluum.estados_api.dominio.arquivos.FabricaDeArquivosCSV;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IBGELocalidadesCliente {
    private static final Logger LOGGER = Logger.getLogger(FabricaDeArquivosCSV.class.getName());

    private final String ESTADOS_RECURSO = "estados";
    private final String MUNICIPIOS_RECURSO = "municipios";

    public Flux<String> buscaIdDeCidadeAtravesDo(String nome) {
        LOGGER.log(Level.INFO, "inicia busca de municipios pelo nome {0}", nome);
        return this.buscaTodosOsEstadosEMunicipios()
                .filter(municipio -> municipio.containsValue(nome))
                .map(municipio -> municipio.get("id").toString())
                .cache(Duration.ofMinutes(30));
    }

    private Flux<Map> buscaTodosOsMunicipios(String url) {
        LOGGER.log(Level.INFO, "inicia busca de municipios de: {0}", url);
        return WebClient.create()
                .get()
                .uri(url.toString())
                .retrieve()
                .bodyToFlux(Map.class)
                .timeout(Duration.ofMillis(60000))
                .cache(Duration.ofMinutes(30));
    }

    public Flux<Map> buscaTodosOsEstadosEMunicipios() {
        LOGGER.log(Level.INFO, "inicia busca de estados");
        return WebClient.create()
                .get()
                .uri(URL.URL_BASE.concat(this.ESTADOS_RECURSO))
                .retrieve()
                .bodyToFlux(Map.class)
                .timeout(Duration.ofMillis(60000))
                .map(this::constroiURLDeBuscaDeCidades).flatMap(this::buscaTodosOsMunicipios)
                .cache(Duration.ofMinutes(30));
    }

    private String constroiURLDeBuscaDeCidades(Map estados) {
        LOGGER.log(Level.INFO, "inicia construção de url");
        StringBuilder url = new StringBuilder();
        url.append(URL.URL_BASE).append(this.ESTADOS_RECURSO).append("/");

        estados.forEach((chave, valor) -> {
            if (chave.equals("id")) {
                url.append(valor);
                url.append("|");
            }
        });
        url.deleteCharAt(url.length() - 1);
        url.append("/").append(this.MUNICIPIOS_RECURSO);

        LOGGER.log(Level.INFO, "url: {0}", url.toString());
        return url.toString();
    }
}
