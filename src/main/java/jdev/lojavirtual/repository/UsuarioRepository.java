package jdev.lojavirtual.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = ?1 ")
    Usuario findUserbyLogin(String login);

    @Query("select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into usuarios_acesso (usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")
    void insereAcessoPj(Long id);

}
    
