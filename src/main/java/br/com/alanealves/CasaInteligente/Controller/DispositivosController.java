package br.com.alanealves.CasaInteligente.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alanealves.CasaInteligente.Model.DispositivosModel;
import br.com.alanealves.CasaInteligente.Repository.DispositivosRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dispositivos")
public class DispositivosController {

	final DispositivosRepository dispositivosRepository;

	public DispositivosController(DispositivosRepository dispositivosRepository) {
		super();
		this.dispositivosRepository = dispositivosRepository;
	}

	@GetMapping
	public ResponseEntity<Object> saveDispositivos(@Valid @RequestParam("id") Long id,
			@RequestParam("nome") String nome, @RequestParam("descricao") String descricao,
			@RequestParam("tipo") String tipo) {

		
		var dispositivosModel = new DispositivosModel();

		dispositivosModel.setId(id);
		dispositivosModel.setNome(nome);
		dispositivosModel.setDescricao(descricao);
		dispositivosModel.setTipo(tipo);

		return ResponseEntity.status(HttpStatus.CREATED).body(dispositivosRepository.save(dispositivosModel));
	}

	@GetMapping("Lista")
	public ResponseEntity<List<DispositivosModel>> getAllDispositivos() {
		return ResponseEntity.status(HttpStatus.OK).body(dispositivosRepository.findAll());
	}
}
