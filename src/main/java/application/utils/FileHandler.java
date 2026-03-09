package application.utils;

import application.models.Receipt;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    private static final String RECEIPTS_FILE = "receipts.txt";

    public static void saveReceipt(Receipt receipt) {
        try {
            boolean fileExists = Files.exists(Paths.get(RECEIPTS_FILE));

            try (FileWriter writer = new FileWriter(RECEIPTS_FILE, true)) {
                if (fileExists) {
                    writer.write("\n");
                }
                writer.write(receipt.getFormattedReceipt());
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Error saving receipt: " + e.getMessage());
        }
    }

    public static int getNextReceiptId() {
        try {
            if (Files.exists(Paths.get(RECEIPTS_FILE))) {
                String content = new String(Files.readAllBytes(Paths.get(RECEIPTS_FILE)));
                long count = content.split("Receipt ID:").length - 1;
                return (int) count + 1;
            }
        } catch (IOException e) {
            System.err.println("Error reading receipt count: " + e.getMessage());
        }
        return 1;
    }
}
