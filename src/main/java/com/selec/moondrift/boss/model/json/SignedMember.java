package com.selec.moondrift.boss.model.json;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignedMember {
    private String role;
    private String name;
    private String specEmote;
    private long signuptime;
    private long position;
    private String classEmote;
    private String userid;
    private String reqResClass;
    private String spec;
    private LocalDateTime timestamp;
    private String status;
}
