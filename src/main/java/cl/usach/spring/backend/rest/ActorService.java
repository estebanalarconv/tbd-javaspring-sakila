package cl.usach.spring.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.usach.spring.backend.entities.Actor;
import cl.usach.spring.backend.entities.Film;
import cl.usach.spring.backend.repository.ActorRepository;
import cl.usach.spring.backend.repository.FilmRepository;

@RestController  
@RequestMapping("/actors")
public class ActorService {
	public Actor actor;
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private FilmRepository filmRepository;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Actor> getAllUsers() {
		return actorRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  Actor findOne(@PathVariable("id") Integer id) {
		return actorRepository.findOne(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Actor> create(@RequestBody Actor resource) {
		if (resource.getFirstName().isEmpty() || resource.getLastName().isEmpty()){
			return new ResponseEntity<Actor>(HttpStatus.BAD_REQUEST);
		}else{
			return new ResponseEntity<Actor>(actorRepository.save(resource), HttpStatus.CREATED);
		}
	}
	
	
	@RequestMapping(value = "/{id}/films", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<Film>> getFilm(@PathVariable("id") Integer id){
        if (actorRepository.exists(id)){
        	List<Film> films = actorRepository.findOne(id).getFilms();
        	return new ResponseEntity<List<Film>>(films, HttpStatus.OK);
        }else{
        	return new ResponseEntity<List<Film>>(HttpStatus.NOT_FOUND);
        }		
    }
	
	@RequestMapping(value = "/{actor_id}/films/{film_id}",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Film> create(@PathVariable("actor_id") Integer actor_id, @PathVariable("film_id") Integer film_id) {
		
		if(actorRepository.exists(actor_id) && filmRepository.exists(film_id)){
			Actor actor = actorRepository.findOne(actor_id);
			Film film = filmRepository.findOne(film_id);
			List <Film> films= actor.getFilms();
			List <Actor> actors = film.getActors();
			actors.add(actor);
			films.add(film);
			actor.setFilms(films);
			film.setActors(actors);
			actorRepository.save(actor);
			return new ResponseEntity<Film>(filmRepository.save(film), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Film>(HttpStatus.NOT_FOUND);
		}
	}
}