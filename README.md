# Web service course
This repo contains codes & notes from the Web Services course i learned at my University of Applied Sciences 
from 1.3.2019 - 1.5.2019

## This project has been successfully run on Windows 10 OS. First 4 weeks use Java version 1.7 & Tomcat 7, the rest are Java version 1.8 & Tomcat 8.

## Summary
The course i learned is quite long but got compressed in only 2 months time, not to mention the fact that 
some contents i found to be deprecated (XML-RPC). Furthermore, my knowledge in Java intricacies, Spring framework 
and software design are lacking.

This repo is a new project where i'm gonna specify problem requirements, code the solutions & document what
i've learned during the course. I'm gonna try to take this opportunity to learn as much as possible & hone my adaptability skills.

**TL;DR** Minimal knowledges of Java, Spring, Web/Enterprise development. Trying to get back on track by learning 
as much as possible about web service with Java.

## TOC
- [Web service course](#web-service-course)
  * [Summary](#summary)
      - [Week 1](#week-1)
        * [Web service protocol stack:](#web-service-protocol-stack-)
        * [Requestor Development plan:](#requestor-development-plan-)
        * [Provider Development plan:](#provider-development-plan-)
        * [Server+Client Java with XML-RPC](#server-client-java-with-xml-rpc)
      - [Week 2](#week-2)
        * [Primitive/Reference type](#primitive-reference-type)
        * [wrapper class & boxing](#wrapper-class---boxing)
        * [object comparison & equals()](#object-comparison---equals--)
      - [Week 3](#week-3)
      - [Week 4](#week-4)
      - [Week 5](#week-5)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

#### Week 1
**Web service** is a service available on the Internet (or private network) that uses standard XML messaging system to communicate to different programs.
- Self-describing: public interface written in XML grammar that specify method name, return type & parameter so other developers can use the service.
- Discoverability: simple mechanism to find the service, either through a decentralized system or centralized registry system.
- **Platform-independent:** Because XML or some medium is used to exchange message, a client written in Ruby can communicate with a web service written in Java on different OS, frameworks, libraries.

##### Web service protocol stack:
- Service transport: responsible for transporting messages between applications (HTTP, SMTP, FTP)
- XML messaging: responsible for encoding messages in a common XML format so that messages can be understood at either end. This layer includes XML-RPC and SOAP.
- Service description: responsible for describing the public interface to a specific web service. This is currently done using the Web Service Description language (WSDL) in SOAP.
- Service directory: responsible for centralizing services into a common registry, and providing easy publish/find functionality. This is currently handled via Universal Description, Discovery and Integration (UDDI)

##### Requestor Development plan:
1. Find services via UDDI: Identify and discover services that are relevant to the application. This step usually involves searching the UDDI Business Directory for patterns and services.
2. Retrieve service description file: WSDL or XML-RPC instructions: Once the interesting service has been identified, locate a service description. If this is a SOAP service, there should be WSDL document. If this is an XML-RPC service, there should be some human-readable instructions for integration.
3. Create XML-RPC or SOAP client: This can be an XML-RPC or SOAP client in a language like Java, C#, etc. If the service has a WSDL file, it is also possible to automatically create client code via a WSDL invocation tool.
4. Invoke remote service: Run the Client application to invoke the web service.

##### Provider Development plan:
1. Create core functionality: Develop the core functionality of the service (may connect to database, Enterprise JavaBeans (EJB), COM objects, etc). This is usually the hardest part.
2. Create an XML-RPC or SOAP service wrapper: Develop a service wrapper to the core functionality. This could be an XML-RPC or a SOAP service wrapper. This is usually a relatively simple step.
3. Create WSDL service description or XML-RPC integration instructions: Provide a service description. Create a WSDL file for a SOAP service and create human-readable instructions for an XML-RPC service.
4. Deploy the service: Depending on the need, this could mean installing and running a standalone server or integrating with an existing web server.
5. Register service via UDDI: Publish the existence and specifications of the service. This usually means publishing data to a global UDDI directory or perhaps a private UDDI directory specific to a company (This step wasn't executed during the course).

**XML-RPC:** embed method call & parameters in an XML message, and it got embedded in the body of HTTP to send over 2 different machines.
Client specify a procedure name & params in XML request, and Server returns either a fault or response in XML response. The most complex type
in XML-RPC is array/struct (There's no concept of obj)

##### Server+Client Java with XML-RPC
- Both Server+Client require Java Project.
- Configure build path: 
  - **Server** needs 4 jars: commons-logging, ws-common-util, xmlrpc-common, xmlrpc-server
  - **Client** needs 6 jars: commons-codec, commons-httpclient, commons-logging, ws-commons-util, xmlrpc-client, xmlrpc-common.
- Notes on environment vars:
  - **JAVA_HOME:** home directory of JDK/JRE.
  - **CLASSPATH:** location of .jar files that java compiler would use.
  - **Control Panel->User Accounts->User Accounts->Change my environment variables**
  - Use **set PATH=%PATH%;new_path;** when you wish to append the path & avoid overwriting it.
  - %CLASSPATH%;U:\xmlrpc_jars\commons-logging-1.1.jar;U:\xmlrpc_jars\ws-commons-util-1.0.2.jar;U:\xmlrpc_jars\xmlrpc-client-3.1.jar;U:\xmlrpc_jars\xmlrpc-common-3.1.jar;U:\xmlrpc_jars\xmlrpc-server-3.1.jar;U:\xmlrpc_jars\commons-codec-1.3.jar;U:\xmlrpc_jars\commons-httpclient-3.1.jar
  - XMLRPC=$HOME/web_services/xmlrpc_jars
  - export CLASSPATH=$CLASSPATH:$XMLRPC/commons-codec-1.3.jar:$XMLRPC/commons-httpclient-3.1.jar:$XMLRPC/commons-logging-1.1.jar:$XMLRPC/ws-commons-util-1.0.2.jar:$XMLRPC/xmlrpc-client-3.1.jar:$XMLRPC/xmlrpc-common-3.1.jar:$XMLRPC/xmlrpc-server-3.1.jar:$XMLRPC/servlet-api.jar
- **javac path/to/src/client/\*.java** will compile .java file in **client/Client.java** (Assuming **client.Client** package structure) to **Client.class**
- **java client.Client** to run java program.

#### Week 2
##### Primitive/Reference type
- [Source](https://javarevisited.blogspot.com/2015/09/difference-between-primitive-and-reference-variable-java.html) where i learn & take these notes.
- Primitive type (value types): contain the actual value.
  - int, double, boolean...
  - They have **different size**, so they **aren't interchangeable**.
- Ref type are refs to instances (alias, ptr to another value), typically implemented as ptrs.
  - obj = instance of a class created dynamically on the heap ("new" keyword in Java)
  - string, array, interface, null ptr, classes (including immutable ...)
  - They have the **same size**, regardless of the instances they point to.
    - -> **Substitution/Interchangeable** (to some extent): use an instance of a type as an instance of a related type (String as an Object).
``` Java
Integer i;
Integer j = new Integer();
i = j;	// i points to what j points to.
SomeFunction(i); // i would be modified.
```
- i is going to point to an Integer obj, but right now it is pointing to null. i, as the ref, is stored statically on the stack.
- j is pointing to an Integer obj created & allocated dynamically on the heap by the "new" keyword.
- **Note:** be careful when doing assignment operation or passing obj to method as an argument.

##### wrapper class & boxing
- [Source](https://javarevisited.blogspot.com/2012/07/auto-boxing-and-unboxing-in-java-be.html) where i learn & take these notes.
- Collections (List, HashMap) only accept Object -> Convert primitives (int, long, double) to boxed primitive (Object or Wrapper class: Integer, Long, Double).
- When Java automatically converts primitive type (int) to corresponding wrapper class (Integer): **Autoboxing**, and the reverse process is called **Auto-unboxing**.
  - Compiler uses valueOf() to convert primitive to Object, and intValue(), floatValue() to get the primitive value from Object.
- 2 common situations where autoboxing occur: assignment (Integer iObj = 3;) or method call where the argument expected is Object (intList.set(4);). This should be the same with autounboxing.
``` Java
// Before Java 1.5:
Integer iObj = Integer.valueOf(3);
int i = iObj.intValue();

// After Java 1.5
Integer iObj = 5;
int i = iObj;
```
- When compare 2 wrapper objects, use [equals()](#object-comparison---equals--) but not "==" operator.
- The object may be [cached.](https://javarevisited.blogspot.com/2010/10/what-is-problem-while-using-in.html)
``` Java 
Integer i1 = 5; // Autoboxing, call valueOf()
Integer i2 = 5; // Same.
i1 == i2; // return true because valueOf() caches objs, so it misunderstood and return the same obj (in short, i1 & i2 points to the same obj)

// Pure object comparison
Integer i1 = new Integer(5);
Integer i2 = new Integer(5);
i1 == i2; // return false. In this case, i1 & i2 are initialized explicitly, so they are compared by reference.
``` 
- Don't mix wrapper class and primitive in equality/relational operator (compare primitive and obj, obj could be null -> throws NullPointerException).
- Object can be unnecessary created which cause GC overhead.
``` Java 
Integer sum = 0;
for(int i=1000; i<5000; i++){
	sum+=i;
}

// Translate to below. 4000 objects would be unnecessarily created that slow down the whole system.
int result = sum.intValue() + i;
sum = new Integer(result);     
```

##### object comparison & equals()
- [Source](https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java) where i learn & take these notes.
- By default, Object are compared with **shallow comparison:** compare whether both object refer to the same memory address.
- equals() is overriden where you can compare each field of both objects.
- If both objects are equal according to equals(), then calling hashCode() on each object must produce the same integer result.
  - For Hashing data structures (HashMap, Hashtable, etc), element would be stored in a bucket list with references to a hash code, so comparison methods on that data structure (like HashMap.contains(Element)) would first check the hash code with hashCode() before calling equals().

#### Week 3
- Write/Read from file.
- Serialization/Deserialization.

#### Week 4
- Upload/Download file as byte array.
- UI basics with Swing.
- Storing properties file.

#### Week 5
- Database basics.
- SOAP basics.
- Run & test Axis2 on local Tomcat server.

