package com.selec.moondrift.boss.exception;

import lombok.Getter;

@Getter
public class MissingMemberException extends RuntimeException {

    private final String memberId;

    private final String discordName;

    public MissingMemberException(String memberId, String discordName) {
        super("Any member of the guild is not in the stored map data.");
        this.memberId = memberId;
        this.discordName = discordName;
    }

}
