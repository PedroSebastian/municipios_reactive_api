package br.com.evoluum.estados_api.aplicacao.cliente_ibge;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;

@Component
public class IBGELocalidadesCliente {
    private final String ESTADOS_RECURSO = "estados";
    private final String MUNICIPIOS_RECURSO = "municipios";

    @Cacheable("municipio")
    public Flux<String> buscaIdDeCidadeAtravesDo(String nome) {
        return this.buscaTodosOsEstadosEMunicipios()
                .filter(municipio -> municipio.containsValue(nome))
                .map(municipio -> municipio.get("id").toString())
                .cache(Duration.ofMinutes(30));
    }

    @Cacheable("estados")
    public Flux<Map> buscaTodosOsEstados(String url) {
        return WebClient.create()
                .get()
                .uri(url.toString())
                .retrieve()
                .bodyToFlux(Map.class)
                .timeout(Duration.ofMillis(60000))
                .cache(Duration.ofMinutes(30));
    }

    @Cacheable("municipios")
    public Flux<Map> buscaTodosOsEstadosEMunicipios() {
        return WebClient.create()
                .get()
                .uri(URL.URL_BASE.concat(this.ESTADOS_RECURSO))
                .retrieve()
                .bodyToFlux(Map.class)
                .timeout(Duration.ofMillis(60000))
                .map(this::constroiURLDeBuscaDeCidades).flatMap(this::buscaTodosOsEstados)
                .cache(Duration.ofMinutes(30));
    }

    private String constroiURLDeBuscaDeCidades(Map estados) {
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

        return url.toString();
    }
}
