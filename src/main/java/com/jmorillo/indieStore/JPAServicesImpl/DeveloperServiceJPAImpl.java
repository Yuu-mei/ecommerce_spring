package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.model.Developer;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.DeveloperService;

@Service
@Transactional
public class DeveloperServiceJPAImpl implements DeveloperService{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Developer> obtainAllDevelopers() {
		return entityManager.createQuery("select d from Developer d").getResultList();
	}

	@Override
	public Developer obtainDeveloperById(int dev_id) {
		Query query = entityManager.createQuery("select d from Developer d where d.dev_id = :id");
		query.setParameter("id", dev_id);
		return (Developer) query.getSingleResult();
	}

	@Override
	public void editDeveloper(Developer d) {
		entityManager.merge(d);
	}

	@Override
	public void deleteDeveloper(int dev_id) {
		Developer d = entityManager.find(Developer.class, dev_id);
		List<Videogame> videogames = d.getVideogames();
		// Let's just give a default dev if they only had one dev to avoid issues
		for (Videogame videogame : videogames) {
			if(videogame.getDevelopers().contains(d) && videogame.getDevelopers().size() <= 1){
				Developer default_dev = null;
				try {
					default_dev = (Developer) entityManager.createQuery("select dev from Developer dev where dev.dev_name = :dev_name").setParameter("dev_name", "Unknown Developer").getSingleResult();
				}catch(NoResultException e) {
					default_dev = new Developer("Unknown Developer", "Undefined developer, must be changed ASAP");
					entityManager.persist(default_dev);
				}
				videogame.getDevelopers().add(default_dev);
			}
		}
		entityManager.remove(d);
	}

	@Override
	public void createDeveloper(Developer d) {
		entityManager.persist(d);
	}

}
