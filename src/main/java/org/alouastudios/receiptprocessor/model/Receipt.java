package org.alouastudios.receiptprocessor.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    // Receipt ID looks like UUID format
    @Pattern(regexp = "^\\S+$")
    private String id;

    // NOTE: Get rid of leading AND trailing white space
    @NotBlank
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @NotBlank
    private String purchaseDate;

    @NotBlank
    private String purchaseTime;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<Item> items;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;
}
