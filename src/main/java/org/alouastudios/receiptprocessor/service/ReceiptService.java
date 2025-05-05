package org.alouastudios.receiptprocessor.service;

import org.alouastudios.receiptprocessor.exception.InvalidDateTimeFormatException;
import org.alouastudios.receiptprocessor.exception.ReceiptNotFoundException;
import org.alouastudios.receiptprocessor.model.Item;
import org.alouastudios.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ReceiptService {

    public ReceiptService() {}

    private final Map<String, Receipt> storage = new HashMap<>();

    public String submitReceipt(Receipt receipt) {
        validateDateTime(receipt.getPurchaseDate(), receipt.getPurchaseTime());

        receipt.setId(UUID.randomUUID().toString());
        receipt.setRetailer(receipt.getRetailer().trim());

        // Trimming retailer and short descriptions of leading/trailing white space before pushing to "database" storage
        if (receipt.getItems() != null) {
            List<Item> trimmedItems = new ArrayList<>();
            for (Item item : receipt.getItems()) {
                Item trimmedItem = new Item();
                trimmedItem.setShortDescription(item.getShortDescription().trim());
                trimmedItem.setPrice(item.getPrice());
                trimmedItems.add(trimmedItem);
            }
            receipt.setItems(trimmedItems);
        }

        storage.put(receipt.getId(), receipt);

        return receipt.getId();
    }

    public int getReceiptPoints(String receiptId) {
        Receipt receipt = storage.get(receiptId);
        if (receipt == null) throw new ReceiptNotFoundException(receiptId);

        int receiptPoints = 0;

        receiptPoints += calculatePointsForRetailer(receipt.getRetailer());
        receiptPoints += calculatePointsForTotal(receipt.getTotal());
        receiptPoints += calculatePointsForItems(receipt.getItems());
        receiptPoints += calculatePointsForDateTime(receipt.getPurchaseDate(), receipt.getPurchaseTime());

        return receiptPoints;
    }

    private int calculatePointsForRetailer(String retailer) {
        int result = 0;
        for (char c : retailer.toCharArray()) {
            if (Character.isLetterOrDigit(c)) result++;
        }
        return result;
    }

    private int calculatePointsForTotal(String total) {
        int result = 0;

        // Check if the dollar amount has no cents
        String[] totalPriceParts = total.split("\\.");
        if (Integer.parseInt(totalPriceParts[1]) == 0) result += 50;

        // Check if the total is a multiple of 0.25 or 25 cents
        String concatTotalPrice = totalPriceParts[0] + totalPriceParts[1];
        if (Integer.parseInt(concatTotalPrice) % 25 == 0) result += 25;

        return result;
    }

    private int calculatePointsForItems(List<Item> items) {
        int result = 0;

        // 5 points for every 2 items
        result += (items.size() / 2) * 5;

        for (Item item : items) {
            // Check if description length is a multiple of 3
            if (item.getShortDescription().length() % 3 == 0) {
                // Multiple price by 0.2 and round up (ceil operation)
                double itemPrice = Double.parseDouble(item.getPrice());
                result += (int) Math.ceil(itemPrice * 0.2);
            }
        }

        return result;
    }

    private int calculatePointsForDateTime(String purchaseDate, String purchaseTime) {
        int result = 0;

        // NOTE: purchaseDate will be in "yyyy-mm-dd" format

        int day = LocalDate.parse(purchaseDate).getDayOfMonth();
        boolean isValidDay = day % 2 != 0;
        if (isValidDay) result += 6;

        // NOTE: purchaseTime will be in 24-hour format

        LocalTime time = LocalTime.parse(purchaseTime);
        boolean isValidTime = time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0));
        if (isValidTime) result += 10;

        return result;
    }

    // This function is to check purchaseDate and purchaseTime given in the POST request follows
    // yyyy-mm-dd and hh-mm format
    private void validateDateTime(String purchaseDate, String purchaseTime) {
        try {
            LocalDate.parse(purchaseDate); // throws DateTimeParseException if invalid
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }

        try {
            LocalTime.parse(purchaseTime); // throws DateTimeParseException if invalid
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }
    }
}
