package cl.usach.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.usach.spring.backend.entities.Film;

public interface FilmRepository extends PagingAndSortingRepository<Film, Integer> {
	

}
