package com.webcarros.domain.model;

public enum CargoUsuario {
	
	USER("user"),
	ADMIN("admin");
	
	private String cargo;
	
	private CargoUsuario(String cargo) {
		this.cargo = cargo;
	}
	
	public String getCargo() {
		return cargo;
	}

}
