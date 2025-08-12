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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

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
        // Add Div for page Content
        html = html + "<div class='content'>";
        // Add HTML for the list of pages (as well as topnav)
        html = html + """
            <div class = 'indexcontainer'>
                <div class = 'homeimage'>
                    <strong class = 'welcome'>
                        WOMINJEKA
                    </strong> 
                </div>   
            </div>
            <table class = 'home' cellspacing = '10'>
                <tr>
                    <td name = 'layout'>
                        <div class = 'img1'>
                            <img src = 'Aboriginal-Art-1.jpg' class = 'HI1' alt = 'Image1' height = '250'>
                        </div>
                    </td>
                    <td name = 'layout'>
                        <div class = 'body1'>
                            Welcome to our insightful platform addressing the upcoming Australian Referendum on "The Voice to Parliament," scheduled for October 14, 2023. A significant discussion point for this event is understanding the disparities between Indigenous and Non-Indigenous Australians in critical domains like health, education, employment, housing, and incarceration. In the backdrop of this pivotal national decision, evaluating whether the establishment of "The Voice to Parliament" will effectively advance the interests and progress of Indigenous communities is of paramount importance.
                        </div>
                    </td>
            </table>
            <table cellspacing = '10'>
                <tr>
                    <td name = 'layout'>
                        <div class = 'body2'>
                            In response to this need, our web-application has been meticulously designed to guide voters and the wider public in analyzing unbiased, factual information regarding these disparities. Utilizing data from the latest Australian Censuses (2016 and 2021), our platform presents detailed statistics and in-depth analyses, covering the variances in areas critical to Indigenous and Non-Indigenous lives. We are committed to delivering this content in a manner that is both respectful and comprehensible, catering to a diverse audience seeking to gain a comprehensive understanding of these pressing issues.
                        </div>                       
                    </td>
                    <td name = 'layout'>
                        <div class = 'img2'>
                            <img src = 'AboriginalArt2.avif' class = 'HI1' alt = 'Image1' height = '250'>
                        </div>
                    </td>
                </tr>
            </table>
            <table cellspacing = '10'>
                <tr>
                    <td name = 'layout'>
                        <div class = 'body2'>
                           For more information, here are some trusted and unbiased websites we recommend:
                           <ul>
                            <li><a href="https://www.abs.gov.au/statistics/people/aboriginal-and-torres-strait-islander-peoples/aboriginal-and-torres-strait-islander-people-census/latest-release">Australian Bureau of Statistics</a></li>
                            <li><a href="https://www.niaa.gov.au/indigenous-affairs/referendum-aboriginal-and-torres-strait-islander-voice">National Indigenous Australians Agency</a></li>
                            <li><a href="https://humanrights.gov.au/our-work/aboriginal-and-torres-strait-islander-social-justice/voice-referendum-understanding">Australian Human Rights Commission</a></li>
                            </ul>
                        </div>                       
                    </td>
                </tr>
            </table>

        </center>
        """;


        
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

