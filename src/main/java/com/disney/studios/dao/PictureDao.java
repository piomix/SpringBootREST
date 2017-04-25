package com.disney.studios.dao;

import java.util.List;
import java.util.Map;
import com.disney.studios.model.Picture;

public interface PictureDao {
	
	//TODOs
	Picture findById(long id);

	List<Picture> findByName(String name);
	
	void insertPicture(Picture pic);
	
	List<Picture> findByBreed(String breed);
	
	Map<String,List<Picture>> groupByBreed();
	
	void vote(Picture pic, String op);
	
	List<Picture> findAll();

}