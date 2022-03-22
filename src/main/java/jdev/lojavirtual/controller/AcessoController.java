package jdev.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.lojavirtual.exception.ExceptionMentoriaJava;
import jdev.lojavirtual.model.Acesso;
import jdev.lojavirtual.repository.AcessoRepository;
import jdev.lojavirtual.service.AcessoService;

@CrossOrigin(origins = "*")
@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody //poder dar um retorno da API
	@PostMapping(value = "/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso( @RequestBody Acesso acesso) throws ExceptionMentoriaJava {
		//verifica se já existe
		if(acesso.getId()== null){
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			if(!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Acesso já cadastrado com a descricao: " + acesso.getDescricao());
			}
		}
		
		//recebe json e converte pra objeto

		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	// @CrossOrigin(origins = "http://localhost:4200")
	// @Secured({ "ROLE_ADMIN" })
	@ResponseBody //poder dar um retorno da API
	@DeleteMapping(value = "/deletarAcesso")
	public ResponseEntity<?> deletarAcesso( @RequestBody Acesso acesso) {
		//recebe json e converte pra objeto
		 acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso deletado", HttpStatus.OK);
	}
	@ResponseBody //poder dar um retorno da API
	@DeleteMapping(value = "/deletarAcessoPorId/{id}")
	public ResponseEntity<?> deletarAcessoPorId( @PathVariable("id") Long id) {
		//recebe json e converte pra objeto
		 acessoRepository.deleteById(id);
		
		return new ResponseEntity("Acesso deletado", HttpStatus.OK);
	}

	@ResponseBody //poder dar um retorno da API
	@GetMapping(value = "/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso( @PathVariable("id") Long id) throws ExceptionMentoriaJava {
		//recebe json e converte pra objeto
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		if(acesso == null) {
			throw new ExceptionMentoriaJava("Acesso não encontrado com o id: " + id);
		}
		
		return ResponseEntity.ok(acesso);
	}

	@ResponseBody //poder dar um retorno da API
	@GetMapping(value = "/obterAcessoPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> obterAcessoPorDesc( @PathVariable("desc") String desc) {
		//recebe json e converte pra objeto
		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
		
		return ResponseEntity.ok(acesso);
	}

}
