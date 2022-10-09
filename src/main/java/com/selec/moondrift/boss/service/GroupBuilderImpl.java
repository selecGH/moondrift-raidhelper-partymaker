package com.selec.moondrift.boss.service;

import com.selec.moondrift.boss.internal.GuildMember;
import com.selec.moondrift.boss.model.group.Group;
import com.selec.moondrift.boss.model.group.Raid;
import com.selec.moondrift.boss.util.GroupUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupBuilderImpl implements GroupBuilder {

    @Override
    public List<Raid> buildGroups(List<GuildMember> guildMembers) {
        Collections.shuffle(guildMembers);
        List<Group> groups = this.generateInitialGroups(guildMembers);
        return this.fillGroups(groups, guildMembers);
    }

    private List<Group> generateInitialGroups(List<GuildMember> guildMembers) {
        int numberOfGroups = GroupUtils.countSupports(guildMembers);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberOfGroups; i++) {
            groups.add(new Group());
        }
        return groups;
    }

    private List<Raid> fillGroups(List<Group> groups, List<GuildMember> guildMembers) {
        List<GuildMember> supports = guildMembers
                .stream()
                .filter(guildMember -> guildMember.getCharacterClass().getType().equals("Support"))
                .toList();
        // Add supports to the groups
        for (int i = 0; i < supports.size(); i++) {
            groups.get(i).getMembers().add(supports.get(i));
        }
        List<GuildMember> dps = guildMembers
                .stream()
                .filter(guildMember -> guildMember.getCharacterClass().getType().equals("DPS"))
                .toList();
        List<Group> filledGroups = this.fillDpsSpots(groups, dps);
        // convert group to raid with leaders.
        return this.createRaidFromGroups(filledGroups);
    }

    // TODO Add preferred synergies
    public List<Group> fillDpsSpots(List<Group> groups, List<GuildMember> guildMembers) {

        for (GuildMember gm : guildMembers) {
            int i = 0;
            // Start over from the first group until every member is filled.
            for (; i < groups.size(); i++) {
                // Don't add it to a full group or a group with the same class.
                if (!groups.get(i).isFull()
                        && !groups.get(i).hasClass(gm.getCharacterClass())) {
                    break;
                }
            }
            if (i >= groups.size()) {
                // No room for this DPS, create a new group
                groups.add(new Group());
            }
            groups.get(i).getMembers().add(gm);

        }
        return groups;
    }

    private List<Raid> createRaidFromGroups(List<Group> groups) {
        return groups.stream()
                        .collect(Collectors.partitioningBy(s -> groups.indexOf(s) <= 6))
                        .values()
                        .stream()
                        .map(list -> Raid.builder()
                                .groups(list)
                                .raidLeader("RAID_LEADER")
                                .inviteCommands(this.generateInviteCommands(list))
                                .build())
                        .toList();
    }

    private List<String> generateInviteCommands(List<Group> groups) {
        return groups.stream()
                .flatMap(group -> group.getMembers().stream()
                .map(guildMember -> "/invite " + guildMember.getName()))
                .collect(Collectors.toList());
    }

}
