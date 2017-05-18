package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class efInvoicesEventsBean{
 public long event_id;
 public long invoice_id;
 public String uri;
 public Date date;
 public String type;
 public String content;
 public List  <efRecipientsBean> recipients;
}//efInvoicesEventsBean
