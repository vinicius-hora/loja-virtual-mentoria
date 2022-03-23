package jdev.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.lojavirtual.controller.PessoaController;
import jdev.lojavirtual.enuns.TipoEndereco;
import jdev.lojavirtual.exception.ExceptionMentoriaJava;
import jdev.lojavirtual.model.Endereco;
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
        pessoaJuridica.setNome("José3");
        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setEmail("teste1234@pj.com");
        pessoaJuridica.setTelefone("12345678");
        pessoaJuridica.setInscEstadual("inscEstadual");
        pessoaJuridica.setInscMunicipal("inscMunicipal");
        pessoaJuridica.setRazaoSocial("razaoSocial");
        pessoaJuridica.setCategoria("categoria");
        pessoaJuridica.setNomeFantasia("nomeFantasia");

        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setCidade("cidade");
        endereco.setComplemento("complemento");
        endereco.setBairro("bairro");
        endereco.setRuaLogradouro("logradouro");
        endereco.setNumero("123");
        endereco.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco.setPessoa(pessoaJuridica);
        endereco.setUf("uf");

        Endereco endereco2 = new Endereco();
        endereco2.setCep("12345678");
        endereco2.setCidade("cidade");
        endereco2.setComplemento("complemento");
        endereco2.setBairro("bairro");
        endereco2.setRuaLogradouro("logradouro");
        endereco2.setNumero("123");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setUf("uf");

        pessoaJuridica.getEnderecos().add(endereco);
        pessoaJuridica.getEnderecos().add(endereco2);

        pessoaJuridica= pessoaController.salvarPJ(pessoaJuridica).getBody();

        assertEquals(true, pessoaJuridica.getId() != null);

        

    }


    
}
