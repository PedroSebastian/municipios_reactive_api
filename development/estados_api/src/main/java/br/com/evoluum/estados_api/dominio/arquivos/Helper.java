package br.com.evoluum.estados_api.dominio.arquivos;

import java.util.logging.Logger;

public class Helper {
    public static String trataCaracteresEspeciais(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
