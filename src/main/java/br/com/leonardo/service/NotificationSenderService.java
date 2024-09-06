package br.com.leonardo.service;

import javax.mail.*;
import javax.mail.internet.*;

import br.com.leonardo.manager.LoggerManager;
import br.com.leonardo.utils.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.*;

// Serviço de notificação via Telegram e E-Mail
public class NotificationSenderService {
		
    @SuppressWarnings({ "deprecation" })
	public static void enviarMensagemTelegram(String mensagem, String caminhoArquivo, String token, String chatId) {
    	
    	try {
            // Envia mensagem de texto
            String urlString = "https://api.telegram.org/bot" + token + "/sendMessage";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String payload = "chat_id=" + chatId + "&text=" + URLEncoder.encode(mensagem, "UTF-8");

            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                LoggerManager.logInfo("Mensagem Telegram Enviada para:" + chatId );
            } else {
                LoggerManager.logError("Falha no Envio da Mensagem Telegram" + responseCode);
            }
            
            // Envia arquivo PDF
            if (caminhoArquivo != null && !caminhoArquivo.isEmpty()) {
                urlString = "https://api.telegram.org/bot" + token + "/sendDocument";
                url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                connection.setDoOutput(true);

                String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write((twoHyphens + boundary + lineEnd).getBytes());
                    outputStream.write(("Content-Disposition: form-data; name=\"chat_id\"" + lineEnd + lineEnd + chatId + lineEnd).getBytes());
                    outputStream.write((twoHyphens + boundary + lineEnd).getBytes());
                    outputStream.write(("Content-Disposition: form-data; name=\"document\"; filename=\"" + new File(caminhoArquivo).getName() + "\"" + lineEnd).getBytes());
                    outputStream.write(("Content-Type: application/pdf" + lineEnd + lineEnd).getBytes());

                    File file = new File(caminhoArquivo);
                    try (FileInputStream fileInputStream = new FileInputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }

                    outputStream.write((lineEnd + twoHyphens + boundary + twoHyphens + lineEnd).getBytes());
                    outputStream.flush();
                }

                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    LoggerManager.logInfo("Mensagem Telegram PDF enviada para:" + chatId );
                } else {
                	LoggerManager.logError("Erro no Envio do arquivo PDF Telegram" + responseCode);
                }
            }
        } catch (Exception e) {
            LoggerManager.logError(e.getMessage());
        }

    	        
    }
    
    // Envia mensagem de E-mail
    public static void enviarEmail(String mensagem, String caminhoArquivo, String emailEnvio, String senhaEmail, String emailDestinatario) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailEnvio, senhaEmail);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailEnvio));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            message.setSubject("Atualização Diário Oficial " + DateUtils.formatarData(new Date()));

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mensagem);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (caminhoArquivo != null) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(caminhoArquivo);
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            LoggerManager.logInfo("Mensagem E-Mail enviada para:" + emailDestinatario);
        } catch (MessagingException | IOException e) {
        	LoggerManager.logError("Erro Envio E-mail");
            LoggerManager.logError(e.getMessage());
        }
    }
}
