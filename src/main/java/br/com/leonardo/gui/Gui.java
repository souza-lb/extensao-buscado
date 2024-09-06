package br.com.leonardo.gui;

import javax.swing.*;
import br.com.leonardo.manager.FileManager;
import br.com.leonardo.utils.DateUtils;
import java.awt.*;
import java.util.Date;

public class Gui {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 200;
    private static final int FONT_SIZE = 17;

    public static void mostrarJanela(String mensagem, String caminhoArquivo) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Busca D.O.");
            frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            frame.add(mainPanel, BorderLayout.CENTER);

            JTextArea textArea = new JTextArea(mensagem);
            textArea.setFont(new Font("Sans Serif", Font.PLAIN, FONT_SIZE));
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane scrollPane = new JScrollPane(textArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton abrirPdfButton = new JButton("Abrir PDF");
            abrirPdfButton.setPreferredSize(new Dimension(150, 30));
            abrirPdfButton.addActionListener(e -> FileManager.abrirPDF(caminhoArquivo));
            buttonPanel.add(abrirPdfButton);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Adicionando um timer para fechar a janela após 1 minuto
            Timer timer = new Timer(60000, e -> frame.dispose());
            timer.setRepeats(false);
            timer.start();

            frame.setVisible(true);
        });
    }

    public static String formatarMensagemComData(String mensagem) {
        return mensagem + "\nÚltima verificação em: " + DateUtils.formatarData(new Date());
    }
}
