package br.com.evoluum.estados_api.dominio.arquivos;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.util.List;

public class FabricaDeXML implements FabricaDeArquivos<String> {
    @Override
    public void criaEEscreveArquivoAPartirDe(List<? extends ObjetoTransformavel> itens, String nomeDoArquivo) throws FileNotFoundException {
        throw new NotImplementedException();
    }

    @Override
    public String constroiDados(List<? extends ObjetoTransformavel> itens) {
        throw new NotImplementedException();
    }
}
