package jdev.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jdev.lojavirtual.model.PessoaJuridica;
import jdev.lojavirtual.model.Usuario;
import jdev.lojavirtual.repository.PessoaRepository;
import jdev.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PessoaJuridica salvarPJ(PessoaJuridica pessoaJuridica) {
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
            // usuarioRepository.insereAcessoPj(usuarioPJ.getId());
        }

        return pessoaJuridica;
    }
    
}
