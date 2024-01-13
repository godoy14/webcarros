package com.webcarros.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcarros.domain.model.Carro;
import com.webcarros.domain.model.StatusCarro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro> findByStatus(StatusCarro status);
	
}
