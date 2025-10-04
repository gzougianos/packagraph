package com.github.gzougianos.packagraph.core;

import com.github.romankh3.image.comparison.*;
import com.github.romankh3.image.comparison.model.*;

import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

class FileComparator {

    public static void assertFilesEquals(File expected, File actual) throws Exception {
        if (md5Equal(expected, actual)) {
            return;
        }

        if (isImageFile(expected) && isImageFile(actual)) {
            doImageComparison(expected, actual);
        } else {
            throw new AssertionError("Files are different");
        }
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") ||
                name.endsWith(".jpeg") ||
                name.endsWith(".png");
    }

    private static void doImageComparison(File expected, File actual) {
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expected.getAbsolutePath());
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actual.getAbsolutePath());

        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        if (Objects.equals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState())) {
            return;
        }

        if (imageComparisonResult.getDifferencePercent() > 10) {
            throw new AssertionError("Image files are different by " + imageComparisonResult.getDifferencePercent() + "%");
        }
    }

    private static File copyFileToTargetDir(File f) throws IOException {
        File targetDir = new File("./target");
        File targetFile = new File(targetDir.getCanonicalFile(), f.getName());
        Files.deleteIfExists(targetFile.toPath());
        Files.copy(f.toPath(), targetFile.toPath());
        return targetFile;
    }

    private static boolean md5Equal(File file1, File file2) throws IOException, NoSuchAlgorithmException {
        return md5Checksum(file1).equals(md5Checksum(file2));
    }

    private static String md5Checksum(File file) throws IOException, NoSuchAlgorithmException {
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
