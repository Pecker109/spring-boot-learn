package basic.event.controller;

import basic.event.service.BasicEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Pecker
 * @since 2025/6/10 16:35
 */
@RestController
public class BasicEventController {

    @Autowired
    BasicEventService basicEventService;

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public String publish(@PathVariable("value") String value) {
        basicEventService.publish(value);
        return "success, " + LocalDateTime.now().toString();
    }
}
