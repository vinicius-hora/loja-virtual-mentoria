package jdev.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.lojavirtual.model.Acesso;
import jdev.lojavirtual.repository.AcessoRepository;
import jdev.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody //poder dar um retorno da API
	@PostMapping(value = "*/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso( @RequestBody Acesso acesso) {
		//recebe json e converte pra objeto
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}

	@ResponseBody //poder dar um retorno da API
	@DeleteMapping(value = "*/salvarAcesso")
	public ResponseEntity<?> deletarAcesso( @RequestBody Acesso acesso) {
		//recebe json e converte pra objeto
		 acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso deletado", HttpStatus.OK);
	}

}
