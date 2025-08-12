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
public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page4.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.2</title>";

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
                <h1>Focused View by LGA/State</h1>
            </div>
        """;

        // PRESENT INFORMATION ON LGAS
        html = html + "<div class='flexbox-container flexbox-lga'>";
        html = html + """
            <b>Enter a LGA:</b>
            <form action="/page4.html" method="post">
            <input type = 'text' name = "lga1" id = "lga1" placeholder = "Type Lga Here"><br>

            <br>
            <b>Select an Education level, Indigenous Status and Sex to Compare Education Data:</b><br>
            <select name = "education_level_drop" id = "education_level_drop">
            <option value = "No Education">No Education</option>
            <option value = "Year 8 or below">Year 8 or Below</option>
            <option value = "Year 9 Equivalent">Year 9 Equivalent</option>
            <option value = "Year 10 Equivalent">Year 10 Equivalent</option>
            <option value = "Year 11 Equivalent">Year 11 Equivalent</option>
            <option value = "Year 12 Equivalent">Year 12 Equivalent</option>
            </select>
            <select name = "indigenous_status_drop1" id = "indigenous_status_drop1">
            <option value = "indig">Indigenous</option>
            <option value = "non_indig">Non-Indigenous</option>
            <option value = "indig_ns">Not Stated</option>
            </select>
            <select name = "sex_drop1" id = "sex_drop1">
            <option value = "f">Female</option>
            <option value = "m">Male</option>
            </select>
            <br>

            <b>Select an Age, Indigenous Status and Sex to Compare Age Data:</b><br>
            <select name = "age_drop" id = "age_drop">
            <option value = "0-4">0-4 Years</option>
            <option value = "5-9">5-9 Years</option>
            <option value = "10-14">10-14 Years</option>
            <option value = "15-19">15-19 Years</option>
            <option value = "20-24">20-24 Years</option>
            <option value = "25-29">25-29 Years</option>
            <option value = "30-34">30-34 Years</option>
            <option value = "35-39">35-39 Years</option>
            <option value = "40-44">40-44 Years</option>
            <option value = "45-49">45-49 Years</option>
            <option value = "50-54">50-54 Years</option>
            <option value = "55-59">55-59 Years</option>
            <option value = "60-64">60-64 Years</option>
            <option value = "65+">65+ Years</option>
            </select>
            <select name = "indigenous_status_drop2" id = "indigenous_status_drop2">
            <option value = "indig">Indigenous</option>
            <option value = "non_indig">Non-Indigenous</option>
            <option value = "indig_ns">Not Stated</option>
            </select>
            <select name = "sex_drop2" id = "sex_drop2">
            <option value = "f">Female</option>
            <option value = "m">Male</option>
            </select>
            <br>
            <b>Select an Weekly Income and Indigenous Status to Compare Income Data:</b>
            <br>
            <select name = "income_drop" id = "income_drop">
            <option value = "1-149">$1-$149</option>
            <option value = "150-299">$150-$299</option>
            <option value = "300-399">$300-$399</option>
            <option value = "400-499">$400-$499</option>
            <option value = "500-649">$500-$649</option>
            <option value = "650-799">$650-$799</option>
            <option value = "800-999">$800-$999</option>
            <option value = "1000-1249">$1000-$1249</option>
            <option value = "1250-1499">$1250-$1499</option>
            <option value = "1500-1999">$1500-$1999</option>
            <option value = "2000-2499">$2000-$2499</option>
            <option value = "2500-2999">$2500-$2999</option>
            <option value = "3000-3499">$3000-$3499</option>
            </select>
            <select name = "indigenous_status_drop3" id = "indigenous_status_drop3">
            <option value = "indig">Indigenous</option>
            <option value = "non_indig">Non-Indigenous</option>
            <option value = "indig_ns">Not Stated</option>
            </select>
            <br>
            <button type='submit' class='btn btn-primary' style='margin-top: 10px;'>Submit</button>
            </form>
            </div>
                """;

        
        JDBCConnection jdbc = new JDBCConnection();
        
        String user_lga = context.formParam("lga1");


            String user_state = context.formParam("statedrop");

            // DECLARE STATES
            ArrayList<String> states = new ArrayList<String>();
            states.add("NSW");
            states.add("Victoria");
            states.add("QLD");
            states.add("South Australia");
            states.add("Western Australia");
            states.add("Tasmania");
            states.add("Northern Territory");
            states.add("ACT");
            states.add("Other");
    
            String lgas_state = "";
    
            if (user_lga != null) {
                int int_user_lga = Integer.parseInt(user_lga);
                
                if (int_user_lga >= 10000 && int_user_lga <= 19999) {
                    lgas_state = states.get(0);
                }
                else if (int_user_lga >= 20000 && int_user_lga <= 29999) {
                    lgas_state = states.get(1);
                }
                else if (int_user_lga >= 30000 && int_user_lga <= 39999) {
                    lgas_state = states.get(2);
                }
                else if (int_user_lga >= 40000 && int_user_lga <= 49999) {
                    lgas_state = states.get(3);
                }
                else if (int_user_lga >= 50000 && int_user_lga <= 59999) {
                    lgas_state = states.get(4);
                }
                else if (int_user_lga >= 60000 && int_user_lga <= 69999) {
                    lgas_state = states.get(5);
                }
                else if (int_user_lga >= 70000 && int_user_lga <= 79999) {
                    lgas_state = states.get(6);
                }
                else if (int_user_lga >= 80000 && int_user_lga <= 89999) {
                    lgas_state = states.get(7);
                }
                else if (int_user_lga >= 90000 && int_user_lga <= 99999) {
                    lgas_state = states.get(8);
                }
            }
    
        // GET POPULATION DIFFERENCE FOR EACH STATE
        
    
        if (user_lga != null) {
            ArrayList<String> lga_info = jdbc.get_2b_details(user_lga);
            html = html + "<div class='flexbox-container flexbox-lga'>";
            html = html + "<b>LGA Code:</b><br>";
            html = html + "<p>" + user_lga + "</p><br>";
            html = html + "<b>Name:</b><br>";
            html = html + "<p>" + lga_info.get(0) + "</p><br>";
            html = html + "<b>State:</b><br>";
            html = html + "<p>" + lgas_state + "</p><br>";
            html = html + "<b>Type:</b><br>";
            html = html + "<p>" + lga_info.get(1)+ "</p><br>";
            html = html + "<b>Area:</b><br>";
            html = html + "<p>" + lga_info.get(2) + "</p><br>";


            String lga_popn_diff = jdbc.get_popn_diff(user_lga);
            html = html + "<b>Change in Population between 2016 and 2021:</b>";
            html = html + "<p>" + lga_popn_diff + "</p>";



            html = html + "</div>";


        }
        else if (user_lga == null && user_state != null) {
            html = html + "<div class='flexbox-container flexbox-state'>";
            html = html + "<b>State Name:</b><br>";
            html = html + "<p>" + user_state + "</p>";
            html = html + "</div>";
        }
            

        
        String user_education_level = context.formParam("education_level_drop");
        String user_inidgenous_status1 = context.formParam("indigenous_status_drop1");
        String user_sex1 = context.formParam("sex_drop1");

        String user_age_range = context.formParam("age_drop");
        String user_indigenous_status2 = context.formParam("indigenous_status_drop2");
        String user_sex2 = context.formParam("sex_drop2");

        String user_income_range = context.formParam("income_drop");
        String user_indigenous_status3 = context.formParam("indigenous_status_drop3");

        // CALCULATED DIFFERENCES
        int education_difference = jdbc.change_dataset1(user_education_level, user_lga, user_inidgenous_status1, user_sex1);
        int age_difference = jdbc.change_dataset2(user_lga, user_age_range, user_indigenous_status2, user_sex2);
        int income_difference = jdbc.change_dataset3(user_lga, user_income_range, user_indigenous_status3);

        if (user_lga != null) {
            html = html + "<div class='flexbox-container flexbox-lga'>";
            html = html + "<b>Change in Education Between 2016 and 2021:</b>";
            html = html + "<p>" + education_difference + "</p>";


            html = html + "<b>Change in Age Between 2016 and 2021:</b>";
            html = html + "<p>"+ age_difference + "</p>";
     

            html = html + "<b>Change in Weekly Income Between 2016 and 2021:</b>";
            html = html + "<p>"+ income_difference + "</p>";
            html = html + "</div>";
        }


    
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
