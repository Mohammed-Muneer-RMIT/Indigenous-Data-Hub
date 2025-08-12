package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/database.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs2016() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2016'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("code");
                String name  = results.getString("name");

                // Create a LGA Object
                LGA lga = new LGA(code, name, 2016);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    // TODO: Add your required methods here

    public ArrayList<String> getImagePath() {
        ArrayList<String> path = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT ImagePath FROM PersonaDetails";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                path.add("/" + results.getString("ImagePath"));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return path;
    }

    public ArrayList<String> getPersonaDetails() {
        ArrayList<String> details = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM PersonaDetails";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                details.add(results.getString("Name"));
                details.add(results.getString("Description"));
                details.add(results.getString("Needs"));
                details.add(results.getString("Goals"));
                details.add(results.getString("SkillsExperience"));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return details;
    }


    public ArrayList<Data> getAllData(String category , String lga, String column, String order) {
        String var = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        ArrayList<Data> dataset = new ArrayList<Data>();
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select * from " + category + " where lga_code = "+ lga + " and year = 2021";
            if (column != null && order != null){
                query = "select * from " + category + " where lga_code = "+ lga + " and year = 2021 " + "order by " + column + " " + order ;
            }
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                int code     = results.getInt("lga_code");
                int count     = results.getInt("count");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String var1  = results.getString(var);
                int year     = results.getInt("year");

                
                Data data = new Data(code, year, count, status, sex, var1);
                dataset.add(data);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return dataset;
        
    }

    public ArrayList<Income> getAllIncome(String category , String lga, String column, String order) {
        String var = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        ArrayList<Income> dataset = new ArrayList<Income>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select * from " + category + " where lga_code = "+ lga + " and year = 2021";
            if (column != null && order != null){
                query = "select * from " + category + " where lga_code = "+ lga + " and year = 2021 " + "order by " + column + " " + order;
                ;
            }
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int code     = results.getInt("lga_code");
                int count     = results.getInt("count");
                String status  = results.getString("indigenous_status");
                String var1  = results.getString(var);
                int year     = results.getInt("year");
            
                Income data = new Income(code, year, count, status,  var1);
                dataset.add(data);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return dataset;
    }

    public ArrayList<RawProp> getRawProp(String category , String lga,String yr) {
        String var = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        ArrayList<RawProp> dataset = new ArrayList<RawProp>();
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select distinct lga_code, year, " + var + " from " + category + " where lga_code = "+ lga + " and year = 2021";
            /*if (column != null && order != null){
                query = "select * from " + category + " where lga_code = "+ lga + " " + "order by " + column + " " + order;
            }*/
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("lga_code");
                String var1  = results.getString(var);
                int gap    = 10 - ((int)Math.round(getGapsbyLGAandVar(category, yr, lga, var1)));
                int year     = results.getInt("year");
                int raw = getRawValue(category, lga, var1, "indig" );
                double prop = Math.round(((double)getRawValue(category,lga,var1, "indig") / getProportion(category, lga, "indig")) * 100);
                RawProp data = new RawProp(code, year, var1, raw, prop, gap);
                dataset.add(data);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return dataset;
    }

    public ArrayList<Income> getIncome(String category) {
        String var = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        // Create the ArrayList of LGA objects to return
        ArrayList<Income> dataset = new ArrayList<Income>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "select * from " + category + " where year = 2021";
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("lga_code");
                int count     = results.getInt("count");
                String status  = results.getString("indigenous_status");
                String var1  = results.getString(var);
                int year     = results.getInt("year");
                
                // Create a LGA Object
                Income data = new Income(code, year, count, status, var1);
                // Add the lga object to the array
                dataset.add(data);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return dataset;
    }

    public int getRawValue(String category, String lga, String var, String status) {
        int count = 0;
        String varname = "";
        if (category.equals("populationstatistics")){
            varname = "age";
        }
        else if (category.equals("HealthIssues")){
            varname = "condition";
        }
        else if (category.equals("Education")){
            varname = "education";
        }
        else{
            varname = "income";
        }
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select SUM(count) as counter from " + category + " where lga_code = " + lga + " and " + varname + " == '" + var + "' and year = 2021 and indigenous_status == '" + status + "'";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                count = results.getInt("counter");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return count;
    }

    public int getProportion(String category, String lga, String status) {
        int count1 = 0;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == '" + status + "' and lga_code = " + lga ;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                count1 = results.getInt("counted");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return count1;
    }

    public double getGapsbyLGAandVar(String category, String year, String lga, String var){
        String varname = "";
        if (category.equals("populationstatistics")){
            varname = "age";
        }
        else if (category.equals("HealthIssues")){
            varname = "condition";
        }
        else if (category.equals("Education")){
            varname = "education";
        }
        else{
            varname = "income";
        }
        double val1 = 0;
        double val2 = 0;
        double val3 = getPopulationProportionbyLGA(category, lga, year);
        Connection connection = null;
        if (year == null || category.equals("HealthIssues")){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and lga_code = " + lga + " and " + varname + " == '" + var + "'";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig' and lga_code = " + lga + " and " + varname + " == '" + var + "'";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    else if (lga == null){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year = " + year + " and " + varname + " == '" + var + "'" ;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig' and year = " + year + " and " + varname + " == '" + var + "'";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    else {
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year = " + year + " and lga_code = " + lga  + " and " + varname + " == '" + var + "'";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig'  and year = " + year + " and lga_code = " + lga + " and " + varname + " == '" + var + "'";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
        return (((val2 - val1) * val3)/(val1 + val2)) * 100;
    }

    public double getPopulationProportionbyLGA (String category, String lga, String year) {
        double val1 = 0;
        double val2 = 0;
        Connection connection = null;
        if(category.equals("HealthIssues")){
            year = "2021";
        }
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status = 'indig' and lga_code = " + lga + " and year = " + year + "";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from "+ category +" where indigenous_status = 'non_indig' and lga_code = " + lga + " and year = " + year + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return val1/(val1 + val2);
    }

    public ArrayList<Data> getData(String category) {
        String var = "";
        //String html = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        // Create the ArrayList of LGA objects to return
        ArrayList<Data> dataset = new ArrayList<Data>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query

            String query = "select * from " + category + " where year = 2021;";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("lga_code");
                int count     = results.getInt("count");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String var1  = results.getString(var);
                int year     = results.getInt("year");
                
                //html = html + "<tbody><tr><td class = 'coldata'>" + code + " </td><td class = 'coldata'>" + year + " </td><td class = 'coldata'>" + count + " </td><td class = 'coldata'>"
                //    + var1 + "</td><td class = 'coldata'>"+ sex + "</td><td class = 'coldata'>" + status + "</td></tr></tbody>";
                // Create a LGA Object
                Data data = new Data(code, year, count, status, sex, var1);
                // Add the lga object to the array
                dataset.add(data);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return dataset;
        //return html;
    }
    
    public ArrayList<String> get_2b_details(String lga) {
        ArrayList<String> lga_details = new ArrayList<>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT lga_name, lga_type, area_sqkm FROM LGA WHERE lga_code = " + lga + "";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                lga_details.add(results.getString("lga_name"));
                lga_details.add(results.getString("lga_type"));
                lga_details.add(results.getString("area_sqkm"));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return lga_details;
    }

    public String get_popn_diff(String lga) {
        String popn_diff = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT ((SELECT SUM(count) AS SUM2021 FROM PopulationStatistics
                WHERE year='2021'
                AND lga_code == """ + lga + " "+"""
                GROUP BY lga_code)
                -
                (SELECT SUM(count) AS SUM2016 FROM PopulationStatistics
                WHERE year='2016'
                AND lga_code == """ + lga + " "+"""
                GROUP BY lga_code)) AS x
                FROM PopulationStatistics
                GROUP BY x;
                    """;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                popn_diff = results.getString("x");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return popn_diff;
    }

    public int change_dataset1(String education_level, String lga, String indigenous_status, String sex) {
        int change_in_data1 = 0;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT ((SELECT count AS COUNT2021 FROM Education
                WHERE year='2021'
                AND lga_code = """ + lga + " " + """
                 AND indigenous_status == '""" + indigenous_status + """
                ' AND sex == '""" + sex  + """
                    ' AND education == '""" + education_level + """ 
                ' group by education, lga_code, indigenous_status, sex)
                -
                (SELECT count AS COUNT2016 FROM Education
                WHERE year='2016'
                AND lga_code = """ + lga + " " + """
                 AND indigenous_status == '""" + indigenous_status + """
                ' \t AND sex == '""" + sex  + """
                    ' AND education == '""" + education_level + """
                ' group by education, lga_code, indigenous_status, sex)) as x from Education group by x""";
                // System.out.print(query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                change_in_data1 = results.getInt("x");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return change_in_data1;
    }

    public int change_dataset2(String lga, String age_range, String indigenous_status, String sex) {
        int change_in_data2 = 0;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT ((SELECT count AS COUNT2021 FROM PopulationStatistics
                WHERE year='2021'
                AND lga_code = """ + lga + " " + """
                    AND age == '""" + age_range + """
                ' \t AND indigenous_status == '""" + indigenous_status + """
                    ' AND sex == '""" + sex + """
                ' GROUP BY age, lga_code, indigenous_status, sex)
                -
                (SELECT count AS COUNT2016 FROM PopulationStatistics
                WHERE year='2016'
                AND lga_code = """ + lga + " " + """
                     AND age == '""" + age_range + """ 
                ' \t and indigenous_status == '""" + indigenous_status + """
                    ' AND sex == '""" + sex + """
                ' GROUP BY age, lga_code, indigenous_status, sex)) AS x
                FROM PopulationStatistics
                GROUP BY x
                ;
                    """;
                    // System.out.print(query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                change_in_data2 = results.getInt("x");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return change_in_data2;
    }

    public int change_dataset3(String lga, String income_range, String indigenous_status) {
        int change_in_data3 = 0;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT ((SELECT count AS COUNT2021 FROM WeeklyIncome
                    WHERE year='2021'
                    AND lga_code = """ + lga + " " + """
                        AND income = '""" + income_range + """
                    ' GROUP BY income, lga_code, indigenous_status)
                    -
                    (SELECT count AS COUNT2016 FROM WeeklyIncome
                    WHERE year='2016'
                    AND lga_code = """ + lga + " " + """
                        AND income = '""" + income_range + """
                    ' GROUP BY income, lga_code, indigenous_status)) AS x
                    FROM WeeklyIncome
                    GROUP BY x
                    ; 
                    """;
                    System.out.print(query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                change_in_data3 = results.getInt("x");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return change_in_data3;
    }

    public ArrayList<GapData> getLGAGapData(String category , String lga,String yr) {
        String var = "";
        if (category.equals("populationstatistics")){
            var = "age";
        }
        else if (category.equals("HealthIssues")){
            var = "condition";
        }
        else if (category.equals("Education")){
            var = "education";
        }
        else{
            var = "income";
        }
        ArrayList<GapData> dataset = new ArrayList<GapData>();
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select distinct lga_code, year, " + var + " from " + category + " where lga_code = "+ lga + "";
            /*if (column != null && order != null){
                query = "select * from " + category + " where lga_code = "+ lga + " " + "order by " + column + " " + order;
            }*/
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("lga_code");
                String var1  = results.getString(var);
                int gap    = ((int)Math.round(getGapsbyLGAandVar(category, yr, lga, var1)));
                int year     = results.getInt("year");
                GapData data = new GapData(code, year, var1, gap);
                // Add the lga object to the array
                dataset.add(data);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return dataset;
    }

    public double getGaps(String category, String year){
        double val1 = 0;
        double val2 = 0;
        double val3 = getPopulationProportion(category, year);
        Connection connection = null;
        if (year == null){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year" ;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig' ";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    else {
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year == " + year + "";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig'  and year == " + year + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
        return (((val2 - val1) * val3)/(val1 + val2)) * 100;
    }

 
    public double getGapsbyLGA(String category, String year, String lga){
        double val1 = 0;
        double val2 = 0;
        double val3 = getPopulationProportionbyLGA(category, lga, year);
        Connection connection = null;
        if (year == null || category.equals("HealthIssues")){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and lga_code = " + lga + "" ;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig' and lga_code = " + lga + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    else if (lga == null){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year = " + year + "" ;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig' and year = " + year + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    else {
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "select sum(count) as counted from " + category + " where indigenous_status == 'indig' and year = " + year + " and lga_code = " + lga + "";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from " + category + " where indigenous_status == 'non_indig'  and year = " + year + " and lga_code = " + lga + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
        return (((val2 - val1) * val3)/(val1 + val2)) * 100;
    }

    public double getPopulationProportion (String category, String year) {
        double val1 = 0;
        double val2 = 0;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if (category.equals("HealthIssues")){
                year = "2021";
            }
            String query = "select sum(count) as counted from " + category + " where indigenous_status = 'indig' and year = " + year + "";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) 
            {
                val1 = results.getInt("counted");
            }
            statement.close();
            Statement statement1 = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "select sum(count) as counted from "+ category +" where indigenous_status = 'non_indig' and year = " + year + "";
            ResultSet results1 = statement.executeQuery(query1);
            while (results.next()) 
            {
                val2 = results1.getInt("counted");
            }
            statement1.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return val1/(val1 + val2);
    }


}

