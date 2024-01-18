package br.com.alanealves.CasaInteligente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alanealves.CasaInteligente.Model.UsuarioModel;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuarioModel, Long> {

	boolean existsById(Long usuario);
}
