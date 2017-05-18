package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class efInvoicesBean{
 public long invoice_id	;//Gegenereerd door het systeem
 public String uri;
 public String external_invoice_id;
 public long client_id;
 public String client_uri;
 public String type;//creditnote invoice
 public String number;//Uniek nummer van de factuur
 public String reference;
 public Date date;//Datum creatie van de factuur. Wordt gegenereerd indien niet meegegeven. Formaat YYYY-MM-DD
 public Date date_delivery;
 public String status;//open overdue closed.
 public int days_due;//Betalingstermijn in aantal dagen van de factuur. Indien 0 is er geen betalingstermijn. Niet van toepassing voor creditnota (type =creditnote)
 public int days_overdue;
 public Date date_overdue;
 public int discount_percentage;//Kortingspercentage voor de hele factuur
 public int cash_discount_percentage;//Kortingspercentage financiële korting voor de hele factuur. Bedrag moet tussen 0 en 100 liggen. Enkel gehele getallen zijn toegestaan. Indien groter dan 0 is het veld  cash_discount_days_valid verplicht
 public int cash_discount_days_valid;//Aantal dagen dat de financiële korting geldig is. Bedrag moet tussen 0 en 100 liggen. Enkel gehele getallen zijn toegestaan. Indien niet opgegeven is deze waarde 0
 public Date cash_discount_date_valid;//Datum tot wanneer de financiële korting geldig is
 public String currency;
 public double exchange_rate;
 public String show_tax_for_exchange_rate;//yes no. Indien leeg is de standaard waarde yes
 public String language;//taal waarin de factuur wordt opgemaakt dutch french english. Indien leeg wordt de standaard taal (dutch) gebruikt
 public String tax_calculation;//De berekeningswijze BTW van de factuur. total item Standaard total. Bij total BTW berekend op totaalbedrag, bij item berekend per factuurlijn. Wisselen tussen de berekeningswijze kan afrondingsverschillen geven.	total
 public List<efInvoicesItemsBean> items;
 public double discount_total_without_tax;//Totaal bedrag toegekende korting op factuurtotaal zonder BTW. Wordt automatisch berekend
 public double total_without_tax;//Totaal bedrag alle factuur-items zonder BTW inclusief eventuele korting. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public double tax_rate_1;//1e BTW-percentage die kan gebruikt worden op de factuur. Wordt tijdens creatie overgenomen uit de instellingen en kan niet zelf worden opgegeven
 public double total_tax_1;//Totaal bedrag BTW volgens 1e BTW-percentage over alle factuur-items heen. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public double tax_rate_2;
 public double total_tax_2;
 public double tax_rate_3;
 public double total_tax_3;
 public double cash_discount_total;//Totaal bedrag van de toegekende financiële korting op het factuurtotaal. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public double discount_total_with_tax;//Totaal bedrag toegekende korting op het factuurtotaal inclusief BTW. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public double total_with_tax;//Totaal bedrag over alle factuur-items heen inclusief BTW. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public double total_paid;//Totaal bedrag dat reeds werd betaald door klant. Is de sommatie van alle bedragen in payments. Wordt automatisch berekend en kan niet zelf worden opgegeven
 public Long structured_message=null;//Gestructureerde mededeling volgens Belgisch bankformaat (zonder formatering) met lengte van 12 cijfers en met controle op checksum (laatste 2 cijfers). Optioneel mee te geven. Niet van toepassing voor creditnota Wordt  automatisch gegenereerd indien niet opgegeven en indien deze optie werd geactiveerd in de Instellingen
 public String note=null;//Nota die onderaan de factuur wordt getoond
 public List<efTagsBean> tags;//Lijst met tags die aanduiden welke acties reeds werden uitgevoerd voor de factuur
 public List<efInvoicesPaymentsBean> payments;//Lijst met ontvangen betalingen van de factuur
 public List<efInvoicesRemarksBean> remarks;//persoonlijke opmerkingen op de factuur	
 public List<efInvoicesEventsBean> events;//gebeurtenissen van de factuur

 public String toString(){
  String rv=null;
  StringBuffer sb=new StringBuffer();
  sb.append("invoice_id="+invoice_id);
  sb.append(";");
  sb.append("number="+number);
  sb.append(";");
  sb.append("client_id="+client_id);
  sb.append(";");
  sb.append("date="+efAPI.sdf1.format(date));
  sb.append(";");
  sb.append("tax_calculation="+tax_calculation);
  sb.append(";");
  sb.append("items#="+items.size());
  sb.append(";");
  sb.append("remarks#="+remarks.size());
  sb.append(";");
  sb.append("payments#="+payments.size());
  sb.append(";");
  sb.append("total_with_tax="+total_with_tax);
  sb.append(";");
  sb.append("total_paid="+total_paid);
  sb.append(";");
  sb.append("note="+note);
  sb.append(";");
  rv=sb.toString();
  return(rv);
 }//toString

}//efInvoicesBean
