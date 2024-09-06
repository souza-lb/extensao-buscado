package br.com.leonardo.service;

import java.util.Date;
import java.util.List;

import br.com.leonardo.utils.DateUtils;

public class MessageGeneratorService {
    public static String gerarMensagem(List<Integer> paginasNome, String nome) {
        String dataFormatada = DateUtils.formatarData(new Date());
        if (paginasNome.isEmpty()) {
            return "Olá " + nome + " \nSeu nome ainda não saiu no D.O\nÚltima verificação em: " + dataFormatada;
        } else {
            String paginasEvento = String.join(", ", paginasNome.toString());
            return "Olá " + nome + " \nSeu nome foi localizado no D.O mais recente!\n Verifique a(s) página(s): " + paginasEvento + "\nÚltima verificação em: " + dataFormatada;
        }
    }
}
