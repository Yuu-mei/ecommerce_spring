package com.jmorillo.indieStore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.jmorillo.indieStore.validationsConstants.ValidationsConstants;

@Entity
public class Developer {
	@Id
	@GeneratedValue
	private int dev_id;
	@Pattern(regexp = ValidationsConstants.REGEXP_USERNAME, message = "Developer name between 3-30 characters, using only letters _ - and spaces")
	@NotEmpty
	private String dev_name;
	// This field will have a brief description of the developer (optional)
	private String dev_info;
	@ManyToMany(mappedBy="developers")
	private List<Videogame> videogames;
	
	public Developer() {
		// TODO Auto-generated constructor stub
	}
	
	public Developer(String dev_name, String dev_info) {
		this.dev_name = dev_name;
		this.dev_info = dev_info;
	}

	public int getDev_id() {
		return dev_id;
	}

	public void setDev_id(int dev_id) {
		this.dev_id = dev_id;
	}

	public String getDev_name() {
		return dev_name;
	}

	public void setDev_name(String dev_name) {
		this.dev_name = dev_name;
	}

	public String getDev_info() {
		return dev_info;
	}

	public void setDev_info(String dev_info) {
		this.dev_info = dev_info;
	}
	
	public List<Videogame> getVideogames() {
		return videogames;
	}

	public void setVideogames(List<Videogame> videogames) {
		this.videogames = videogames;
	}
	
	@PreRemove
	private void removeVideogameAssociation() {
		for (Videogame videogame : videogames) {
			videogame.getDevelopers().remove(this);
		}
	}
	
}
