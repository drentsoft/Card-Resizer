/*
 * Copyright (c) 2017, Derwent Ready @ Drentsoft
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors 
 *    may be used to endorse or promote products derived from this software 
 *    without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.drentsoft.cardresizer.ui.pdfjob;

import com.drentsoft.cardresizer.job.PDFJob;
import com.drentsoft.cardresizer.PDFPageInfo;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class PDFFilesPanel extends javax.swing.JPanel {

    PDFJob job;
    
    DefaultListModel pagesModel;
    DefaultListModel ignoredModel;
    
    /**
     * Creates new form PDFJobPanel
     */
    public PDFFilesPanel() {
        initComponents();
        pagesModel = new DefaultListModel();
        pagesList.setModel(pagesModel);
        ignoredModel = new DefaultListModel();
        ignoreList.setModel(ignoredModel);
    }
    
    public void setJob( PDFJob job ) {
        this.job = job;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        pagesList = new javax.swing.JList<>();
        getResBtn = new javax.swing.JButton();
        remPageBtn = new javax.swing.JButton();
        addPageBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ignoreList = new javax.swing.JList<>();
        clearFileBtn = new javax.swing.JButton();

        pagesList.setBorder(javax.swing.BorderFactory.createTitledBorder("Pages To Render"));
        pagesList.setToolTipText("Shows the open files for the current job.");
        pagesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pagesListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(pagesList);

        getResBtn.setText("Get Resolution");
        getResBtn.setToolTipText("Get the selected file's resolution for resizing to the correct aspect ratio.");

        remPageBtn.setText("Remove >");
        remPageBtn.setToolTipText("");
        remPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remPageBtnActionPerformed(evt);
            }
        });

        addPageBtn.setText("< Add");
        addPageBtn.setToolTipText("Don't be alarmed if the pages don't go back in the same order they'll still all render to individual files.");
        addPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPageBtnActionPerformed(evt);
            }
        });

        ignoreList.setBorder(javax.swing.BorderFactory.createTitledBorder("Pages To Ignore"));
        ignoreList.setToolTipText("Shows the open files for the current job.");
        ignoreList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ignoreListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(ignoreList);

        clearFileBtn.setText("Clear File");
        clearFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(getResBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(remPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(clearFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(remPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearFileBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getResBtn))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void remPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remPageBtnActionPerformed
        if( pagesList.getSelectedIndex() >= 0 ) {
            PDFPageInfo removing = (PDFPageInfo) pagesModel.remove( pagesList.getSelectedIndex() );
            job.renderingPages.remove(removing);
            ignoredModel.addElement(removing);
        }
    }//GEN-LAST:event_remPageBtnActionPerformed

    private void addPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPageBtnActionPerformed
        if( ignoreList.getSelectedIndex() >= 0 ) {
            PDFPageInfo removing = (PDFPageInfo) ignoredModel.remove( ignoreList.getSelectedIndex() );
            job.renderingPages.add(removing);
            pagesModel.addElement(removing);
        }
    }//GEN-LAST:event_addPageBtnActionPerformed

    private void pagesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pagesListMouseClicked
        if( evt.getClickCount() == 2 || SwingUtilities.isRightMouseButton(evt) ) {
            PDFPageInfo info = (PDFPageInfo) pagesModel.getElementAt( pagesList.getSelectedIndex() );
            String val = JOptionPane.showInputDialog( this, "Set Page Name", info.getOutputName() );
            if( val != null && !val.isEmpty() ) {
                info.outputName = val;
            }
        }
    }//GEN-LAST:event_pagesListMouseClicked

    private void clearFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFileBtnActionPerformed
        job.clearFile();
        pagesModel.clear();
        ignoredModel.clear();
    }//GEN-LAST:event_clearFileBtnActionPerformed

    private void ignoreListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ignoreListMouseClicked
        if( evt.getClickCount() == 2 || SwingUtilities.isRightMouseButton(evt) ) {
            PDFPageInfo info = (PDFPageInfo) ignoredModel.getElementAt( ignoreList.getSelectedIndex() );
            String val = JOptionPane.showInputDialog( this, "Set Page Name", info.getOutputName() );
            if( val != null && !val.isEmpty() ) {
                info.outputName = val;
            }
        }
    }//GEN-LAST:event_ignoreListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPageBtn;
    private javax.swing.JButton clearFileBtn;
    public javax.swing.JButton getResBtn;
    public javax.swing.JList<String> ignoreList;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JList<String> pagesList;
    private javax.swing.JButton remPageBtn;
    // End of variables declaration//GEN-END:variables
}
