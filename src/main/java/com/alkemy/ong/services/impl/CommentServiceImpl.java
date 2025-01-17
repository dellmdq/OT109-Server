package com.alkemy.ong.services.impl;

import com.alkemy.ong.dtos.responses.CommentListDTO;
import com.alkemy.ong.dtos.requests.CommentPostRequestDTO;
import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repositories.CommentRepository;
import com.alkemy.ong.repositories.NewsRepository;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public CommentDTO create(CommentPostRequestDTO commentDTO) {

        Comment entity = commentMapper.commentDto2Entity(commentDTO);
        Comment entityCreated = commentRepository.save(entity);
        CommentDTO result = commentMapper.commentEntity2Dto(entityCreated);
        return result;
    }


    @Override
    public List<Comment> findAll() throws NotFoundException {
        return commentRepository.findByOrderByCreatedAtDesc();
    }

    @Override
    public Comment findById(Long id) throws NotFoundException {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment Not Found.");
        }
        return commentRepository.findById(id).orElseThrow( () -> new NotFoundException("Comment not found."));
    }

    @Override
    public Comment update(Comment comment, Long id) throws NotFoundException {

        Comment uptComment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment does not exist"));

        uptComment.setBody(comment.getBody());
        return commentRepository.save(uptComment);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentListDTO> findCommentsByNewsId(Long id) {
        return commentRepository.findCommentsByNewsId(id);
    }

    @Override
    public Boolean validUser(String email,Long commentId) {
        Optional<String> owner = commentRepository.findOwnerEmail(commentId);
        return owner.map(s -> s.equals(email)).orElse(false);
    }

    @Override
    public Boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }


}
