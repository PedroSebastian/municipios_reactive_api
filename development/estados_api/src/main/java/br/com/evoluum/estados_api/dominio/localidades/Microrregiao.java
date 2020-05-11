package br.com.evoluum.estados_api.dominio.localidades;

public class Microrregiao {
    private Long id;
    private String nome;
    private Mesorregiao mesorregiao;

    public Microrregiao() {}

    public Microrregiao(Long id, String nome, Mesorregiao mesorregiao) {
        this.id = id;
        this.nome = nome;
        this.mesorregiao = mesorregiao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Mesorregiao getMesorregiao() {
        return mesorregiao;
    }
}
