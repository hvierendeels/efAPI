package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//informatieve lijn , alles op 0 null

public class efInvoicesPaymentsBean{
 public long payment_id;
 public long invoice_id;
 public String uri;
 public Date date;
 public double amount;
 public String description;
 public String remaining_amount=null;//"YES"
}//efInvoicesPaymentsBean
