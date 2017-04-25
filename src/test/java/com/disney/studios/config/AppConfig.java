package com.disney.studios.config;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.disney.studios.dao.PictureDaoImpl;
import com.disney.studios.dao.PictureDao;
import com.disney.studios.PetLoader;

@ComponentScan({ "com.disney" , "com.disney.studios" })
@Configuration
public class AppConfig {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	PetLoader petLoader;

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Bean
	public PictureDao picturesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		return new PictureDaoImpl(jdbcTemplate);
  }
	
	/*@PostConstruct
	public void startDBManager() {
		
		//hsqldb
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });
	 } */

}