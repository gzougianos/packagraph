package com.github.gzougianos.packagraph.core;

import com.github.romankh3.image.comparison.*;
import com.github.romankh3.image.comparison.model.*;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

class FileComparator {

    public static void assertFilesEquals(File expected, File actual) throws Exception {
        if (areFilesEqual(expected, actual)) {
            return;
        }
        
        if (expected.getName().endsWith(".svg")) {
            File copiedExpected = copyFileToTargetDir(expected);
            File copiedActual = copyFileToTargetDir(actual);
            throw new AssertionError("Files are different.\n" +
                    "Expected: " + copiedExpected.getAbsolutePath() + "\n" +
                    "Actual: " + copiedActual.getAbsolutePath());
        } else {
            BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expected.getAbsolutePath());
            BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actual.getAbsolutePath());

            ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();

            assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
        }
    }

    private static File copyFileToTargetDir(File f) throws IOException {
        File targetDir = new File("./target");
        File targetFile = new File(targetDir.getCanonicalFile(), f.getName());
        Files.deleteIfExists(targetFile.toPath());
        Files.copy(f.toPath(), targetFile.toPath());
        return targetFile;
    }

    private static boolean areFilesEqual(File file1, File file2) throws IOException, NoSuchAlgorithmException {
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
}
