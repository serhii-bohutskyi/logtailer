package com.bohutskyi.logtailer.controller;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Serhii_Bohutskyi
 */
public abstract class AbstractFrameController {

  protected void registerListener(AbstractButton button, ActionListener listener) {
    button.addActionListener(listener);
  }

}
