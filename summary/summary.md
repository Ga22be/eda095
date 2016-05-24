
## **Summary of course EDA095** ##
 *by Johan Davidsson*

----------
###**Streams:** I/O in Java
* Read  from an **input stream**, write to an **output stream**

####**Input**
![](https://raw.githubusercontent.com/JDavidsson/eda095/master/summary/inputsreams.png)




**Read ten bytes**
```java
        byte[] input = new byte[10]
        for (int i = 0; i < input.length; i++) {
    	    int byte = inputStream.read();
    	    if (byte == -1) break; 
    	    input[i] = (byte) byte;
	    }
```
**or just**
```java
    byte[] input = new byte[10]; 
    int r = inputStream.read(input);
```

####**Output**

![](https://raw.githubusercontent.com/JDavidsson/eda095/master/summary/outputstreams.png)

* **Buffers** increases effectiviness but don't forget to **flush() !**


```java
OutputStream os = ...
PrintWriter out = new PrintWriter(os,true);
//true means autoflushing
out.write("nicely flushed!");

```


----------
###**Threads:** do things in parallel

	

 * **Deadlocks** 
 ```
threads x,y  wants access to objects A,B
x gets A first
Y gets B first
now both x ,y stuck waiting 

 ```

#### **Threads in java**
* Both class **Thread** and interface **Runnable** implements/overrides the **run()** method.

* Start Thread: 


```java
MyThread mt = new MyThread();
mt.start(); 
```
	
***or***


```java
Runnable r = ...
new Thread(r).start();
```

#### **Synchronization and Monitors**
`synchronized void synchMe() { ... }`

* The thread running the *synchronized* method **owns** it. 
*  Therefore other Threads that want to run `synchMe()`are in the **blocked state**

`wait()` `notify()` `notifyAll()` are useful if the **Monitor**-object isn't ready for action.

**A monitor class:**
```java
public synchronized int getNumber() {
	if (numbers.empty()) wait();
	return numbers.pop();
}
public synchronized void addNumber(int number) {
	numbers.push(number);
	notifyAll();
}
```

 - `notify()`awakens **one** arbitrary thread that's waiting
 - `notifyAll()`awekens **all** waiting threads





----------
###**IP + URL/URI:** explanation

* **URI** *(Uniform Resource Identifier)* **:** reference to a resource (subsumes URL)

* **URL** *(Uniform Resource Locator)* **:** reference to web resource
	* **URL** are the most common form of an ***URI***

creating a **URL** with Java:
```java
    URL baseURL = new URL("http://www.base.com");
    new URL(baseURL,"relative.html");
```

 - **IP:** stands for **I** *don't care about explaining I-* **P** *right now*

----------

###**Socket** TCP connection (Java)

> A socket is one end-point of a two-way communication link between two programs running on the network. 

* Client  > **Socket** `getInputStream // getOutputStream`

```java
try { 
	Socket socket = new Socket(host,port);
	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
	//do things with I/O
	String received = in.readLine());
	out.println("I want fish!");

	} catch (fish) { eat(); }
	
```

* Server  > **ServerSocket** `accept()` `close()`

**ServerSocket** with thread pool using **Executors** 
```java
ServerSocket ss = new ServerSocket();
ExecutorService pool = Executors.newFixedThreadPool(5);
while (wantSocket) {
	Socket socket = ss.accept();
	pool.submit(new MyThread(socket));
}
//MyThread does things with socket

```

----------





###**Datagram ** UDP connection (Java)

* Datagrams can be sent with UDP with Java classes

	* **DatagramPacket**  (represents the packet)
	* **DatagramSocket** retrives/sends packets

***Code that receives/send packets*** 
```java
import burgers;
import imagine.exception.handling;

DatagramSocket ds = new DatagramSocket(port);
byte[] buf = new byte[65536];
DatagramPacket packet = new DataGramPacket(but, buf.length);

ds.receive(packet); //fill em up
String message = new String(dp.getData(), 0, dp.getLength());
String reply = "What are you talking about?";
switch (message) {
	case "burger":
		reply = "where´s the extra cheese?";
	case "burger with extra cheese":
		reply = "thanks";
}
byte[] b = reply.getBytes();
DatagramPacket replyPacket = new DatagramPacket(b,b.length,address,port);
ds.send(replyPacket); 
```


#### **Multicast** Send *one copy* to many receivers 

* **TTL** *(time to live)* **:** prevents packet from circulating the web foreeeeever. When the counter hits 0 the packet dies. Useful in **Multicasting**

* Java uses **MulticastSocket**
*  `joinGroup(inetAddress)` is used to subscribe (receive) 

```java
MulticastSocket ms = new MulticastSocket();
ms.setTimeToLive(2);
InetAddres address = InetAddress.getByName("some.multicast.url");
DatagramPacket packet = new Da.....
// lines creating packet 
ms.send(packet);
```

----------


###**HTTP:**
* **GET** *requests data from specified resource (URI)*
	*  `foo.se?name1=value1&name2=value2` query in URL
* **POST** *submits data to be processed to a specified resource*
  * `foo.se` query in "message body" of POST
  
* PUT *stores the resource*
* HEAD *like GET but returns headers*
* DELETE *deletes resource*
* and OPTION,TRACE,CONNECT...



----------



###**XML** *(eXtensible Markup Language)* **:**  markup

* **DOM** *(Document Object Modell)* **:** builds tree of XML
* **SAX** *(Simple API for XML)* **:** event-oriented XML-parser

Both DOM & SAX are XML-parsers. The difference is that DOM builds a tree representing the *(whole)* XML document whilst SAX parses "tag by tag". This makes SAX faster than DOM although DOM is more usable to analyze/transform and recommended by W3C.

* **XSL** *(eXtensible Stylesheet Language)* **:** like CSS for XML kinda
* **XSLT** *(XSL Transformations)* **:** used for transformations on XML documents. For instance XML to HTML.
* **XPath** **:** names and accesses nodes. Used by **XSLT**.


----------


###**Other words/concepts**


* **MIME-type:** two-part identifier for file formats
	* `text/html; charset=UTF-8`
	* text is the type, html is the subtype and chars... is the parameter.

* **UTF-8:** explanation
* **Unicode:** explanation
* **JSP ** *(Java Server Pages)* **:** explanation
* **REST** *( - architecure)* **:** explanation


----------

###**Notes:**

 - Remember, exceptions are thrown everywhere
 - Catch 'em all

