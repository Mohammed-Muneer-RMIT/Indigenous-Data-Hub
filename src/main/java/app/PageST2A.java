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
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Census Results Analysis</title>";

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
                <h1>Census Results Analysis</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
        
        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs        
        html = html + """
            <h1>LGA Filter</h1>
            <form action="/page3.html" method="post">
                <select name = "categorydrop" id = "categorydrop">
                    <option value = "populationstatistics">Population</option>
                    <option value = "HealthIssues">Health Issues</option>
                    <option value = "Education">Education</option>
                    <option value = "weeklyincome">Weekly Income </option> 
                    <input type = 'text' name = "lga" id = "lga" placeholder = "Type Lga Here"><br>
                </select>
            """;
            
        html = html + """

                <select name = "columndrop" id = "columndrop">
                    <option value = 'lga_code'>LGA Code</option>
                    <option value = 'indigenous_status'> Indigenous Status</option>
                    <option value = 'sex'>Sex</option>
                    <option value = "var">Outcome</option>
                    <option value = 'count'>Count</option>
                </select>
                <select name = "orderdrop" id = "orderdrop">
                    <option value = 'ASC'>Ascending</option>
                    <option value = 'DESC'>Descending</option>
                </select>
                <button type='submit' class='btn btn-primary' style='margin-top: 10px;'>Submit</button>
            </form>
        """;
        String var = "";
        String categorydrop = context.formParam("categorydrop");
        String lgaselect = context.formParam("lga");
        String columndrop = context.formParam("columndrop");
        String orderdrop = context.formParam("orderdrop");
        String header = "";
        if(categorydrop != null){
            if (categorydrop.equals("populationstatistics")){
                header = "Population Statistics";
            }
            else if (categorydrop.equals("HealthIssues")){
                header = "Health Conditions";
            }
            else if (categorydrop.equals("Education")){
                header = "Schooling and Education";
            }
            else{
                header = "Weekly Household Income";
            } 
            html = html + "<center><h1>Data for "+ header + " </h1></center>";
        }
        
        if (categorydrop != null){
            ArrayList<Data> dataset = jdbc.getAllData(categorydrop, lgaselect, columndrop, orderdrop);
            ArrayList<Income> ds = jdbc.getAllIncome(categorydrop, lgaselect, columndrop, orderdrop);
            if (categorydrop.equals("populationstatistics")){
                var = "age";
            }
            else if (categorydrop.equals("healthissues")){
                var = "condition";
            }
            else if (categorydrop.equals("education")){
                var = "education";
            }
            else{
                var = "income";
            } 
            String var1 = var;
            html = html + """
            <div class = 'censustables'>
            <div class = 'table-wrap'>
            <h1 class = 'censusheaders'><center>For Chosen LGA</center></h1>
            <table class = 'data'>
            <thead><tr>
            <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Count</th><th class = 'dataname'>""";
            
            if (var.equals("income")){
                html = html + var1 + """                   
                </th><th class = 'dataname'>Status</th>          
                </tr></thead>
                """;
                for (int i = 0; i < ds.size(); i++)
                {
                    html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + "</td><td class = 'coldata'>" + ds.get(i).getYear() + "</td><td class = 'coldata'>" + ds.get(i).getCount() + "</td><td class = 'coldata'>"
                    + ds.get(i).getVar() + "</td><td class = 'coldata'>" + ds.get(i).getStatus() + "</td></tr></tbody>";
                }
            }
            else {
                html = html + var1 + """                   
                </th><th class = 'dataname'>Sex</th><th class = 'dataname'>Status</th>          
                </tr></thead>
                """;
                for (int i = 0; i < dataset.size(); i++)
                {
                    html = html + "<tbody><tr><td class = 'coldata'>" + dataset.get(i).getCode() + "</td><td class = 'coldata'>" + dataset.get(i).getYear() + "</td><td class = 'coldata'>" + dataset.get(i).getCount() + "</td><td class = 'coldata'>"
                    + dataset.get(i).getVar() + "</td><td class = 'coldata'>"+ dataset.get(i).getSex() + "</td><td class = 'coldata'>" + dataset.get(i).getStatus() + "</td></tr></tbody>";
                }
            }
            
            html = html + "</table>"+ "</div>";
        }   
        if (categorydrop != null){
            ArrayList<Data> datasets = jdbc.getData(categorydrop);
            ArrayList<Income> ds = jdbc.getIncome(categorydrop);
            if (categorydrop.equals("populationstatistics")){
                var = "age";
            }
            else if (categorydrop.equals("healthissues")){
                var = "condition";
            }
            else if (categorydrop.equals("education")){
                var = "education";
            }
            else{
                var = "income";
            } 
            String var1 = var;
            html = html + """
            
            
            <div class = 'table-wrap'>
            <h1 class = 'censusheaders'><center>All LGA's</center></h1>
            <table class = 'data'>
            <thead><tr>
            <th class = 'dataname'>Code</th><th class = 'dataname'>Year</th><th class = 'dataname'>Count</th><th class = 'dataname'>""";
            
            if (var.equals("income")){
                html = html + var1 + """                   
                    </th><th class = 'dataname'>Status</th>          
                    </tr></thead>
                    """;
                    for (int i = 0; i < ds.size(); i++)
                    {
                        html = html + "<tbody><tr><td class = 'coldata'>" + ds.get(i).getCode() + "</td><td class = 'coldata'>" + ds.get(i).getYear() + "</td><td class = 'coldata'>" + ds.get(i).getCount() + "</td><td class = 'coldata'>"
                        + ds.get(i).getVar() + "</td><td class = 'coldata'>" + ds.get(i).getStatus() + "</td></tr></tbody>";
                    }
            }
            else{
                html = html + var1 + """                   
                </th><th class = 'dataname'>Sex</th><th class = 'dataname'>Status</th>          
                </tr></thead>
                """;
                for (int i = 0; i < datasets.size(); i++)
                {
                    html = html + "<tbody><tr><td class = 'coldata'>" + datasets.get(i).getCode() + "</td><td class = 'coldata'>" + datasets.get(i).getYear() + "</td><td class = 'coldata'>" + datasets.get(i).getCount() + "</td><td class = 'coldata'>"
                    + datasets.get(i).getVar() + "</td><td class = 'coldata'>"+ datasets.get(i).getSex() + "</td><td class = 'coldata'>" + datasets.get(i).getStatus() + "</td></tr></tbody>";
                }
            }
            html = html + "</table>"+ "</div>";
        } 
        if (categorydrop != null){
            if (categorydrop.equals("populationstatistics")){
                var = "age";
            }
            else if (categorydrop.equals("healthissues")){
                var = "condition";
            }
            else if (categorydrop.equals("education")){
                var = "education";
            }
            else{
                var = "income";
            } 
            String var2 = var;
            ArrayList<RawProp> dsrp = jdbc.getRawProp(categorydrop, lgaselect, "2021");

            html = html + """
            
            <div class = 'table-wrap'>
            <h1 class = 'censusheaders'><center>Raw and Proportional Values</center></h1>
            <table class = 'data'>
            <thead><tr>
            <th class = 'dataname'>Code </th><th class = 'dataname'>Year </th><th class = 'dataname'>""";
            
            html = html + var2 + """                   
            </th><th class = 'dataname'>Raw Value </th><th class = 'coldata'>Proportional Value </th><th class = 'coldata'> Gap Score </th>       
            </tr></thead>
            """;
                for (int i = 0; i < dsrp.size(); i++)
                {
                    html = html + "<tbody><tr><td class = 'coldata'>" + dsrp.get(i).getCode() + " </td><td class = 'coldata'>" + dsrp.get(i).getYear() + " </td><td class = 'coldata'>" + dsrp.get(i).getVar() + "</td><td class = 'coldata'>"
                    + dsrp.get(i).getRaw() + " </td><td class = 'coldata'>" + dsrp.get(i).getProp() + "% </td><td class = 'coldata'>" + dsrp.get(i).getGap()+ "/10 </tr></tbody>";
                }
            html = html + "</table>"+ "</div></div>";
        }
    
        
        // Finish the List HTML
        html = html + "</table>"+ "</div>" + "</ul>"; 
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

