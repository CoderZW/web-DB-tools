package com.homethy.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author qian.xu@renren-inc.com on 17-10-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomDaoTest {
  @Autowired
  CustomDao customDao;

  @Test
  public void getColumns() throws Exception {
    Assert.assertNotNull(customDao);
    List<String> columns = customDao.getColumns("website_info", CustomDao.SCHEMA_SITEBUILT);
    Assert.assertFalse(columns.isEmpty());
  }

  @Test
  public void isViewExistInSchema() throws Exception {
    Assert.assertTrue(customDao.isViewExistInSchema("cms_sql", CustomDao.SCHEMA_SITEBUILT_PREVIEW));
  }

  @Test
  public void isTableExistInSchema() throws Exception {
    Assert.assertTrue(customDao.isTableExistInSchema("website_info", CustomDao
        .SCHEMA_SITEBUILT_PREVIEW));
  }

  @Test
  public void getPrimaryKey() throws Exception {
    List<String> keys = customDao.getPrimaryKey("website_info", CustomDao.SCHEMA_SITEBUILT);
    Assert.assertFalse(keys.isEmpty());
    Assert.assertEquals(keys.get(0), "id");
  }

}