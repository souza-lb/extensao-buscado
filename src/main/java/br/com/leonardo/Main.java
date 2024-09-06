package br.com.leonardo;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import br.com.leonardo.gui.Gui;
import br.com.leonardo.manager.DriverManager;
import br.com.leonardo.manager.FileManager;
import br.com.leonardo.manager.LoggerManager;
import br.com.leonardo.service.ConfigLoaderService;
import br.com.leonardo.service.MessageGeneratorService;
import br.com.leonardo.service.NotificationSenderService;
import br.com.leonardo.service.WebScrapperService;
import br.com.leonardo.utils.PDFUtils;

public class Main {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        // Define o agendamento das verificações
        LocalTime[] horários = {
            LocalTime.of( 8, 5),
            LocalTime.of(12, 5),
            LocalTime.of(16, 5),
            LocalTime.of(18, 5)
        };

        // Executa a verificação no momento da abertura
        executarTarefa();

        // Agendar as tarefas para os horários determinados
        for (LocalTime horário : horários) {
            agendarTarefa(horário);
        }

        // Manter o main thread ativo
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void agendarTarefa(LocalTime horário) {
        long initialDelay = calcularDelayParaOHorario(horário);
        long period = TimeUnit.DAYS.toMillis(1);

        scheduler.scheduleAtFixedRate(() -> executarTarefa(), initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private static long calcularDelayParaOHorario(LocalTime horário) {
        LocalTime agora = LocalTime.now();
        long delay = agora.until(horário, TimeUnit.MILLISECONDS.toChronoUnit());

        if (delay < 0) {
            delay += TimeUnit.DAYS.toMillis(1);
        }

        return delay;
    }

    // Executa as tarefas de verigicação, envio de notificações e exibição de janela
    private static void executarTarefa() {
        ConfigLoaderService configLoader = new ConfigLoaderService();
        configLoader.load();

        LoggerManager.setupLogging();
        WebDriver driver = DriverManager.setupDriver();

        try {
            String pdfUrl = WebScrapperService.acessarSite(driver);
            if (pdfUrl != null) {
                String caminhoArquivo = FileManager.baixarPDF(pdfUrl);
                if (caminhoArquivo != null) {
                    List<Integer> paginasNome = PDFUtils.lerPDF(caminhoArquivo, configLoader.getNomeBusca());
                    String mensagem = MessageGeneratorService.gerarMensagem(paginasNome, configLoader.getNomeBusca());
                    Gui.mostrarJanela(mensagem, caminhoArquivo);
                    NotificationSenderService.enviarMensagemTelegram(mensagem, caminhoArquivo, configLoader.getTokenTelegram(), configLoader.getChatIdTelegram());
                    NotificationSenderService.enviarEmail(mensagem, caminhoArquivo, configLoader.getEmailEnvio(), configLoader.getSenhaEmail(), configLoader.getEmailDestinatario());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerManager.logError("Erro durante a execução: " + e.getMessage());
        } finally {
        	// Fecha o driver para evitar consumo de recursos
            driver.quit();
        }
    }
}
