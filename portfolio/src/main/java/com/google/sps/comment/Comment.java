package com.google.sps.comment;

import com.google.sps.user.User;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import org.jetbrains.annotations.Nullable;


/** Stores the data related to one comment. */
public class Comment {
  private final String senderId;
  private final String message;
  @Nullable
  private final String imgBlobKey;

  public Comment(String senderId, String message, @Nullable String imgBlobKey) {
    this.senderId = senderId;
    this.message = message;
    this.imgBlobKey = imgBlobKey;
  }

  public String getSenderId() {
    return senderId;
  }
  
  public String getMessage() {
    return message;
  }

  @Nullable
  public String getImgBlobKey() {
    return imgBlobKey;
  }
}