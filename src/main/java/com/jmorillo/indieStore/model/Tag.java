package com.jmorillo.indieStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jmorillo.indieStore.validationsConstants.ValidationsConstants;

@Entity
public class Tag {
	@Id
	@GeneratedValue
	private int id;
	
	//Longest I can think of is 'simulation' and that's 10 chars
	@Pattern(regexp = ValidationsConstants.REGEXP_TAG_NAME, message = "Name must be between 3-15 chars and can contain letters, spaces and -")
	@NotEmpty
	@Size(min=3, max=15)
	private String name;
	
	@NotEmpty
	@Size(min=50, message = "Description must be at least 50 chars long")
	private String description;
	
	public Tag() {
		
	}

	public Tag(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
