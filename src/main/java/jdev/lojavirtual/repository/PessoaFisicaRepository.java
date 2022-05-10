package jdev.lojavirtual.repository;

import jdev.lojavirtual.model.PessoaFisica;
import jdev.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {


    @Query("select pf from PessoaFisica pf where pf.cpf = ?1 ")
    public PessoaFisica existeCpfCadastrados(String cpf);
}
    