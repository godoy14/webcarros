package com.webcarros.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcarros.domain.model.Usuario;
import com.webcarros.domain.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	@Transactional
	public Usuario cadastrar(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent()) {
			throw new RuntimeException("Já existe um usuário cadastrado para o email informado");
		}
		
		return usuarioRepository.save(usuario);
		
	}

}
