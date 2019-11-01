//herman vierendeels,may 2017,belgium
//export CLASSPATH=./:/home/tshvr/my/:/opt/httpcomponents-core-4.4.6/lib/httpcore-4.4.6.jar:/opt/httpcomponents-client-4.5.3/lib/httpclient-4.5.3.jar:/opt/commons-logging-1.2/commons-logging-1.2.jar:/opt/commons-codec-1.10/commons-codec-1.10.jar:/opt/gson-2.8.0.jar
//  -Djava.util.logging.config.file=logging.properties
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain activities -action get
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action get
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action get -id 258359 -format json
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action get -number ''
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action jsontest -json_file client_good.txt
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action post -json_file client_good.txt
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action trim_some_fields
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain numberToId
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain invoices -action get   //nog doen type<> invoice
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain invoices -action get -id 14
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain invoices -action get -number 2017-0001
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain invoices -action blank_note -number 2017-0001
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain invoices -action post -json_file invoice_good.txt
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain quotes -action get
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain quotes -action fix_days_valid 
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain quotes -action get -id 21
//java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain quotes -action post -json_file quotes_good.txt


//if password xxxx:Exception in thread "main" java.lang.RuntimeException: sc=401 rqln=GET https://eenvoudigfactureren.be/api/v1/clients?format=json&filter=number=1 HTTP/1.1

//Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
//trustStore is: /opt/jdk1.7.0_03/jre/lib/security/cacerts
//trustStore type is : jks
//https://stackoverflow.com/questions/6908948/java-sun-security-provider-certpath-suncertpathbuilderexception-unable-to-find
//http://magicmonster.com/kb/prg/java/ssl/pkix_path_building_failed.html
//export certificats from browser
//cp -i /opt/jdk1.7.0_03/jre/lib/security/cacerts /temp/
//keytool -list -v -keystore /temp/cacerts
//keytool -import -alias USERTrustRSACertificationAuthority -keystore /temp/cacerts -file USERTrustRSACertificationAuthority.crt
//keytool -import -alias USERTrustRSACertificationAuthority -keystore /opt/jdk1.7.0_03/jre/lib/security/cacerts -file USERTrustRSACertificationAuthority.crt
//java  -Djavax.net.debug=SSL -Djavax.net.ssl.trustStore=/temp/cacerts test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain clients -action get

import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Type;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hvr4.eenvoudigfactureren.efAPI;
import hvr4.eenvoudigfactureren.efClientsBean;
import hvr4.eenvoudigfactureren.efActivitiesBean;
import hvr4.eenvoudigfactureren.efQuotesBean;
import hvr4.eenvoudigfactureren.efInvoicesBean;

public class test_efAPI{

 public final static String readJsonLine(String json_file_s) throws Exception{
  FileInputStream fis=new FileInputStream(json_file_s);
  BufferedReader brd=new BufferedReader(new InputStreamReader(fis));
  String json_line=brd.readLine();
  return(json_line);
 }//readJsonLine

 public final static void main(String[] argv) throws Exception {

  String username=null;
  String password=null;
  String domain=null;
  String action=null;
  long from_id;
  long to_id;
  String id=null;
  String number=null;
  String format=null;

  String json_line;
  String json_file=null;
  List<efClientsBean> ecbs=null;
  efClientsBean ecb=null;
  efClientsBean ecb1=null;
  HashMap<String,Long> n_2_id=null;
  efQuotesBean eqb=null;
  efQuotesBean eqb1=null;
  efInvoicesBean eib=null;
  efInvoicesBean eib1=null;
  String e_c=null;
  List<efQuotesBean> eqbs=null;

  if(argv.length==0) {System.exit(1);}

  for (int i = 0; i < argv.length; ++i) {
   String arg = argv[i];
   if (arg.equalsIgnoreCase("-username")) {
    username=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-password")) {
    password=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-domain")) {
    domain=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-action")) {
    action=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-json_file")) {
    json_file=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-id")) {
    id=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-number")) {
    number=argv[i + 1];
    ++i;
    continue;
   }
   if (arg.equalsIgnoreCase("-format")) {
    format=argv[i + 1];
    ++i;
    continue;
   }
   else{
    System.out.println(arg);
    System.exit(1);
   }
  }//for


  efAPI ef_api=new efAPI(username,password);

  if("numberToId".equals(domain)){
   n_2_id=ef_api.numberToId();
   for(Map.Entry<String,Long> e : n_2_id.entrySet()){
    System.out.println("number="+e.getKey()+" id="+e.getValue());
   }//for
   System.exit(0);
  }//numberToId

  if("clients".equals(domain)){
   if(action.equals("trim_some_fields")){
    int j=0;
    ecbs=ef_api.getClients();
    for(int i=0;i<ecbs.size();i++){
     ecb=ecbs.get(i);
     String phone_number_trimmed=ecb.phone_number.trim();
     String name_trimmed=ecb.name.trim();
     String email_trimmed=ecb.email_address.trim();
     String tax_code_trimmed=ecb.tax_code.trim();
     if(! ( ecb.email_address.equals(email_trimmed) && ecb.tax_code.equals(tax_code_trimmed) && ecb.name.equals(name_trimmed) && ecb.phone_number.equals(phone_number_trimmed) ) ){
      //System.out.println("*"+ecb.email_address+"*"+" "+email_trimmed);
      //System.out.println("*"+ecb.tax_code+"*"+" "+tax_code_trimmed);
      j++;
      System.out.println(ecb);
      ecb.name=name_trimmed;
      ecb.email_address=email_trimmed;
      ecb.tax_code=tax_code_trimmed;
      ecb.phone_number=phone_number_trimmed;
      ecb1=ef_api.doPutClient(ecb);
      System.out.println(ecb1);
      if(j==30) break;
     }//if
    }//for
    System.exit(0);
   }//trim_some_fields
   else if(action.equals("get")){
    if(number!=null){
     ecb=ef_api.getClientByNumber(number);
     System.out.println(ecb);
    }//number
    else if(id!=null){
     long id_l=(new Long(id)).longValue();
     ecb=ef_api.getClientById(id_l);
     System.out.println(ecb);
     if("json".equals(format)){
      e_c=ef_api.getJsonClientById(id_l);
      System.out.println(e_c);
     }
    }//id
    else{
     ecbs=ef_api.getClients();
     System.out.println("ecbs size="+ecbs.size());
     for(int i=0;i<ecbs.size();i++){
      ecb=ecbs.get(i);
      System.out.println(ecb);
     }//for
     System.exit(0);
    }//else
    System.exit(0);
   }//get
   if(action.equals("post")){
    json_line=readJsonLine(json_file);
    ecb=ef_api.getClientsBeanFromJson(json_line);
    System.out.println("ecb="+ecb);
    ecb1=ef_api.doPostClient(ecb);
    System.out.println("ecb1="+ecb1);
    System.exit(0);
   }//post
   if(action.equals("jsontest")){
    json_line=readJsonLine(json_file);
    System.out.println("json_line="+json_line);
    ecb=ef_api.getClientsBeanFromJson(json_line);
    System.out.println("ecb="+ecb);
    System.exit(0);
   }//jsontest
  }//clients

  if("quotes".equals(domain)){
   if("fix_days_valid".equals(action)){
    System.out.println("fix_days_valid begin");
    eqbs=ef_api.getQuotes();
    for(int i=0;i<eqbs.size();i++){
     eqb=eqbs.get(i);
     if(eqb.days_valid==0){
      System.out.println("eqb="+eqb);
      eqb.days_valid=30;
      eqb1=ef_api.doPutQuote(eqb);
      System.out.println("eqb1="+eqb1);
     }//if
    }//for
    System.out.println("fix_days_valid end");
    System.exit(0);
   }//days_valid
   else if("get".equals(action)){
    if(id!=null){
     e_c=ef_api.getQuoteAsJson((new Long(id)).longValue());
     System.out.println("e_c="+e_c);
     eqb=ef_api.getQuote((new Long(id)).longValue());
     System.out.println("eqb="+eqb);
    }//id
    else{
     eqbs=ef_api.getQuotes();
     for(int i=0;i<eqbs.size();i++){
      eqb=eqbs.get(i);
      System.out.println(eqb);
     }//for
    }//list
    System.exit(0);
   }//get
   else if("post".equals(action)){
    json_line=readJsonLine(json_file);
    eqb=ef_api.getQuotesBeanFromJson(json_line);
    eqb1=ef_api.doPostQuote(eqb);
    System.out.println(eqb1);
    System.exit(0);
   }//post
  }//quotes

  if("invoices".equals(domain)){
   if("blank_note".equals(action)){
    if(number!=null){
      eib=ef_api.getInvoiceByNumber(number);
      eib.note="";
      eib1=ef_api.doPutInvoice(eib);
      System.out.println("eib1="+eib1);
    }//number
    System.exit(0);
   }//blank_note
   else if("get".equals(action)){
    if(id!=null){
     e_c=ef_api.getInvoiceAsJson((new Long(id)).longValue());
     System.out.println("e_c="+e_c);
     eib=ef_api.getInvoice((new Long(id)).longValue());
     System.out.println("eib="+eib);
     if(eib.days_due==0){
      eib.days_due=30;
      eib1=ef_api.doPutInvoice(eib);
      System.out.println("eib1="+eib1);
     }//0
    }//id
    else if(number!=null){
     eib=ef_api.getInvoiceByNumber(number);
     System.out.println("eib="+eib);
     String eib_json=ef_api.getInvoiceAsJson(eib.invoice_id);
     System.out.println("eib_json="+eib_json);
     System.exit(0);
    }//number
    else{
     List<efInvoicesBean> eibs=ef_api.getInvoices();
     for(int i=0;i<eibs.size();i++){
      eib=eibs.get(i);
      System.out.println(eib);
     }//for
    }//list
    System.exit(0);
   }//get
   else if("post".equals(action)){
    json_line=readJsonLine(json_file);
    System.out.println("json_line="+json_line);
    eib=ef_api.getInvoicesBeanFromJson(json_line);
    eib1=ef_api.doPostInvoice(eib);
    System.out.println("eib1="+eib1);
    System.exit(0);
   }//post
  }//invoices
  else if("activities".equals(domain)){
   List<efActivitiesBean> eabs=ef_api.getActivities();
   for(int i=0;i<eabs.size();i++){
    efActivitiesBean eab=eabs.get(i);
    System.out.println(eab);
   }//for
   System.exit(0);
  }//activities

  if(1==1) System.exit(0);



  for(int i=0;i<800;i++){
   try{
    ecb1=ef_api.getClientByNumber(""+i);
    System.out.println(ecb1);
   }
   catch(Exception excptn){
    System.out.println(excptn);
   }
  }//for
  
  //ef_api.close();
 }//main
}//test_efAPI{
