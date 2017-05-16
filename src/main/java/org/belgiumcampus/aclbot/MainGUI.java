/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.belgiumcampus.aclbot;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author Richard
 */
public class MainGUI extends javax.swing.JFrame {

    private Thread mainThread;

    public MainGUI() {
        initComponents();
        LogArea.setText("");
        PrintStream outStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                LogArea.append("[INFO] " + new Date(System.currentTimeMillis()) + ": " + x + "\n");
            }
        };
        PrintStream errStream = new PrintStream(System.err) {
            @Override
            public void println(String x) {
                LogArea.append("[ERROR] " + new Date(System.currentTimeMillis()) + ": " + x + "\n");
            }
        };
        System.setErr(errStream);
        System.setOut(outStream);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButt = new javax.swing.JButton();
        stopButt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        notificationArea = new javax.swing.JTextArea();
        sendNotiButt = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        LogArea = new javax.swing.JTextArea();
        logLbl = new javax.swing.JLabel();
        groupEditButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        startButt.setText("Start Bot");
        startButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtActionPerformed(evt);
            }
        });

        stopButt.setText("Stop Bot");
        stopButt.setEnabled(false);
        stopButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtActionPerformed(evt);
            }
        });

        notificationArea.setColumns(20);
        notificationArea.setLineWrap(true);
        notificationArea.setRows(5);
        notificationArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(notificationArea);

        sendNotiButt.setText("Send Notification");

        LogArea.setColumns(20);
        LogArea.setRows(5);
        jScrollPane2.setViewportView(LogArea);

        logLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logLbl.setText("Log");

        groupEditButt.setText("Group Editor");
        groupEditButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupEditButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startButt, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopButt, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendNotiButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(groupEditButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(groupEditButt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendNotiButt, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stopButt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startButt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void groupEditButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupEditButtActionPerformed
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                GroupEditorGUI geg = new GroupEditorGUI();
                geg.setVisible(true);
            }
        };
        Thread gegThread = new Thread(runner);
        gegThread.start();
    }//GEN-LAST:event_groupEditButtActionPerformed

    private void startButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtActionPerformed
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    ApiContextInitializer.init();
                    TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
                    telegramBotsApi.registerBot(new Bot());
                } catch (TelegramApiException ex) {
                    System.err.println(ex);
                }
            }
        };
        mainThread = new Thread(run);
        mainThread.start();
        stopButt.setEnabled(true);
        startButt.setEnabled(false);
    }//GEN-LAST:event_startButtActionPerformed

    private void stopButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtActionPerformed
        mainThread.stop();
        stopButt.setEnabled(false);
        startButt.setEnabled(true);
    }//GEN-LAST:event_stopButtActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea LogArea;
    private javax.swing.JButton groupEditButt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel logLbl;
    private javax.swing.JTextArea notificationArea;
    private javax.swing.JButton sendNotiButt;
    private javax.swing.JButton startButt;
    private javax.swing.JButton stopButt;
    // End of variables declaration//GEN-END:variables
}
