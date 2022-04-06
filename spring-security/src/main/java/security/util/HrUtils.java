package security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import security.model.Hr;


public class HrUtils {
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
