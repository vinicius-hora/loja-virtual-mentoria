package jdev.lojavirtual;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.lojavirtual.controller.AcessoController;
import jdev.lojavirtual.model.Acesso;
import jdev.lojavirtual.repository.AcessoRepository;
import jdev.lojavirtual.service.AcessoService;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
class LojaVirtualMentoriaApplicationTests extends TestCase {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoController acessoController;

//	@Test
//	public void testCadastraAcesso() {
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_ADMIN");
//		acessoService.save(acesso);
//	}
	
	@Test
	public void testCadastraAcessoController() {
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		acesso = acessoController.salvarAcesso(acesso).getBody();
		assertEquals(true, acesso.getId() > 0);
		assertEquals("ROLE_ADMIN", acesso.getDescricao());

		//TESTE DE CARREGAMENTO
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		assertEquals(acesso.getId(), acesso2.getId());

		//teste de deleteById
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush();
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		assertEquals(true, acesso3 == null);

		acesso = new Acesso();

		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoController.salvarAcesso(acesso).getBody();
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

		assertEquals(1, acessos.size());

		acessoRepository.deleteById(acesso.getId());

	}

}
