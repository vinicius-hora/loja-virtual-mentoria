package jdev.lojavirtual.repository;

import jdev.lojavirtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jdev.lojavirtual.model.PessoaJuridica;

import java.util.List;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query("select p from PessoaJuridica p where p.cnpj = ?1 ")
    public PessoaJuridica existeCNPJCadastrados(String cnpj);

    @Query("select p from PessoaJuridica p where p.cnpj = ?1 ")
    public List<PessoaJuridica> existeCNPJCadastradosList(String cnpj);

    @Query("select p from PessoaJuridica p where p.inscEstadual = ?1 ")
    public PessoaJuridica existeInscricaoEstadualCadastrados(String inscEstadual);

    @Query("select p from PessoaJuridica p where p.inscEstadual = ?1 ")
    public List<PessoaJuridica> pesquisaPorCnpjPessoaJuridica(String inscEstadual);

    @Query("select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
    public List<PessoaJuridica> pesquisaPorNomePessoaJuridica(String nome);

//    @Query("select pf from PessoaFisica pf where pf.cpf = ?1 ")
//    public PessoaFisica existeCpfCadastrados(String cpf);
}
    
