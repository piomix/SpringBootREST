package com.disney.studios.controller;

import com.disney.studios.dao.PictureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.disney.studios.model.Picture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@RestController
public class PetService {

    @Autowired
    private PictureDao picturesRepository;
	
	/**
     * GET Pictures By Name mapping
     * @param Name
	 * @return ResponseEntity
     * @throws Exception In case something goes wrong while we load the breeds.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dogpictures/name/{petName}")
    public ResponseEntity readPictureByName(@PathVariable String petName) {

	List pictures = this.picturesRepository.findByName(petName);
		if (pictures == null) {
			return new ResponseEntity("No Pictures found for the name " + petName, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(pictures, HttpStatus.OK);
    }
	
	/**
     * GET Pictures By Breed mapping
     * @param Breed
	 * @return ResponseEntity
     * @throws Exception In case something goes wrong while we load the breeds.
     */
	@RequestMapping(method = RequestMethod.GET, value = "/dogpictures/breed/{petBreed}")
    public ResponseEntity readPictureByBreed(@PathVariable String petBreed) {

	List pictures = this.picturesRepository.findByBreed(petBreed);
		if (pictures == null) {
			return new ResponseEntity("No Pictures found for the breed " + petBreed, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(pictures, HttpStatus.OK);
    }
	/**
     * GET Pictures ALL Pictures
     * @return List
     * @throws Exception In case something goes wrong while we load the breeds.
     */
	@GetMapping("/dogpictures")
    public List readPictures() {
        return this.picturesRepository.findAll();
    }
	
	/**
     * GET Pictures ordered by Breed
     * @return Map
     * @throws Exception In case something goes wrong while we load the breeds.
     */
	@GetMapping("/dogpictures/breed/grouped")
    public Map groupedPictures() {
        return this.picturesRepository.groupByBreed();
    }
	
	/**
     * GET Picture By ID
     * @return ResponseEntity
     * @throws Exception In case something goes wrong while we load the breeds.
     */
	@GetMapping("/dogpictures/id/{id}")
    public ResponseEntity readPictureById(@PathVariable long id) {
		
		Picture picture = this.picturesRepository.findById(id);
		if (picture == null) {
			return new ResponseEntity("No Picture found for the Id " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(picture, HttpStatus.OK);
    }
	
	/**
     * PUT Vote capability 
     * @param PictureId
	 * @param Type of vote (up, dn)
	 * @return ResponseEntity
     * @throws Exception In case something goes wrong while we load the breeds.
     */
	@RequestMapping(method = RequestMethod.PUT, value = "/dogpictures/voting/petId/{id}/vote/{op}")
    public ResponseEntity votePicture(@PathVariable("id") long id, @PathVariable("op") String op) {
		
		// TODO: Validate duplicated votes
		if (op.equals("up") || op.equals("dn")){ 
			Picture picture = this.picturesRepository.findById(id);
			if (picture == null) {
				return new ResponseEntity("No Picture found for the Id " + id, HttpStatus.NOT_FOUND);
				}
				this.picturesRepository.vote(picture, op);
				return new ResponseEntity(picture, HttpStatus.OK); }
		else {
			return new ResponseEntity("Not a valid vote: " + op, HttpStatus.NOT_IMPLEMENTED);
		}
	}
}
