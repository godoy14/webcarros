package com.webcarros.domain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_fotos")
public class Foto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "foto_id")
	private Long id;
	
	@Column(name = "foto_codigo")
	private String codigo;
	
	@Column(name = "foto_nome", nullable=false)
	private String nome;
	
	@Column(name = "foto_url", nullable=false)
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "foto_carro", nullable = false)
	private Carro carro;
	
	@Lob
	@Column(name = "foto_image", nullable=false, columnDefinition="mediumblob")
	private byte[] image;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String uuid) {
		this.codigo = uuid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	@PrePersist
	private void gerarUUID() {
		setCodigo(UUID.randomUUID().toString());
	}

}
