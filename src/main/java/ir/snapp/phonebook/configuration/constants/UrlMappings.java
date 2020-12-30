package ir.snapp.phonebook.configuration.constants;

import lombok.experimental.UtilityClass;

/**
 * This class include Rest URL constants
 *
 * @author Pouya Ashna
 */
@UtilityClass
public class UrlMappings {

    private static final String CONTACT = "contacts";
    public static final String CONTACT_CREATE = CONTACT;
    public static final String CONTACT_SEARCH = CONTACT + "/search";
}
