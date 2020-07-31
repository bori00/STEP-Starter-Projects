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
import com.google.sps.data.RepositoryType;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import org.jetbrains.annotations.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List; 
import java.util.ArrayList;

/** 
 * This servlet handles comment's data.
 *
 * <p>{@link #doPost(HttpServletRequest, HttpServletResponse) doPost()} stores get's the recently submitted comment's data from the request 
 * and stores in a database using Datastore API
 *
 * <p>{@link #doGet(HttpServletRequest, HttpServletResponse) doGet()} returns the
 * comments from the database, aftern converting them to JSON
 */
@WebServlet("/comments-data")
public class CommentsServlet extends HttpServlet {
    private static final String COMMENT_INPUT_NAME = "comment";
    private static final String IMG_INPUT_NAME = "commentImg";

    private class CommentData {
        private final Comment comment;
        private final User sender;

        CommentData(Comment comment, User sender) {
            this.comment = comment;
            this.sender = sender;
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderId = getUserIdFromUserService();
        // It's guaranteed that the user is logged in at this point
        UserRepository myUserRepository = new UserRepositoryFactory()
                                            .getUserRepository(RepositoryType.DATASTORE);
        CommentRepository myCommentRepository = new CommentRepositoryFactory()
                                            .getCommentRepository(RepositoryType.DATASTORE);
        User sender = myUserRepository.getUser(senderId);
        if(sender == null) { // Save user data only if not already saved
            sender = getUserFromRequest(request);
            myUserRepository.saveUser(sender);
        }
        myCommentRepository.saveComment(getCommentFromRequest(senderId, request));
        response.sendRedirect("/contact.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
        CommentRepository myCommentRepository = new CommentRepositoryFactory()
                                            .getCommentRepository(RepositoryType.DATASTORE);
        UserRepository myUserRepository = new UserRepositoryFactory()
                                            .getUserRepository(RepositoryType.DATASTORE);
        int maxComments = Integer.parseInt(request.getParameter("max-comments"));
        List<Comment> comments = myCommentRepository.getGivenNumberOfComments(maxComments);
        HashMap<String, User> users = myUserRepository.getAllUsers();
        ArrayList<CommentData> result = new ArrayList<>();
        for (Comment comment : comments) {
            User correspondingUser = users.get(comment.getSenderId());
            result.add(new CommentData(comment, correspondingUser));
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
        if(request.getParameterValues("type")!=null) { // Box is checked
            jobTitle = request.getParameter("job-title");
        }
        User newUser = new User(getUserIdFromUserService(), firstName, lastName, email, phone, jobTitle);
        return newUser;
    }

    private Comment getCommentFromRequest(String senderId, HttpServletRequest request) {
        String message = request.getParameter(COMMENT_INPUT_NAME);
        @Nullable String imgUrl = getUploadedFileUrl(request, IMG_INPUT_NAME);
        System.out.println("!!!The image's url is: " + imgUrl);
        Comment newComment = new Comment(senderId, message, imgUrl);
        return newComment;
    }

    @Nullable
    private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get(formInputElementName);

        // User submitted form without selecting a file, so we can't get a URL. (dev server)
        if (blobKeys == null || blobKeys.isEmpty()) {
            return null;
        }

        BlobKey blobKey = blobKeys.get(0);

        // User submitted form without selecting a file, so we can't get a URL. (live server)
        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
        if (blobInfo.getSize() == 0) {
            blobstoreService.delete(blobKey);
            return null;
        }

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        try {
            URL url = new URL(imagesService.getServingUrl(options));
            return url.getPath();
        } catch (MalformedURLException e) {
            return imagesService.getServingUrl(options);
        }
    }

    public static String getUserEmailFromUserService() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getEmail();
    }

    public static String getUserIdFromUserService() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }
}