package br.com.alanealves.CasaInteligente.Controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alanealves.CasaInteligente.Model.UsuarioModel;
import br.com.alanealves.CasaInteligente.Repository.UsuariosRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

	final UsuariosRepository usuariosRepository;

	public UsuariosController(UsuariosRepository usuariosRepository) {
		super();
		this.usuariosRepository = usuariosRepository;
	}

	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	@GetMapping
	public ResponseEntity<Object> saveUsuarios(@Valid @RequestParam("id") Long id, @RequestParam("nome") String nome,
			@RequestParam("password") String password) throws NoSuchAlgorithmException {
		
		final MessageDigest digest = MessageDigest.getInstance("SHA-256");
		final byte[] hashbytes = digest.digest(
		  password.getBytes(StandardCharsets.UTF_8));
		String sha3Hex = bytesToHex(hashbytes);
        System.out.println(sha3Hex);
        
		var usuariosModel = new UsuarioModel();
		usuariosModel.setId(id);
		usuariosModel.setNome(nome);
		usuariosModel.setPassword(sha3Hex);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuariosRepository.save(usuariosModel));
	}

	@GetMapping("Lista")
	public ResponseEntity<List<UsuarioModel>> getAllUsuariios() {
		return ResponseEntity.status(HttpStatus.OK).body(usuariosRepository.findAll());
	}
}
