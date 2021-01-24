package com.newsum.courses.course_ideas;

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
    	Spark.get("/", (req, res) -> {return new ModelAndView(null,"index.hbs");}, new HandlebarsTemplateEngine());
    	
    	Spark.post("sign-in", (req, res) -> {return new ModelAndView(null,"sign-in.hbs");}, new HandlebarsTemplateEngine());
    }
}
