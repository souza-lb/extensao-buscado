package br.com.leonardo.service;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigLoaderService {
    private Dotenv dotenv;

    public void load() {
        dotenv = Dotenv.load();
    }

    public String getNomeBusca() {
        return dotenv.get("NOME_BUSCA");
    }

    public String getTokenTelegram() {
        return dotenv.get("TOKEN_TELEGRAM");
    }

    public String getChatIdTelegram() {
        return dotenv.get("CHAT_ID_TELEGRAM");
    }

    public String getEmailEnvio() {
        return dotenv.get("EMAIL_ENVIO");
    }

    public String getSenhaEmail() {
        return dotenv.get("SENHA_EMAIL");
    }

    public String getEmailDestinatario() {
        return dotenv.get("EMAIL_DESTINO");
    }
}
