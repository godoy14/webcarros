package com.webcarros.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webcarros.api.dto.carro.CarroAssemblers;
import com.webcarros.api.dto.carro.CarroInputModel;
import com.webcarros.domain.model.Carro;
import com.webcarros.domain.model.Foto;
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
	
	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private CarroAssemblers carroAssemblers;
	
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
	
	public List<Carro> listarPeloUsuario(Long usuarioId) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		return carroRepository.findByUsuario(usuario);
	}
	
	public List<Carro> listarPeloNomeEStatus(String nome, String status) {
		StatusCarro statusEnum = StatusCarro.valueOf(status);
		return carroRepository.findByNomeContainsAndStatus(nome, statusEnum);
	}
	
	public Carro buscarOuFalhar(Long carroId) {
		return carroRepository.findById(carroId).orElseThrow();
	}
	
	public Carro buscarOuFalhar(String codigo) {
		return carroRepository.findByCodigo(codigo).orElseThrow();
	}
	
	@Transactional
	public Carro cadastrar(CarroInputModel carro) {
		
		Carro novoCarro = carroAssemblers.toDomainObject(carro);
		
		Usuario usuario = usuarioService.buscarOuFalhar(carro.getUsuario().getId());
		novoCarro.setUsuario(usuario);
		
		return carroRepository.save(novoCarro);
		
	}
	
	@Transactional
	public Carro cadastrarComFotos(CarroInputModel carro, List<MultipartFile> fotos) {
		
		Carro novoCarro = carroAssemblers.toDomainObject(carro);
		
		Usuario usuario = usuarioService.buscarOuFalhar(carro.getUsuario().getId());
		novoCarro.setUsuario(usuario);
		
		carroRepository.save(novoCarro);
		
		fotos.forEach(foto -> {
			Foto novaFoto = fotoService.cadastrarNovaFoto(foto, novoCarro);
			novoCarro.getFotos().add(novaFoto);
		});
		
		return novoCarro;
		
	}
	
	@Transactional
	public void deletar(Long carroId) {
		carroRepository.deleteById(carroId);
		carroRepository.flush();
	}
	
	@Transactional
	public Carro atualizar(CarroInputModel carro, List<MultipartFile> fotos, String codigo) {
		
		Carro carroAtual = buscarOuFalhar(codigo);
		
		carroAtual.setAno(carro.getAno());
		carroAtual.setCidade(carro.getCidade());
		carroAtual.setKm(carro.getKm());
		carroAtual.setNome(carro.getNome());
		carroAtual.setPreco(carro.getPreco());
		
		List<Foto> novasFotos = new ArrayList<>();
		
		fotos.forEach(foto -> {
			Foto novaFoto = (Foto) fotoService.buscarPorNomeECarro(foto.getOriginalFilename(), carroAtual.getCodigo());
			System.out.println(foto.getName());
			if ( novaFoto == null) {
				novaFoto = new Foto();
				novaFoto.setNome(foto.getOriginalFilename());
				novaFoto.setCarro(carroAtual);
				try {
					novaFoto.setImage(foto.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			novasFotos.add(novaFoto);
		});
		carroAtual.getFotos().clear();
		
		carroAtual.setFotos(novasFotos);
		
		carroRepository.save(carroAtual);
		carroRepository.flush();
		
		return carroAtual;
		
	}

}
