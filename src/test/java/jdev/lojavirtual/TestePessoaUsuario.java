package jdev.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.lojavirtual.controller.PessoaController;
import jdev.lojavirtual.exception.ExceptionMentoriaJava;
import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.model.PessoaJuridica;
import jdev.lojavirtual.repository.PessoaRepository;
import jdev.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadPessoaFisica() throws ExceptionMentoriaJava{

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("José");
        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setEmail("teste@pj.com");
        pessoaJuridica.setTelefone("12345678");
        pessoaJuridica.setInscEstadual("inscEstadual");
        pessoaJuridica.setInscMunicipal("inscMunicipal");
        pessoaJuridica.setRazaoSocial("razaoSocial");
        pessoaJuridica.setCategoria("categoria");
        pessoaJuridica.setNomeFantasia("nomeFantasia");

        pessoaController.salvarPJ(pessoaJuridica);

        // PessoaFisica pessoaFisica = new PessoaFisica();
        // pessoaFisica.setNome("José");
        // pessoaFisica.setCpf("123456789");
        // pessoaFisica.setEmail("teste@teste.com");
        // pessoaFisica.setTelefone("12345678");
        
        // pessoaFisica.setEmpresa(pessoaJuridica);

    }


    
}
