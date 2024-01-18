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
@RequestMapping("/controleTelevisor")
public class TelevisorController implements DispositivoControlavel {

	final UsuariosDispositivosRepository usuariosDispositivosRepository;
	final UsuariosRepository usuariosRepository;
	final DispositivosRepository dispositivosRepository;

	public TelevisorController(UsuariosDispositivosRepository usuariosDispositivosRepository,
			UsuariosRepository usuariosRepository, DispositivosRepository dispositivosRepository) {
		super();
		this.usuariosDispositivosRepository = usuariosDispositivosRepository;
		this.usuariosRepository = usuariosRepository;
		this.dispositivosRepository = dispositivosRepository;
	}

	private boolean ligado;
	private int volume;
	private int canal;

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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (ligado) {
			return "Televidor já Está Ligado ...";
		}

		ligado = true;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Televisor foi ligado";
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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (!ligado) {
			return "TV já Esta DesLigado ...";
		}

		ligado = false;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "TV foi Desligado";
	}

	@GetMapping("/aumentarVolume")
	public String aumentarVolume(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		volume = usuariosDispositivosOptional.get().getVolume();

		if (!ligado) {
			return "TV Esta DesLigada, Favor Ligar Antes ...";
		}

		volume++;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(volume);
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Volume aumentado para: " + volume;
	}

	@GetMapping("/diminuirVolume")
	public String diminuirVolume(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		volume = usuariosDispositivosOptional.get().getVolume();

		if (!ligado) {
			return "TV Esta DesLigada, Favor Ligar Antes ...";
		}

		if (volume == 0) {
			return "TV já Esta no Volume Mínimo ...";
		}

		volume--;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(volume);
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Volume diminuido para: " + volume;
	}

	@GetMapping("/avancarCanal")
	public String avancar(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		canal = usuariosDispositivosOptional.get().getCancalFaixa();

		if (!ligado) {
			return "TV Esta DesLigada, Favor Ligar Antes ...";
		}

		canal++;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(canal);
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Canal avançado para: " + canal;
	}

	@GetMapping("/retrocederCanal")
	public String retroceder(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("TV")) {
			return "Este Dispositivo Não é uma TV, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		canal = usuariosDispositivosOptional.get().getCancalFaixa();

		if (!ligado) {
			return "TV Esta DesLigada, Favor Ligar Antes ...";
		}

		if (canal == 0) {
			return "TV já Esta no Canal Mínimo ...";
		}

		canal--;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(canal);
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Canal Retrocedido para: " + volume;
	}

	public String aumentarTemperatura(Long usuario, Long dispositivo) {
		return null;
	}

	public String diminuirTemperatura(Long usuario, Long dispositivo) {
		return null;
	}

	public boolean isLigado() {
		return ligado;
	}

	public void setLigado(boolean ligado) {
		this.ligado = ligado;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getCanal() {
		return canal;
	}

	public void setCanal(int canal) {
		this.canal = canal;
	}

}
