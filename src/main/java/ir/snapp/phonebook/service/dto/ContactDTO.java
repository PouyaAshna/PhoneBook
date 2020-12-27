package ir.snapp.phonebook.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ContactDTO {

    @JsonIgnore
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^09[0-9]+$")
    private String phoneNumber;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,64}$")
    private String email;
    @NotBlank
    private String organization;
    @NotBlank
    private String github;
}
