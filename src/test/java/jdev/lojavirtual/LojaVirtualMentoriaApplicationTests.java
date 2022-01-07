package jdev.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.lojavirtual.controller.AcessoController;
import jdev.lojavirtual.model.Acesso;
import jdev.lojavirtual.repository.AcessoRepository;
import jdev.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
class LojaVirtualMentoriaApplicationTests {
	
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
		acessoController.salvarAcesso(acesso);
	}

}
