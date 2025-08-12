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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
            <div class = 'indexcontainer'>
                <div class = 'missionimage'>
                    <strong class = 'welcome'>
                        Our Mission
                    </strong> 
                </div>   
            </div>
        """;


        html = html + "<div class='flexbox-container flexbox-mission'>";
        html = html + """
            
            <p>
                <pre class = 'missiontext'>
                <center>
                Our mission is to illuminate the pathways of knowledge and insight. We focus on the societal, economic, and cultural disparities and connections between Indigenous and Non-Indigenous Australians. 
                Utilizing our dedicated web application, we aim to present a comprehensive, respectful, and unbiased exploration of data from the 2016 and 2021 Australian Censuses. 
                Our areas of focus include key aspects such as health, education, employment, housing, and incarceration.

                We are committed to offering our users, voters, educators, policymakers, students, and citizens an accessible platform providing high-level summaries and in-depth, analytical content. 
                Our tools and content are tailored for a diverse audience, enabling an informed, engaged, and empathetic understanding of both communities. In supporting national dialogue and decision-making, 
                especially regarding the Voice to Parliament Referendum, we enhance public knowledge and empathy.
                
                Our vision is to foster a well-informed public, equipped with factual, comprehensive, and richly contextual information. This initiative contributes to a more inclusive, equitable, and understanding
                Australia. Through 'Bridge of Understanding', we start a journey of enlightenment, empathy, and empowerment, aiding in shaping a future where every voice is valued in Australia's continuous 
                quest for unity and progress."
                </center>
                </pre>
            </p>
                """;
        html = html + "</div>";

        // PRESENT THE PERSONAS 
        // RETRIEVE PERSONAS FROM THE DATABASE
        JDBCConnection jdbc = new JDBCConnection();
        

        // DISPLAY THE PERSONAS FROM THE DATABASE
        ArrayList<String> paths = jdbc.getImagePath();
        ArrayList<String> personaDetails = jdbc.getPersonaDetails();

        html = html + """
            <div class='header'>
                <h1>Targeted Personas </h1>
            </div>
        """;


        html = html + "<div class='flexbox-container flexbox-persona1'>";
        // html = html + "<div class = 'flexbox-item flexbox-persona'>";
        html = html + "<div class = 'flexbox-item flexbox-image'>";
        html = html + "<h1>" + personaDetails.get(0) + "</h1>";
        html = html + "<img src='" + paths.get(0) + "' / style = width:400px;height:400px;>";
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Description>'";
        html = html + """
            <br>
            <br>
            <b>Description:</b>
            <br>   
                """;
        html = html + personaDetails.get(1);
        html = html + "</div>";
        
        html = html + "<div class = 'flexbox-item flexbox-Needs>'";
        html = html + """
            <br>
            <br>
            <b>Needs:</b>
            <br>   
                """;
        html = html + personaDetails.get(2);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Goals>'";
        html = html + """
            <br>
            <br>
            <b>Goals:</b>
            <br>   
                """;
        html = html + personaDetails.get(3);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-SkillsExperience>'";
        html = html + """
            <br>
            <br>
            <b>Skills and Experience:</b>
            <br>   
                """;
        html = html + personaDetails.get(4);
        html = html + "</div>";

        html = html + "</div>";

        


        html = html + "<div class='flexbox-container flexbox-persona2'>";

        html = html + "<div class = 'flexbox-item flexbox-image'>";
        html = html + "<h1>" + personaDetails.get(5) + "</h1>";
        html = html + "<img src='" + paths.get(1) + "' / style = width:400px;height:400px;>";
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Description>'";
        html = html + """
                <br>
                <br>
                <b>Description:</b>
                <br>
                """;
        html = html + personaDetails.get(6);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Needs>'";
        html = html + """
                <br>
                <br>
                <b>Needs:</b>
                <br>
                """;
        html = html + personaDetails.get(7);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Goals>'";
        html = html + """
                <br>
                <br>
                <b>Goals:</b>
                <br>
                """;
        html = html + personaDetails.get(8);
        html = html + "</div";

        html = html + "<div class = 'flexbox-item flexbox-SkillsExperience>'";
        html = html + """
                <br>
                <br>
                <b>Skills and Experience:</b>
                <br>
                """;
        html = html + personaDetails.get(9);
        html = html + "</div>";
    
        html = html + "</div>";


        html = html + "<div class='flexbox-container flexbox-persona3'>";

        html = html + "<div class = 'flexbox-item flexbox-image'>";
        html = html + "<h1>" + personaDetails.get(10) + "</h1>";
        html = html + "<img src='" + paths.get(2) + "' / style = width:400px;height:400px;>";
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Description'>";
        html = html + """
            <br>
            <br>
            <b>Description:</b>
            <br>
            """;
        html = html + personaDetails.get(11);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Needs'>";
        html = html + """
            <br>
            <br>
            <b>Needs:</b>
            <br>
            """;
        html = html + personaDetails.get(12);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-Goals'>";
        html = html + """
            <br>
            <br>
            <b>Goals:</b>
            <br>
            """;
        html = html + personaDetails.get(13);
        html = html + "</div>";

        html = html + "<div class = 'flexbox-item flexbox-SkillsExperience'>";
        html = html + """
            <br>
            <br>
            <b>Skills and Experience:</b>
            <br>
            """;
        html = html + personaDetails.get(14);
        html = html + "</div>";
        
        html = html + "</div>";

        // STUDENTS
        html = html + "<div class='flexbox-container flexbox-students'>";
        html = html + "<b>Collin Ng (s3901105@student.rmit.edu.au), Mohammed Muneer (s4029181@student.rmit.edu.au)</b>";
        html = html + "</div>";



        // Add Div for page Content
        html = html + "<div class='content'>";

        // Close Content div
        html = html + "</div>";

        // Add Div for page Content
        html = html + "<div class='content'>";

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