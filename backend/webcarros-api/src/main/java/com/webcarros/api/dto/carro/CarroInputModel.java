package com.webcarros.api.dto.carro;

import java.math.BigDecimal;

import com.webcarros.api.dto.usuario.UsuarioIdInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarroInputModel {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String ano;
	
	@NotNull
	private BigDecimal preco;
	
	@NotBlank
	private String cidade;
	
	@NotNull
	private Long km;
	
	@Valid
	@NotNull
	private UsuarioIdInput usuario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Long getKm() {
		return km;
	}

	public void setKm(Long km) {
		this.km = km;
	}

	public UsuarioIdInput getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioIdInput usuario) {
		this.usuario = usuario;
	}

}
