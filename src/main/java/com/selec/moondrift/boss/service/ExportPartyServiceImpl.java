package com.selec.moondrift.boss.service;

import com.selec.moondrift.boss.internal.ExcelFormats;
import com.selec.moondrift.boss.internal.GuildMember;
import com.selec.moondrift.boss.internal.MoondriftGuildData;
import com.selec.moondrift.boss.model.group.Group;
import com.selec.moondrift.boss.model.group.Raid;
import com.selec.moondrift.boss.model.json.EventData;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportPartyServiceImpl implements ExportPartyService {

    private final GroupBuilder groupBuilder;
    private final ExcelFormats excelFormats;

    @Override
    public InputStream exportXlsParty(EventData data) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        this.writeUsers(workbook, data.getSignups()
                .stream()
                .filter(user -> user.getSpec().equals("Accepted"))
                .map(user -> MoondriftGuildData.MOONDRIFT_MEMBERS.get(user.getUserid()))
                .collect(Collectors.toList()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void writeUsers(XSSFWorkbook workbook, List<GuildMember> members) {
        List<Raid> raids = groupBuilder.buildGroups(members);
        this.writeRaids(workbook, raids);
    }

    private void writeRaids(XSSFWorkbook workbook, List<Raid> raids) {

        Sheet sheet = workbook.createSheet("Persons");

        Cell currentCell;
        int row = 0;
        int column = 0;
        for (Raid raid : raids) {
            Row headerRow = sheet.createRow(row++);
            // Print raid headers
            currentCell = this.createCell(sheet, headerRow, column);
            currentCell.setCellValue(raid.getRaidLeader());
            currentCell.setCellStyle(this.excelFormats.createHeaderStyle(workbook));
            for (Group group : raid.getGroups()) {
                Row groupRow = sheet.createRow(row++);
                // Print group headers
                currentCell = this.createCell(sheet, groupRow, column);
                currentCell.setCellValue(group.getGroupType());
                currentCell.setCellStyle(this.excelFormats.createGroupHeaderStyle(workbook, group));
                for (GuildMember member : group.getMembers()) {
                    Row memberRow = sheet.createRow(row++);
                    // Print member name
                    currentCell = this.createCell(sheet, memberRow, column);
                    currentCell.setCellValue(member.getName());
                    currentCell.setCellStyle(this.excelFormats.createNormalStyle(workbook));
                    // Print member class
                    currentCell = this.createCell(sheet, memberRow, column + 1);
                    currentCell.setCellValue(member.getCharacterClass().getName());
                    currentCell.setCellStyle(this.excelFormats.createNormalStyle(workbook));
                    // Add invite command to the previous line in the next column
                    currentCell = this.createCell(sheet, memberRow, column + 2);
                    currentCell.setCellValue("/invite " + member.getName());
                    currentCell.setCellStyle(this.excelFormats.createNormalStyle(workbook));
                }
            }
            sheet.autoSizeColumn(column);
            column++;
        }

    }

    private Cell createCell(Sheet sheet, Row row, Integer column) {
        return row.createCell(column);
    }

}
