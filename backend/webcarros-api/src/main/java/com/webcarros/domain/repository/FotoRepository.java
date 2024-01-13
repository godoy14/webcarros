package com.webcarros.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcarros.domain.model.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{
	
	Optional<Foto> findByCodigo(String codigo);

}
