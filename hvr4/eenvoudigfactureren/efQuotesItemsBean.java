package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;

public class efQuotesItemsBean{
 public long item_id;
 public long quote_id;
 public String uri;
 public double amount;//Bedrag per eenheid (1 quantity) 
 public double amount_with_tax;
 public double quantity;
 public String unit;
 public String stockitem_code;
 public String quantity_with_unit;
 public String description;
 public double total_without_tax;
 public double tax_rate;
 public String tax_rate_special_status;//btw verlegd
 public double total_with_tax;
}//efQuotesItemsBean
