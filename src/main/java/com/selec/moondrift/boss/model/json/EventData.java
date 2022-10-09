package com.selec.moondrift.boss.model.json;

import lombok.Data;

import java.util.List;

@Data
public class EventData {
    private String date;
    private String template;
    private long unixtime;
    private long lastUpdated;
    private List<SignedMember> signups;
    private String leadername;
    private String color;
    private EventAdvancedConfig advanced;
    private long closingtime;
    private String servericon;
    private String description;
    private String title;
    private String serverid;
    private String leaderid;
    private String raidid;
    private String temprole;
    private String servername;
    private String channelName;
    private String time;
    private String channelid;
}
