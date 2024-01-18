package br.com.alanealves.CasaInteligente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alanealves.CasaInteligente.Model.DispositivosModel;

@Repository
public interface DispositivosRepository extends JpaRepository<DispositivosModel, Long> {
	
	boolean existsById(Long dispositivo);
}
