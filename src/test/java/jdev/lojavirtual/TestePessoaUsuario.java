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
    public void testCadPessoaJuridica() throws ExceptionMentoriaJava{

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("José3");
        pessoaJuridica.setCnpj("02.233.507/0001-10");
        pessoaJuridica.setEmail("teste1234@pj.com");
        pessoaJuridica.setTelefone("12345678");
        pessoaJuridica.setInscEstadual("inscEstadualteste");
        pessoaJuridica.setInscMunicipal("inscMunicipalteste");
        pessoaJuridica.setRazaoSocial("razaoSocialteste");
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

    @Test
    public void testCadPessoaFisica() throws ExceptionMentoriaJava{

        PessoaJuridica pessoaJuridica = pessoaRepository.existeCNPJCadastrados("1648037837642");

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome("José3");
        pessoaFisica.setCpf("496.023.650-72");
        pessoaFisica.setEmail("teste1234@pj.com");
        pessoaFisica.setTelefone("12345678");
        pessoaFisica.setEmpresa(pessoaJuridica);

        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setCidade("cidade");
        endereco.setComplemento("complemento");
        endereco.setBairro("bairro");
        endereco.setRuaLogradouro("logradouro");
        endereco.setNumero("123");
        endereco.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco.setPessoa(pessoaFisica);
        endereco.setUf("uf");
        endereco.setEmpresa(pessoaJuridica);

        Endereco endereco2 = new Endereco();
        endereco2.setCep("12345678");
        endereco2.setCidade("cidade");
        endereco2.setComplemento("complemento");
        endereco2.setBairro("bairro");
        endereco2.setRuaLogradouro("logradouro");
        endereco2.setNumero("123");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setPessoa(pessoaFisica);
        endereco2.setUf("uf");
        endereco2.setEmpresa(pessoaJuridica);

        pessoaFisica.getEnderecos().add(endereco);
        pessoaFisica.getEnderecos().add(endereco2);

        pessoaFisica= pessoaController.salvarPf(pessoaFisica).getBody();

        assertEquals(true, pessoaFisica.getId() != null);



    }


    
}
