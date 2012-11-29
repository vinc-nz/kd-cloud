package com.kdcloud.ext.rehab.db;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class DualModeSession {

	@Id
	Long id;
	Key<RehabUser> rehabuser;
	Key<CompleteExercise> exercise;
	Date startdate;

	public DualModeSession() {
	}

	public Key<CompleteExercise> getExercise() {
		return exercise;
	}

	public void setExercise(Key<CompleteExercise> exercise) {
		this.exercise = exercise;
	}

	public void setStartDate(Date startDate) {
		this.startdate = startDate;
	}

	public Key<RehabUser> getRehabUser() {
		return rehabuser;
	}

	public void setRehabUser(Key<RehabUser> rehabUser) {
		this.rehabuser = rehabUser;
	}

	public Date getStartDate() {
		return startdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((startdate == null) ? 0 : startdate.hashCode());
		result = prime * result
				+ ((exercise == null) ? 0 : exercise.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((rehabuser == null) ? 0 : rehabuser.hashCode());
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
		DualModeSession other = (DualModeSession) obj;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (exercise == null) {
			if (other.exercise != null)
				return false;
		} else if (!exercise.equals(other.exercise))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rehabuser == null) {
			if (other.rehabuser != null)
				return false;
		} else if (!rehabuser.equals(other.rehabuser))
			return false;
		return true;
	}



}