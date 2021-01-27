package com.newsum.courses.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleCourseIdeaDAO implements CourseIdeaDAO
{
	private List<CourseIdea> ideas;
	
	public SimpleCourseIdeaDAO()
	{
		ideas = new ArrayList<>();
	}

	@Override
	public List<CourseIdea> findAll() {
		return new ArrayList<>(ideas);
	}

	@Override
	public boolean add(CourseIdea idea) {
		return ideas.add(idea);
	}
	
}