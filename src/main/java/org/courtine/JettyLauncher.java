package org.courtine;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public class JettyLauncher {
  public static Server createServer(int port) throws IOException {

    Server server = new Server(port);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setWarResource(Resource.newClassPathResource("webapp"));
    webapp.setErrorHandler(new MedicisErrorHandler());

    // Enable the AnnotationConfiguration in order to correctly set up the jsp container
    // cf. https://www.eclipse.org/jetty/documentation/current/configuring-jsp.html
    Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
    classlist.addBefore(
        "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
        "org.eclipse.jetty.annotations.AnnotationConfiguration"
    );
    webapp.setAttribute(
        "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
        ".*/.*jsp-api-[^/]*\\.jar$|.*/.*jsp-[^/]*\\.jar$|.*/.*taglibs[^/]*\\.jar$"
    );

    server.setHandler(webapp);

    return server;
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    Server server = createServer(port);

    server.start();
    server.dumpStdErr();
    server.join();
  }

  /**
   * Force error pages for all request methods.
   * cf. https://github.com/eclipse/jetty.project/issues/163
   */
  static class MedicisErrorHandler extends ErrorPageErrorHandler {
    @Override
    public boolean errorPageForMethod(String method) {
      return true;
    }
  }
}


