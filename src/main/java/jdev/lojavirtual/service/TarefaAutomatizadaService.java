package jdev.lojavirtual.service;

import jdev.lojavirtual.model.Usuario;
import jdev.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Component
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

//    @Scheduled(initialDelay = 2000, fixedDelay = 86400000) //roda a cada 24h+
    // vai rodar todo dia as 11h da manha
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    public void notificarUserTrocaSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();

        for(Usuario usuario: usuarios){
            StringBuilder msg = new StringBuilder();
            msg.append("Olá,").append(usuario.getPessoa().getNome()).append("<br/>");
            msg.append("Está na hora de trocar sua senha, já se passou 90 dias de validade.").append("<br/>");
            msg.append("Troque sua senha na loja virtual do Vinicius");

            serviceSendEmail.enviarEmailHtml(usuario.getLogin(), "troca de Senha", msg.toString());

            Thread.sleep(3000);
        }

    }
}


