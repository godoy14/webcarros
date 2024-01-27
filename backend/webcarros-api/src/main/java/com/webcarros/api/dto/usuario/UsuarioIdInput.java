package com.webcarros.api.dto.usuario;

import jakarta.validation.constraints.NotNull;

public class UsuarioIdInput {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
