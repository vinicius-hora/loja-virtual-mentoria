package jdev.lojavirtual.controller;

import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.repository.PessoaFisicaRepository;
import jdev.lojavirtual.util.ValidaCNPJ;
import jdev.lojavirtual.util.ValidaCPF;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.lojavirtual.exception.ExceptionMentoriaJava;
import jdev.lojavirtual.model.PessoaJuridica;
import jdev.lojavirtual.repository.PessoaRepository;
import jdev.lojavirtual.service.PessoaUserService;

@RestController

public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;


    @ResponseBody
    @PostMapping("/salvarPj")
    public ResponseEntity<PessoaJuridica>salvarPJ(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if(pessoaJuridica == null) {
            throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nulo");
        }
        if(pessoaJuridica.getId() == null && pessoaRepository.existeCNPJCadastrados(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionMentoriaJava("CNPJ já cadastrado, numero: " + pessoaJuridica.getCnpj());
        }
        if(pessoaJuridica.getId() == null && pessoaRepository.existeInscricaoEstadualCadastrados(pessoaJuridica.getInscEstadual()) != null) {
            throw new ExceptionMentoriaJava("InscricaoEstadual já cadastrada, numero: " + pessoaJuridica.getInscEstadual());
        }
        if(!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())){
            throw new ExceptionMentoriaJava("Cnpj inválido, numero: " + pessoaJuridica.getCnpj());
        }
        pessoaJuridica = pessoaUserService.salvarPJ(pessoaJuridica);
            
    
        
        return ResponseEntity.ok(pessoaJuridica);
    }

    @ResponseBody
    @PostMapping("/salvarPf")
    public ResponseEntity<PessoaFisica>salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

        if(pessoaFisica == null) {
            throw new ExceptionMentoriaJava("Pessoa Fisica não pode ser nulo");
        }
        if(pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrados(pessoaFisica.getCpf()) != null) {
            throw new ExceptionMentoriaJava("CPF já cadastrado, numero: " + pessoaFisica.getCpf());
        }

        if(!ValidaCPF.isCPF(pessoaFisica.getCpf())){
            throw new ExceptionMentoriaJava("Cpf inválido, numero: " + pessoaFisica.getCpf());
        }
        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);



        return ResponseEntity.ok(pessoaFisica);
    }

    
    
}
