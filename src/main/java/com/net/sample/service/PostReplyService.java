package com.net.sample.service;

import com.net.sample.model.PostReply;
import com.net.sample.repository.PostReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostReplyService {

    @Autowired
    private PostReplyRepository postReplyRepository;

    public void addReply(PostReply reply) {

        postReplyRepository.saveReply(reply);
    }


}
