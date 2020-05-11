package br.com.evoluum.estados_api.dominio.arquivos;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public interface FabricaDeArquivos<T> {
    final static Logger LOGGER = Logger.getLogger(FabricaDeArquivos.class.getName());

    public void criaEEscreveArquivoAPartirDe(List<? extends ObjetoTransformavel> itens, String nomeDoArquivo) throws FileNotFoundException;
    public T constroiDados(List<? extends ObjetoTransformavel> itens);
}
