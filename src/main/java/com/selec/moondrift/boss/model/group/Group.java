package com.selec.moondrift.boss.model.group;

import com.selec.moondrift.boss.internal.ClassData;
import com.selec.moondrift.boss.internal.Constants;
import com.selec.moondrift.boss.internal.GuildMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {

    List<GuildMember> members;

    public Group() {
        members = new ArrayList<>();
    }

    public Boolean hasSupport() {
        return members.stream()
                .filter(member -> member.getCharacterClass().getType().equals("Support"))
                .findAny()
                .isPresent();
    }

    public Boolean isFull() {
        return members.size() >= Constants.MAX_GROUP_SIZE;
    }

    public Boolean hasClass(ClassData characterClass) {
        return members
                .stream()
                .anyMatch(member -> member.getCharacterClass().equals(characterClass));
    }

    public String getGroupType() {
        return members
                .stream()
                .anyMatch(member -> member.getCharacterClass().equals(ClassData.Sorceress))
                ? "Grupo de burst" : "Grupo normal";
    }

}
