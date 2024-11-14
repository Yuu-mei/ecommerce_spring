package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.Developer;

public interface DeveloperService {
	List<Developer> obtainAllDevelopers();
	Developer obtainDeveloperById(int dev_id);
	void editDeveloper(Developer d);
	void deleteDeveloper(int dev_id);
	void createDeveloper(Developer d);
}
