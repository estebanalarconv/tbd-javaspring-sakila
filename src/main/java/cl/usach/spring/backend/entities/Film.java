package cl.usach.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Column(name="title", nullable=false, length=255)
	private String title;

	@Column(name="description", nullable=false, length=512)
	private String description;
	
	@Column(name="release_year", nullable=false)
	private int releaseYear;

	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JsonIgnore
	 @JoinTable(
	      name="film_actor",
	      joinColumns=@JoinColumn(name="film_id", referencedColumnName="film_id"),
	      inverseJoinColumns=@JoinColumn(name="actor_id", referencedColumnName="actor_id"))
	  private List<Actor> actors;
	
  
	public Film() {
	}

	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public List<Actor> getActors() {
		return this.actors;
	}
	
	public void setActors(List<Actor> actors)
	{
		this.actors = actors;
	}

}