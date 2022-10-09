package com.selec.moondrift.boss.model.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Raid {

    String raidLeader;
    List<Group> groups;
    List<String> inviteCommands;

}
