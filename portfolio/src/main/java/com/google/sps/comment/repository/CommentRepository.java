package com.google.sps.comment.repository;

import com.google.sps.comment.Comment;
import org.jetbrains.annotations.Nullable;
import java.util.List; 

public interface CommentRepository{
    public void saveComment(Comment comment);

    public List<Comment> getAllComments();

    public List<Comment> getGivenNumberOfComments(int maxNoComments);

    public void deleteAllComments();
}