package com.selec.moondrift.boss.util;

import com.selec.moondrift.boss.internal.GuildMember;

import java.util.List;

public class GroupUtils {

    public static Integer countSupports(List<GuildMember> guildMembers) {
        return Math.toIntExact(guildMembers.stream()
                        .filter(guildMember -> guildMember.getCharacterClass().getType().equals("Support"))
                .count());
    }

}
