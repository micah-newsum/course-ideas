package com.newsum.courses.course_ideas;

import java.util.HashMap;
import java.util.Map;

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
    	Spark.get("/", (req, res) -> {
							    		Map<String,String> model = new HashMap<>();
								    	String userName = req.cookie("username");
										model.put("username", userName);
    									return new ModelAndView(model,"index.hbs");
    								}, new HandlebarsTemplateEngine());
    	Spark.post("/sign-in", (req, res) -> {
									    		Map<String,String> model = new HashMap<>();
										    	String userName = req.queryParams("username");
										    	res.cookie("username", userName);
												model.put("username", userName);
    											return new ModelAndView(model,"sign-in.hbs");
    										}, new HandlebarsTemplateEngine());
    }
}
