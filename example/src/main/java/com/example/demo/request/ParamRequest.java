package com.example.demo.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-19
 */
@Data
public class ParamRequest {
    private static final String SWITCH_ON = "on";
    private static final String SWITCH_OFF = "off";

    private String pwdSwitch;

    @AssertTrue(message = "开关字符串只能是 on 或 off")
    private boolean isPwdSwitch() {
        return SWITCH_ON.equals(pwdSwitch) || SWITCH_OFF.equals(pwdSwitch);
    }
}
