package com.uniovi.services;

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

	public Page<Publication> getMarks(Pageable pageable) {
		Page<Publication> publication = publicationsRepository.findAll(pageable);
		return publication;
	}

	public Publication getPublication(Long id) {
		return publicationsRepository.findById(id).get();
	}

	public void addPublication(Publication publication) {
		publicationsRepository.save(publication);
	}

	public void deletePublication(Long id) {
		publicationsRepository.deleteById(id);
	}

	public Page<Publication> getPublicationsForUser(Pageable pageable, User user) {
		return publicationsRepository.findAllByUser(pageable, user);
	}
}
