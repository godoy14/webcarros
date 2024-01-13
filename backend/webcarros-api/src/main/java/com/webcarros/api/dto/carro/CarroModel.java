package com.webcarros.api.dto.carro;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.webcarros.domain.model.Foto;
import com.webcarros.domain.model.StatusCarro;
import com.webcarros.domain.model.Usuario;

public class CarroModel {
	
	private Long id;
	
	private String codigo;
	
	private String nome;
	
	private String ano;
	
	private BigDecimal preco;
	
	private String cidade;
	
	private Long km;
	
	private StatusCarro status;
	
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataAtualizacao;
	
	private Usuario usuario;
	
	private List<Foto> fotos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

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

	public StatusCarro getStatus() {
		return status;
	}

	public void setStatus(StatusCarro status) {
		this.status = status;
	}

	public OffsetDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(OffsetDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public OffsetDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
}
