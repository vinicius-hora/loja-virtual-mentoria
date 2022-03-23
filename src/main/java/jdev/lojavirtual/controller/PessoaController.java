package jdev.lojavirtual.controller;

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


    @ResponseBody
    @PostMapping("/salvarPJ")
    public ResponseEntity<PessoaJuridica>salvarPJ(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if(pessoaJuridica == null) {
            throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nulo");
        }
        if(pessoaJuridica.getId() == null && pessoaRepository.existeCNPJCadastrados(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionMentoriaJava("CNPJ já cadastrado, numero: " + pessoaJuridica.getCnpj());
        }
        pessoaJuridica = pessoaUserService.salvarPJ(pessoaJuridica);
            
    
        
        return ResponseEntity.ok(pessoaJuridica);
    }

    
    
}
