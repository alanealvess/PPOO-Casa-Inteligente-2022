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
@RequestMapping("/controleAudio")
public class AudioController implements DispositivoControlavel {

	final UsuariosDispositivosRepository usuariosDispositivosRepository;
	final UsuariosRepository usuariosRepository;
	final DispositivosRepository dispositivosRepository;

	public AudioController(UsuariosDispositivosRepository usuariosDispositivosRepository,
			UsuariosRepository usuariosRepository, DispositivosRepository dispositivosRepository) {
		super();
		this.usuariosDispositivosRepository = usuariosDispositivosRepository;
		this.usuariosRepository = usuariosRepository;
		this.dispositivosRepository = dispositivosRepository;
	}

	private boolean ligado;
	private int volume;
	private int faixa;

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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (ligado) {
			return "Audio já Esta Ligado ...";
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

		return "Dispositivo de áudio foi ligado";
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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (!ligado) {
			return "Audio já Esta DesLigado ...";
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

		return "Dispositivo de áudio foi Desligado";
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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		volume = usuariosDispositivosOptional.get().getVolume();

		if (!ligado) {
			return "Audio Esta DesLigado, Favor Ligar Antes ...";
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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		volume = usuariosDispositivosOptional.get().getVolume();

		if (!ligado) {
			return "Audio Esta DesLigado, Favor Ligar Antes ...";
		}

		if (volume == 0) {
			return "Audio já Esta no Volume Mínimo ...";
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

	@GetMapping("/avancarFaixa")
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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		faixa = usuariosDispositivosOptional.get().getCancalFaixa();

		if (!ligado) {
			return "Audio Esta DesLigado, Favor Ligar Antes ...";
		}

		faixa++;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(faixa);
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Faixa avançada para: " + faixa;
	}

	@GetMapping("/retrocederFaixa")
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

		if (!dispositivosOptional.get().getTipo().equals("Audio")) {
			return "Este Dispositivo Não é um Audio, Favor Verificar ... " + dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		faixa = usuariosDispositivosOptional.get().getCancalFaixa();

		if (!ligado) {
			return "Audio Esta DesLigado, Favor Ligar Antes ...";
		}

		if (faixa == 0) {
			return "Audio já Esta na Faixa Mínima ...";
		}

		faixa--;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(faixa);
		usuariosDispositivosModel.setTemperatura(usuariosDispositivosOptional.get().getTemperatura());

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Faixa Retrocedido para: " + faixa;
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

	public int getFaixa() {
		return faixa;
	}

	public void setFaixa(int faixa) {
		this.faixa = faixa;
	}

}
