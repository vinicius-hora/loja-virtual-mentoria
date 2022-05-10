package jdev.lojavirtual.repository;

import jdev.lojavirtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jdev.lojavirtual.model.PessoaJuridica;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query("select p from PessoaJuridica p where p.cnpj = ?1 ")
    public PessoaJuridica existeCNPJCadastrados(String cnpj);

    @Query("select p from PessoaJuridica p where p.inscEstadual = ?1 ")
    public PessoaJuridica existeInscricaoEstadualCadastrados(String inscEstadual);

//    @Query("select pf from PessoaFisica pf where pf.cpf = ?1 ")
//    public PessoaFisica existeCpfCadastrados(String cpf);
}
    
