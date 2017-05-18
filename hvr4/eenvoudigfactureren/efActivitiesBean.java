package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import hvr4.eenvoudigfactureren.efReferenceBean;

public class efActivitiesBean{
 public long activity_id;
 public String uri;
 public String type;
 public Date date;
 public String user;
 public String description;
 public String information;
 public long client_id;
 public long invoice_id;
 public long receipt_id;
 public long quote_id;
 public List<efReferenceBean> references;

 public String toString(){
  String rv=null;
  StringBuffer sb=new StringBuffer();
  sb.append("efActivitiesBean:");
  sb.append("type="+type);
  sb.append(";");
  sb.append("date="+efAPI.sdf1.format(date));
  sb.append(";");
  sb.append("information="+information);
  sb.append(";");
  if(client_id!=0L){
   sb.append("client_id="+client_id);
   sb.append(";");
  }
  rv=sb.toString();
  return(rv);
 }//toString
}//efActivitiesBean
