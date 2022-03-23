package jdev.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jdev.lojavirtual.model.PessoaJuridica;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query("select p from PessoaJuridica p where p.cnpj = ?1 ")
    public PessoaJuridica existeCNPJCadastrados(String cnpj);

}
    
