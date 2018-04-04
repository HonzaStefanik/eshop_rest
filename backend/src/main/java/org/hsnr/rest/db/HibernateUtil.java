package org.hsnr.rest.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HibernateUtil {

  private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);

  private static SessionFactory sessionFactory;

  private HibernateUtil() {} // util class

  static{
    try {
      sessionFactory = new Configuration().configure().buildSessionFactory();
    } catch (Exception e) {
      LOG.error("Error building Sessionfactory", e);
    }
  }

  public static SessionFactory getSessionFactory(){
    return sessionFactory;
  }

  public static void shutDown(){
    LOG.debug("Sessionfactory closed --> '{}'", sessionFactory);
    getSessionFactory().close();
  }

}
