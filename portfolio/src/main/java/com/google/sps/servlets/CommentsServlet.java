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
import com.google.sps.user.User;
import com.google.sps.user.repository.UserRepository;
import com.google.sps.user.repository.UserRepositoryFactory;
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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** This servlet handles comment's data: 
 *- doPost() stores get's the recently submitted comment's data from the request 
 *          and stores in a database using Datastore API
 *- doGet() returns the comments from the database, aftern converting them to JSON*/
@WebServlet("/comments-data")
public class CommentsServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = getUserIdFromUserService();
        //it's guaranteed that the user is logged in at this point
        UserRepository myUserRepository = new UserRepositoryFactory().getUserRepository("Datastore");
        User sender = myUserRepository.getUser(userId);
        if(sender==null){ //save user data only if not already saved
            sender = getUserFromRequest(request);
            myUserRepository.saveUser(sender);
        }
        Comment.getCommentFromRequest(sender, request).saveToDatastore();
        response.sendRedirect("/contact.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
        int maxComments = Integer.parseInt(request.getParameter("max-comments"));
        List<Entity> results = getCommentsFromDatastore(maxComments); 
        response.setContentType("application/json;");
        response.getWriter().println(convertToJsonUsingGson(results));
    }

    private List<Entity> getCommentsFromDatastore(int maxComments){
        Query commentsQuery = new Query(Comment.ENTITY_NAME);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(commentsQuery);
        return results.asList(FetchOptions.Builder.withLimit(maxComments));
    }

    private String convertToJsonUsingGson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }

    private static User getUserFromRequest(HttpServletRequest request){
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = getUserEmailFromUserService();
        String phone = request.getParameter("phone");
        String jobTitle = null;
        if(request.getParameterValues("type")!=null){ //box is checked
            jobTitle = request.getParameter("job-title");
        }
        User newUser = new User(getUserIdFromUserService(), firstName, lastName, email, phone, jobTitle);
        return newUser;
    }

    public static String getUserEmailFromUserService(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getEmail();
    }

    public static String getUserIdFromUserService(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }
}