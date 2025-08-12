package app;

/**
 * Class represeting a dataset from the Studio Project database
 * */
public class RawProp {
   // LGA 2016 Code
   private int code;

   private int gap;

   private String var;

   private int year;

   private int raw;

   private double prop;

   /**
    * Create an LGA and set the fields
 * @return 
    */
    public RawProp(int code, int year, String var, int raw, double prop, int gap ) {
      this.code = code;
      this.year = year;
      this.gap = gap;
      this.var = var;
      this.raw = raw;
      this.prop = prop;
    }

    public int getCode() {
      return code;
   }
    public int getYear() {
      return year;
    }
    public int getRaw() {
        return raw;
    }
    public double getProp() {
        return prop;
    }
    public double getGap(){
        return gap;
    }
     public String getVar() {
        return var;
    }
}