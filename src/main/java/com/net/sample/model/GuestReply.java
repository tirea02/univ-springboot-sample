package com.net.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestReply {
    private int r_num;
    private String r_nick;
    private String r_subject;
    private LocalDateTime r_wdate;
    private int r_code;
}
