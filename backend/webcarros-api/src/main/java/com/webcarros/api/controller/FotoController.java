package com.webcarros.api.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webcarros.domain.model.Foto;
import com.webcarros.domain.service.FotoService;

@RestController
@RequestMapping(path = "fotos/")
public class FotoController {

	@Autowired
	private FotoService fotoService;

	@GetMapping("/{codigo}")
	public ResponseEntity<InputStreamResource> buscarFotoPorCodigo(@PathVariable String codigo) {

		Foto foto = fotoService.buscarFotoPorCodigo(codigo);

		InputStream inputStream = new ByteArrayInputStream(foto.getImage());

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(inputStream));
	}

}
