package br.com.alanealves.CasaInteligente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModel;
import br.com.alanealves.CasaInteligente.Model.UsuariosDispositivosModelId;

@Repository
public interface UsuariosDispositivosRepository extends JpaRepository<UsuariosDispositivosModel, UsuariosDispositivosModelId> {

	boolean existsByUsuarioAndDispositivo(Long usuario, Long dispisitivo);
}
