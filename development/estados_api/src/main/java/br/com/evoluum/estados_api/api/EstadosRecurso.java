package br.com.evoluum.estados_api.api;

import br.com.evoluum.estados_api.aplicacao.servicos.EstadosServico;
import br.com.evoluum.estados_api.dominio.dtos.MunicipioDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("localidades")
public class EstadosRecurso {
    private EstadosServico estadosServico;

    public EstadosRecurso(EstadosServico estadosServico) {
        this.estadosServico = estadosServico;
    }

    @GetMapping("municipios")
    public Mono<List<MunicipioDTO>> buscaTodos() {
        return this.estadosServico.preparaEDevolveEstadosEMunicipiosEmJSON();
    }

    @GetMapping(path = "municipios.csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public Mono<Resource> buscaTodosEmCSV() {
        return this.estadosServico.preparaEDevolveEstadosEMunicipiosEmCSV().then(this.downloadCSV());
    }

    @GetMapping("municipio")
    public ResponseEntity<Flux<String>> buscaCidadePelo(@RequestParam(required = true) String nomeCidade) {
        CacheControl cacheControl = CacheControl.maxAge(15, TimeUnit.MINUTES)
                .noTransform()
                .mustRevalidate();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(
                        this.estadosServico.buscaIdDeCidadeAtravesDo(nomeCidade)
                        .defaultIfEmpty("Nenhum resultado encontrado para " + "\"" + nomeCidade + "\"" + ".")
                );
    }

    private Mono<Resource> downloadCSV() {
        return Mono.just(new FileSystemResource("municipios.csv"));
    }
}
