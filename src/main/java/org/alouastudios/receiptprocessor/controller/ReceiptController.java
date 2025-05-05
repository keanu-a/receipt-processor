package org.alouastudios.receiptprocessor.controller;

import jakarta.validation.Valid;
import org.alouastudios.receiptprocessor.model.Receipt;
import org.alouastudios.receiptprocessor.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> submitReceipt(@Valid @RequestBody Receipt receipt) {
        System.out.println("Received receipt: " + receipt);
        String receiptId = receiptService.submitReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", receiptId));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable String id) {
        int points = receiptService.getReceiptPoints(id);
        return ResponseEntity.ok(Map.of("points", points));
    }
}
