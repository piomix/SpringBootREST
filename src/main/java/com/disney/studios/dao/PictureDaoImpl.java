package com.disney.studios.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.disney.studios.model.Picture;
import java.util.stream.Collectors;


@Repository
public class PictureDaoImpl implements PictureDao {
	
	
	private static final String SQL_INSERT = "insert into pictures (url, favorited, breed, name) values (:url,:favorited,:breed,:name)";
	private static final String SQL_VOTE_UP = "update pictures set favorited=favorited+1 where id=:id";
	private static final String SQL_VOTE_DN = "update pictures set favorited=favorited-1 where id=:id";
	

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public PictureDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = jdbcTemplate;
	}

	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	@Override
	public List<Picture> findByBreed(String breed){
	
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("breed", breed);
        
		String sql = "SELECT * FROM Pictures WHERE breed=:breed ORDER BY favorited DESC";
		
        List<Picture> result = namedParameterJdbcTemplate.query(
                    sql,
                    params,
                    new PictureMapper());
        return result;
	}
	
	/**
     * Group all Pictures by breed
	 * @return List
     * @throws Exception In case things go horribly, horribly wrong.
     */
	@Override
	public Map<String,List<Picture>> groupByBreed(){
	
		List<Picture> pictures = this.findAll();
		
		Map<String, List<Picture>> picturesByBreed = pictures
				.stream()
				.collect(Collectors.groupingBy(Picture::getBreed));
		
		return picturesByBreed;
	}
	
	@Override
	public List<Picture> findByName(String name) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        
		String sql = "SELECT * FROM Pictures WHERE name=:name ORDER BY favorited DESC";
		
        List<Picture> result = namedParameterJdbcTemplate.query(
                    sql,
                    params,
                    new PictureMapper());
        return result;
        
	}
	
	@Override
	public Picture findById(long id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        
		String sql = "SELECT * FROM Pictures WHERE id=:id";
		
        List<Picture> result = namedParameterJdbcTemplate.query(
                    sql,
                    params,
                    new PictureMapper());
        return result
			.stream()
			.findFirst()
			.orElse(null);
	}

	/**
     * Return all of the pictures
	 * @return List<Picture>
     * @throws Exception In case things go horribly, horribly wrong.
     */
	@Override
	public List<Picture> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM pictures ORDER BY favorited DESC";
		
        List<Picture> result = namedParameterJdbcTemplate.query(sql,
			params,
			new PictureMapper());
        
        return result;
        
	}
	
	/**
     * Load record into the database to the data store.
     * @param Picture
	 * @return pictureId
     * @throws Exception In case things go horribly, horribly wrong.
     */
	 public void insertPicture(Picture pic){
		
		Map<String, Object> args = new HashMap<String, Object>();
			args.put("url", pic.getUrl());
			args.put("favorited", pic.getFavorited());
			args.put("name", pic.getName()); 
			args.put("breed", pic.getBreed());
			
		namedParameterJdbcTemplate.update(SQL_INSERT, args);
	
	}
	
	/**
     * Load record into the database to the data store.
     * @param Picture
	 * @param String op "up" "dn"
     * @throws Exception In case things go horribly, horribly wrong.
     */
	 public void vote(Picture pic, String op){
		
		//TODO: Validate input values specially over PIC.ID 
		
		if (op.equals("up")){
		
		Map<String, Object> args = new HashMap<String, Object>();
			args.put("id", pic.getId());
		namedParameterJdbcTemplate.update(SQL_VOTE_UP, args);
		}
		
		if(op.equals("dn")){
		
		Map<String, Object> args = new HashMap<String, Object>();
			args.put("id", pic.getId());
		namedParameterJdbcTemplate.update(SQL_VOTE_DN, args);
		}
	}

	private static final class PictureMapper implements RowMapper<Picture> {

		public Picture mapRow(ResultSet rs, int rowNum) throws SQLException {
			Picture pic = new Picture();
			pic.setId(rs.getInt("id"));
			pic.setFavorited(rs.getInt("favorited"));
			pic.setName(rs.getString("name"));
			pic.setBreed(rs.getString("breed"));
			pic.setUrl(rs.getString("url"));
			return pic;
		}
	}

}