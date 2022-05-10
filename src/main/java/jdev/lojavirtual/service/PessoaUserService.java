package jdev.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.mail.MessagingException;

import jdev.lojavirtual.dto.CepDTO;
import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jdev.lojavirtual.model.PessoaJuridica;
import jdev.lojavirtual.model.Usuario;
import jdev.lojavirtual.repository.PessoaRepository;
import jdev.lojavirtual.repository.UsuarioRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaJuridica salvarPJ(PessoaJuridica pessoaJuridica) {
        //pessoaJuridica = pessoaRepository.save(pessoaJuridica);

        for(int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);

        }
        pessoaJuridica = pessoaRepository.save(pessoaJuridica);

        Usuario usuarioPJ = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

        if(usuarioPJ == null) {
            //verifica se tem a chave estrangeira gerada pelo jpa que não permite acesso para varios usuários
            String constraint = usuarioRepository.consultaConstraintAcesso();
            if(constraint != null) {
                //vai direto ao banco e remove a chave estrangeira se houver
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }
            usuarioPJ = new Usuario();
            usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPJ.setLogin(pessoaJuridica.getEmail());
            usuarioPJ.setPessoa(pessoaJuridica);
            usuarioPJ.setSenha(pessoaJuridica.getCnpj());
            usuarioPJ.setEmpresa(pessoaJuridica);

            String senha = "teste";
            String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
            usuarioPJ.setSenha(senhaCriptografada);
            usuarioPJ = usuarioRepository.save(usuarioPJ);
            usuarioRepository.insereAcessoPessoaJuridica(usuarioPJ.getId());

            StringBuilder mensagemHtml = new StringBuilder();
            mensagemHtml.append("<h1>Olá " + pessoaJuridica.getNome() + "</h1>");
            mensagemHtml.append("<b> Seu cadastro foi realizado com sucesso! </b>");
            mensagemHtml.append("<b> Login </b>" +pessoaJuridica.getEmail() + "<br>");
            mensagemHtml.append("<b> Senha </b>" + senha + "<br>");
            mensagemHtml.append("<b> Acesse o sistema <a href='http://localhost:8080/lojavirtual'> aqui </a> </b>");

            try {
                serviceSendEmail.enviarEmailHtml(pessoaJuridica.getEmail(), "Acesso gerado - mentoria jdev", mensagemHtml.toString());
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        }

        return pessoaJuridica;
    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

        //pessoaJuridica = pessoaRepository.save(pessoaJuridica);

        for(int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
            pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
//            pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);

        }
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

        Usuario usuarioPJ = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

        if(usuarioPJ == null) {
            //verifica se tem a chave estrangeira gerada pelo jpa que não permite acesso para varios usuários
            String constraint = usuarioRepository.consultaConstraintAcesso();
            if(constraint != null) {
                //vai direto ao banco e remove a chave estrangeira se houver
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }
            usuarioPJ = new Usuario();
            usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPJ.setLogin(pessoaFisica.getEmail());
            usuarioPJ.setPessoa(pessoaFisica);
            usuarioPJ.setEmpresa(pessoaFisica.getEmpresa());

            String senha = "teste";
            String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
            usuarioPJ.setSenha(senhaCriptografada);
            usuarioPJ = usuarioRepository.save(usuarioPJ);
            usuarioRepository.insereAcessoPessoaFisica(usuarioPJ.getId());

            StringBuilder mensagemHtml = new StringBuilder();
            mensagemHtml.append("<h1>Olá " + pessoaFisica.getNome() + "</h1>");
            mensagemHtml.append("<b> Seu cadastro foi realizado com sucesso! </b>");
            mensagemHtml.append("<b> Login </b>" +pessoaFisica.getEmail() + "<br>");
            mensagemHtml.append("<b> Senha </b>" + senha + "<br>");
            mensagemHtml.append("<b> Acesse o sistema <a href='http://localhost:8080/lojavirtual'> aqui </a> </b>");

            try {
                serviceSendEmail.enviarEmailHtml(pessoaFisica.getEmail(), "Acesso gerado - mentoria jdev", mensagemHtml.toString());
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return pessoaFisica;
    }
    public CepDTO consultaCep (String cep) {

        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

}
