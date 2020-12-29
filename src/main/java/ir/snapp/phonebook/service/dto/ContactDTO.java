package ir.snapp.phonebook.service.dto;

import ir.snapp.phonebook.configuration.constants.TemplateRegex;
import ir.snapp.phonebook.configuration.validation.PersistGroup;
import ir.snapp.phonebook.configuration.validation.SearchGroup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldNameConstants
public class ContactDTO {

    @Null(groups = {PersistGroup.class, SearchGroup.class})
    private Long id;

    @NotBlank(groups = PersistGroup.class)
    private String name;

    @NotBlank(groups = PersistGroup.class)
    @Size(min = 11, max = 11, groups = PersistGroup.class)
    @Size(max = 11, groups = SearchGroup.class)
    @Pattern(regexp = TemplateRegex.CELLPHONE, groups = PersistGroup.class)
    @Pattern(regexp = TemplateRegex.NUMBER, groups = SearchGroup.class)
    private String phoneNumber;

    @NotBlank(groups = PersistGroup.class)
    @Pattern(regexp = TemplateRegex.EMAIL, groups = PersistGroup.class)
    private String email;

    @NotBlank(groups = PersistGroup.class)
    private String organization;

    @NotBlank(groups = PersistGroup.class)
    private String github;
}
