package com.google.sps.comment.repository;

import com.google.sps.comment.Comment;
import com.google.sps.data.RepositoryType;
import com.google.sps.comment.repository.impl.DatastoreCommentRepository;
import org.jetbrains.annotations.Nullable;

/** Creates a CommentRepository according to the requested type. */
public class CommentRepositoryFactory {
  
  public CommentRepository getCommentRepository(RepositoryType repositoryType) {
    if (repositoryType == null) {
      throw new IllegalArgumentException("repositoryType can't be null!");
    }
    switch (repositoryType) {
      case DATASTORE:
        return new DatastoreCommentRepository();
      default:
        throw new IllegalArgumentException("not an existing respository type");
    }
  }
}