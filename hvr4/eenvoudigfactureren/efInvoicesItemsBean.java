package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class efInvoicesItemsBean{
 public long item_id;
 public long invoice_id;
 public String uri;
 public double amount;//Bedrag per eenheid van item zonder BTW. Verplicht op te geven bij creatie of als alternatief kan veld amount_with_tax opgegeven worden. tot 6 decimalen nauwkeurig opgegeven worden, echter wordt het bedrag slechts tot op 2 decimalen nauwkeurig op de factuur getoond. Het maximumbedrag is 9.999.999,99
 public double amount_with_tax;//Bedrag per eenheid van item inclusief BTW. Wordt automatisch berekend of kan opgegeven worden als alternatief voor het verplichte veld amount. Veld wordt enkel gebruikt bij opgave en wordt niet gebruikt bij weergave van het factuur-item
 public double quantity;
 public String unit;//Eenheid van het item. Niet verplicht. Maximum 10 karakters lang en moet beginnen met een letter. Mag geen spaties bevatten
 public String stockitem_code;//	
 public String quantity_with_unit;//Aantal en eenheid van het item gescheiden door een spatie. Kan enkel gebruikt worden tijdens aanmaken of bewerken. Kan niet gebruikt worden in combinatie met veld quantity of unit
 public String description;
 public double total_without_tax;//Totaal bedrag van item zonder BTW. Wordt berekend a.d.h.v. veld amount en quantity
 public double tax_rate;//BTW-percentage van item. Moet overeenkomen met één van de BTW-percentages van de factuur of moet ‘0’ zijn. Indien niet opgegeven wordt ‘0’ verondersteld
 public String tax_rate_special_status;//Speciale status van tax_rate.Kan enkel gebruikt worden indien het BTW-percentage 0 is. Het veld is optioneel en kan maximum 20 karakters lang zijn.	Verlegd*
 public double total_with_tax;//Totaal bedrag van item inclusief BTW. Wordt berekend a.d.h.v. veld amount, quantity en tax_rate
}//efInvoicesItemsBean
