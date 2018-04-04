package org.hsnr.rest;

import org.hsnr.rest.db.H2ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.sql.SQLException;

@SpringBootApplication
@ImportResource("springcontext.xml")
public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws SQLException {
    LOG.info("Program started in Main");
    SpringApplication.run(Main.class, args);

    new H2ServerController();

  }
}