package org.courtine;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Controller extends HttpServlet {

  private ServletConfig config;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // Custom jspx/500.jspx template should be used and returned.
    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occured");
  }
}
