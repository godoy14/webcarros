package com.webcarros.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_carros")
public class Carro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "carro_id")
	private Long id;
	
	@Column(name = "carro_codigo")
	private String codigo;
	
	@Column(name = "carro_nome", nullable = false)
	private String nome;
	
	@Column(name = "carro_ano")
	private String ano;
	
	@Column(name = "carro_preco")
	private BigDecimal preco;
	
	@Column(name = "carro_cidade")
	private String cidade;
	
	@Column(name = "carro_km")
	private Long km;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "carro_status")
	private StatusCarro status = StatusCarro.A_VENDA;
	
	@CreationTimestamp
	@Column(name = "carro_data_criacao", columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(name = "carro_data_atualizacao", columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "carro_usuario_id", nullable = false)
	private Usuario usuario;
	
	@OneToMany(mappedBy = "carro", cascade = CascadeType.ALL)
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

	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

}
