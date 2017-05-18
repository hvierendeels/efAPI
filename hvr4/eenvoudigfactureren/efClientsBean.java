package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;

//Nieuwe optionele velden factuuropties per klant client.default_days_due, client.default_days_valid, client.default_language, client.default_currency
public class efClientsBean{
 public long client_id;//Gegenereerd door het systeem
 public String uri;
 public String external_client_id;
 public String name;
 public String attention;
 public String number;//Klantennummer
 public String street;//Straat + huisnummer
 public String city;
 public String postal_code;
 public String country;
 public String tax_code;//BTW-nummer van de klant
 public String email_address;
 public String phone_number;
 public String website;
 public String state;//status klant. ‘active’, ‘archived’. 
 public String private_note;//problem converting private_note
 public int invoice_count;
 public int receipt_count;
 public int quote_count;
 public ArrayList<efContactsBean> contacts;
 public ArrayList<efDetailsBean> details;

 public String toString(){
  String rv=null;
  StringBuffer sb=new StringBuffer();
  sb.append("efClientsBean:");
  sb.append("client_id="+client_id);
  sb.append(";");
  sb.append("number="+number);
  sb.append(";");
  sb.append("name="+name);
  sb.append(";");
  sb.append("private_note="+private_note);
  sb.append(";");
  sb.append("email_address="+email_address);
  sb.append(";");
  rv=sb.toString();
  return(rv);
 }//toString
}//efClientsBean
/**********
private_note string but interpreted as array by json processor!!!
INFO: e_c=[{"client_id":"67327","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67327","external_client_id":"","number":"46","name":"Connie Rits","attention":"","street":"Lindenstraat 4 A","city":"Sint-Katherina-Lombeek","postal_code":"1742","country":"BELGIE","tax_code":"","email_address":"                                                                                ","phone_number":"02\/5823041","website":"","state":"active","private_note":"[5,6,7]","invoice_count":0,"receipt_count":0,"quote_count":0,"contacts":[],"details":[]}]

{"client_id":"67445","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67445","external_client_id":"","number":"176","name":"Hedwig Vermeulen","attention":"","street":"Iepenlaan 22","city":"Ternat","postal_code":"1740","country":"Belgi\u00eb","tax_code":"          ","email_address":"herman.vierendeels@telenet.be","phone_number":"02 5821924","website":"","state":"active","private_note":"vierendeels [6ok]","invoice_count":0,"receipt_count":0,"quote_count":"1","contacts":[{"contact_id":"44582","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67445\/contacts\/44582","contact_name":"Herman","email_address":"herman.vierendeels@gmail.com","phone_number":""}],"details":[]}

{"client_id":"67186","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67186","external_client_id":"","number":"1","name":"Van Rossem LTC nv - Horta","attention":"","street":"Assesteenweg 115","city":"Ternat","postal_code":"1740","country":"Belgi\u00eb","tax_code":"","email_address":"","phone_number":"","website":"","state":"active","private_note":"","invoice_count":0,"receipt_count":0,"quote_count":0,"contacts":[],"details":[]},{"client_id":"67187","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67187","external_client_id":"","number":"2","name":"Bianca Flamant","attention":"tav ","street":"Onkerzeelstraat 111","city":"Geraardsbergen","postal_code":"9500","country":"BE","tax_code":"geen btw","email_address":"x@y","phone_number":"02558","website":"website","state":"active","private_note":"","invoice_count":0,"receipt_count":0,"quote_count":0,"contacts":[],"details":[]}
***********/
