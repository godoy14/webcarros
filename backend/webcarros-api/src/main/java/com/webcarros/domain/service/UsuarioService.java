package com.webcarros.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcarros.api.dto.usuario.UsuarioAssemblers;
import com.webcarros.api.dto.usuario.UsuarioInputModel;
import com.webcarros.domain.model.Usuario;
import com.webcarros.domain.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioAssemblers usuarioAssemblers;
	
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).get();
	}
	
	@Transactional
	public Usuario cadastrar(UsuarioInputModel usuarioInput) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioInput.getEmail());
		
		if (usuarioExistente.isPresent()) {
			throw new RuntimeException("Já existe um usuário cadastrado para o email informado");
		}
		
		Usuario usuario = usuarioAssemblers.toDomainObject(usuarioInput);
		
		return usuarioRepository.save(usuario);
		
	}

}
