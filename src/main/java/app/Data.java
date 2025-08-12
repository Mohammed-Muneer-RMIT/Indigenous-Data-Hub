package app;

/**
 * Class represeting a dataset from the Studio Project database
 * */
public class Data {
   // LGA 2016 Code
   private int code;

   private int count;

   private String status;

   private String sex;

   private String var;

   private int year;

   /**
    * Create an LGA and set the fields
 * @return 
    */
   public Data(int code, int year, int count, String status, String sex, String var ) {
      this.code = code;
      this.year = year;
      this.count = count;
      this.status = status;
      this.sex = sex;
      this.var = var;
    }

   public int getCode() {
      return code;
   }
   public int getYear() {
      return year;
    }
   public int getCount() {
    return count;
    }
 public String getStatus() {
    return status;
    }
 public String getSex() {
    return sex;
    }
 public String getVar() {
    return var;
    }
}
