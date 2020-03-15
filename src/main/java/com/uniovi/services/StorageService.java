package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.DBFile;
import com.uniovi.repositories.DBFileRepository;

import java.io.IOException;

@Service
public class StorageService {
	@Autowired
	private DBFileRepository dbFileRepository;

	private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

	/**
	 * Stores the MultipartFile converting it to a DBFile
	 * @param file
	 * @return
	 */
	public DBFile storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				logger.error("File with invalid path sequence not stored: " + fileName);
				throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
			logger.info("Stored file " + dbFile.getId());
			return dbFileRepository.save(dbFile);
		} catch (IOException ex) {
			logger.error("Error storing file " +fileName);
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * Returns the file form the DB
	 * @param fileId
	 * @return
	 */
	public DBFile getFile(String fileId) {
		logger.info("Retrieved file " + fileId);
		return dbFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
	}
}
