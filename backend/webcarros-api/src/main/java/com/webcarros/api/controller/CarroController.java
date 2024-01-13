package com.webcarros.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<CarroModel> listarTodos(@RequestParam(value = "status") String status) {
		return carroAssemblers.toCollectionModel(carroService.listarPeloStatus(status));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CarroModel cadastrar(@ModelAttribute @Valid CarroInputModel carroInput, @RequestPart List<MultipartFile> fotos) {
		Carro carro = carroService.cadastrar(carroAssemblers.toDomainObject(carroInput));
		
		if (carro == null) {
			System.out.println("Erro no cadastro do Carro");
		}
		
		
		return carroAssemblers.toModel(carro);
	}

}
