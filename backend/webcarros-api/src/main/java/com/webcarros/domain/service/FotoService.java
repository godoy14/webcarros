package com.webcarros.domain.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webcarros.domain.model.Carro;
import com.webcarros.domain.model.Foto;
import com.webcarros.domain.repository.FotoRepository;

import jakarta.transaction.Transactional;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	public Foto buscarFotoPorCodigo(String codigo) {
		return fotoRepository.findByCodigo(codigo).orElseThrow();
	}

	public List<Foto> buscarPorNomeECarro(String nome, String carroCodigo) {
		return fotoRepository.findByNomeAndCarroCodigo(nome, carroCodigo);
	}

	@Transactional
	public Foto cadastrarNovaFoto(MultipartFile file, Carro carro) {

		Foto foto = new Foto();
		foto.setCarro(carro);
		foto.setNome(file.getOriginalFilename());
		try {
			foto.setImage(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fotoRepository.save(foto);
	}

}
