package com.bohutskyi.logtailer.ui;

import javax.swing.JOptionPane;
import java.util.List;

public class Notifications {

    public static void showFormValidationAlert(List<String> errors) {
        StringBuilder message = new StringBuilder();
        for (String error : errors) {
            message.append(error).append("\n");
        }
        JOptionPane.showMessageDialog(null,
                message.toString(),
                "Validation Alert",
                JOptionPane.ERROR_MESSAGE);
    }

}
