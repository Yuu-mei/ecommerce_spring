package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.Tag;

public interface TagService {
	List<Tag> obtainAllTags();
	Tag obtainTagById(int tag_id);
	void editTag(Tag t);
	void deleteTag(int tag_id);
	void createTag(Tag t);
}
