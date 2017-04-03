package com.bohutskyi.logtailer.ui;

import javax.swing.*;
import java.util.List;

public class Notifications {

    public static void showFormValidationAlert(List<String> errors) {
        StringBuffer message = new StringBuffer();
        for (String error : errors) {
            message.append(error).append("\n");
        }
        JOptionPane.showMessageDialog(null,
                message.toString(),
                "Validation Alert",
                JOptionPane.ERROR_MESSAGE);
    }

}
