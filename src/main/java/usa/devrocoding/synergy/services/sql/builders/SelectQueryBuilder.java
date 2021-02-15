package usa.devrocoding.synergy.services.sql.builders;

public class SelectQueryBuilder {

  public String query;

  private final String space = " ";
  private final String comma = ",";
  private final String quote = "'";

  private void add(String s){
    this.query += s;
  }

  public SelectQueryBuilder(){
    this.query = "SELECT" + this.space;
  }

  public SelectQueryBuilder everything(){
    this.add("*");
    return this;
  }

  public SelectQueryBuilder select(String... columns){
    if (columns.length <= 0){
      this.everything();
    }else {
      for (int i = 0; i < columns.length; i++) {
        this.add(i == 0 ? columns[i] : this.comma + this.space + columns[i]);
      }
    }
    return this;
  }

  public SelectQueryBuilder leftJoin(String table, String oncolumn, String check){
    this.add("LEFT JOIN" + this.space + table + this.space + "ON" +
        this.space + table + "." + this.space + oncolumn + "=" + this.space + check);
    return this;
  }

  public SelectQueryBuilder leftJoin(String table, String as, String oncolumn, String check){

    return this;
  }

  public SelectQueryBuilder where(){

    return this;
  }

  public SelectQueryBuilder group(){

    return this;
  }

  public SelectQueryBuilder orderBy(){

    return this;
  }

}
