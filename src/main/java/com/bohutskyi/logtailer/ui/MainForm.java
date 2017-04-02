package com.bohutskyi.logtailer.ui;

import com.bohutskyi.logtailer.event.*;
import com.bohutskyi.logtailer.service.FormParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Serhii Bohutskyi
 */
@Component
public class MainForm extends javax.swing.JFrame {

    private volatile boolean isTailing = false;
    private volatile boolean isTailingToLocal = false;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    private void initComponents() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        jPanel1 = new javax.swing.JPanel();
        sshPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        hostTextField = new javax.swing.JTextField();
        portTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        passwordPasswordField = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        serverLogPathTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        localLogPathTextField = new javax.swing.JTextField();
        tailButton = new javax.swing.JButton();
        tailToFileButton = new javax.swing.JButton();
        errorsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logTextPane = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Tailer"));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 10));

        sshPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(" ssh"));

        jLabel1.setText("Host:");

        jLabel2.setText("Port:");

        hostTextField.setToolTipText("ip address or domain name of server");
        hostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostTextFieldActionPerformed(evt);
            }
        });

        portTextField.setText("22");
        portTextField.setToolTipText("default port 22");
        portTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Username:");

        usernameTextField.setToolTipText("ssh username");
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("Password:");

        passwordPasswordField.setToolTipText("ssh user password");

        jLabel5.setText("Server log path:");

        serverLogPathTextField.setToolTipText("full path to server log");

        jLabel6.setText("Local log path:");

        tailButton.setText("Tail");
        tailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tailButtonActionPerformed(evt);
            }
        });

        tailToFileButton.setText("Tail to file");
        tailToFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tailToFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sshPanelLayout = new javax.swing.GroupLayout(sshPanel);
        sshPanel.setLayout(sshPanelLayout);
        sshPanelLayout.setHorizontalGroup(
                sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sshPanelLayout.createSequentialGroup()
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(sshPanelLayout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(sshPanelLayout.createSequentialGroup()
                                                                .addComponent(hostTextField)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(localLogPathTextField)
                                                        .addGroup(sshPanelLayout.createSequentialGroup()
                                                                .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(serverLogPathTextField)))
                                        .addGroup(sshPanelLayout.createSequentialGroup()
                                                .addComponent(tailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tailToFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(errorsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        sshPanelLayout.setVerticalGroup(
                sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sshPanelLayout.createSequentialGroup()
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(serverLogPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(localLogPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addComponent(errorsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tailButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(tailToFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        logTextPane.setMinimumSize(new java.awt.Dimension(16, 6));
        jScrollPane1.setViewportView(logTextPane);

        logDoc = logTextPane.getStyledDocument();

        keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.RED);
        StyleConstants.setBackground(keyWord, Color.YELLOW);
        StyleConstants.setBold(keyWord, true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sshPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(sshPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 642, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 200, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setVisible(true);
    }

    private void tailButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (isTailing) {
            publisher.publishEvent(new StopTailEvent());
            tailButton.setText("Tail");
            isTailing = false;
        } else {
            publisher.publishEvent(new StartTailEvent(prepareParameters()));
            tailButton.setText("Stop tailing");
            isTailing = true;
        }
    }

    private Map<FormParameter, String> prepareParameters() {
        HashMap<FormParameter, String> params = new HashMap<FormParameter, String>();
        params.put(FormParameter.HOST, hostTextField.getText());
        params.put(FormParameter.PORT, portTextField.getText());
        params.put(FormParameter.USERNAME, usernameTextField.getText());
        params.put(FormParameter.PASSWORD, new String(passwordPasswordField.getPassword()));
        params.put(FormParameter.SERVER_LOG_PATH, serverLogPathTextField.getText());
        return params;
    }

    private void tailToFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (isTailingToLocal) {
            publisher.publishEvent(new StopTailToFileEvent());
            tailToFileButton.setText("Tail to file");
            isTailingToLocal = false;
        } else {
            publisher.publishEvent(new StartTailToFileEvent(localLogPathTextField.getText()));
            tailToFileButton.setText("Stop tailing to file");
            isTailingToLocal = true;
        }
    }

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void portTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void hostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    @EventListener
    public void handlePrintBufferEvent(final PrintBufferEvent event) throws BadLocationException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (String log : event.getList()) {
                    try {
                        logDoc.insertString(logDoc.getLength(), "\n" + log, keyWord);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @EventListener
    public void handleShowErrors(ShowErrorsEvent showErrorsEvent) {

        tailButton.setText("Tail");
        isTailing = false;
        StringBuffer message = new StringBuffer();
        for (String error : showErrorsEvent.getErrors()) {
            message.append(error).append("\n");
        }
        JOptionPane.showMessageDialog(this, message.toString());

    }

    // Variables declaration - do not modify
    private javax.swing.JLabel errorsLabel;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField localLogPathTextField;
    private javax.swing.JTextPane logTextPane;
    private javax.swing.JPasswordField passwordPasswordField;
    private javax.swing.JTextField portTextField;
    private javax.swing.JTextField serverLogPathTextField;
    private javax.swing.JPanel sshPanel;
    private javax.swing.JButton tailButton;
    private javax.swing.JButton tailToFileButton;
    private javax.swing.JTextField usernameTextField;
    private StyledDocument logDoc;
    private SimpleAttributeSet keyWord;
    // End of variables declaration                   
}
