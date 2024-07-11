import java.nio.file.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Jiff {
    public static void main(String[] args) {
        if (args.length < 2) {
            if (args.length < 1) System.out.println("No input provided. Please use the program as follows:\n\t java jiff <file 1> <file 2>");
            else                 System.out.printf ("No input provided. Please use the program as follows:\n\t java jiff %s <file 2>\n", args[0]);
        } else {
            if (args[0].equals(args[1])) {
                System.out.println("They are the same path, please provide two different paths.");
                return;
            }
            //Here we recognize two different files and see their respective last modification timestamps
            Path firstFilePath  = Paths.get(args[0]);
            Path secondFilePath = Paths.get(args[1]);
            if (!Files.exists(firstFilePath)) {
                System.err.printf("File %s does not exist.\n", firstFilePath);
                return;
            } else if (!Files.exists(secondFilePath)) {
                System.err.printf("File %s does not exist.\n", secondFilePath);
                return;
            }
            try {
                long firstFileSize  = Files.size(firstFilePath);
                long secondFileSize = Files.size(secondFilePath);
                // If they do not have the same file size, they must be different... right?
                if (firstFileSize != secondFileSize) {
                    System.out.printf("File %s is shorter.\n", firstFileSize < secondFileSize ? firstFilePath : secondFilePath);
                    Scanner keyboard = new Scanner(System.in);
                    System.out.println("Do you want to know where it diverts? (y/n): ");
                    if (keyboard.nextLine().charAt(0) == 'y') sameFileSize(firstFilePath, secondFilePath);
                    keyboard.close();
                }
                // But same file size might be a coincidence, you never know.
                else sameFileSize(firstFilePath, secondFilePath);
                
            } catch (IOException e) {
                System.err.println("Error getting file size: " + e.getMessage());
            }
        }
    }

    private static void sameFileSize(Path firstFilePath, Path secondFilePath) {
        try {
            String firstSHA256Hash  = getFileChecksum(firstFilePath,  "SHA-256");
            String secondSHA256Hash = getFileChecksum(secondFilePath, "SHA-256");

            // Same size, same hash... It is the same, 100%.
            if (firstSHA256Hash.equals(secondSHA256Hash)) System.out.println("Files are matching");

            // [Insert "COINCIDENDE... I THINK NOT!" meme]
            // Weird, different hash but same size... Must be different then i guess.
            else differentHash(firstFilePath, secondFilePath);
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Error getting files hash: " + e.getMessage());
        }
    }

    private static void differentHash(Path firstFilePath, Path secondFilePath) {
        try (
            BufferedReader reader1    = Files.newBufferedReader(firstFilePath);
            BufferedReader reader2    = Files.newBufferedReader(secondFilePath);
        ){
            String         line1      = null;
            String         line2      = null;
            int            lineNumber = 1;
            
            // LETS GO GAMBLING!!
            while ((line1 = reader1.readLine()) != null | (line2 = reader2.readLine()) != null) {
                if (line1 == null || line2 == null) {
                    System.out.printf("File %s is shorter.\n", line1 == null ? firstFilePath : secondFilePath);
                    return;
                } else if (!line1.equals(line2)) {
                    System.out.printf("File difference in line %d:%n\n", lineNumber);
                    System.out.printf("File %s: %s%n\n", firstFilePath,  line1);
                    System.out.printf("File %s: %s%n\n", secondFilePath, line2);
                    return;
                }
                // OGH, DANG IT!
                ++lineNumber;
            }
            System.out.println("Files are matching");
        } catch (NoSuchFileException NoSuchFileException) {
            System.err.println("Path does not correspond to anything: " + NoSuchFileException.getMessage());
        } catch (IOException IOException) {
            System.err.println("Error reading file attributes: " + IOException.getMessage());
        }
    }

    private static String getFileChecksum(Path filePath, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
        }

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}