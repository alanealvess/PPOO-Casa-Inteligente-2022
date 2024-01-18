package br.com.alanealves.CasaInteligente.Controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alanealves.CasaInteligente.Interfaces.DispositivoControlavel;
import br.com.alanealves.CasaInteligente.Model.DispositivosModel;
import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModelId;
import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModel;
import br.com.alanealves.CasaInteligente.Repository.DispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosDispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosRepository;

@RestController
@RequestMapping("/controleLampadas")
public class LampadaController implements DispositivoControlavel {

	final UsuariosDispositivosRepository usuariosDispositivosRepository;
	final UsuariosRepository usuariosRepository;
	final DispositivosRepository dispositivosRepository;

	public LampadaController(UsuariosDispositivosRepository usuariosDispositivosRepository,
			UsuariosRepository usuariosRepository, DispositivosRepository dispositivosRepository) {
		super();
		this.usuariosDispositivosRepository = usuariosDispositivosRepository;
		this.usuariosRepository = usuariosRepository;
		this.dispositivosRepository = dispositivosRepository;
	}

	private boolean ligada;

	@GetMapping("/ligar")
	public String ligar(@RequestParam("idUsuario") Long idUsuario, @RequestParam("idDispositivo") Long idDispositivo) {

		if (usuariosRepository.existsById(idUsuario) == false) {
			return "Usuário Não Cadastrado, Favor Verificar ...";
		}

		if (dispositivosRepository.existsById(idDispositivo) == false) {
			return "Dispositivo Não Cadastrado, Favor Verificar ...";
		}

		if (usuariosDispositivosRepository.existsByUsuarioAndDispositivo(idUsuario, idDispositivo) == false) {
			return "Usuário Não Tem Permissão Sobre o Dispositivo, Favor Verificar ...";
		}

		Optional<DispositivosModel> dispositivosOptional = dispositivosRepository.findById(idDispositivo);

		if (!dispositivosOptional.get().getTipo().equals("Lampada")) {
			return "Este Dispositivo Não é uma Lâmpada, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligada = usuariosDispositivosOptional.get().isLigado();

		if (ligada) {
			return "Lâmpada já Está Ligada ...";
		}

		ligada = true;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligada);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Lâmpada foi ligada";
	}

	@GetMapping("/desligar")
	public String desligar(@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("idDispositivo") Long idDispositivo) {

		if (usuariosRepository.existsById(idUsuario) == false) {
			return "Usuário Não Cadastrado, Favor Verificar ...";
		}

		if (dispositivosRepository.existsById(idDispositivo) == false) {
			return "Dispositivo Não Cadastrado, Favor Verificar ...";
		}

		if (usuariosDispositivosRepository.existsByUsuarioAndDispositivo(idUsuario, idDispositivo) == false) {
			return "Usuário Não Tem Permissão Sobre o Dispositivo, Favor Verificar ...";
		}

		Optional<DispositivosModel> dispositivosOptional = dispositivosRepository.findById(idDispositivo);

		if (!dispositivosOptional.get().getTipo().equals("Lampada")) {
			return "Este Dispositivo Não é uma Lâmpada, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligada = usuariosDispositivosOptional.get().isLigado();

		if (!ligada) {
			return "Lâmpada já Esta DesLigado ...";
		}

		ligada = false;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligada);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Lâmpada foi Desligado";
	}

	public String aumentarVolume(Long idUsuario, Long idDispositivo) {
		return null;
	}

	public String diminuirVolume(Long idUsuario, Long idDispositivo) {
		return null;
	}

	public String avancar(Long idUsuario, Long idDispositivo) {
		return null;
	}

	public String retroceder(Long idUsuario, Long idDispositivo) {
		return null;
	}

	public String aumentarTemperatura(Long usuario, Long dispositivo) {
		return null;
	}

	public String diminuirTemperatura(Long usuario, Long dispositivo) {
		return null;
	}

	public boolean isLigada() {
		return ligada;
	}

	public void setLigada(boolean ligada) {
		this.ligada = ligada;
	}

}
