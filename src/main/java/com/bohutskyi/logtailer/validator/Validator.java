package com.bohutskyi.logtailer.validator;

import com.bohutskyi.logtailer.service.FormParameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Validator {

    public List<String> validate(Map<FormParameter, String> params) {
        List<String> errors = new ArrayList<String>();
        String host = params.get(FormParameter.HOST);
        String port = params.get(FormParameter.PORT);
        String username = params.get(FormParameter.USERNAME);
        String password = params.get(FormParameter.PASSWORD);
        String serverLogPath = params.get(FormParameter.SERVER_LOG_PATH);

        if (StringUtils.isEmpty(host)) {
            errors.add("Host is not valid!");
        }
        if (StringUtils.isEmpty(port) && !NumberUtils.isDigits(port)) {
            errors.add("Port is not valid!");
        }
        if (StringUtils.isEmpty(username)) {
            errors.add("Username not valid!");
        }
        if (StringUtils.isEmpty(password)) {
            errors.add("Password not valid!");
        }
        if (StringUtils.isEmpty(serverLogPath)) {
            errors.add("Server log path not valid!");
        }

        return errors;
    }
}
