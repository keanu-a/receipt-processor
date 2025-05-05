package org.alouastudios.receiptprocessor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    // NOTE: Get rid of leading AND trailing white space
    @NotBlank
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;
}
