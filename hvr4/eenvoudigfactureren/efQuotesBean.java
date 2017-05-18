package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//informatieve lijn , alles op 0 null

public class efQuotesBean{
 public long quote_id;//Gegenereerd door het systeem
 public String uri;
 public String external_quote_id;
 public long client_id;
 public String client_uri;
 public String type;
 public String number;
 public String reference;
 public Date date;
 public String status;
 public int days_valid;
 public Date date_expires;//sometimes "" , problem Gson!!
 public int discount_percentage;
 public String currency;
 public double exchange_rate;
 public String show_tax_for_exchange_rate;
 public String language;
 public String tax_calculation;//total item
 public List <efQuotesItemsBean> items;
 public double discount_total_without_tax;
 public double total_without_tax;
 public double tax_rate_1;
 public double total_tax_1;
 public double tax_rate_2;
 public double total_tax_2;
 public double tax_rate_3;
 public double total_tax_3;
 public double discount_total_with_tax;
 public double total_with_tax;
 public String note;//$MC: BTW verlegd - handelingen waarvoor de BTW verschuldigd is door de medecontractant
 public List<efTagsBean> tags;
 public List<efQuotesRemarksBean> remarks;
 public List<efQuotesEventsBean> events;

 public String toString(){
  String rv=null;
  StringBuffer sb=new StringBuffer();
  sb.append("quote_id="+quote_id);
  sb.append(";");
  sb.append("number="+number);
  sb.append(";");
  sb.append("client_id="+client_id);
  sb.append(";");
  sb.append("date="+efAPI.sdf1.format(date));
  sb.append(";");
  sb.append("tax_calculation="+tax_calculation);
  sb.append(";");
  sb.append("days_valid="+days_valid);
  sb.append(";");
  sb.append("items#="+items.size());
  sb.append(";");
  sb.append("remarks#="+remarks.size());
  rv=sb.toString();
  return(rv);
 }//toString

}//efQuotesBean
/************
INFO: e_c=[{"quote_id":"145322","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145322","external_quote_id":"","client_id":"67720","client_uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67720","type":"quote","number":"1","reference":"","date":"2017-05-12","status":"open","days_valid":30,"date_expires":"2017-06-10","discount_percentage":0,"currency":"EUR","exchange_rate":"1.0000000000","show_tax_for_exchange_rate":"yes","language":"dutch","tax_calculation":"total","items":[{"item_id":"461735","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145322\/items\/461735","description":"Test offerte","amount":"0.00","quantity":0,"unit":null,"stockitem_code":null,"total_without_tax":"0.00","tax_rate":0,"tax_rate_special_status":"MC","total_with_tax":"0.00"},{"item_id":"461736","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145322\/items\/461736","description":"Huur kraan","amount":"500.25","quantity":1,"unit":null,"stockitem_code":null,"total_without_tax":"500.25","tax_rate":0,"tax_rate_special_status":"MC","total_with_tax":"500.25"},{"item_id":"461737","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145322\/items\/461737","description":"Werk","amount":"50.00","quantity":3,"unit":null,"stockitem_code":null,"total_without_tax":"150.00","tax_rate":0,"tax_rate_special_status":"MC","total_with_tax":"150.00"}],"discount_total_without_tax":"0.00","total_without_tax":"650.25","tax_rate_1":21,"total_tax_1":"0.00","tax_rate_2":12,"total_tax_2":"0.00","tax_rate_3":6,"total_tax_3":"0.00","discount_total_with_tax":"0.00","total_with_tax":"650.25","note":"$MC: BTW verlegd - handelingen waarvoor de BTW verschuldigd is door de medecontractant$\r\n\r\nDeze offerte is enkel geldig mits goedkeuring binnen de vooropgestelde termijn.","tags":[{"tag_id":3,"name":"@pdf"}],"remarks":[{"remark_id":"75235","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145322\/remarks\/75235","date":"2017-05-12","description":"Test notitie"}],"events":[]}]
**************************/
