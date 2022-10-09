package com.selec.moondrift.boss.validation;

import com.selec.moondrift.boss.exception.MissingMemberException;
import com.selec.moondrift.boss.internal.MoondriftGuildData;
import com.selec.moondrift.boss.model.json.EventData;
import org.springframework.stereotype.Component;

@Component
public class MapMembersValidator {

    public void validateMapMembers(EventData data) {
        data.getSignups().stream()
                .filter(signedMember -> signedMember.getSpec().equals("Accepted"))
                .filter(signedMember -> !MoondriftGuildData.MOONDRIFT_MEMBERS.containsKey(signedMember.getUserid()))
                .findAny()
                .ifPresent(member -> {
                    throw new MissingMemberException(member.getUserid(), member.getName());
                });
    }

}
