package com.google.sps.comment.repository;

import com.google.sps.comment.Comment;
import org.jetbrains.annotations.Nullable;
import java.util.List; 

/** Handles the storage of comments. */
public interface CommentRepository {
    public void saveComment(Comment comment);

    /* Returns all comments from the database. */
    public List<Comment> getAllComments();

    /* 
    * Returns maxNoComments, if there are available at least maxNoComments comments in the database. 
    * Otherwise returns all comments.
    */
    public List<Comment> getGivenNumberOfComments(int maxNoComments);

    public void deleteAllComments();
}