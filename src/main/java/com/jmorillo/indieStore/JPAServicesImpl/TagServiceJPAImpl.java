package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.model.Tag;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.TagService;
import com.jmorillo.indieStore.services.VideogameService;

@Service
@Transactional
public class TagServiceJPAImpl implements TagService{
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	VideogameService videogameService;

	@Override
	public List<Tag> obtainAllTags() {
		return entityManager.createQuery("select t from Tag t").getResultList();
	}

	@Override
	public Tag obtainTagById(int tag_id) {
		Query query = entityManager.createQuery("select t from Tag t where t.id = :id");
		query.setParameter("id", tag_id);
		return (Tag) query.getSingleResult();
	}

	@Override
	public void editTag(Tag t) {
		entityManager.merge(t);
	}

	@Override
	public void deleteTag(int tag_id) {
		Tag t = (Tag) entityManager.find(Tag.class, tag_id);
		// Sanity check so it doesn't complain about the removal of the tag from a videogame
		List<Videogame> videogames = videogameService.obtainAllVideogames();
		for (Videogame videogame : videogames) {
			if(videogame.getTag().getId() == tag_id) {
				// Let's give it another tag as it can only have one at a time and we pray it doesn't have any issues finding it because someone deleted it
				Tag default_tag = null;
				try {
					default_tag = (Tag) entityManager.createQuery("select t from Tag t where t.name = :tag_name").setParameter("tag_name", "Unknown").getSingleResult();
				}catch(NoResultException e) {
					// Worst case scenario we create the tag and add it again
					default_tag = new Tag("Unknown", "Undefined tag, must be changed ASAP");
					entityManager.persist(default_tag);
				}
				videogame.setTag(default_tag);
				entityManager.merge(videogame);
			}
		}
		entityManager.remove(t);
	}

	@Override
	public void createTag(Tag t) {
		entityManager.persist(t);
	}

}
