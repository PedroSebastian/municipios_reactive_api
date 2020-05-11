package br.com.evoluum.estados_api.dominio.localidades;

public class Regiao {
    private Long id;
    private String nome;
    private String sigla;

    public Regiao() {}

    public Regiao(Long id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}
