package com.webcarros.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webcarros.api.dto.carro.CarroAssemblers;
import com.webcarros.api.dto.carro.CarroInputModel;
import com.webcarros.api.dto.carro.CarroModel;
import com.webcarros.domain.model.Carro;
import com.webcarros.domain.service.CarroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/carros")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@Autowired
	private CarroAssemblers carroAssemblers;
	
//	@GetMapping
//	public List<CarroModel> listarTodos() {
//		return carroAssemblers.toCollectionModel(carroService.listarTodos());
//	}
	
	@GetMapping
	public List<CarroModel> listarCarrosPorStatus(@RequestParam(value = "status") String status) {
		return carroAssemblers.toCollectionModel(carroService.listarPeloStatus(status));
	}
	
	@GetMapping("/pesquisa")
	public List<CarroModel> listarCarrosPeloNomeContendo(
			@RequestParam(value = "nome") String nome,
			@RequestParam(value = "status") String status) {
		return carroAssemblers.toCollectionModel(carroService.listarPeloNomeEStatus(nome, status));
	}
	
	@GetMapping("/{usuarioId}")
	public List<CarroModel> listarCarrosPorUsuario(@PathVariable Long usuarioId) {
		return carroAssemblers.toCollectionModel(carroService.listarPeloUsuario(usuarioId));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CarroModel cadastrar(
			@ModelAttribute @Valid CarroInputModel carroInput, 
			@RequestPart List<MultipartFile> fotos) {
		
		Carro carro = new Carro();
		
		if (fotos.isEmpty() || fotos == null) {
			carro = carroService.cadastrar(carroInput);
		} else {
			carro = carroService.cadastrarComFotos(carroInput, fotos);
		}
		
		if (carro == null) {
			System.out.println("Erro no cadastro do Carro");
		}
		
		
		return carroAssemblers.toModel(carro);
	}
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/{carroCodigo}")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CarroModel atualizar(
			@ModelAttribute @Valid CarroInputModel carroInput,
			@RequestPart List<MultipartFile> fotos,
			@PathVariable String carroCodigo) {
		
		Carro carroAtualizado = carroService.atualizar(carroInput, fotos, carroCodigo);
		
		return carroAssemblers.toModel(carroAtualizado);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void deletar(
			@RequestParam(value = "codigoCarro") String codigoCarro,
			@RequestParam(value = "usuario") Long usuarioId
		) {
		
		Carro carro = carroService.buscarOuFalhar(codigoCarro);
		
		if (carro.getUsuario().getId() != usuarioId) {
			throw new RuntimeException("Usuario sem permissão");
		}
		
		carroService.deletar(carro.getId());
		
	}

}
