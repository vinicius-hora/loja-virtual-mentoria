package jdev.lojavirtual;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

	@Autowired
	private WebApplicationContext wac;

	@Test
	@DisplayName("Teste do serviço de salvar acesso")
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE");
		//criar json
		ObjectMapper mapper = new ObjectMapper();
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/salvarAcesso")
		.content(mapper.writeValueAsString(acesso))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON));
		System.out.println("RETORNO DA API: " + retornoApi.andReturn().getResponse().getContentAsString());
		//converter retorno da api para um objeto de acesso

		Acesso objetoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		//acessoRepository.deleteById(objetoRetorno.getId());
	}

	@Test
	@DisplayName("Teste do serviço de DELETAR acesso")
	public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		acesso = acessoRepository.save(acesso);
		//criar json
		ObjectMapper mapper = new ObjectMapper();
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deletarAcesso")
		.content(mapper.writeValueAsString(acesso))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON));
		System.out.println("RETORNO DA API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNO DA API: " + retornoApi.andReturn().getResponse().getStatus());
		//converter retorno da api para um objeto de acesso

		//Acesso objetoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		//assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		//acessoRepository.deleteById(objetoRetorno.getId());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		assertEquals("Acesso deletado", retornoApi.andReturn().getResponse().getContentAsString());

	}

	@Test
	@DisplayName("Teste do serviço de DELETAR acesso por ID")
	public void testRestApiDeleteAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		acesso = acessoRepository.save(acesso);
		//criar json
		ObjectMapper mapper = new ObjectMapper();
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deletarAcessoPorId/" + acesso.getId())
		.content(mapper.writeValueAsString(acesso))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON));
		System.out.println("RETORNO DA API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNO DA API: " + retornoApi.andReturn().getResponse().getStatus());
		//converter retorno da api para um objeto de acesso

		//Acesso objetoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		//assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		//acessoRepository.deleteById(objetoRetorno.getId());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		assertEquals("Acesso deletado", retornoApi.andReturn().getResponse().getContentAsString());

	}

	@Test
	@DisplayName("Teste do serviço de OBTER acesso por ID")
	public void testRestApiObterAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		acesso = acessoRepository.save(acesso);
		//criar json
		ObjectMapper mapper = new ObjectMapper();
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
		.content(mapper.writeValueAsString(acesso))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON));
		
		//converter retorno da api para um objeto de acesso

		Acesso objetoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		//assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		//acessoRepository.deleteById(objetoRetorno.getId());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		assertEquals(acesso.getId(), objetoRetorno.getId());


		//deletar teste para não popular o banco
		acessoRepository.deleteById(objetoRetorno.getId());

	}

	@Test
	@DisplayName("Teste do serviço de OBTER acesso por DESCRICAO")
	public void testRestApiObterAcessoPorDesc() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		acesso = acessoRepository.save(acesso);
		//criar json
		ObjectMapper mapper = new ObjectMapper();
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/obterAcessoPorDesc/DELETE")
		.content(mapper.writeValueAsString(acesso))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		//converter retorno da api para um objeto de acesso

		List<Acesso> objetoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});

		// //assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		// //acessoRepository.deleteById(objetoRetorno.getId());
		// assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		// assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		// assertEquals(acesso.getId(), objetoRetorno.getId());

		assertEquals(1, objetoRetorno.size());
		assertEquals(acesso.getDescricao(), objetoRetorno.get(0).getDescricao());


		//deletar teste para não popular o banco
		acessoRepository.deleteById(acesso.getId());

	}

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
