package com.webcarros.api.dto.carro;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcarros.domain.model.Carro;

@Component
public class CarroAssemblers {

	@Autowired
	private ModelMapper modelMapper;

	public CarroModel toModel(Carro carro) {
		return modelMapper.map(carro, CarroModel.class);
	}


	public List<CarroModel> toCollectionModel(Collection<Carro> carros) {
		return carros.stream()
				.map(carro -> toModel(carro))
				.collect(Collectors.toList());
	}

	public Carro toDomainObject(CarroInputModel carroInput) {
		return modelMapper.map(carroInput, Carro.class);
	}

}
