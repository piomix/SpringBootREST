package com.disney.studios.config;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.context.annotation.*;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.disney.studios.dao.PictureDaoImpl;
import com.disney.studios.dao.PictureDao;
import com.disney.studios.PetLoader;

//@ComponentScan({ "com.disney" , "com.disney.studios" })
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringRootConfig {

	//jdbc:hsqldb:mem:testdb

	@Bean
    DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .addScript("classpath:db/sql/schema.sql")
      .build();
    }
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Bean
	public PictureDao picturesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		return new PictureDaoImpl(jdbcTemplate);

	/*@PostConstruct
 	*/
	}
  }