package com.uniovi.entities;

import javax.persistence.*;

/**
 * Friendship entity, it stores the user who requested the friendship and the user who received the friensdhip.
 * It can be pending to be accepted or already accepted, this state is captured with the pending attribute.
 * It is created by the requester, and accepted by the requested
 */

@Entity
public class Friendship {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User requester;					// The user sending the friendship requested
	
	@ManyToOne
	private User requested;					// The user receiving the frienship request
	
	private boolean pending;
	
	//-------------------------------------------------------------------------------------------
	
	public Friendship() {
		this.pending = true;
	}
	
	public Friendship(User requester, User requested) {
		this();
		setRequester(requester);
		setRequested(requested);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public User getRequested() {
		return requested;
	}

	public void setRequested(User requested) {
		this.requested = requested;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requested == null) ? 0 : requested.hashCode());
		result = prime * result + ((requester == null) ? 0 : requester.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Friendship other = (Friendship) obj;
		if (requested == null) {
			if (other.requested != null)
				return false;
		} else if (!requested.equals(other.requested))
			return false;
		if (requester == null) {
			if (other.requester != null)
				return false;
		} else if (!requester.equals(other.requester))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Friendship [requester=" + requester + ", requested=" + requested + ", pending=" + pending
				+ "]";
	}
	
}