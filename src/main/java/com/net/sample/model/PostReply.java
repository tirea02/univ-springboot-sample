package com.net.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReply {
    private int id;
    private int userAccountId;
    private int postId;
    private String content;
    private LocalDateTime date;

    //adding for convenient
    private String userId;

}
