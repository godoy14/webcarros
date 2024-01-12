package com.webcarros.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webcarros.api.dto.usuario.UsuarioAssemblers;
import com.webcarros.api.dto.usuario.UsuarioInputModel;
import com.webcarros.api.dto.usuario.UsuarioModel;
import com.webcarros.domain.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioAssemblers usuarioAssemblers;
	
	@GetMapping
	@ResponseBody
	public List<UsuarioModel> listarTodos() {
		return usuarioAssemblers.toCollectionModel(usuarioService.listar());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public UsuarioModel cadastrar(@RequestBody @Valid UsuarioInputModel usuarioInput) {
		return usuarioAssemblers.toModel(usuarioService.cadastrar(usuarioAssemblers.toDomainObject(usuarioInput)));
	}

}
