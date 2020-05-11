package br.com.evoluum.estados_api.dominio.localidades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mesorregiao {
    private Long id;
    private String nome;
    @JsonProperty("UF")
    private UF uf;

    public Mesorregiao() {}

    public Mesorregiao(Long id, String nome, UF uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public UF getUf() {
        return uf;
    }
}
