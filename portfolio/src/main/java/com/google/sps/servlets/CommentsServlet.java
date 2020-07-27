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

import com.google.sps.comment.Comment;
import com.google.sps.user.User;
import com.google.sps.user.repository.UserRepository;
import com.google.sps.user.repository.UserRepositoryFactory;
import com.google.sps.comment.repository.CommentRepository;
import com.google.sps.comment.repository.CommentRepositoryFactory;
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
import java.util.HashMap;

/** This servlet handles comment's data: 
 *- doPost() stores get's the recently submitted comment's data from the request 
 *          and stores in a database using Datastore API
 *- doGet() returns the comments from the database, aftern converting them to JSON*/
@WebServlet("/comments-data")
public class CommentsServlet extends HttpServlet {
    private class CommentData{
        private String message;
        private User sender;

        CommentData(String message, User sender){
            this.message = message;
            this.sender = sender;
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderId = getUserIdFromUserService();
        //it's guaranteed that the user is logged in at this point
        UserRepository myUserRepository = new UserRepositoryFactory()
                                            .getUserRepository(UserRepositoryFactory.UserRepositoryType.DATASTORE);
        CommentRepository myCommentRepository = new CommentRepositoryFactory()
                                            .getCommentRepository(CommentRepositoryFactory.CommentRepositoryType.DATASTORE);
        User sender = myUserRepository.getUser(senderId);
        if(sender==null){ //save user data only if not already saved
            sender = getUserFromRequest(request);
            myUserRepository.saveUser(sender);
        }
        myCommentRepository.saveComment(getCommentFromRequest(senderId, request));
        response.sendRedirect("/contact.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
        CommentRepository myCommentRepository = new CommentRepositoryFactory()
                                            .getCommentRepository(CommentRepositoryFactory.CommentRepositoryType.DATASTORE);
        UserRepository myUserRepository = new UserRepositoryFactory()
                                            .getUserRepository(UserRepositoryFactory.UserRepositoryType.DATASTORE);
        int maxComments = Integer.parseInt(request.getParameter("max-comments"));
        List<Comment> comments = myCommentRepository.getGivenNumberOfComments(maxComments);
        HashMap<Integer, User> users = myUserRepository.getAllUsers();
        ArrayList<CommentData> result = new ArrayList<CommentData>();
        for(Comment comment : comments){
            User correspondingUser = users.get(comment.getSenderId().hashCode());
            result.add(new CommentData(comment.getMessage(), correspondingUser));
        }
        response.setContentType("application/json;");
        response.getWriter().println(convertToJsonUsingGson(result));
    }

    private String convertToJsonUsingGson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }

    private User getUserFromRequest(HttpServletRequest request){
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

    private Comment getCommentFromRequest(String senderId, HttpServletRequest request){
        String message = request.getParameter("comment");
        Comment newComment = new Comment(senderId, message);
        return newComment;
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