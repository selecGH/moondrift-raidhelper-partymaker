package com.selec.moondrift.boss.web;

import com.selec.moondrift.boss.internal.MoondriftGuildData;
import com.selec.moondrift.boss.model.group.Raid;
import com.selec.moondrift.boss.model.json.EventData;
import com.selec.moondrift.boss.service.ExportPartyService;
import com.selec.moondrift.boss.service.GroupBuilder;
import com.selec.moondrift.boss.validation.MapMembersValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/export")
@RequiredArgsConstructor
public class ExportPartyController {

    private final ExportPartyService exportPartyService;
    private final GroupBuilder groupBuilder;
    private final MapMembersValidator validator;

    @PostMapping("/xlsx")
    public void exportXlsParty(@RequestBody EventData data, HttpServletResponse response) throws IOException {
        validator.validateMapMembers(data);
        var result = exportPartyService.exportXlsParty(data);
        IOUtils.copy(result, response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping("/json")
    public List<Raid> exportJsonGroups(@RequestBody EventData data) {
        validator.validateMapMembers(data);
        return this.groupBuilder.buildGroups(
                data.getSignups()
                        .stream()
                        .filter(user -> user.getSpec().equals("Accepted"))
                        .map(user -> MoondriftGuildData.MOONDRIFT_MEMBERS.get(user.getUserid()))
                        .collect(Collectors.toList())
        );
    }

}
