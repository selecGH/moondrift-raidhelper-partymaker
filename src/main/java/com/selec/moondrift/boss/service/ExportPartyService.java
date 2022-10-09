package com.selec.moondrift.boss.service;

import com.selec.moondrift.boss.model.json.EventData;

import java.io.IOException;
import java.io.InputStream;

public interface ExportPartyService {

    InputStream exportXlsParty(EventData data) throws IOException;

}
