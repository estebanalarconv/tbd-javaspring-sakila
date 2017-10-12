package cl.usach.spring.backend.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.usach.spring.backend.entities.Film;
import cl.usach.spring.backend.entities.Actor;
import cl.usach.spring.backend.repository.ActorRepository;
import cl.usach.spring.backend.repository.FilmRepository;

@RestController  
@RequestMapping("/films")
public class FilmService {
	public Film film;
	
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ActorRepository actorRepository;


	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Film> getAllFilms() {
		return filmRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  Film findOne(@PathVariable("id") Integer id) {
		return filmRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Film create(@RequestBody Film resource) {
	     return filmRepository.save(resource);
	}
	

	@RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    public ResponseEntity<List<Actor>> getActors(@PathVariable("id") Integer id){
        if (filmRepository.exists(id)){
        	List<Actor> actors = filmRepository.findOne(id).getActors();
        	return new ResponseEntity<List<Actor>>(actors, HttpStatus.OK);
        }else{
        	return new ResponseEntity<List<Actor>>(HttpStatus.NOT_FOUND);
        }		
    }
	
	@RequestMapping(value = "/{film_id}/actors/{actor_id}",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Actor> create(@PathVariable("actor_id") Integer actor_id, @PathVariable("film_id") Integer film_id) {
		
		if(actorRepository.exists(actor_id) && filmRepository.exists(film_id)){
			Actor actor = actorRepository.findOne(actor_id);
			Film film = filmRepository.findOne(film_id);
			List <Film> films= actor.getFilms();
			List <Actor> actors = film.getActors();
			actors.add(actor);
			films.add(film);
			actor.setFilms(films);
			film.setActors(actors);
			filmRepository.save(film);
			return new ResponseEntity<Actor>(actorRepository.save(actor), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Actor>(HttpStatus.NOT_FOUND);
		}
		
	}
	 
}