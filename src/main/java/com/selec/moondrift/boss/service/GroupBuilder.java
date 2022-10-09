package com.selec.moondrift.boss.service;

import com.selec.moondrift.boss.internal.GuildMember;
import com.selec.moondrift.boss.model.group.Raid;

import java.util.List;

public interface GroupBuilder {

    List<Raid> buildGroups(List<GuildMember> signedMembers);

}
