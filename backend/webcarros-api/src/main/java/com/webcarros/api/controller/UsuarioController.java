package com.webcarros.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webcarros.api.dto.auth.AuthenticationDTO;
import com.webcarros.api.dto.auth.LoginResponseDTO;
import com.webcarros.api.dto.usuario.UsuarioAssemblers;
import com.webcarros.api.dto.usuario.UsuarioInputModel;
import com.webcarros.api.dto.usuario.UsuarioModel;
import com.webcarros.core.security.TokenService;
import com.webcarros.domain.model.Usuario;
import com.webcarros.domain.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioAssemblers usuarioAssemblers;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;

	@GetMapping
	@ResponseBody
	public List<UsuarioModel> listarTodos() {
		return usuarioAssemblers.toCollectionModel(usuarioService.listar());
	}

	@PostMapping("/cadastro")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public UsuarioModel cadastrar(@RequestBody @Valid UsuarioInputModel usuarioInput) {

		return usuarioAssemblers.toModel(usuarioService.cadastrar(usuarioInput));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((Usuario) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

}
