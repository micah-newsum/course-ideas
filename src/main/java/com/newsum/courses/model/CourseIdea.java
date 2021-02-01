package com.newsum.courses.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.slugify.Slugify;

public class CourseIdea 
{
	private String title;
	private String username;
	private String slug;
	private Set<String> voters;
	
	public CourseIdea(String title, String username)
	{
		this.voters = new HashSet<>();
		this.title = title;
		this.username = username;
		try {
			Slugify slugify = new Slugify();
			slug = slugify.slugify(title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addVoter(String voterUsername)
	{
		return voters.add(voterUsername);
	}
	
	public int getVoteCount()
	{
		return voters.size();
	}

	public String getTitle() {
		return title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getSlug() {
		return slug;
	}
	
	public List<String> getVoters(){
		return new ArrayList<>(voters);
	}
}
