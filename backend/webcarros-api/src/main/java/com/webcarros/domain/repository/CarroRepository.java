package com.webcarros.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcarros.domain.model.Carro;
import com.webcarros.domain.model.StatusCarro;
import com.webcarros.domain.model.Usuario;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro> findByStatus(StatusCarro status);

	Optional<Carro> findByCodigo(String codigo);

	List<Carro> findByUsuario(Usuario usuario);

	List<Carro> findByNomeContainsAndStatus(String nome, StatusCarro status);

}
