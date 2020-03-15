package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	@Autowired
	private PublicationsRepository publicationsRepository;

	private static final Logger logger = LoggerFactory.getLogger(PublicationsService.class);

	/**
	 * Gets a publication with the id
	 * @param id
	 * @return
	 */
	public Publication getPublication(Long id) {
		logger.info("Retrieved the publication with id " + id);
		return publicationsRepository.findById(id).get();
	}

	/**
	 * Adds a publication to the database
	 * @param publication
	 */
	public void addPublication(Publication publication) {
		publicationsRepository.save(publication);
		logger.info("Created publication with id " + publication.getId());
	}

	/**
	 * Deletes a publication with the id
	 * @param id
	 */
	public void deletePublication(Long id) {
		publicationsRepository.deleteById(id);
		logger.info("Deleted publication with id " + id);
	}

	/**
	 * Gets all the publications from a user
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<Publication> getPublicationsForUser(Pageable pageable, User user) {
		logger.info("The user with the email " + user.getEmail() + " has listed their publications");
		return publicationsRepository.findAllByUser(pageable, user);
	}
}
