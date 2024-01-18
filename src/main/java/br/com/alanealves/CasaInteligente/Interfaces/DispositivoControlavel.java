package br.com.alanealves.CasaInteligente.Interfaces;

public interface DispositivoControlavel {

    String ligar(Long usuario, Long dispositivo);
    String desligar(Long usuario, Long dispositivo);
    String aumentarVolume(Long usuario, Long dispositivo);
    String diminuirVolume(Long usuario, Long dispositivo);
    String avancar(Long usuario, Long dispositivo);
    String retroceder(Long usuario, Long dispositivo);
    String aumentarTemperatura(Long usuario, Long dispositivo);
    String diminuirTemperatura(Long usuario, Long dispositivo);
}
