package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page6.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>          
                <a href='/'><img src = 'logo.png' height = 20px></a>
                <a href='mission.html'><strong>Mission</strong></a>
                <a href='page3.html'><strong>Census Results Analysis</strong></a>
                <a href='page4.html'><strong>Focused View by LGA/State</strong></a>
                <a href='page5.html'><strong>Gap-Scores</strong></a>
                <a href='page6.html'><strong>LGA's With Similar Characteristics</strong></a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>LGAs With Similar Characteristics</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // PRESENT INFORMATION ON LGAS
        html = html + "<div class='flexbox-container flexbox-lga'>";
        html = html + """
            <form action="page6.html" method="post">
            <input type = 'text' name = "lga2" id = "lga2" placeholder = "Type Lga Here"><br>
                <select name = "categorydrop" id = "categorydrop">
                    <option value = "populationstatistics">Population</option>
                    <option value = "healthissues">Health Issues</option>
                    <option value = "education">Education</option>
                    <option value = "weeklyincome">Weekly Income </option> 
                </select>
                <button type='submit' class='btn btn-primary' style='margin-top: 10px;'>Submit</button>
                """;


        JDBCConnection jdbc = new JDBCConnection();
        
        String user_lga2 = context.formParam("lga2");


        // Finish the List HTML
        html = html + "</ul>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
