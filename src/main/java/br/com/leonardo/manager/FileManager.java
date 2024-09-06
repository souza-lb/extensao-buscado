package br.com.leonardo.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
    private static final String DIARIO_OFICIAL_DIR = "diario_oficial";

    public static String baixarPDF(String pdfUrl) {
        try {
            @SuppressWarnings("deprecation")
			URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream in = connection.getInputStream();
            String nomeArquivo = new File(url.getPath()).getName();
            String caminhoArquivo = DIARIO_OFICIAL_DIR + "/" + nomeArquivo;
            LoggerManager.logInfo("Arquivo de D.O mais recente:" + caminhoArquivo);

            Files.createDirectories(Paths.get(DIARIO_OFICIAL_DIR));
            Files.copy(in, Paths.get(caminhoArquivo), StandardCopyOption.REPLACE_EXISTING);

            return caminhoArquivo;
        } catch (IOException e) {
            LoggerManager.logError(e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("deprecation")
	public static void abrirPDF(String caminhoArquivo) {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + caminhoArquivo);
            } else if (System.getProperty("os.name").startsWith("Mac")) {
                Runtime.getRuntime().exec("open " + caminhoArquivo);
            } else {
                Runtime.getRuntime().exec("xdg-open " + caminhoArquivo);
            }
        } catch (IOException e) {
            LoggerManager.logError(e.getMessage());
        }
    }
}
