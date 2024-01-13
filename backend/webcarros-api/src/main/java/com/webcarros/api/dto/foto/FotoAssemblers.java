package com.webcarros.api.dto.foto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcarros.domain.model.Foto;

@Component
public class FotoAssemblers {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoModel toModel(Foto foto) {
		return modelMapper.map(foto, FotoModel.class);
	}

}
