package br.com.alanealves.CasaInteligente.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModel;
import br.com.alanealves.CasaInteligente.Repository.DispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosDispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuariosDispositivos")
public class UsuariosDispositivosController {

	final UsuariosDispositivosRepository usuariosDispositivosRepository;
	final DispositivosRepository dispositivosRepository; 
	final UsuariosRepository usuariosRepository; 

	public UsuariosDispositivosController(UsuariosDispositivosRepository usuariosDispositivosRepository,
			DispositivosRepository dispositivosRepository, UsuariosRepository usuariosRepository) {
		super();
		this.usuariosDispositivosRepository = usuariosDispositivosRepository;
		this.dispositivosRepository = dispositivosRepository;
		this.usuariosRepository = usuariosRepository;
	}

	@GetMapping
	public ResponseEntity<Object> saveUsuariosDispositivos(@Valid @RequestParam("idUsuario") Long idUsuario,
			@RequestParam("idDispositivo") Long idDispositivo) {

		if (dispositivosRepository.existsById(idDispositivo) == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Código do Dispositivo não Localizado ...");
		}
		
		if (usuariosRepository.existsById(idUsuario) == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Código do Usuário não Localizado ...");
		}
		
		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(false);
		usuariosDispositivosModel.setVolume(0);
		usuariosDispositivosModel.setCancalFaixa(0);
		usuariosDispositivosModel.setTemperatura(0);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuariosDispositivosRepository.save(usuariosDispositivosModel));
	}

	@GetMapping("Lista")
	public ResponseEntity<List<UsuariosDispositivosModel>> getAllUsuariiosDispositivos() {
		return ResponseEntity.status(HttpStatus.OK).body(usuariosDispositivosRepository.findAll());
	}
}
