# efAPI
herman vierendeels,may 2017,belgium

eenvoudigfactureren.be is a belgian invoicing service.
They provide an api to handle clients, invoices, quotes.
This project contains code and json-texts to demonstrate the use of their api in java.

The java code uses apache HttpClient for communication and google Gson for conversion from/to json-javabean.

Following extra libs are needed   
httpcore-4.4.6.jar   
httpclient-4.5.3.jar   
commons-logging-1.2.jar   
commons-codec-1.10.jar   
gson-2.8.0.jar   


To compile java code:   

when on efAPI/hvr4/eenvoudigfactureren   

export CLASSPATH=../../:../../lib/commons-codec-1.10.jar:../../lib/commons-logging-1.2.jar:../../lib/gson-2.8.0.jar:../../lib/httpclient-4.5.3.jar:../../lib/httpcore-4.4.6.jar

javac *.java


export CLASSPATH=./:../../:../../lib/commons-codec-1.10.jar:../../lib/commons-logging-1.2.jar:../../lib/gson-2.8.0.jar:../../lib/httpclient-4.5.3.jar:../../lib/httpcore-4.4.6.jar
