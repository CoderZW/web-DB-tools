package com.homethy.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qian.xu@renren-inc.com on 17-10-13.
 */
@RunWith(JUnit4.class)
public class TmpTest {
  @Test
  public void te() throws Exception {
//    String sql = "SELECT * FROM information_schema.KEY_COLUMN_USAGE limit 5  ;  \n" +
//        "SELECT * FROM information_schema.TABLE_CONSTRAINTS limit 5;\n" +
//        "SELECT * FROM information_schema.TABLES limit 5;  iu:;";
    String sql = "SELECT * FROM information_schema.KEY_COLUMN_USAGE limit 5;";
    String[] split = sql.trim().split("\\s*;\\s*");
    System.out.println(split.length);
    System.out.println(Arrays.asList(split));
  }


  @Test
  public void test() {
    String command = "INSERT INTO `sitebuilt`.`cms_sync_detail`(`id`,`site_id`,`record_id`," +
        "`status`,`msg`,`create_time`,`modify_time`,`domain_name`) VALUES (NULL, '787', " +
        "'3666', '2', 'sync success : testw11-sit3.chime.me', '2017-09-20 11:00:36','2017-09-20" +
        "11:00:53', '')";

    String command2 = "INSERT INTO \"sitebuilt\".'cms_sync_detail'  VALUES (NULL, '787', " +
        "'3666', '2', 'sync success : testw11-sit3.chime.me', '2017-09-20 11:00:36','2017-09-20" +
        "11:00:53', '')";
    String command3 = "iNsert into \"sitebuilt\".'cms_sync_detail'  set domain_name='xxx'";
    String command4 = "iNsert into \"sitebuilt\".'cms_sync_detail'  select a,b,c from sitebuilt" +
        ".aaa ";
    String command5 = "uPdate\"sitebuilt\".'cms_sync_detail'  set domain_name='xxx'";
    String command6 = "dELETe from    \"sitebuilt\".'cms_sync_detail'  where domain_name='xxx'";
//    String mask = "insert\\s+into\\s+[a-z0-9_`\\.]+\\s*\\([\\S\\s]+\\)\\s*values\\s*\\(";
//    String mask = "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+values";
//    String mask = "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+set\\s+";
//    String mask = "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+select\\s+";

//    String mask = "update\\s+[a-z0-9_`'\"\\.]+\\s+set\\s+";
    String mask = "delete\\s+from\\s+[a-z0-9_`'\"\\.]+\\s*";
    Pattern pattern = Pattern.compile(mask, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(command6);
//    boolean matches = matcher.matches();
    boolean matches = matcher.lookingAt();
    System.out.println(" ************");
    System.out.println(matches);
    System.out.println(" ************");
  }

  @Test
  public void test1() {
    String str = "INSERT INTO `sitebuilt`.`cms_sync_detail`(`id`,`site_id`,`record_id`," +
        "`status`,`msg`,`create_time`,`modify_time`,`domain_name`) VALUES (NULL, '787', " +
        "'3666', '2', 'sync success : testw11-sit3.chime.me', '2017-09-20 11:00:36','2017-09-20" +
        "11:00:53', '')";
    String s = "ttt ";
    String regex = "insert\\s+into\\s+(.+)\\s*\\((.+)\\)\\s*values";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(str);//匹配类
    if (matcher.find()) {
      System.out.println(
          matcher.groupCount());
      System.out.println(matcher.group(1));//打印中间字符
      System.out.println(matcher.group(2));
    }
  }
}
