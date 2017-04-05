package com.bohutskyi.logtailer.ui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.*;

/**
 * @author Serhii Bohutskyi
 */
public class MainFrameUi extends MainFrame {

    public MainFrameUi() {
        initComponents();

        ((DefaultCaret) logTextPane.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logTextPane.setEditable(false);
    }

    private void initComponents() {

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
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        logTextPane = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Tailer"));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 10));

        sshPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(" ssh"));

        jLabel1.setText("Host:");

        jLabel2.setText("Port:");

        hostTextField.setToolTipText("ip address or domain name of server");

        portTextField.setText("22");
        portTextField.setToolTipText("default port 22");

        jLabel3.setText("Username:");

        usernameTextField.setToolTipText("ssh username");

        jLabel4.setText("Password:");

        passwordPasswordField.setToolTipText("ssh user password");

        jLabel5.setText("Server log path:");

        serverLogPathTextField.setToolTipText("full path to server log");

        jLabel6.setText("Local log path:");

        tailButton.setText("Tail");

        tailToFileButton.setText("Tail to file");

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
    // End of variables declaration


    public static java.util.List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        java.util.List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }

    @Override
    protected void updateFont(Font font) {
        for (Component component : getAllComponents(jPanel1)) {
            component.setFont(font);
        }
    }

    @Override
    public JTextField getLocalLogPathTextField() {
        return localLogPathTextField;
    }

    @Override
    public JTextField getHostTextField() {
        return hostTextField;
    }

    @Override
    public Document getLogDocument() {
        return logDoc;
    }

    @Override
    public JPasswordField getPasswordPasswordField() {
        return passwordPasswordField;
    }

    @Override
    public JTextField getPortTextField() {
        return portTextField;
    }

    @Override
    public JTextField getServerLogPathTextField() {
        return serverLogPathTextField;
    }

    @Override
    public JButton getTailButton() {
        return tailButton;
    }

    @Override
    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    @Override
    public JButton getTailToFileButton() {
        return tailToFileButton;
    }
}
