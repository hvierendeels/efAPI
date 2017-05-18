package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;

//Nieuwe optionele velden factuuropties per klant client.default_days_due, client.default_days_valid, client.default_language, client.default_currency
public class efClientsBean{
 public long client_id;
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
 public String private_note;
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
