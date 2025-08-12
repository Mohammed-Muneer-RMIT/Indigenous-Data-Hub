package app;

/**
 * Class represeting a dataset from the Studio Project database
 * */
public class GapData {
   // LGA 2016 Code
   private int code;

   private int gap;

   private String var;

   private int year;

   /**
    * Create an LGA and set the fields
 * @return 
    */
   public GapData(int code, int year, String var, int gap ) {
      this.code = code;
      this.year = year;
      this.gap = gap;
      this.var = var;
    }

   public int getCode() {
      return code;
   }
   public int getYear() {
      return year;
    }
   public int getGap() {
    return gap;
    }
 public String getVar() {
    return var;
    }
}
