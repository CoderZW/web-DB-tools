package com.homethy.util;

import com.homethy.domain.dns.DNSRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.xbill.DNS.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leifeifei on 17-10-12.
 */
public class DNSUtil {

  public static List<DNSRecord> getDomainMXRecords(String domain){
    List list = new ArrayList<>();
    try{
      Record[] records =null;
      Lookup lookup = new Lookup(domain, Type.MX);
      lookup.run();

      if(lookup.getResult() == Lookup.SUCCESSFUL){
        records=lookup.getAnswers();
      }else{
        return list;
      }
      for (int i = 0; i < records.length; i++) {
        DNSRecord dnsRecord = new DNSRecord();
        MXRecord mx = (MXRecord) records[i];
        dnsRecord.setName(mx.getName().toString());
        dnsRecord.setType("MX");
        dnsRecord.setValue(mx.getPriority() + " " + mx.getTarget().toString());
        dnsRecord.setTtl(mx.getTTL());
        list.add(dnsRecord);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return list;
  }

  public static List<DNSRecord> getDomainRecordsByType(String domain,int type){
    List list = new ArrayList<>();
    try{
      Record[] records =null;
      Lookup lookup = new Lookup(domain, type);
      lookup.run();

      if(lookup.getResult() == Lookup.SUCCESSFUL){
        records=lookup.getAnswers();
      }else{
        return list;
      }
      Record[] answers = lookup.getAnswers();
      for(Record rec : answers){
        String record = HomethyStringUtil.replceMultiTab(rec.toString());
        System.out.println(record);
        String recordStr [] = record.split("\t");
        if(recordStr.length==5){
          DNSRecord dnsRecord = new DNSRecord();
          dnsRecord.setName(recordStr[0]);
        dnsRecord.setType(recordStr[3]);
        dnsRecord.setValue(recordStr[4]);
        dnsRecord.setTtl(NumberUtils.toLong(recordStr[1]));
        list.add(dnsRecord);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return list;
  }


  /**
   * 获取域名常见records ： A、MX、TXT、NS、SOA、SRV、AAAA、www的CNAME
   * @param domain
   * @return
   */
  public static List<DNSRecord> getDomainCommonRecords(String domain){
    List<DNSRecord> list = new ArrayList<>();
    list.addAll(getDomainRecordsByType(domain,Type.A));
    list.addAll(getDomainRecordsByType(domain,Type.MX));
    list.addAll(getDomainRecordsByType(domain,Type.TXT));
    list.addAll(getDomainRecordsByType(domain,Type.SRV));
    list.addAll(getDomainRecordsByType(domain,Type.SOA));
    list.addAll(getDomainRecordsByType(domain,Type.AAAA));
    list.addAll(getDomainRecordsByType(domain,Type.CNAME));
    list.addAll(getDomainRecordsByType(domain,Type.NS));
    return list;
  }

  /**
   * 获取当前域名及其顶级域名的DNS配置
   * @param domain
   * @return
   */
  public static List<DNSRecord> getMainDomainCommonRecords(String domain){
    String mainDomain = HomethyStringUtil.getMainDomain(domain);
    List<DNSRecord> domainRecordList =  getDomainCommonRecords(domain);
    if(!domain.toLowerCase().equals(mainDomain)){
      List<DNSRecord> mainDomainRecordList =  getDomainCommonRecords(mainDomain);
      domainRecordList.addAll(mainDomainRecordList);
    }
    return domainRecordList;
  }

  public static boolean isChimeThirdDomain(String domain){
    boolean result = false;
    List<DNSRecord> list = getDomainRecordsByType(domain,Type.A);

    String deaultA = "52.52.24.52,52.9.101.47";
    if(CollectionUtils.isNotEmpty(list)){
      for (DNSRecord dnsRecord : list) {
        if(StringUtils.isNotBlank(dnsRecord.getValue()) && deaultA.contains(dnsRecord.getValue())){
          result = true;
          break;
        }
      }
    }

    if(!result){
      String mainDomain = HomethyStringUtil.getMainDomain(domain);
      if(domain.equals(mainDomain)){
        return result;
      }

      List<DNSRecord> cnameList = getDomainRecordsByType(domain,Type.CNAME);
      List<DNSRecord> mlist = getDomainRecordsByType(mainDomain,Type.A);

      if(CollectionUtils.isNotEmpty(mlist) && CollectionUtils.isNotEmpty(cnameList)){
        boolean flag = false;
        for (DNSRecord dnsRecord : mlist) {
          if(StringUtils.isNotBlank(dnsRecord.getValue()) && deaultA.contains(dnsRecord.getValue())){
            flag = true;
            break;
          }
        }
        //顶级域名是我们的A记录，查看是否有该域名的cname，且cname值等于顶级域名
        if(flag){
          String mainCname = mainDomain+".";
          for (DNSRecord dnsRecord : cnameList) {
            if(StringUtils.isNotBlank(dnsRecord.getValue()) && (mainCname.equals(dnsRecord.getValue().trim()) || mainDomain.equals(dnsRecord.getValue().trim()))){
              result = true;
              break;
            }
          }
        }

      }
    }

    return result;
  }


  private static final int DEFAULT_PORT = 43;

  public static String query(String domain) throws Exception {
    if(StringUtils.isBlank(domain)){
      return "";
    }
    String server = "";
    String tld = HomethyStringUtil.getDomainTld(domain);
    Map<String,String> map = PropertiesResolver.WHOSIS_SERVER_PROPERTIES;
    if(MapUtils.isNotEmpty(map) && map.get(tld) != null){
      server = map.get(tld);
    }
    if(StringUtils.isNotBlank(server)){
      return query(HomethyStringUtil.getMainDomain(domain), server);
    }else{
      return "";
    }
  }

  public static String query(String domain, String server) throws Exception {
    String result = "";
    if(StringUtils.isBlank(server)){
      return result;
    }
    if(server.contains(",")){
      String servers [] =  server.split(",");
      for (String nsServer : servers) {
        String re = queryNs(domain,nsServer);
        if(StringUtils.isNotBlank(server)){
          result = re;
          break;
        }
      }
    }else{
      result = queryNs(domain,server);
    }
    return result;
  }

  public static String queryNs(String domain, String server) throws Exception{
      Socket socket = new Socket(server, DEFAULT_PORT);
      String lineSeparator = "\r\n";

      PrintWriter out = new PrintWriter(socket.getOutputStream());
      out.println(domain);
      out.flush();

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      StringBuilder ret = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        ret.append(line + lineSeparator);
      }
      socket.close();
      return ret.toString();
    }

//  private static String getTLD(String domain) {
//    final int index;
//    return (domain == null || (index = domain.lastIndexOf('.') + 1) < 1) ? domain
//        : (index < (domain.length())) ? domain.substring(index) : "";
//  }

  public static Map queryDomainInfoMap(String domain) throws Exception{
    String domainInfo = query(domain);
    Map infoMap = new LinkedHashMap();
    if(StringUtils.isNotBlank(domainInfo)){
      String lastUpdateDesc = "";
      if(domainInfo.contains(">>>")){
        lastUpdateDesc = domainInfo.substring(domainInfo.indexOf(">>>"));
        domainInfo = domainInfo.substring(0,domainInfo.indexOf(">>>"));
      }
      String infoStr [] = domainInfo.split("\r\n");
      for (String info : infoStr) {
        if(StringUtils.isNotBlank(info)){
          String key = info.substring(0,info.indexOf(":"));
          String value = info.substring(info.indexOf(":")+1);
          infoMap.put(key.trim(),value.trim());
        }
      }
      if(StringUtils.isNotBlank(lastUpdateDesc)){
        infoMap.put("Last Update Description",lastUpdateDesc);
      }
    }
    return infoMap;
  }
//  public static void main(String args[]) throws Exception{
////    String statement="sadasd;\n\n  \n  dfsfsfsf;\ndsadsa;\n;";
////    String a=HomethyStringUtil.replceMultipleOnlySpace(statement);
////    String sqlStr [] = a.split(";\n");
////    System.out.println(sqlStr);
////    System.out.println(sqlStr.length);
////    List<DNSRecord> list = getDomainCommonRecords("site.chime.me");
////    try {
////      System.out.println(JacksonUtils.toJson(list));
////    }catch (Exception e){
////      e.printStackTrace();
////    }
////    getDomainARecords("www.thereneewhiteteam.com",Type.);
//
////    System.out.println(query("baidu.com"));
////    System.out.println(query("csdn.net"));
////    System.out.println(query("apache.org"));
////    System.out.println(query("360.cn"));          //china
////    System.out.println(query("mixi.jp"));         //japan
////    System.out.println(query("laneige.co.kr"));   //korea
////    System.out.println(query("chime.me"));   //korea
////    System.out.println(query("qq.com.cn"));   //korea
////    System.out.println(query("thereneewhiteteam.com"));   //korea
//    Map map = queryDomainInfoMap("site.chime.me");
//
//    System.out.println(map.toString());
//  }




  public static void main(String[] args) throws Exception {

    String host="chime.me";
    int port = 443;
    char[] passphrase = "changeit".toCharArray();
//    if ((args.length == 1) || (args.length == 2)) {
//      String[] c = args[0].split(":");
//      host = c[0];
//      port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
//      String p = (args.length == 1) ? "changeit" : args[1];
//      passphrase = p.toCharArray();
//    } else {
//      System.out.println("Usage: java InstallCert <host>[:port] [passphrase]");
//      return;
//    }

    File file = new File("jssecacerts");
    if (file.isFile() == false) {
      char SEP = File.separatorChar;
      File dir = new File(System.getProperty("java.home") + SEP
          + "lib" + SEP + "security");
      file = new File(dir, "jssecacerts");
      if (file.isFile() == false) {
        file = new File(dir, "cacerts");
      }
    }
    System.out.println("Loading KeyStore " + file + "...");
    InputStream in = new FileInputStream(file);
    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
    ks.load(in, passphrase);
    in.close();

    SSLContext context = SSLContext.getInstance("TLS");
    TrustManagerFactory tmf =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(ks);
    X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
    SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
    context.init(null, new TrustManager[] {tm}, null);
    SSLSocketFactory factory = context.getSocketFactory();

    System.out.println("Opening connection to " + host + ":" + port + "...");
    SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
    socket.setSoTimeout(10000);
    try {
      System.out.println("Starting SSL handshake...");
      socket.startHandshake();
      socket.close();
      System.out.println();
      System.out.println("No errors, certificate is already trusted");
    } catch (SSLException e) {
      System.out.println();
      e.printStackTrace(System.out);
    }

    X509Certificate[] chain = tm.chain;
    if (chain == null) {
      System.out.println("Could not obtain server certificate chain");
      return;
    }

    BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in));

    System.out.println();
    System.out.println("Server sent " + chain.length + " certificate(s):");
    System.out.println();
    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    for (int i = 0; i < chain.length; i++) {
      X509Certificate cert = chain[i];
      System.out.println
          (" " + (i + 1) + " Subject " + cert.getSubjectDN());
      System.out.println("   Issuer  " + cert.getIssuerDN());
      sha1.update(cert.getEncoded());
      System.out.println("   sha1    " + toHexString(sha1.digest()));
      md5.update(cert.getEncoded());
      System.out.println("   md5     " + toHexString(md5.digest()));
      System.out.println();
    }

    System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1]");
    String line = reader.readLine().trim();
    int k;
    try {
      k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
    } catch (NumberFormatException e) {
      System.out.println("KeyStore not changed");
      return;
    }

    X509Certificate cert = chain[k];
    String alias = host + "-" + (k + 1);
    ks.setCertificateEntry(alias, cert);

    OutputStream out = new FileOutputStream("jssecacerts");
    ks.store(out, passphrase);
    out.close();

    System.out.println();
    System.out.println(cert);
    System.out.println();
    System.out.println
        ("Added certificate to keystore 'jssecacerts' using alias '"
            + alias + "'");
  }

  private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

  private static String toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 3);
    for (int b : bytes) {
      b &= 0xff;
      sb.append(HEXDIGITS[b >> 4]);
      sb.append(HEXDIGITS[b & 15]);
      sb.append(' ');
    }
    return sb.toString();
  }

  private static class SavingTrustManager implements X509TrustManager {

    private final X509TrustManager tm;
    private X509Certificate[] chain;

    SavingTrustManager(X509TrustManager tm) {
      this.tm = tm;
    }

    public X509Certificate[] getAcceptedIssuers() {
      throw new UnsupportedOperationException();
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      throw new UnsupportedOperationException();
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      this.chain = chain;
      tm.checkServerTrusted(chain, authType);
    }
  }
}

