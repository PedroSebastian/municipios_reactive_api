package br.com.evoluum.estados_api.dominio.arquivos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FabricaDeArquivosCSV implements FabricaDeArquivos<Stream<String>> {
    private static final Logger LOGGER = Logger.getLogger(FabricaDeArquivosCSV.class.getName());

    private String cabecalho;
    private int cabecalhoFlag = 0;
    private final String EXTENCAO = ".csv";

    public FabricaDeArquivosCSV adicionaCabecalhoE(String... itensDoCabecalho) {
        if (itensDoCabecalho != null) {
            this.montaCabecalho(itensDoCabecalho);
        } else {
            LOGGER.log(Level.WARNING, "nenhum item de cabeçalho adicionado");
        }
        return this;
    }

    @Override
    public void criaEEscreveArquivoAPartirDe(List<? extends ObjetoTransformavel> itens, String nomeDoArquivo) throws FileNotFoundException {
        PrintWriter escritora = new PrintWriter(nomeDoArquivo + this.EXTENCAO);
        LOGGER.log(Level.INFO, "inicia criação do arquivo {0} ", nomeDoArquivo + this.EXTENCAO);
        this.constroiDados(itens).forEach(escritora::println);
        escritora.close();
        LOGGER.log(Level.INFO, "{0} salvo, recursos liberados", nomeDoArquivo + this.EXTENCAO);
    }

    @Override
    public Stream<String> constroiDados(List<? extends ObjetoTransformavel> itens) {
        LOGGER.log(Level.INFO, "iniciando transformação dos itens para linhas csv");
        return itens.stream()
                .map(this::estruturaDadosEmLinhaCSV);
    }

    private String estruturaDadosEmLinhaCSV(ObjetoTransformavel objetoTransformavel) {
        List<String> linha = objetoTransformavel.objetoParaListaDeAtributos();

        if (this.adicionaCabecalhoNoCSV()) {
            LOGGER.log(Level.INFO, "cabeçalho adicionado ao arquivo");
            return this.cabecalho + converteParaLinha(linha.stream().toArray(String[]::new))
                    + converteParaLinha(linha.stream().toArray(String[]::new));
        }

        return converteParaLinha(linha.stream().toArray(String[]::new));
    }

    private String converteParaLinha(String[] linha) {
        LOGGER.log(Level.INFO, "inicia conversão da linha {0}", linha.toString());
        return Stream.of(linha)
                .map(Helper::trataCaracteresEspeciais)
                .collect(Collectors.joining(","));
    }

    private void montaCabecalho(String... cabecalho) {
        LOGGER.log(Level.INFO, "inicia estruturação do cabeçalho do arquivo");

        int indice = 0;
        StringBuilder linhaCabecalho = new StringBuilder();
        for (String item : cabecalho) {
            if (indice < cabecalho.length) {
                LOGGER.log(Level.INFO, "coluna: {0}", item);
                linhaCabecalho.append(Helper.trataCaracteresEspeciais(item)).append(',');
                indice++;
            }
        }

        LOGGER.log(Level.INFO, "cabeçalho criado: {0}", linhaCabecalho.toString());
        this.cabecalho = linhaCabecalho.toString() + "\n";
    }

    private boolean adicionaCabecalhoNoCSV() {
        if (this.cabecalhoFlag == 0 && this.cabecalho != null) {
            this.cabecalhoFlag++;
            return true;
        }
        return false;
    }
}
