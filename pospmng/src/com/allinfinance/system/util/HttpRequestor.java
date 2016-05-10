package com.allinfinance.system.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;


public class HttpRequestor {
    
    private String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer socketTimeout = null;
    private String proxyHost = null;
    private Integer proxyPort = null;
    static final String BOUNDARY = "----BoundarySMFEtUYQG6r5B920";  // 定义数据分隔线
    /**
     * Do GET request
     * @param url
     * @return
     * @throws Exception
     * @throws IOException
     */
    public String doGet(String url) throws Exception {
        
        URL localURL = new URL(url);
        
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setRequestProperty("Accept-Charset", charset);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        
        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }

        return resultBuffer.toString();
    }
    
    /**
     * Do POST request(multipart: file upload)
     * @param url
     * @param parameterMap
     * @param fileMap
     * @return
     * @throws Exception 
     */
    public String doPost(String url, Map parameterMap, Map fileMap) throws Exception {
    	
    	ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息   
        InputStream is = null;//输入流，用来读取服务器返回的信息  
        byte[] res = null;//保存服务器返回的信息的byte数组
        URL localURL = new URL(url);
        
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
    	
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        httpURLConnection.setRequestProperty("Charsert", charset);
        
        httpURLConnection.setDoOutput(true); 
        httpURLConnection.setDoInput(true); 
        httpURLConnection.setUseCaches(false); 
        httpURLConnection.setRequestMethod("POST");
        
        OutputStream dout = httpURLConnection.getOutputStream(); 
        
        ////1.先写文字形式的post流 
        //头 
        String boundary = BOUNDARY; 
        //中 
        StringBuffer resSB = new StringBuffer("\r\n"); 
        //尾 
        String endBoundary = "\r\n--" + boundary + "--\r\n"; 
        
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String)iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String)parameterMap.get(key);
                } else {
                    value = "";
                }
                
                resSB.append("Content-Disposition: form-data; name=\"").append(key).
                	append("\"\r\n").append("\r\n").append(value).append("\r\n").append("--").append(boundary).append("\r\n");
            }
        }
        //写出流 
        dout.write( ("--"+boundary + resSB.toString()).getBytes("utf-8"));
        
        //2.再写文件开式的post流 
        //fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath 
        resSB = new StringBuffer();
        if (fileMap != null) {
        	String fileFeild = (String)fileMap.get("fileFeild");
        	String fileName = (String)fileMap.get("fileName"); 
            String fileType = (String)fileMap.get("fileType"); 
            String filePath = (String)fileMap.get("filePath"); 
            
            resSB.append("Content-Disposition: form-data; name=\"").append(fileFeild).append("\"; filename=\"").append( 
                    fileName).append("\"\r\n").append("Content-Type: ").append(fileType).append("\r\n\r\n"); 
             
            dout.write(resSB.toString().getBytes("utf-8"));
            
            //开始写文件 
            File file = new File(filePath); 
            DataInputStream in = new DataInputStream(new FileInputStream(file)); 
            int bytes = 0; 
            byte[] bufferOut = new byte[1024 * 5]; 
            while ((bytes = in.read(bufferOut)) != -1) { 
                dout.write(bufferOut, 0, bytes); 
            }
            
            //if(i < num-1){ 
                dout.write( endBoundary.getBytes("utf-8") ); 
            //}
             
            in.close();
        }
        
        //3.最后写结尾 
        dout.write( endBoundary.getBytes("utf-8") ); 
        dout.flush();
        dout.close();
        
        is = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String tempLine = null;
        StringBuffer resultBuffer = new StringBuffer();
        
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }
        return resultBuffer.toString();
    }
    
    /**
     * Do POST request
     * @param url
     * @param parameterMap
     * @return
     * @throws Exception 
     */
    public String doPost(String url, Map parameterMap) throws Exception {
        
        /* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String)iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String)parameterMap.get(key);
                } else {
                    value = "";
                }
                
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        
        System.out.println("POST parameter : " + parameterBuffer.toString());
        
        URL localURL = new URL(url);
        
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", charset);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            
            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }

        return resultBuffer.toString();
    }

    private URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }
    
    /**
     * Render request according setting
     * @param request
     */
    private void renderRequest(URLConnection connection) {
        
        if (connectTimeout != null) {
            connection.setConnectTimeout(connectTimeout);
        }
        
        if (socketTimeout != null) {
            connection.setReadTimeout(socketTimeout);
        }
        
    }

    /*
     * Getter & Setter
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
    
//    public static void main(String[] args) throws Exception {
//        
//        /* Post Request */
//        Map dataMap = new HashMap();
//        dataMap.put("username", "Nick Huang");
//        dataMap.put("blog", "IT");
//        System.out.println(new HttpRequestor().doPost("http://localhost:8080/OneHttpServer/", dataMap));
//        
//        /* Get Request */
//        System.out.println(new HttpRequestor().doGet("http://localhost:8080/OneHttpServer/"));
//    }

}

