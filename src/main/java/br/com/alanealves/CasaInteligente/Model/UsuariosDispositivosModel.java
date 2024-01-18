package br.com.alanealves.CasaInteligente.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(UsuariosDispositivosModelId.class)
@Table(name = "usuarios_dispositivos", schema = "casa")
public class UsuariosDispositivosModel {

	@Id
	@Column(name = "id_usuario")
	private Long usuario;
	@Id
	@Column(name = "id_dispositivo")
	private Long dispositivo;
	@Column(nullable = false)
	private boolean ligado;
	private Integer volume;
	@Column(name = "canal_faixa")
	private Integer cancalFaixa;
	private Integer temperatura;

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Long dispositivo) {
		this.dispositivo = dispositivo;
	}

	public boolean isLigado() {
		return ligado;
	}

	public void setLigado(boolean ligado) {
		this.ligado = ligado;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getCancalFaixa() {
		return cancalFaixa;
	}

	public void setCancalFaixa(Integer cancalFaixa) {
		this.cancalFaixa = cancalFaixa;
	}

	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

}
