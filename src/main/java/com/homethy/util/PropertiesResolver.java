package com.homethy.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 使用yourConfig.properties中的key作为Map中的key来索引值
 * 
 * @Author hongshuai.zhang
 * @CreateDate 2017-01-13 21:25:24
 */
public class PropertiesResolver {

	public static final Map<String,String> DATABASE_PROPERTIES = loadProperties("conf/application.properties");
  public static final Map<String,String> WHOSIS_SERVER_PROPERTIES = loadProperties("conf/whois_server.properties");
  public static final Map<String,String> REGISTRAR_DNS_KERWORDS_PROPERTIES = loadProperties("conf/registrar_dns_keywords.properties");

  private static Map<String, String> loadProperties(String location){
		Properties props = new Properties();
		Map<String,String> result = new HashMap<String,String>();
		try{
			File f = new File(System.getProperty("user.dir") + File.separator+location);
			FileReader r = new FileReader(f);
			props.load(r);
			Set<String> keys = props.stringPropertyNames();
			for(String key:keys){
				result.put(key, props.getProperty(key));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
}
