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
    // Get the input from the form.
    String text = request.getParameter("text-input");     // Comment given to post
    //TODO: have to get it from window somehow, so that whne he posts something, the initial number of comments are loaded. for now, it's going to null and hence, everything is being posted.
    String limit = request.getParameter("comment-limit");     // Comment given to post
    if(text != null && text.length()>0){
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
