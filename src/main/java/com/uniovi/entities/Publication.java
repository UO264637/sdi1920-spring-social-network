package com.uniovi.entities;

import java.io.InputStream;
import java.util.Date;

import javax.persistence.*;

import org.apache.tomcat.util.codec.binary.Base64;

@Entity
public class Publication {
	@Id
	@GeneratedValue
	private Long id;
	private String text;
	private String title;
	private Date date;
	@OneToOne
    @JoinColumn(name = "FK_IMAGE")
	private DBFile image;
	
	@Transient
	private InputStream is;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Publication() {
	}

	public Publication(String title, String text, User user) {
		super();
		this.text = text;
		this.title = title;
		this.date = new Date();
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public DBFile getImage() {
		return image;
	}

	public void setImage(DBFile image) {
		this.image = image;
	}
	
	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	@Override
	public String toString() {
		return "Publication [id=" + id + ", text=" + text + ", title=" + title + ", date=" + date + ", user=" + user
				+ "]";
	}
	
	/**
	 * Transforms the image to Base64 to be displayed
	 * @return image in base64
	 */
	public String generateBase64Image() {
	    return Base64.encodeBase64String(image.getData());
	}
	
	/**
	 * @return true if the post has image
	 */
	public boolean hasImage() {
		return image != null;
	}

}
