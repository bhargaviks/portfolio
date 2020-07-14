
package com.google.sps.servlets;

import com.google.sps.data.Point;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Returns a Point data as a JSON array, e.g. [{"lat": 38.4404675, "lng": -122.7144313, "info": This is my school}] */
@WebServlet("/point-data")
public class PlacesServlet extends HttpServlet {

  private Collection<Point> points;

  @Override
  public void init() {
    points = new ArrayList<>();

    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/points-data.csv"));
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cells = line.split(",");

      double lat = Double.parseDouble(cells[0]);
      double lng = Double.parseDouble(cells[1]);
      String info = cells[2];

      points.add(new Point(lat, lng, info));
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(points);
    response.getWriter().println(json);
  }
}
