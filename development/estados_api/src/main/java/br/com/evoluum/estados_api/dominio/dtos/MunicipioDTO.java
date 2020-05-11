package br.com.evoluum.estados_api.dominio.dtos;

import br.com.evoluum.estados_api.dominio.arquivos.FabricaDeArquivosCSV;
import br.com.evoluum.estados_api.dominio.arquivos.ObjetoTransformavel;
import br.com.evoluum.estados_api.dominio.localidades.Municipio;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MunicipioDTO implements ObjetoTransformavel {
    private static final Logger LOGGER = Logger.getLogger(FabricaDeArquivosCSV.class.getName());

    private Long idEstado;
    private String siglaEstado;
    private String regiaoNome;
    private String nomeCidade;
    private String nomeMesorregiao;
    private String nomeFormatado;

    public MunicipioDTO(Municipio municipio) {
        this.mapeia(municipio);
    }

    private void mapeia(Municipio municipio) {
        LOGGER.log(Level.INFO, "mapeando municipio para municipioDTO");
        this.idEstado = municipio.getMicrorregiao().getMesorregiao().getUf().getId();
        this.siglaEstado = municipio.getMicrorregiao().getMesorregiao().getUf().getSigla();
        this.regiaoNome = municipio.getMicrorregiao().getMesorregiao().getUf().getRegiao().getNome();
        this.nomeCidade = municipio.getNome();
        this.nomeMesorregiao = municipio.getMicrorregiao().getMesorregiao().getNome();

        this.formataNome();
    }

    private void formataNome() {
        StringBuilder nomeCidadeFormatado = new StringBuilder();
        this.nomeFormatado = nomeCidadeFormatado.append(this.nomeCidade).append("/").append(this.siglaEstado).toString();
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public String getRegiaoNome() {
        return regiaoNome;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public String getNomeMesorregiao() {
        return nomeMesorregiao;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }

    @Override
    public List<String> objetoParaListaDeAtributos() {
        List<String> linha = new ArrayList<>();
        linha.add(this.getIdEstado().toString());
        linha.add(this.getSiglaEstado());
        linha.add(this.getRegiaoNome());
        linha.add(this.getNomeCidade());
        linha.add(this.getNomeMesorregiao());
        linha.add(this.getNomeFormatado());

        LOGGER.log(Level.INFO, "converte municipioDTO para List<String>");
        return linha;
    }
}
