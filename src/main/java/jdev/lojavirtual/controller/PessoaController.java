package jdev.lojavirtual.controller;

import jdev.lojavirtual.dto.CepDTO;
import jdev.lojavirtual.model.Endereco;
import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.repository.EnderecoRepository;
import jdev.lojavirtual.repository.PessoaFisicaRepository;
import jdev.lojavirtual.util.ValidaCNPJ;
import jdev.lojavirtual.util.ValidaCPF;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jdev.lojavirtual.exception.ExceptionMentoriaJava;
import jdev.lojavirtual.model.PessoaJuridica;
import jdev.lojavirtual.repository.PessoaRepository;
import jdev.lojavirtual.service.PessoaUserService;

import javax.validation.Valid;

@RestController

public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    @ResponseBody
    @PostMapping("/salvarPj")
    public ResponseEntity<PessoaJuridica>salvarPJ(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

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
        if(pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
            for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
                pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
                pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
            }
        }
        else{
            for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

                if(!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
                    CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

                    pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                    pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                    pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                    pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
                    pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
                }
            }
        }

        pessoaJuridica = pessoaUserService.salvarPJ(pessoaJuridica);
            
    
        
        return ResponseEntity.ok(pessoaJuridica);
    }

    @ResponseBody
    @PostMapping("/salvarPf")
    public ResponseEntity<PessoaFisica>salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

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

    @ResponseBody
    @GetMapping("/consultacep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) throws ExceptionMentoriaJava {
      CepDTO cepDTO = pessoaUserService.consultaCep(cep);

      return ResponseEntity.ok(cepDTO);
    }

    
    
}
