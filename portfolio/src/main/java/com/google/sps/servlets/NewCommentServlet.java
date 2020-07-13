package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new comments. */
@WebServlet("/new-comment")
public class NewCommentServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String text = request.getParameter("text-input");     // Comment given to post
    String limit = request.getParameter("comment-limit");     // Comment given to post
    if(text != null && text.length()>0){                      // if the user has typed nothing, so post only if you have any new data. Otherwise, just take care of the limit.
      long timestamp = System.currentTimeMillis();          // Time at which the comment was made
      Entity commentEntity = new Entity("Comment");
      
      commentEntity.setProperty("comment", text);
      commentEntity.setProperty("timestamp", timestamp);
      
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    }

    response.sendRedirect("/comments.html?limit="+limit);

  }
}
