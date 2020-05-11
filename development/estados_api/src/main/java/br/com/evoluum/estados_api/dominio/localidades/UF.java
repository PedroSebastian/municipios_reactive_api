package br.com.evoluum.estados_api.dominio.localidades;

public class UF {
    private Long id;
    private String nome;
    private String sigla;
    private Regiao regiao;

    public UF() {}

    public UF(Long id, String nome, String sigla, Regiao regiao) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.regiao = regiao;
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

    public Regiao getRegiao() {
        return regiao;
    }
}
