package hvr4.eenvoudigfactureren;

import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class efReferenceBean{
 public String reference_type;//bv invoices
 public long reference_id;//ID van het object waar naartoe wordt gerefereerd
 public String reference_uri;//Gegenereerd door het systeem.	–
 //Mogelijke activiteitstypes: login, settings_create, settings_update, settings_password_reset, settings_password_forgot, client_create, client_update, client_delete, clientcontact_create, clientcontact_update, clientcontact_delete, clientcontact_delete_all, clientnote_update, invoice_create, invoice_update, invoice_create_copy, invoice_print, invoice_email, invoicepayment_create, invoicepayment_delete, invoicepayment_delete_all, invoicenote_create, invoicenote_delete, invoicenote_delete_all, backup_create, backup_restore, import_csv, import_other_account

}//efReferenceBean

