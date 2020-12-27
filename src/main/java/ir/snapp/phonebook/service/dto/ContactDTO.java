package ir.snapp.phonebook.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.snapp.phonebook.configuration.validation.PersistGroup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldNameConstants
public class ContactDTO {

    @JsonIgnore
    private Long id;
    @NotBlank(groups = PersistGroup.class)
    private String name;
    @NotBlank(groups = PersistGroup.class)
    @Size(min = 11, max = 11, groups = PersistGroup.class)
    @Pattern(regexp = "^09[0-9]+$")
    private String phoneNumber;
    @NotBlank(groups = PersistGroup.class)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,64}$", groups = PersistGroup.class)
    private String email;
    @NotBlank(groups = PersistGroup.class)
    private String organization;
    @NotBlank(groups = PersistGroup.class)
    private String github;
}
