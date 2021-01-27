package com.newsum.courses.course_ideas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< Updated upstream
=======
import com.newsum.courses.model.CourseIdea;
import com.newsum.courses.model.CourseIdeaDAO;
import com.newsum.courses.model.SimpleCourseIdeaDAO;

>>>>>>> Stashed changes
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
<<<<<<< Updated upstream
=======
    	Spark.staticFileLocation("public"); //tells web server to look in this directory if static files not found
    	CourseIdeaDAO courseIdeaDAO = new SimpleCourseIdeaDAO();
    	
    	Spark.before((req, res) -> {
    		if (req.cookie("username") != null)
    		{
    			req.attribute(req.cookie("username"));
    		}
    	});
    	
    	Spark.before("/ideas", (req, res) -> {
    		if (req.cookie("username") == null)
    		{
    			res.redirect("/");
    			Spark.halt();
    		}
    	});
    	
>>>>>>> Stashed changes
    	Spark.get("/", (req, res) -> {
							    		Map<String,String> model = new HashMap<>();
								    	String userName = req.attribute("username");
										model.put("username", userName);
    									return new ModelAndView(model,"index.hbs");
    								}, new HandlebarsTemplateEngine());
    	Spark.post("/sign-in", (req, res) -> {
												res.cookie("username", req.queryParams("username"));
    											res.redirect("/");
    											return null;
    										});
    	
    	Spark.get("/ideas", (req, res) -> {
    		Map<String,List<CourseIdea>> model = new HashMap<>();
			model.put("ideas", courseIdeaDAO.findAll());
			return new ModelAndView(model,"ideas.hbs");
			}, new HandlebarsTemplateEngine());
    	
    	Spark.post("/ideas", (req, res) -> {
    		String title = req.queryParams("title");
    		CourseIdea idea = new CourseIdea(title,req.attribute("username"));
    		courseIdeaDAO.add(idea);
    		res.redirect("/ideas");
			return null;
			});
    }
}
