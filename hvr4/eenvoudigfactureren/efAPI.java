package hvr4.eenvoudigfactureren;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.lang.reflect.Type;

import java.net.URI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.*;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext; 
import com.google.gson.JsonElement; 
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.RequestLine;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.HttpHeaders;



import hvr4.eenvoudigfactureren.efActivitiesBean;
import hvr4.eenvoudigfactureren.efClientsBean;
import hvr4.eenvoudigfactureren.efContactsBean;
import hvr4.eenvoudigfactureren.efDetailsBean;
import hvr4.eenvoudigfactureren.efInvoicesBean;
import hvr4.eenvoudigfactureren.efInvoicesResultBean;
import hvr4.eenvoudigfactureren.efQuotesBean;
import hvr4.eenvoudigfactureren.efQuotesResultBean;

public class efAPI{

 private static final Logger logger=Logger.getLogger(efAPI.class.getName());

 private static final String ef_hostname="eenvoudigfactureren.be";
 private static final String ef_api_url_s="https://eenvoudigfactureren.be/api/v1/";

 public static final String sdfx_pattern="yyyy-MM-dd HH:mm:ss";
 public static final SimpleDateFormat sdfx=new SimpleDateFormat(sdfx_pattern);
 public static final String sdf1_pattern="yyyy-MM-dd";
 public static final SimpleDateFormat sdf1=new SimpleDateFormat(sdf1_pattern);

 public static final String $clients="clients";
 public static final String $client_created="client created";
 public static final String $client_removed="client removed";
 public static final String $client_updated="client updated";
 public static final String $invoices="invoices";
 public static final String $invoice_created="invoice created";
 public static final String $invoice_updated="invoice updated";
 public static final String $quotes="quotes";
 public static final String $quote_updated="quote updated";
 public static final String $activities="activities";
 public static final String $format_json="format=json";
 public static final String $application_json_utf_8="application/json;charset=UTF-8";
 public static final String $application_json="application/json";

 public static final String readEntity(HttpEntity entity) throws Exception{
  StringBuffer sb=new StringBuffer();
  byte[] buf=new byte[4096];
  InputStream is=entity.getContent();
  int rl=is.read(buf);
  while(rl!=-1){
   String s=new String(buf,0,rl);//otherwise 0x at end of string
   sb.append(s);
   rl=is.read(buf);
  }//while
  return(sb.toString());
 }//readEntity

 public static final Gson getGson(){
  Gson rv=null;
  GsonBuilder gbld=new GsonBuilder();
  gbld=gbld.setDateFormat(sdf1_pattern);
  //gbld=gbld.setLenient();
  //gbld.serializeNulls();//By default, Gson omits all fields that are null during serialization
  gbld=gbld.registerTypeAdapter(Date.class,new JsonDeserializer<Date>(){
   @Override
   public Date deserialize(final JsonElement json,final Type typeOfT,final JsonDeserializationContext context) throws JsonParseException{
    //logger.info("json="+json+" typeOfT="+typeOfT+" context="+context);
    Date rv=null;
    try {
     String jsonAsString=json.getAsString();
     if(jsonAsString.length()==0);//empty string
     else if(jsonAsString.length()==sdf1_pattern.length()) rv=sdf1.parse(jsonAsString);
     else rv=sdfx.parse(jsonAsString);
    } catch (Exception e) {
     throw(new RuntimeException(e));
    }
    return(rv);
   }//deserialize
  });//register
  rv=gbld.create();
  return(rv);
 }//getGson

 private CloseableHttpClient httpclient=null;
 private HttpClientContext localContext=null;
 private HttpHost host=null;

 public Gson gson=null;
 public Type listType_clients=null;
 public Type listType_quotes=null; 
 public Type listType_invoices=null; 
 public Type listType_activities=null; 

 public efAPI(String username,String password){
  super();
  host=new HttpHost(ef_hostname,443,"https");
  CredentialsProvider credsProvider=new BasicCredentialsProvider();
  AuthScope authscope=new AuthScope(host);
  UsernamePasswordCredentials upc=new UsernamePasswordCredentials(username,password);
  credsProvider.setCredentials(authscope,upc);
  CookieStore cookieStore = new BasicCookieStore();
  httpclient=HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
  AuthCache authCache=new BasicAuthCache();
  BasicScheme basicAuth=new BasicScheme();
  authCache.put(host,basicAuth);
  localContext=HttpClientContext.create();
  localContext.setAuthCache(authCache);
  localContext.setCookieStore(cookieStore);

  this.gson=getGson();
  listType_clients=new TypeToken<ArrayList<efClientsBean>>(){}.getType();
  listType_quotes=new TypeToken<ArrayList<efQuotesBean>>(){}.getType();
  listType_invoices=new TypeToken<ArrayList<efInvoicesBean>>(){}.getType();
  listType_activities=new TypeToken<List<efActivitiesBean>>(){}.getType();
 }//efAPI constructor

 //invoices
 public String efInvoicesBeanToJson(efInvoicesBean eib){
  String eib_json=gson.toJson(eib);
  return(eib_json);
 }//efInvoicesBeanToJson

 public efInvoicesBean doPutInvoice(efInvoicesBean invoice) throws Exception{
  efInvoicesBean rv=null;
  String json_line=gson.toJson(invoice);
  String uri_s=ef_api_url_s+$invoices+"/"+invoice.invoice_id+"?"+$format_json;
  String e_c=doHttpPut(json_line,uri_s);
  logger.finest("e_c="+e_c);
  efInvoicesResultBean eirb=gson.fromJson(e_c,efInvoicesResultBean.class);
  if(eirb.success.equals($invoice_updated)){
    rv=getInvoice(invoice.invoice_id);
   }//success
   else{
    logger.warning("put invoice "+invoice+" e_c="+e_c);
   }//else
  return(rv);
 }//doPutInvoice

 public efInvoicesBean doPostInvoice(efInvoicesBean invoice) throws Exception{
  efInvoicesBean rv=null;
  //todo put checks in function apart
  if(invoice.days_due==0) invoice.days_due=30;//zet hier default uit settings
  if(invoice.items!=null){
   for(int i=0;i<invoice.items.size();i++){
    efInvoicesItemsBean eiib=invoice.items.get(i);
    eiib.item_id=0l;
    eiib.uri="";
   }
  }//
  if(invoice.payments!=null){
   for(int i=0;i<invoice.payments.size();i++){
    efInvoicesPaymentsBean eipb=invoice.payments.get(i);
    eipb.payment_id=0l;
    eipb.uri="";
   }
  }//
  if(invoice.remarks!=null){
   for(int i=0;i<invoice.remarks.size();i++){
    efInvoicesRemarksBean eirb=invoice.remarks.get(i);
    eirb.remark_id=0l;
    eirb.uri="";
   }
  }//

  String json_line=gson.toJson(invoice);
  logger.finest("json_line="+json_line);
  String e_c=doPostInvoice(json_line);
  efInvoicesResultBean eirb=gson.fromJson(e_c,efInvoicesResultBean.class);
  if(eirb.success.equals($invoice_created)){
   rv=getInvoice(eirb.invoice_id);  
  }//success
  else{
   logger.warning("invoice "+invoice+" e_c="+e_c);
  }//else
  return(rv);
 }//doPostInvoice

 public String doPostInvoice(String json_line) throws Exception{
  String rv=null;
  logger.finest("json_line="+json_line);
  String uri_s=ef_api_url_s+$invoices+"?"+$format_json;
  rv=doHttpPost(json_line,uri_s);
  //{"error":"structured_message too short"}
  //{"error":"amount can not be 0.00"}
  //{"code":400,"error":"Syntax error"} todo sc=200 in this case!!  
  //{"success":"invoice created","invoice_id":"145786","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/invoices\/145786"}
  logger.finest("rv="+rv);
  return(rv);
 }//doPostInvoice

 public List<efInvoicesBean> getInvoices() throws Exception{
  List<efInvoicesBean> rv=null;
  String uri_s=ef_api_url_s+$invoices+"?"+$format_json+"&sort=-invoice_id";
  String e_c=doHttpGet(uri_s);
  rv=gson.fromJson(e_c,listType_invoices);
  return(rv);
 }//getInvoices 

 public String getInvoiceAsJson(long invoice_id) throws Exception{
  String rv=null;
  String uri_s=ef_api_url_s+$invoices+"/"+invoice_id+"?"+$format_json;
  rv=doHttpGet(uri_s);
  return(rv);
 }//getInvoiceAsJson

 public efInvoicesBean getInvoiceByNumber(String number) throws Exception{
  efInvoicesBean rv=null;
  String uri_s=ef_api_url_s+$invoices+"?"+$format_json+"&filter=number="+number;
  String e_c=doHttpGet(uri_s);
  List<efInvoicesBean> dummy=gson.fromJson(e_c,listType_invoices);
  if(dummy.size()==1){
   rv=dummy.get(0);
  }
  return(rv);
 }//getInvoiceByNumber
 
 public efInvoicesBean getInvoice(long invoice_id) throws Exception{
  efInvoicesBean rv=null;
  String e_c=getInvoiceAsJson(invoice_id);
  logger.finest("e_c="+e_c);
  rv=gson.fromJson(e_c,efInvoicesBean.class);
  return(rv);
 }//getInvoice

  public efInvoicesBean getInvoicesBeanFromJson(String json_line){
  efInvoicesBean rv=null;
  if(json_line.startsWith("[")){
   ArrayList<efInvoicesBean> dummy=gson.fromJson(json_line,listType_invoices);
   if(dummy.size()==1){
    rv=dummy.get(0);
   }//
   else{
   logger.warning("size<>1 ("+dummy.size()+") bean in "+json_line);
   }//>0
  }//[
  else{
   rv=gson.fromJson(json_line,efInvoicesBean.class);
  }
  return(rv);
 }//getInvoicesBeanFromJson

 //invoices

 public String efQuotesBeanToJson(efQuotesBean eqb){
  String eqb_json=gson.toJson(eqb);
  return(eqb_json);
 }//efQuotesBeanToJson

 public String doPostQuote(String json_line) throws Exception{
  String rv=null;
  logger.finest("json_line="+json_line);
  String uri_s=ef_api_url_s+$quotes+"?"+$format_json;
  rv=doHttpPost(json_line,uri_s);
  return(rv);
  //{"success":"quote created","quote_id":"145604","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/quotes\/145604"}
  //{"error":"number not unique"}
  //{"error":"items cannot be empty"}
 }//doPostQuote

 public efQuotesBean doPostQuote(efQuotesBean quote) throws Exception{
  efQuotesBean rv=null;
  if(quote.days_valid==0) quote.days_valid=30;
  //todo checks in function apart
  if(quote.items!=null){
   for(int i=0;i<quote.items.size();i++){
    efQuotesItemsBean eqib=quote.items.get(i);
    eqib.item_id=0l;
    eqib.uri="";
   }
  }//
  if(quote.remarks!=null){
   for(int i=0;i<quote.remarks.size();i++){
    efQuotesRemarksBean eqrb=quote.remarks.get(i);
    eqrb.remark_id=0l;
    eqrb.uri="";
   }
  }//
  String json_line=gson.toJson(quote);
  String e_c=doPostQuote(json_line);
  efQuotesResultBean eqprb=gson.fromJson(e_c,efQuotesResultBean.class);
  rv=getQuote(eqprb.quote_id);  
  return(rv);
 }//doPostQuote

 public efQuotesBean doPutQuote(efQuotesBean quote) throws Exception{
  efQuotesBean rv=null;
  String json_line=gson.toJson(quote);
  String uri_s=ef_api_url_s+$quotes+"/"+quote.quote_id+"?"+$format_json;
  String e_c=doHttpPut(json_line,uri_s);
  logger.finest("e_c="+e_c);
  // e_c={"success":"quote updated"}
  efQuotesResultBean eqrb=gson.fromJson(e_c,efQuotesResultBean.class);
  if(eqrb.success.equals($quote_updated)){
    rv=getQuote(quote.quote_id);
   }//success
   else{
    logger.warning("put quote "+quote+" e_c="+e_c);
   }//else
  return(rv);
 }//doPutQuote
 //
 //clients
 public efClientsBean doPostClient(efClientsBean client) throws Exception{
   efClientsBean rv=null;
   client.client_id=0l;
   client.uri="";
   if(client.contacts!=null){
    for(int i=0;i<client.contacts.size();i++){
     efContactsBean ent=client.contacts.get(i);
     ent.contact_id=0l;
     ent.client_id=0l;
     ent.uri="";
    }
   }//not null
   if(client.details!=null){
    for(int i=0;i<client.details.size();i++){
     efDetailsBean edb=client.details.get(i);
     edb.detail_id=0l;
     edb.client_id=0l;
     edb.uri="";
    }//for
   }//not null
   String json_line=gson.toJson(client);
   String uri_s=ef_api_url_s+$clients+"?"+$format_json;
   String e_c=doHttpPost(json_line,uri_s);
   //{"success":"client created","client_id":"67290","uri":"https:\/\/eenvoudigfactureren.be\/api\/v1\/clients\/67290"}[\r][\n]"
   //{"error":"detail_id unknown"}
   //Exception in thread "main" java.lang.RuntimeException: doPost sc=400 rqln=POST https://eenvoudigfactureren.be/api/v1/clients?format=json HTTP/1.1 rv={"error":"phone_number too long"}
   //Exception in thread "main" java.lang.RuntimeException: doPostClient sc=200 rqln=POST https://eenvoudigfactureren.be/api/v1/clients?format=json HTTP/1.1 e_c={"code":400,"error":"Malformed UTF-8 characters, possibly incorrectly encoded"} ckey 65 ?
   efClientsResultBean ecrb=gson.fromJson(e_c,efClientsResultBean.class);
   if(ecrb.success.equals($client_created)){
    rv=getClientById(ecrb.client_id);
   }//success
   else{
    logger.warning("client "+client+" e_c="+e_c);
   }//else
   return(rv);
 }//doPostClient

 public efClientsBean doPutClient(efClientsBean client) throws Exception{
  efClientsBean rv=null;
  String json_line=gson.toJson(client);
  String uri_s=ef_api_url_s+$clients+"/"+client.client_id+"?"+$format_json;
  String e_c=doHttpPut(json_line,uri_s);
  logger.finest("e_c="+e_c);
  efClientsResultBean ecrb=gson.fromJson(e_c,efClientsResultBean.class);
  if(ecrb.success.equals($client_updated)){
   rv=getClientById(client.client_id);
  }//success
  else{
   logger.warning("put client "+client+" e_c="+e_c);
  }//else
  return(rv);
 }//doPutClient

 public efClientsBean getClientsBeanFromJson(String json_line){
  efClientsBean rv=null;
  if(json_line.startsWith("[")){
   ArrayList<efClientsBean> dummy=gson.fromJson(json_line,listType_clients);
   if(dummy.size()==1){
    rv=dummy.get(0);
   }//
   else{
   logger.warning("size<>1 ("+dummy.size()+") bean in "+json_line);
   }//>0
  }//[
  else{
   rv=gson.fromJson(json_line,efClientsBean.class);
  }
  return(rv);
 }//getClientsBeanFromJson

 public ArrayList<efClientsBean> getClients() throws Exception{
  ArrayList<efClientsBean> rv=null;
  String uri_s=ef_api_url_s+$clients+"?"+$format_json+"&sort=number";//number todo eerst sort dan dormat?
  String e_c=doHttpGet(uri_s);
  Type listType=new TypeToken<ArrayList<efClientsBean>>(){}.getType();
  rv=gson.fromJson(e_c,listType);
  logger.finest("rv.size="+rv.size());
  return(rv);
 }//getClients

 public efClientsBean getClient(String uri_s) throws Exception{
  efClientsBean rv=null;
  String e_c=getJsonClient(uri_s);
  rv=getClientsBeanFromJson(e_c);
  return(rv);
 }//getClient

 public String getJsonClient(String uri_s) throws Exception{
  String rv=null;
  rv=doHttpGet(uri_s);
  return(rv);
 }//getJsonClient

 public String getJsonClientById(long id) throws Exception{
  String uri_s=ef_api_url_s+$clients+"/"+id+"?"+$format_json;
  String json_text=getJsonClient(uri_s);
  return(json_text);
 }//getJsonClientById

 public efClientsBean getClientById(long id) throws Exception{
  efClientsBean rv=null;
  String uri_s=ef_api_url_s+$clients+"/"+id+"?"+$format_json;
  rv=getClient(uri_s);
  return(rv);
 }//getClientById

 public efClientsBean getClientByNumber(String number) throws Exception{
  efClientsBean rv=null;
  String uri_s=ef_api_url_s+$clients+"?"+$format_json+"&filter=number="+number;
  logger.finest("uri_s="+uri_s);
  rv=getClient(uri_s);
  return(rv);
 }//getClientByNumber

 public void deleteClientByNumber(String number) throws Exception{
  efClientsBean ecb=getClientByNumber(number);
  deleteClientById(ecb.client_id);  
 }//deleteClientByNumber

 public void deleteClientById(long client_id) throws Exception{
  String uri_s=ef_api_url_s+$clients+"/"+client_id+"?"+$format_json;
  String e_c=doHttpDelete(uri_s);
  logger.finest("e_c="+e_c);//INFO: e_c={"success":"client removed"}
  efClientsResultBean ecrb=gson.fromJson(e_c,efClientsResultBean.class);
  if(ecrb.success.equals($client_removed)){}//success
  else{
   logger.warning("client "+client_id+" e_c="+e_c);
  }//else
 }//deleteClientById
 //clients

 public String getQuoteAsJson(long quote_id) throws Exception{
  String rv=null;
  String uri_s=ef_api_url_s+$quotes+"/"+quote_id+"?"+$format_json;
  rv=doHttpGet(uri_s);
  return(rv);
 }//getQuoteAsJson

 public efQuotesBean getQuote(long quote_id) throws Exception{
  efQuotesBean rv=null;
  String e_c=getQuoteAsJson(quote_id);
  logger.finest("e_c="+e_c);
  rv=gson.fromJson(e_c,efQuotesBean.class);
  return(rv);
 }//getQuote

 public List<efQuotesBean> getQuotes() throws Exception{
  List<efQuotesBean> rv=null;
  String uri_s=ef_api_url_s+$quotes+"?"+$format_json+"&sort=-quote_id";//this sort works!
  String e_c=doHttpGet(uri_s);
  rv=gson.fromJson(e_c,listType_quotes);
  return(rv);
 }//getQuotes 

  public efQuotesBean getQuotesBeanFromJson(String json_line){
  efQuotesBean rv=null;
  if(json_line.startsWith("[")){
   ArrayList<efQuotesBean> dummy=gson.fromJson(json_line,listType_quotes);
   if(dummy.size()==1){
    rv=dummy.get(0);
   }//
   else{
   logger.warning("size<>1 ("+dummy.size()+") bean in "+json_line);
   }//>0
  }//[
  else{
   rv=gson.fromJson(json_line,efQuotesBean.class);
  }
  return(rv);
 }//getQuotesBeanFromJson

 public List<efActivitiesBean> getActivities() throws Exception{
  List<efActivitiesBean> rv=null;
  String uri_s=ef_api_url_s+$activities+"?"+$format_json;//sort not possible
  //todo put this in subroutine expected type [ or single
  String e_c=doHttpGet(uri_s);
  rv=gson.fromJson(e_c,listType_activities);
  return(rv);
 }//getActivities()

 public HashMap<String,Long> numberToId() throws Exception{
  //if clients with nummber "" or number "null" -> n2id contains less entries than there are clients
  HashMap<String,Long> rv=new HashMap<String,Long>();
  ArrayList<efClientsBean> ecbs=getClients();
  for(int i=0;i<ecbs.size();i++){
   efClientsBean ecb=ecbs.get(i);
   Long id_l=new Long(ecb.client_id);
   Long dummy=rv.get(ecb.number);
   if(dummy==null){
    rv.put(ecb.number,id_l);
   }
   else{
    logger.warning("n2id contains already *"+ecb.number+"* for key "+id_l);
   }
  }//for
  return(rv); 
 }//numberToId

 public String doHttpDelete(String uri_s) throws Exception{
  String e_c=null;
  HttpDelete httpdelete=new HttpDelete(uri_s);
  RequestLine rqln=httpdelete.getRequestLine();
  logger.finest("uri_s="+uri_s+" rqln="+rqln);
  CloseableHttpResponse response=httpclient.execute(host,httpdelete,localContext);
  StatusLine rsl=response.getStatusLine();
  //logger.info("rsl="+rsl.toString());//INFO: rsl=HTTP/1.1 200 OK
  int sc=rsl.getStatusCode();
  if(sc!=200){throw(new RuntimeException("sc="+sc+" rqln="+rqln));}
  //logger.info("sc="+sc);
  HttpEntity entity=response.getEntity();
  if(entity==null){throw(new RuntimeException("entity==null"));}
  Header ct=entity.getContentType();
  //logger.info("header name="+ct.getName()+" value="+ct.getValue());//INFO: header name=Content-Type value=application/json; charset=utf-8
  //logger.info("entity="+entity.toString());//INFO: entity=ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Chunked: true]}
  e_c=readEntity(entity);
  EntityUtils.consume(entity);
  response.close();
  return(e_c);
 }//doHttpDelete

 public String doHttpPost(String json_text,String uri_s) throws Exception{
  String e_c=null;
  int sc=0;
  StatusLine rsl=null;
  logger.finest("uri_s="+uri_s);
  HttpEntity jso_entity=new StringEntity(json_text,"UTF-8");
  HttpPost httpPost=new HttpPost(uri_s);
  httpPost.setHeader(HttpHeaders.CONTENT_TYPE,$application_json_utf_8);
  httpPost.setHeader(HttpHeaders.ACCEPT,$application_json);
  httpPost.setEntity(jso_entity);
  RequestLine rqln=httpPost.getRequestLine();
  CloseableHttpResponse response=httpclient.execute(httpPost,localContext);
  try{
   rsl=response.getStatusLine();
   //logger.info(rsl.toString());//INFO: rsl=HTTP/1.1 200 OK
   sc=rsl.getStatusCode();
   //logger.info("post sc="+sc);//post sc=201 INFO: HTTP/1.1 201 Created
   HttpEntity entity=response.getEntity();
   //logger.info("entity="+entity.toString());
   Header ct=entity.getContentType();
   //logger.info("header name="+ct.getName()+" value="+ct.getValue());//INFO: header name=Content-Type value=application/json; charset=utf-8
   e_c=readEntity(entity);
   EntityUtils.consume(entity);
  }//try
  finally {response.close();}
  if(sc==201){
  }//201
  else{
   throw(new RuntimeException("doPost sc="+sc+" rqln="+rqln+" rsl="+rsl+" e_c="+e_c+" json_text="+json_text));
  }
  return(e_c);
 }//doHttpPost

 public String doHttpPut(String json_text,String uri_s) throws Exception{
  String rv=null;
  int sc=0;
  logger.finest("uri_s="+uri_s);
  HttpEntity jso_entity=new StringEntity(json_text,"UTF-8");
  HttpPut httpPut=new HttpPut(uri_s);
  httpPut.setHeader(HttpHeaders.CONTENT_TYPE,$application_json_utf_8);
  httpPut.setHeader(HttpHeaders.ACCEPT,$application_json);
  httpPut.setEntity(jso_entity);
  RequestLine rqln=httpPut.getRequestLine();
  CloseableHttpResponse response=httpclient.execute(httpPut,localContext);
  try{
   StatusLine rsl=response.getStatusLine();
   //logger.info(rsl.toString());//INFO: rsl=HTTP/1.1 200 OK
   sc=rsl.getStatusCode();
   HttpEntity entity=response.getEntity();
   //logger.info("entity="+entity.toString());
   Header ct=entity.getContentType();
   //logger.info("header name="+ct.getName()+" value="+ct.getValue());//INFO: header name=Content-Type value=application/json; charset=utf-8
   rv=readEntity(entity);
   EntityUtils.consume(entity);
  }//try
  finally {response.close();}
  if(sc==200){
  }//200
  else{
   throw(new RuntimeException("doPut sc="+sc+" rqln="+rqln+" rv="+rv+" json_text="+json_text));
  }
  return(rv);
 }//doHttpPut

 public String doHttpGet(String uri_s) throws Exception{
  String rv=null;
  logger.finest("uri_s="+uri_s); 
  HttpGet httpget=new HttpGet(uri_s);
  RequestLine rqln=httpget.getRequestLine();
  CloseableHttpResponse response=httpclient.execute(host,httpget,localContext);
  StatusLine rsl=response.getStatusLine();
  logger.finest("rsl="+rsl.toString());
  int sc=rsl.getStatusCode();
  HttpEntity entity=response.getEntity();
  if(entity==null){throw(new RuntimeException("entity==null"));}
  Header ct=entity.getContentType();
  rv=readEntity(entity);
  EntityUtils.consume(entity);
  response.close();
  if(sc!=200){throw(new RuntimeException("sc="+sc+" rqln="+rqln+" rv="+rv));}
  logger.finest("rv="+rv);
  return(rv);
 }//doHttpGet


 public void close() throws Exception {
  if(httpclient!=null){
   httpclient.close();
  }
 }//close

}//efAPI
