package ir.snapp.phonebook.configuration.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TemplateRegex {

    public static final String CELLPHONE = "^" + TemplateRegex.FAKER_CELLPHONE + "$";
    public static final String EMAIL = "^" + TemplateRegex.FAKER_EMAIL + "$";
    public static final String NUMBER = "^[0-9]+$";

    public static final String FAKER_CELLPHONE = "09[0-9]{9}";
    public static final String FAKER_EMAIL = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,64}";
}
