package com.skychat.server.model;

import lombok.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class Chat {

    @Getter
    private Integer id;

    private Integer pid = 0;
    @NonNull
    private Integer user_id;
    @NonNull
    private Integer tag;
    @NonNull
    private String content;

    private String pictures = "";

    private Integer create_time = (int)new Timestamp(new Date().getTime()).getTime() / 1000;

    private Integer update_time = (int)new Timestamp(new Date().getTime()).getTime() / 1000;;

    private Integer status = 1;

    /*public Chat (Integer user_id,Integer tag,String content) {
        setUser_id(user_id);
        setTag(tag);
        setContent(content);
    }*/

}