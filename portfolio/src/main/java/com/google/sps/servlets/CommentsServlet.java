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

import com.google.sps.data.Comment;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List; 
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/** This servlet handles comment's data: 
 - doPost() stores get's the recently submitted comment's data from the request 
            and stores in a database using Datastore API
 - doGet() returns the comments from the database, aftern converting them to JSON*/
@WebServlet("/comments-data")
public class CommentsServlet extends HttpServlet {
    private final String commentEntityName = "Comment";
    private final String firstNameProperty = "firstName";
    private final String lastNameProperty = "lastName";
    private final String emailProperty = "email";
    private final String messageProperty = "message";
    private final String phoneProperty = "phone";
    private final String jobTitleProperty = "jobTitle";


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comment newComment = getCommentFromRequest(request);
        putCommentToDatastore(newComment);
        response.sendRedirect("/contact.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
        PreparedQuery results = getCommentsFromDatastore(); 
        List<Comment> comments = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            comments.add(getCommentFromCommentEntity(entity));
        }
        response.setContentType("application/json;");
        response.getWriter().println(convertToJsonUsingGson(comments));
    }

    private Comment getCommentFromRequest(HttpServletRequest request){
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("comment");
        String jobTitle = null;
        if(request.getParameterValues("type")!=null){ //box is checked
            jobTitle = request.getParameter("job-title");
        }
        Comment newComment = new Comment(firstName, lastName, email, phone, message, jobTitle);
        return newComment;
    }

    private void putCommentToDatastore(Comment myComment){
        Entity commentEntity = getCommentEntityFromComment(myComment);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
    }

    private Entity getCommentEntityFromComment(Comment myComment){
        Entity commentEntity = new Entity(commentEntityName);
        commentEntity.setProperty(firstNameProperty, myComment.getFirstName());
        commentEntity.setProperty(lastNameProperty, myComment.getLastName());
        commentEntity.setProperty(emailProperty, myComment.getEmail());
        commentEntity.setProperty(phoneProperty, myComment.getPhone());
        commentEntity.setProperty(messageProperty, myComment.getMessage());
        commentEntity.setProperty(jobTitleProperty, myComment.getJobTitle());
        return commentEntity;
    }

    private PreparedQuery getCommentsFromDatastore(){
        Query commentsQuery = new Query(commentEntityName);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(commentsQuery);
        return results;
    }

    private Comment getCommentFromCommentEntity(Entity commentEntity){
        String firstName = (String) commentEntity.getProperty(firstNameProperty);
        String lastName = (String) commentEntity.getProperty(lastNameProperty);
        String email = (String) commentEntity.getProperty(emailProperty);
        String phone = (String) commentEntity.getProperty(phoneProperty);
        String message = (String) commentEntity.getProperty(messageProperty);
        String jobTitle = (String) commentEntity.getProperty(jobTitleProperty);
        Comment newComment = new Comment(firstName, lastName, email, phone, message, jobTitle);
        return newComment;
    }

    private String convertToJsonUsingGson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }
}