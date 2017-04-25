package com.disney.studios.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.disney.studios.model.Picture;


public class PictureDaoTest {

    private EmbeddedDatabase db;
    PictureDao picDao;

    @Before
    public void setUp() {
        
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("classpath:db/sql/schema.sql") 
    		.build();
    }
	
    @Test
    public void testFindByname() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	PictureDaoImpl picDao = new PictureDaoImpl(template);
		
		Picture pic = new Picture(1, 1, "myurl", "mybreed1", "myname");
		Picture pic2 = new Picture(2, 2, "myurl", "mybreed", "myname");
		Picture pic3 = new Picture(3, 3, "myurl", "mybreed", "myname");
		
		picDao.insertPicture(pic);
		picDao.insertPicture(pic2);
		picDao.insertPicture(pic3);

		
    	//Print All the Pictures in the DB
		picDao.findAll()
		.stream()
		.forEach( x -> System.out.println(x.toString()));
		//Retrieve by name
		picDao.findByName("myname")
		.stream()
		.forEach( x -> System.out.println(x.toString()));
		
		//VoteUp
		picDao.vote(pic2, "up");
		
		picDao.findAll()
		.stream()
		.forEach( x -> System.out.println(x.toString()));
		
		//VoteDown
		picDao.vote(pic2, "dn");
		
		
		picDao.findAll()
		.stream()
		.forEach( x -> System.out.println(x.toString()));
		
		System.out.println("findById::: " + picDao.findById(1).toString());
		//System.out.println("findById::: " + picDao.findById(-1).toString());
		
		System.out.println("findByBreed:::");
		
		 picDao.findByBreed("mybreed")
		.stream()
		.forEach( x -> System.out.println(x.toString()));
		
		
		//Print the List<Pictures> per Breed
		picDao.groupByBreed()
			.forEach((key, value) -> { System.out.println("Map::: Breed : " + key + " List<Pictures> Object : " + value);});
		
    	Assert.assertNotNull(picDao.findAll());

    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}