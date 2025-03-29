package com.github.gzougianos.packagraph2;

import java.io.*;
import java.nio.file.*;
import java.security.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileComparator {

    public static void assertFilesEquals(File f1, File f2) throws Exception {
        assertTrue(areFilesEqual(f1, f2));
    }

    public static boolean areFilesEqual(File file1, File file2) throws IOException, NoSuchAlgorithmException {
        return getMD5Checksum(file1).equals(getMD5Checksum(file2));
    }

    private static String getMD5Checksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(file.toPath());
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) { /* Read the file stream to update digest */ }
        }
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        File file1 = new File("path/to/file1.txt");
        File file2 = new File("path/to/file2.txt");

        if (areFilesEqual(file1, file2)) {
            System.out.println("Files are identical ✅");
        } else {
            System.out.println("Files are different ❌");
        }
    }
}
