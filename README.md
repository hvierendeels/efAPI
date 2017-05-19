# efAPI 
herman vierendeels,may 2017,belgium  

eenvoudigfactureren.be is a belgian invoicing service.  
They provide an api to handle clients, invoices, quotes ,stockitems and more.  
This project contains code and json-texts to demonstrate some use of their api in java.  

The java code uses apache HttpClient for communication and google Gson for conversion from/to json--javabean.  

Following extra libs are needed   
httpcore-4.4.6.jar   
httpclient-4.5.3.jar   
 commons-logging-1.2.jar   
 commons-codec-1.10.jar   
gson-2.8.0.jar   

When downloading zipfile from project page, you get  
efAPI-master.zip  

Unzipping gives following directory structure in efAPI-master   
.gitignore   
hvr4   
json   
lib   
LICENSE   
README.md   
test_efAPI.java   

To compile java code:   
change directory to efAPI-master    

export CLASSPATH=./:./lib/httpcore-4.4.6.jar:lib/httpclient-4.5.3.jar:./lib/commons-logging-1.2.jar:./lib/commons-codec-1.10.jar:./lib/gson-2.8.0.jar   
javac test_efAPI.java    

Than you can execute e.g.  
 java test_efAPI -username demo@eenvoudigfactureren.be -password demo -domain activities -action get   

There is also a jar , efAPI.jar , in lib which can directly be used  
java  -jar lib/efAPI.jar -username demo@eenvoudigfactureren.be -password demo -domain activities -action get  

efAPI.jar has been constructed by:  
jar cmf test_efAPI.manifest lib/efAPI.jar test_efAPI.class hvr4/eenvoudigfactureren/*.class  


Further example calls can be found at the top of the file test_efAPI.java



