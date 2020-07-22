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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments-data")
public class CommentsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comment newComment = getCommentFromRequest(request);
        putCommentToDatastore(newComment);
        response.sendRedirect("/contact.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
        PreparedQuery results = getCommentsFromDatastore(); 
        int maxComments = Integer.parseInt(request.getParameter("max-comments"));
        List<Comment> comments = new ArrayList<>();
        int noComments=0;
        for (Entity entity : results.asIterable()) {
            comments.add(getCommentFromCommentEntity(entity));
            noComments++;
            if(noComments==maxComments){
                break;
            }
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
        Comment newComment = new Comment(firstName, lastName, email, phone, message);
        if(request.getParameterValues("type")!=null){ //box is checked
            newComment.setJobRelated();
            String jobTitle = request.getParameter("job-title");
            newComment.addJobTitle(jobTitle);
        }
        return newComment;
    }

    private void putCommentToDatastore(Comment myComment){
        Entity commentEntity = getCommentEntityFromComment(myComment);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
    }

    private Entity getCommentEntityFromComment(Comment myComment){
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("firstName", myComment.getFirstName());
        commentEntity.setProperty("lastName", myComment.getLastName());
        commentEntity.setProperty("email", myComment.getEmail());
        commentEntity.setProperty("phone", myComment.getPhone());
        commentEntity.setProperty("message", myComment.getMessage());
        commentEntity.setProperty("jobRelated", String.valueOf(myComment.isJobRelated()));
        commentEntity.setProperty("jobTitle", myComment.getJobTitle());
        return commentEntity;
    }

    private PreparedQuery getCommentsFromDatastore(){
        Query commentsQuery = new Query("Comment");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(commentsQuery);
        return results;
    }

    private Comment getCommentFromCommentEntity(Entity commentEntity){
        String firstName = (String) commentEntity.getProperty("firstName");
        String lastName = (String) commentEntity.getProperty("lastName");
        String email = (String) commentEntity.getProperty("email");
        String phone = (String) commentEntity.getProperty("phone");
        String message = (String) commentEntity.getProperty("message");
        Comment newComment = new Comment(firstName, lastName, email, phone, message);
        boolean jobRelated = (boolean) Boolean.parseBoolean((String) commentEntity.getProperty("jobRelated"));
        if(jobRelated){
            String jobTitle = (String) commentEntity.getProperty("jobTitle");
            newComment.setJobRelated();
            newComment.addJobTitle(jobTitle);
        }
        return newComment;
    }

    private String convertToJsonUsingGson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }
}