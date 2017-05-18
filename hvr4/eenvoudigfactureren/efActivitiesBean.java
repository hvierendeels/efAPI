package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import hvr4.eenvoudigfactureren.efReferenceBean;

public class efActivitiesBean{
 public long activity_id;//Gegenereerd door het systeem
 public String uri;//aanspreken activiteit langs de API.	–
 public String type;//
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
  sb.append("date="+date);
  sb.append(";");
  sb.append("information="+information);
  sb.append(";");
  if(client_id!=0L){
   sb.append("client_id="+client_id);
  }
  sb.append(";");
  rv=sb.toString();
  return(rv);
 }//toString

}//efActivitiesBean
/************
INFO: e_c=[{"activity_id":"858924","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/858924","type":"client_delete","date":"2017-05-11 14:42:40","user":"lieven vierendeels","description":"Klant verwijderd","information":"Klant Van Rossem LTC nv - Horta (ID 67186) verwijderd","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"857257","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/857257","type":"login","date":"2017-05-10 09:40:59","user":"lieven vierendeels","description":"Aangemeld","information":"Aangemeld met IP adres 141.135.113.198","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"857008","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/857008","type":"import_csv","date":"2017-05-09 22:37:50","user":"lieven vierendeels","description":"Importeren Excel-bestand","information":"Importeren Excel-bestand CUSTOMERS_defactuur_select.csv (1 klanten toegevoegd\/bijgewerkt)","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"857007","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/857007","type":"client_create","date":"2017-05-09 22:37:50","user":"lieven vierendeels","description":"Klant aangemaakt","information":"Klant aangemaakt Bianca Flamant","client_id":"67187","invoice_id":null,"receipt_id":null,"quote_id":null,"references":[{"reference_type":"clients","reference_id":"67187","reference_uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67187"}]},{"activity_id":"856996","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/856996","type":"client_update","date":"2017-05-09 22:21:02","user":"lieven vierendeels","description":"Klant bijgewerkt","information":"Klant Klant met ID 67186 bijgewerkt","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"856995","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/856995","type":"client_create","date":"2017-05-09 22:19:03","user":"lieven vierendeels","description":"Klant aangemaakt","information":"Klant aangemaakt Klant met ID 67186","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"856980","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/856980","type":"settings_update","date":"2017-05-09 22:10:00","user":"lieven vierendeels","description":"Instellingen bijgewerkt","information":"Instellingen werden bijgewerkt","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]},{"activity_id":"856968","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/activities\/856968","type":"settings_create","date":"2017-05-09 22:03:23","user":"lieven vierendeels","description":"Account aangemaakt","information":"Account werd aangemaakt","client_id":null,"invoice_id":null,"receipt_id":null,"quote_id":null,"references":[]}]
*******/
