package com.vitalisalexia.sms_backend.student;

//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    final Logger log = LoggerFactory.getLogger(StudentService.class);

    public Page<Student> getStudents(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return studentRepo.findAll(pageable);
    }

    public Student save(Student student) {
        return studentRepo.save(student);
    }

    public Student update(Student student){
        Optional<Student> studentOptional = studentRepo.findById(student.getId());
        if(studentOptional.isPresent())
            return studentRepo.save(student);
        return null;
    }

    public Optional<Student> delete(Integer id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        studentOptional.ifPresent(student -> {
            student.setStatus(0);
            studentRepo.save(student);
        });
        return studentOptional;
    }


    public Optional<Student> getStudentById(Integer id) {
        return studentRepo.findById(id);
    }

    public Long count() {
        return studentRepo.count();
    }

    public void generate(Integer count) {
        //generating student in excel sheet logic
        System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25----------------------->Generating " + count + " students\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25----------------------->");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("studentId");
        headerRow.createCell(1).setCellValue("firstName");
        headerRow.createCell(2).setCellValue("lastName");
        headerRow.createCell(3).setCellValue("DOB");
        headerRow.createCell(4).setCellValue("class");
        headerRow.createCell(5).setCellValue("score");
        headerRow.createCell(6).setCellValue("status");
        headerRow.createCell(7).setCellValue("photoPath");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(randomString());
            row.createCell(2).setCellValue(randomString());
            row.createCell(3).setCellValue(randomDate());
            row.createCell(4).setCellValue(randomClass());
            row.createCell(5).setCellValue(randomScore());
            row.createCell(6).setCellValue(1);
            row.createCell(7).setCellValue("");
        }

        //String timeStamp = getCurrentTimestamp();
        //String filePath = "C:\\var\\log\\applications\\API\\dataprocessing\\students_"+timeStamp+".xlsx";
        String filePath = "C:\\var\\log\\applications\\API\\dataprocessing\\students.xlsx";

        File directory = new File("C:\\var\\log\\applications\\API\\dataprocessing\\");
        if (!directory.exists()) {
            directory.mkdirs();  // Create the necessary directories if they don't exist
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            // Write the workbook to the file
            workbook.write(fileOut);
            System.out.println("--------------------------------------\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------Excel file saved successfully at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private String getCurrentTimestamp() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
//        LocalDateTime now = LocalDateTime.now();
//        return now.format(formatter);
//    }
    private String randomString(){
        Random random = new Random();
        int length = 3 + random.nextInt(6);
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            randomString.append(randomChar);
        }
        //System.out.println("--------------------------------------\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------random string : " + randomString);
        return randomString.toString();
    }

    private Date randomDate() {

        Random random = new Random();
        Calendar start = Calendar.getInstance();
        start.set(2000, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2010, Calendar.DECEMBER, 31);
        long startMillis = start.getTimeInMillis();
        long endMillis = end.getTimeInMillis();
        long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
        System.out.println("--------------------------------------\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25-----------------------random date: " + randomMillis);
        return new Date(randomMillis);
    }

    private String randomClass(){
        String[] classes = {"class1", "class2", "class3", "class4", "class5"};
        Random random = new Random();
        int randIndex = random.nextInt(5);
        return classes[randIndex];
    }

    private Integer randomScore(){
        Random random = new Random();
        return random.nextInt(55) + 31;
    }

    public void processData() {
        String filePath = "C:\\var\\log\\applications\\API\\dataprocessing\\students.xlsx";
        try (FileInputStream fis = new FileInputStream(filePath)){
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet){
                if(row.getRowNum() == 0){
                    continue;
                }
                Cell cell = row.getCell(5);
                if (cell != null && cell.getCellType() == CellType.NUMERIC)
                {
                    double score = cell.getNumericCellValue();
                    log.info("score is now is {}",score);
                    cell.setCellValue(score + 10);
                    log.info("New score is {}", score + 10);
                }
            }
            try(FileOutputStream fout = new FileOutputStream(filePath)){
                workbook.write(fout);
                log.info("file update");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void uploadData() {
        String filePath = "C:\\var\\log\\applications\\API\\dataprocessing\\students.xlsx";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                Student student = new Student();
                Cell cell;

                cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    student.setId((int) cell.getNumericCellValue());
                }

                cell = row.getCell(1);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    student.setFirstName(cell.getStringCellValue());
                }

                cell = row.getCell(2);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    student.setLastName(cell.getStringCellValue());
                }

                cell = row.getCell(3);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    student.setBirthDate(cell.getDateCellValue()); // Assuming it's stored as a String for simplicity
                }

                cell = row.getCell(4);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    student.setClassName(cell.getStringCellValue());
                }

                cell = row.getCell(5);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    double score = cell.getNumericCellValue();
                    student.setScore((int) (score + 5)); // Add 5 to the score
                    log.info("Original score: {}, Updated score: {}", score, score + 5);
                }

                student.setStatus(1);

                student.setPhotoPath("");

                studentRepo.save(student);
                log.info("New student added: {}", student);
            }

            log.info("Data uploaded successfully from Excel.");
        }catch (Exception e){
            log.error("Error processing the Excel file", e);
        }
    }
}