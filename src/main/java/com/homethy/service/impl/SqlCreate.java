package com.homethy.service.impl;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import java.io.StringReader;
import java.sql.Connection;

import javax.sql.DataSource;

/**
 * @author qian.xu@renren-inc.com on 17-10-11.
 */
@Service
public class SqlCreate {
  private SqlSessionFactory sqlSessionFactory;
  private DataSource dataSource;

  public void runSqlScript(String sqls) {
    EntityManager entityManager;

    long l = System.currentTimeMillis();
    if (dataSource == null) {
      dataSource = ContextLoader.getCurrentWebApplicationContext().getBean("datasource",
          DataSource.class);
    }
    SqlSession sqlSession = sqlSessionFactory.openSession();
    Connection connection = sqlSession.getConnection();
    ScriptRunner scriptRunner = new ScriptRunner(connection);
    scriptRunner.setSendFullScript(false);
    scriptRunner.setStopOnError(true);
    scriptRunner.runScript(new StringReader(sqls));
//    scriptRunner.closeConnection();
//    sqlSession.close();
    long l1 = System.currentTimeMillis();
//    LOG.info("sqls excute successfully .  uset time : " + (l1 - l) + "ms");
  }


//  public String Insert(String sqls) {
//
//  }
}
