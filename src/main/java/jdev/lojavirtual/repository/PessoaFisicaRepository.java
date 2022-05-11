package jdev.lojavirtual.repository;

import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {


    @Query("select pf from PessoaFisica pf where pf.cpf = :cpf")
    public PessoaFisica existeCpfCadastrados(String cpf);

    @Query("select pf from PessoaFisica pf where pf.cpf = :cpf")
    public List<PessoaFisica> pesquisaPorCpfPessoaFisica(String cpf);

    @Query("select pf from PessoaFisica pf where upper(trim(pf.nome)) like %:nome%")
    public List<PessoaFisica> pesquisaPorNomePessoaFisica(String nome);
}
    
