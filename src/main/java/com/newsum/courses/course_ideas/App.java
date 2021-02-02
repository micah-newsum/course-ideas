package com.newsum.courses.course_ideas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newsum.courses.model.CourseIdea;
import com.newsum.courses.model.CourseIdeaDAO;
import com.newsum.courses.model.NotFoundException;
import com.newsum.courses.model.SimpleCourseIdeaDAO;

import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class App 
{
    private static final String FLASH_MESSAGE_KEY = "flash_message";

	public static void main( String[] args )
    {
    	Spark.staticFileLocation("public"); //tells web server to look in this directory if static files not found
    	CourseIdeaDAO courseIdeaDAO = new SimpleCourseIdeaDAO();
    	
    	Spark.before((req, res) -> {
    		if (req.cookie("username") != null)
    		{
    			req.attribute("username", req.cookie("username"));
    		}
    	});
    	
    	Spark.before("/ideas", (req, res) -> {
    		if (req.cookie("username") == null)
    		{
    			setFlashMessage(req, "Whoops, please sign in first!");
    			res.redirect("/");
    			Spark.halt();
    		}
    	});
    	
    	Spark.get("/", (req, res) -> {
							    		Map<String,String> model = new HashMap<>();
								    	String userName = req.attribute("username");
										model.put("username", userName);
										model.put("flashMessage",captureFlashMessage(req));
    									return new ModelAndView(model,"index.hbs");
    								}, new HandlebarsTemplateEngine());
    	Spark.post("/sign-in", (req, res) -> {
												res.cookie("username", req.queryParams("username"));
    											res.redirect("/");
    											return null;
    										});
    	
    	Spark.get("/ideas", (req, res) -> {
    		Map<String,Object> model = new HashMap<>();
			model.put("ideas", courseIdeaDAO.findAll());
			model.put("flashMessage", captureFlashMessage(req));
			return new ModelAndView(model,"ideas.hbs");
			}, new HandlebarsTemplateEngine());
    	
    	Spark.post("/ideas", (req, res) -> {
    		String title = req.queryParams("title");
    		CourseIdea idea = new CourseIdea(title,req.attribute("username"));
    		courseIdeaDAO.add(idea);
    		res.redirect("/ideas");
			return null;
			});
    	
    	Spark.post("/ideas/:slug/vote", (req, res) -> {    		
    		CourseIdea idea = courseIdeaDAO.findBySlug(req.params("slug"));
    		String username = req.attribute("username");
    		boolean added = idea.addVoter(req.attribute("username"));
    		if (added) {
    			setFlashMessage(req,"Thanks for your vote!");
    		} else {
    			setFlashMessage(req,"You already voted!");
    		}
    		res.redirect("/ideas");
			return null;
			});
    	
      Spark.get("idea/:slug", (req, res) -> {
										    		Map<String, Object> model = new HashMap<>();
										    		model.put("idea", courseIdeaDAO.findBySlug(req.params("slug")));
										    		return new ModelAndView(model,"idea.hbs");
    										   }, new HandlebarsTemplateEngine());
      
    	//catches 404 exception
    	Spark.exception(NotFoundException.class, (exc, req, res) -> {
    		res.status(404);
    		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    		String html = engine.render(new ModelAndView(null,"not-found.hbs"));
    		res.body(html);
    	});
    }

	private static void setFlashMessage(Request req, String message) {
		req.session().attribute(FLASH_MESSAGE_KEY,message);
	}
	
	private static String getFlashMessage(Request req) {
		if (req.session(false) == null) {
			return null;
		}
		if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
			return null;
		}
		return (String) req.session().attribute(FLASH_MESSAGE_KEY);
	}
	
	private static String captureFlashMessage(Request req) {
		String message = getFlashMessage(req);
		if (message != null) {
			req.session().removeAttribute(FLASH_MESSAGE_KEY);
		}
		return message;
	}
}
