package com.webcarros.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcarros.domain.model.Carro;
import com.webcarros.domain.model.StatusCarro;
import com.webcarros.domain.model.Usuario;
import com.webcarros.domain.repository.CarroRepository;

import jakarta.transaction.Transactional;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<Carro> listarTodos() {
		return carroRepository.findAll();
	}
	
	public List<Carro> listarPeloStatus(String status) {
		StatusCarro statusEnum = StatusCarro.valueOf(status);
		if (statusEnum == null) {
			throw new RuntimeException("Status inválido");
		}
		
		return carroRepository.findByStatus(statusEnum);
	}
	
	@Transactional
	public Carro cadastrar(Carro carro) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(carro.getUsuario().getId());
		carro.setUsuario(usuario);
		
		return carroRepository.save(carro);
		
	}

}
