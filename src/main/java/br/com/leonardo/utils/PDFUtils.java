package br.com.leonardo.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import br.com.leonardo.manager.LoggerManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFUtils {
    public static List<Integer> lerPDF(String caminhoArquivo, String nome) {
        List<Integer> paginasNome = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(caminhoArquivo))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            for (int i = 1; i <= document.getNumberOfPages(); i++) {
                pdfStripper.setStartPage(i);
                pdfStripper.setEndPage(i);
                String texto = pdfStripper.getText(document);
                if (texto.contains(nome) || texto.contains(nome.toUpperCase())) {
                    paginasNome.add(i);
                }
            }
        } catch (IOException e) {
            LoggerManager.logError(e.getMessage());
        }
        return paginasNome;
    }
}
