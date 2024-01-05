package com.net.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
    private int g_num;
    private String userID;
    private String g_name;
    private String g_title;
    private String g_content;
    private LocalDateTime g_wdate;
    private int g_hit;
    private String g_img_file;


}
