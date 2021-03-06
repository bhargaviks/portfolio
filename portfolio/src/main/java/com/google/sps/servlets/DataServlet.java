package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for listing all the comments from datastore. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String slimit = request.getParameter("limit");
    int limit;

    try {
      limit = Integer.parseInt(slimit);
      if(limit<0)
        limit = Integer.MAX_VALUE;
    } catch (Exception e) {
      limit = Integer.MAX_VALUE;
    }

    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    int ct=0;         // Counter for limiting number of results. 
    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      if(ct >= limit)
        break;  // Come here when we have already displayed the required number of comments
      
      ct++;
      long id = entity.getKey().getId();
      String text = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");
      double score = (double) entity.getProperty("score"); 

      Comment comment = new Comment(id, text, timestamp, score); 
      comments.add(comment);
    }

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }
}
