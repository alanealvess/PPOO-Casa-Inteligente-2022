package br.com.alanealves.CasaInteligente.Controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alanealves.CasaInteligente.Interfaces.DispositivoControlavel;
import br.com.alanealves.CasaInteligente.Model.DispositivosModel;
import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModel;
import br.com.alanealves.CasaInteligente.Repository.DispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosDispositivosRepository;
import br.com.alanealves.CasaInteligente.Repository.UsuariosRepository;
import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModelId;

@RestController
@RequestMapping("/controleTemperatura")
public class TemperaturaController implements DispositivoControlavel {

	final UsuariosDispositivosRepository usuariosDispositivosRepository;
	final UsuariosRepository usuariosRepository;
	final DispositivosRepository dispositivosRepository;

	public TemperaturaController(UsuariosDispositivosRepository usuariosDispositivosRepository,
			UsuariosRepository usuariosRepository, DispositivosRepository dispositivosRepository) {
		super();
		this.usuariosDispositivosRepository = usuariosDispositivosRepository;
		this.usuariosRepository = usuariosRepository;
		this.dispositivosRepository = dispositivosRepository;
	}

	private boolean ligado;
	private int temperatura;

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

		if (!dispositivosOptional.get().getTipo().equals("Temperatura")) {
			return "Este Dispositivo Não é um Controle de Temperatura, Favor Verificar ... "
					+ dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (ligado) {
			return "Controle de temperatura já Está Ligada ...";
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

		return "Controle de temperatura foi ligado";
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

		if (!dispositivosOptional.get().getTipo().equals("Temperatura")) {
			return "Este Dispositivo Não é um Controle de Temperatura, Favor Verificar ... "
					+ dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();

		if (!ligado) {
			return "Controle de Temperatura já Esta DesLigado ...";
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

		return "Controle de Temperatura foi Desligado";
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

	@GetMapping("/aumentarTemperatura")
	public String aumentarTemperatura(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("Temperatura")) {
			return "Este Dispositivo Não é um Controle de Temperatura, Favor Verificar ... "
					+ dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		temperatura = usuariosDispositivosOptional.get().getTemperatura();

		if (!ligado) {
			return "Controle de Temperatura Esta DesLigada, Favor Ligar Antes ...";
		}

		temperatura++;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(temperatura);

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Controle de Temperatura aumentada para: " + temperatura;
	}

	@GetMapping("/diminuirTemperatura")
	public String diminuirTemperatura(@RequestParam("idUsuario") Long idUsuario,
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

		if (!dispositivosOptional.get().getTipo().equals("Temperatura")) {
			return "Este Dispositivo Não é um Controle de Temperatura, Favor Verificar ... "
					+ dispositivosOptional.get().getTipo();
		}

		var usuariosDispositivosId = new UsuariosDispositivosModelId();
		usuariosDispositivosId.setUsuario(idUsuario);
		usuariosDispositivosId.setDispositivo(idDispositivo);

		Optional<UsuariosDispositivosModel> usuariosDispositivosOptional = usuariosDispositivosRepository
				.findById(usuariosDispositivosId);

		ligado = usuariosDispositivosOptional.get().isLigado();
		temperatura = usuariosDispositivosOptional.get().getTemperatura();

		if (!ligado) {
			return "Controle de Temperatura Esta DesLigada, Favor Ligar Antes ...";
		}

		if (temperatura == 0) {
			return "Controle de Temperatura já Esta no Mínimo ...";
		}

		temperatura--;

		var usuariosDispositivosModel = new UsuariosDispositivosModel();
		usuariosDispositivosModel.setUsuario(idUsuario);
		usuariosDispositivosModel.setDispositivo(idDispositivo);
		usuariosDispositivosModel.setLigado(ligado);
		usuariosDispositivosModel.setVolume(usuariosDispositivosOptional.get().getVolume());
		usuariosDispositivosModel.setCancalFaixa(usuariosDispositivosOptional.get().getCancalFaixa());
		usuariosDispositivosModel.setTemperatura(temperatura);

		usuariosDispositivosRepository.save(usuariosDispositivosModel);

		return "Controle de Temperatura diminuido para: " + temperatura;
	}

	public boolean isLigado() {
		return ligado;
	}

	public void setLigado(boolean ligado) {
		this.ligado = ligado;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

}
