package com.disney.studios.model;

public class Picture {

	private long id, favorited;
	private String url, breed, name;

	public Picture(){};
	public Picture(long id, long favorited, String url, String breed, String name){
		
		this.id = id;
		this.favorited = favorited;
		this.url = url;
		this.breed = breed;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getFavorited() {
		return favorited;
	}

	public void setFavorited(long favorited) {
		this.favorited = favorited;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Picture [id=" + id + ", url=" + url + ", favorited=" + favorited + ", breed=" + breed + ", name=" + name +"]";
	}
}