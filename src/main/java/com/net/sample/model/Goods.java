package com.net.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private int g_code;
    private String g_name;
    private int g_su;
    private int g_dan;
    private LocalDateTime g_wdate;
}
