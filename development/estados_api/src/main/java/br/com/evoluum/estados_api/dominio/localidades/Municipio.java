package br.com.evoluum.estados_api.dominio.localidades;

public class Municipio {
    private Long id;
    private String nome;
    private Microrregiao microrregiao;

    public Municipio() {}

    public Municipio(Long id, String nome, Microrregiao microrregiao) {
        this.id = id;
        this.nome = nome;
        this.microrregiao = microrregiao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }
}
