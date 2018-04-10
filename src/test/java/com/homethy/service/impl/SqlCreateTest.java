package com.homethy.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author qian.xu@renren-inc.com on 17-10-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SqlCreateTest {
  @Autowired
  SqlCreate sqlCreate;

  @Test
  public void runSqlScript() throws Exception {
    Assert.assertNotNull(sqlCreate);
    System.out.println("hahha~~");
  }

  @Test
  public void insert() throws Exception {
  }

}