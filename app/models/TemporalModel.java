package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@MappedSuperclass
public class TemporalModel extends Model {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	protected Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	protected Date updated;

	@PrePersist
	protected void onCreate() {
		updated = created = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}
}