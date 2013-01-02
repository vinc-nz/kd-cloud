package com.kdcloud.ext.rehab.db;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class UserScheduling {
	
	@Id
	Long id;
	Key<RehabUser> user;
	Date startDate;
	Date endDate;
	Key<CompleteExercise> exercise;
	
	public UserScheduling() {}

	public Key<RehabUser> getUser() {
		return user;
	}

	public void setUser(Key<RehabUser> user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Key<CompleteExercise> getExercise() {
		return exercise;
	}

	public void setExercise(Key<CompleteExercise> exercise) {
		this.exercise = exercise;
	}
	
	
	
    

}
