package com.webcarros.api.dto.usuario;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcarros.domain.model.Usuario;

@Component
public class UsuarioAssemblers {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioModel.class);
	}


	public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}

	public Usuario toDomainObject(UsuarioInputModel usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}

}
