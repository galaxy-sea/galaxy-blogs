# 1. 基础知识
## 1.1. 请求执行 //TODO
```java
CloseableHttpClient httpclient = HttpClients.createDefault();
HttpGet httpget = new HttpGet("http://localhost/");
CloseableHttpResponse response = httpclient.execute(httpget);
try {
    <...>
} finally {
    response.close();
}
```
### 1.1.1. HTTP请求
所有HTTP请求都有一个请求行，包括方法名称，请求URI和HTTP协议版本。

HttpClient的支持了在HTTP / 1.1规范中定义的所有HTTP方法的框的：``GET``, ``HEAD``, ``POST``, ``PUT``, ``DELETE``, ``TRACE``, ``OPTIONS``

Request-URI是统一资源标识符，用于标识应用请求的资源。HTTP请求URI由协议方案，主机名，可选端口，资源路径，可选查询和可选片段组成
```java
HttpGet httpget = new HttpGet(
     "http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
```

```java
HttpClient提供了URIBuilder实用程序类来简化请求URI的创建和修改。
URI uri = new URIBuilder()
        .setScheme("http")
        .setHost("www.google.com")
        .setPath("/search")
        .setParameter("q", "httpclient")
        .setParameter("btnG", "Google Search")
        .setParameter("aq", "f")
        .setParameter("oq", "")
        .build();
HttpGet httpget = new HttpGet(uri);
System.out.println(httpget.getURI());

// stdout >

// http://www.google.com/search?q=httpclient&btnG=Google+Search&aq=f&oq=
```

### 1.1.2. HTTP响应
HTTP响应是服务器在接收并解释请求消息后发送回客户端的消息。该消息的第一行包括协议版本，后跟数字状态代码及其相关的文本短语。
```java
HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 
HttpStatus.SC_OK, "OK");

System.out.println(response.getProtocolVersion());
System.out.println(response.getStatusLine().getStatusCode());
System.out.println(response.getStatusLine().getReasonPhrase());
System.out.println(response.getStatusLine().toString());

// stdout >

// HTTP/1.1
// 200
// OK
// HTTP/1.1 200 OK
```

### 1.1.3. 处理消息头
HTTP消息可以包含许多描述消息属性的标题，例如内容长度，内容类型等。HttpClient提供了检索，添加，删除和枚举标头的方法
```java
HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 
    HttpStatus.SC_OK, "OK");
response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
Header h1 = response.getFirstHeader("Set-Cookie");
System.out.println(h1);

Header h2 = response.getLastHeader("Set-Cookie");
System.out.println(h2);

Header[] hs = response.getHeaders("Set-Cookie");
System.out.println(hs.length);

// stdout >

// Set-Cookie: c1=a; path=/; domain=localhost
// Set-Cookie: c2=b; path="/", c3=c; domain="localhost"
// 2
```
获取给定类型的所有标头的最有效方法是使用该 HeaderIterator接口。
```java
HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

HeaderIterator it = response.headerIterator("Set-Cookie");

while (it.hasNext()) {
    System.out.println(it.next());
}

// stdout >

// Set-Cookie: c1=a; path=/; domain=localhost
// Set-Cookie: c2=b; path="/", c3=c; domain="localhost"
```
它还提供了将HTTP消息解析为单个头元素的便捷方法。

```java
HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 
    HttpStatus.SC_OK, "OK");
response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));

while (it.hasNext()) {
    HeaderElement elem = it.nextElement(); 
    System.out.println(elem.getName() + " = " + elem.getValue());
    NameValuePair[] params = elem.getParameters();
    for (int i = 0; i < params.length; i++) {
        System.out.println(" " + params[i]);
    }
}

// stdout >

// c1 = a
// path=/
// domain=localhost
// c2 = b
// path=/
// c3 = c
// domain=localhost
```
### 1.1.4. http 请求体
HTTP消息可以携带与请求或响应相关联的内容实体。实体可以在某些请求和某些响应中找到，因为它们是可选的。使用实体的请求称为包含请求的实体。HTTP规范定义了两个封闭请求方法的实体：POST和 PUT。通常期望响应包含内容实体。有例外的情况，如应对 HEAD方法``204 No Content``， ``304 Not Modified``，``205 Reset Content`` 响应。

HttpClient区分三种实体，具体取决于其内容的来源
- streamed:  内容从流中接收，或在运行中生成。特别地，该类别包括从HTTP响应接收的实体。流实体通常不可重复。
- self-contained:  内容在内存中或通过独立于连接或其他实体的方式获得。自包含实体通常是可重复的。这种类型的实体主要用于封闭HTTP请求的实体。
- wrapping:  内容从另一个实体获得。

当从HTTP响应中流出内容时，这种区别对于连接管理很重要。对于由应用程序创建且仅使用HttpClient发送的请求实体，流式和自包含之间的差异并不重要。在这种情况下，建议将不可重复的实体视为流式传输，将那些可重复的实体视为自包含的

#### 1.1.4.1. 使用 HTTP entities
```java
StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));

System.out.println(myEntity.getContentType());
System.out.println(myEntity.getContentLength());
System.out.println(EntityUtils.toString(myEntity));
System.out.println(EntityUtils.toByteArray(myEntity).length);
// stdout >

// Content-Type: text/plain; charset=utf-8
// 17
// important message
// 17
```
### 1.1.5.  确保低级资源释放
为了确保正确释放系统资源，必须关闭与实体关联的内容流或响应本身
```java
CloseableHttpClient httpclient = HttpClients.createDefault();
HttpGet httpget = new HttpGet("http://localhost/");
CloseableHttpResponse response = httpclient.execute(httpget);
try {
    HttpEntity entity = response.getEntity();
    if (entity != null) {
        InputStream instream = entity.getContent();
        try {
            // do something useful
        } finally {
            instream.close();
        }
    }
} finally {
    response.close();
}
```