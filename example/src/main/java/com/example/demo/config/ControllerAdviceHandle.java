package com.example.demo.config;

import com.example.demo.util.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-12-09
 */
@Slf4j
@ControllerAdvice
public class ControllerAdviceHandle {
    @InitBinder
    public void initMyBinder(WebDataBinder binder) {
        log.info("使用 @ControllerAdvice + @initMyBinder 初始化数据...");
        GenericConversionService gcs = (GenericConversionService) binder.getConversionService();
        if (gcs != null) {
            gcs.addConverter(new DateConverter());
        }

    }

    @ModelAttribute
    public void addModelAttribute(Model model) {
        // 在@RequestMapping的接口中使用@ModelAttribute("user") Object name获取
        log.info("使用 @ControllerAdvice + @ModelAttribute 绑定数据到 ModelAttribute...");
        model.addAttribute("modelUser", "modelUser-value");
    }

    @ModelAttribute
    public void addRequestAttribute(HttpServletRequest request) {
        // 在@RequestMapping的接口中使用@ModelAttribute("user") Object name获取
        log.info("使用 @ControllerAdvice + @ModelAttribute 绑定数据到 RequestAttribute...");
        request.setAttribute("requestUser", "requestUser-value");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody // 如果使用了@RestControllerAdvice，这里就不需要@ResponseBody了
    public Map handler(Exception ex) {
        log.error("ControllerAdvice层-统一异常处理", ex);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 400);
        map.put("msg", ex);
        return map;
    }

}
