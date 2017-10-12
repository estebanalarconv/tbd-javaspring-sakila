package cl.usach.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.usach.spring.backend.entities.Actor;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Integer> {
	

}
