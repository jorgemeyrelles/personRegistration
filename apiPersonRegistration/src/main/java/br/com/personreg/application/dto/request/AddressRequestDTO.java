package br.com.personreg.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
    
    @NotBlank(message = "Number is required")
    private String number;
    
    private String complement;
    
    @NotBlank(message = "ZIP code is required")
    private String zipCode;
    
    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
}
