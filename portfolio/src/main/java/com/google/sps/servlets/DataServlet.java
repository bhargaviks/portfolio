// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  
  private ArrayList<String> messages;

  /** Initializing the arraylist that holds the messages with hard-coded strings */
  @Override
  public void init() {
    messages = new ArrayList<>();
  }

  /* This function is executed when fetch('/data') is called. The goal of this function is to create a gson object, conver messages into gson, and send it bac to script.js */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Gson g = new Gson();                                // Imported in pom and above
    String json = g.toJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
    /* 
      TODO:
      response.setContentType("text/html;");
      response.getWriter().println("<h1>Hello Bhargavi!</h1>");   // Added my name instead of  World.
      response.getWriter().println("<h2>I am learning Servlets!</h2>");
    */
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "");
    messages.add(text);
    response.sendRedirect("/comments.html");

    // TODO: Respond with the result.
    // response.setContentType("text/html;");
    // response.getWriter().println(Arrays.toString(messages));
  }


  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

}
