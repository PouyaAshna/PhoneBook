package ir.snapp.phonebook.service.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;

/**
 * This class contains some of basic method for specification
 *
 * @author Pouya Ashna
 */
@UtilityClass
public class SpecificationUtil {

    public static final String WILDCARD = "%";

    public static String containsLowerCase(String searchField) {
        return WILDCARD + searchField.toLowerCase() + WILDCARD;
    }

    public static <E> Specification<E> nameAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) return null;
            Path<String> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.like(
                    cb.lower(path),
                    containsLowerCase(value)
            );
        };
    }
}
