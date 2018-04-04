package org.hsnr.rest.db;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class H2ServerController {

  private static final Logger LOG = LoggerFactory.getLogger(H2ServerController.class);

  private static Server h2Server = null;
  private static Server h2WebServer = null;

  public H2ServerController() throws SQLException {
    if (h2Server == null) {
      h2Server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
      h2Server.start();
      h2WebServer = Server.createWebServer("-web","-webAllowOthers","-webPort","8082");
      h2WebServer.start();
      LOG.info("H2 TCP/Web servers initialized at: '{}', Webserver at: '{}'", h2Server.getURL(), h2WebServer.getURL());
    } else {
     LOG.error("H2 TCP/Web servers already initialized");
    }
  }

}
