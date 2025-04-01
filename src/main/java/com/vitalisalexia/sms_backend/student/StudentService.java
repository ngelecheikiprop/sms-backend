package com.vitalisalexia.sms_backend.student;

//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import javax.swing.plaf.synth.SynthTextAreaUI;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    public List<Student> getStudents() {
        return studentRepo.findAll();
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

        }

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
}