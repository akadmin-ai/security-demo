package org.springframework.samples.petclinic.codeql;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.sql.*;
import java.nio.file.*;

@Controller
public class VulnerableController {

    @GetMapping("/sqlInjection")
    public void sqlInjection(@RequestParam("id") String id) throws SQLException {
        // SQL Injection Vulnerability
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "root", "password");
        String query = "SELECT * FROM users WHERE id = '" + id + "'"; // Vulnerable to SQL injection
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("username"));
        }
    }

    @GetMapping("/pathTraversal")
    public void pathTraversal(@RequestParam("filename") String filename) throws IOException {
        // Path Traversal Vulnerability
        Path filePath = Paths.get("/home/user/files", filename); // Vulnerable to path traversal
        if (Files.exists(filePath)) {
            BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        }
    }

    @GetMapping("/commandInjection")
    public void commandInjection(@RequestParam("cmd") String cmd) throws IOException {
        // Command Injection Vulnerability
        Runtime.getRuntime().exec("/usr/bin/bash -c " + cmd); // Vulnerable to command injection
    }

    @GetMapping("/fileUpload")
    public void fileUpload(@RequestParam("file") File file) throws IOException {
        // Unsafe file upload vulnerability
        File dest = new File("/uploads/" + file.getName());
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    @GetMapping("/insecureRandom")
    public void insecureRandom() {
        // Insecure random number generation vulnerability
        java.util.Random rand = new java.util.Random(); // Vulnerable to predictable random number generation
        int randomNumber = rand.nextInt();
        System.out.println(randomNumber);
    }

    @GetMapping("/insecureHashing")
    public void insecureHashing(@RequestParam("password") String password) {
        // Insecure Hashing Vulnerability
        String hashedPassword = String.valueOf(password.hashCode()); // Insecure hashing
        System.out.println("Hashed password: " + hashedPassword);
    }
}
