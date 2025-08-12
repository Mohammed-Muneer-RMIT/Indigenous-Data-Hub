package app;

import java.util.ArrayList;
import java.util.List;

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
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page5.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Gap-Scores</title>";

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
        /*html = html + """
            <div class='header'>
                <h1>Gap-Scores</h1>
            </div>
        """;*/

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();
        // Next we will ask this *class* for the LGAs

        // Add HTML for the LGA list
        html = html + "<div class = datacontainer><div class = 'inputcontainer'><h1 class = 'gapheader'>Analyzing The Gap</h1>" + "<ul>";
        html = html + """
                <div class = 'pointers'>Select the datasets you wish to compare:</div>
                <br>
                <div class = 'vertical-checkboxes'>
                    <form  action="/page5.html" method="post">
                        <label class = 'catname'>
                            <input type = "checkbox" name = "Education" id = "Education" value = "Education" class = 'check'> Education
                        </label>
                        <label class = 'catname'>
                            <input type = "checkbox" name = "PopulationStatistics" value = "populationstatistics" class = 'check'> Age
                        </label>
                        <label class = 'catname'>
                            <input type = "checkbox" name = "HealthIssues" value = "HealthIssues" class = 'check'> Health
                        </label>
                        <label class = 'catname'>
                            <input type = "checkbox" name = "WeeklyIncome" value = "WeeklyIncome" class = 'check'> Income
                        </label>
            <h1>Gap Scores</h1>
            <div class = 'pointers'>The Gap Scores range from 1-10, the closer a score is to 10
             the smaller the gap between indigenous and non indigenous people.
             The Scores are calculated by scaling the number of non-indigenous
             people according to the proportion of indigenous people in the 
             population, then the individual categories are compared. </div>
            
                    <select name = "yeardrop" id = "yeardrop">
                        <option>2016 & 2021</option>
                        <option value = '2016'>2016</option>
                        <option value = '2021'>2021</option>
                    </select>    
                    <input type = 'text' name = "lga" id = "lga" placeholder = "Type Lga Here"><br>
                    <button type='submit' class='btn btn-primary' style='margin-top: 10px;'>Submit</button>               
                </form>
            </div>
            </div>
                """;
        // Finally we can print out all of the LGAs
        String edval = context.formParam("Education");
        String ageval = context.formParam("PopulationStatistics");
        String HIval = context.formParam("HealthIssues");
        String WIval = context.formParam("WeeklyIncome");
        String yr = context.formParam("yeardrop");
        String var = "";
        List<Integer> arr = new ArrayList<Integer>();
        List<Integer> arrlga = new ArrayList<Integer>();
        ArrayList<GapData> ds;
        String lga = context.formParam("lga");
        if (edval != null || ageval != null || HIval != null || WIval != null && lga!= null && yr != null){
            if (ageval != null){
                var = "age";
                ds = jdbc.getLGAGapData(ageval, lga, yr);
                html = html + """
                    <div class = 'gaptables'>
                    <table class = 'data'>
                    <thead><tr>
                    <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Gap Score</th><th class = 'dataname'>""" + var + """                   
                    </th>         
                    </tr></thead>
                    """;
                    
                    for (int i = 0; i < ds.size(); i++)
                    {
                        html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + " </td><td class = 'coldata'>" + ds.get(i).getYear() + " </td><td class = 'coldata'>" + ds.get(i).getGap() + "/10 </td><td class = 'coldata'>"
                        + ds.get(i).getVar() + "</td></tr></tbody>";
                    }
                    html = html + "</table>"+ "</div>";

            }
            if(HIval != null){
                var = "condition";
                ds = jdbc.getLGAGapData(HIval, lga, yr);
                html = html + """
                    <div class = 'gaptables'>
                    <table class = 'data'>
                    <thead><tr>
                    <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Gap Score</th><th class = 'dataname'>""" + var + """                   
                    </th>         
                    </tr></thead>
                    """;
                    
                    for (int i = 0; i < ds.size(); i++)
                    {
                        html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + " </td><td class = 'coldata'>" + ds.get(i).getYear() + " </td><td class = 'coldata'>" + ds.get(i).getGap() + "/10 </td><td class = 'coldata'>"
                        + ds.get(i).getVar() + "</td></tr></tbody>";
                    }
                    html = html + "</table>"+ "</div>";

            }
            if(edval != null){
                var = "education";
                ds = jdbc.getLGAGapData(edval, lga, yr);
                html = html + """
                    <div class = 'gaptables'>
                    <table class = 'data'>
                    <thead><tr>
                    <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Gap Score</th><th class = 'dataname'>""" + var + """                   
                    </th>         
                    </tr></thead>
                    """;
                    
                    for (int i = 0; i < ds.size(); i++)
                    {
                        html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + " </td><td class = 'coldata'>" + ds.get(i).getYear() + " </td><td class = 'coldata'>" + ds.get(i).getGap() + "/10 </td><td class = 'coldata'>"
                        + ds.get(i).getVar() + "</td></tr></tbody>";
                    }
                    html = html + "</table>"+ "</div>";

            }
            if(WIval != null){
                var = "income";
                ds = jdbc.getLGAGapData(WIval, lga, yr);
                html = html + """
                    <div class = 'gaptables'>
                    <table class = 'data'>
                    <thead><tr>
                    <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Gap Score</th><th class = 'dataname'>""" + var + """                   
                    </th>         
                    </tr></thead>
                    """;
                    
                    for (int i = 0; i < ds.size(); i++)
                    {
                        html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + " </td><td class = 'coldata'>" + ds.get(i).getYear() + " </td><td class = 'coldata'>" + ds.get(i).getGap() + "/10 </td><td class = 'coldata'>"
                        + ds.get(i).getVar() + "</td></tr></tbody>";
                    }
                    html = html + "</table>"+ "</div>";
            } 
        }
        html = html + "</div>";
        html = html + "<div class = 'gapcontainer'><div class = 'totalgap'><h1>Gap Scores for Total Population</h1>";
            if (edval != null){
                double gap = jdbc.getGaps(edval, yr);
                html = html + """  
                        <pre class = 'scores'>Education:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arr.add((int) Math.round(gap));
            }
            if (ageval != null){
                double gap = jdbc.getGaps(ageval, yr);
                html = html + """
                        <pre class = 'scores'>Age:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arr.add((int) Math.round(gap));
            }
            if (HIval != null){
                double gap = jdbc.getGaps(HIval, yr);
                html = html + """
                        <pre class = 'scores'>Health Issues:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arr.add((int) Math.round(gap));
            }
            if (WIval != null){
                double gap = jdbc.getGaps(WIval, yr);
                html = html + """
                        <pre class = 'scores'>Income:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arr.add((int) Math.round(gap));
            }
            if (edval != null || ageval != null || HIval != null || WIval != null){
                int tot = 0;
                for(int i = 0; i< arr.size(); i++){
                    tot += arr.get(i);
                }
                int avgGap = tot/(arr.size());
                html = html + """
                    <pre class = 'scores'>Total Gap Score:  <br><pre><h1>""" + (10-avgGap) + "/10";
                html = html + """
                    <h1>
            
                """;
            }
            html = html + "</div>";


            html = html + "<div class = 'lgagap'><h1>Gap Scores for LGA</h1>";
            if (edval != null){
                double gap = jdbc.getGapsbyLGA(edval, yr, lga);
                html = html + """  
                        <pre class = 'scores'>Education:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arrlga.add((int) Math.round(gap));
            }
            if (ageval != null && lga!= null){
                double gap = jdbc.getGapsbyLGA(ageval, yr, lga);
                html = html + """
                        <pre class = 'scores'>Age:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arrlga.add((int) Math.round(gap));
            }
            if (HIval != null && lga!= null){
                double gap = jdbc.getGapsbyLGA(HIval, yr, lga);
                html = html + """
                        <pre class = 'scores'>Health Issues:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arrlga.add((int) Math.round(gap));
            }
            if (WIval != null && lga!= null){
                double gap = jdbc.getGapsbyLGA(WIval, yr, lga);
                html = html + """
                        <pre class = 'scores'>Income:  """ + (10 - Math.round(Math.abs(gap)));
                html = html + """
                        /10<pre>
                
                        """;
                arrlga.add((int) Math.round(gap));
            }
            if (edval != null || ageval != null || HIval != null || WIval != null && lga!= null){
                int tot = 0;
                for(int i = 0; i< arrlga.size(); i++){
                    tot += arrlga.get(i);
                }
                int avgGap = tot/(arr.size());
                html = html + """
                    <pre class = 'scores'>Total Gap Score:  <br><pre><h1>""" + (10-avgGap) + "/10";
                html = html + """
                    <h1>
            
                """;
            }
        
        html = html + "</div></div>";

        html = html + "</ul>";
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
