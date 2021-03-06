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
package com.drentsoft.cardresizer.ui.imagejob;

import com.drentsoft.cardresizer.ImageInfo;
import com.drentsoft.cardresizer.PDFPageInfo;
import com.drentsoft.cardresizer.job.ImageJob;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ImageFilesPanel extends javax.swing.JPanel {

    ImageJob job;
    DefaultListModel filesModel;
    
    /**
     * Creates new form ImageJobPanel
     */
    public ImageFilesPanel() {
        initComponents();
        filesModel = new DefaultListModel();
        filesList.setModel(filesModel);
    }
    
    public void setJob( ImageJob job ) {
        this.job = job;
        for( ImageInfo f : job.originals ) {
            filesModel.addElement(f);
        }
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
        filesList = new javax.swing.JList<>();
        remFilesBtn = new javax.swing.JButton();
        getResBtn = new javax.swing.JButton();

        filesList.setToolTipText("Shows the open files for the current job.");
        filesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filesListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(filesList);

        remFilesBtn.setText("Remove File(s)");
        remFilesBtn.setToolTipText("Remove the selected files from the job");
        remFilesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remFilesBtnActionPerformed(evt);
            }
        });

        getResBtn.setText("Get Resolution");
        getResBtn.setToolTipText("Get the selected file's resolution for resizing to the correct aspect ratio.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(remFilesBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(getResBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remFilesBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getResBtn))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void remFilesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remFilesBtnActionPerformed
        int[] selected = filesList.getSelectedIndices();
        for( int i = selected.length - 1; i >= 0; i-- ) {
            filesModel.removeElementAt(selected[i]);
            job.originals.remove(i);
        }
    }//GEN-LAST:event_remFilesBtnActionPerformed

    private void filesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filesListMouseClicked
        if( evt.getClickCount() == 2 || SwingUtilities.isRightMouseButton(evt) ) {
            ImageInfo info = (ImageInfo) filesModel.getElementAt( filesList.getSelectedIndex() );
            String val = JOptionPane.showInputDialog( this, "Set Output Name", info.getOutputName() );
            if( val != null && !val.isEmpty() ) {
                info.outputName = val;
            }
        }
    }//GEN-LAST:event_filesListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JList<String> filesList;
    public javax.swing.JButton getResBtn;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton remFilesBtn;
    // End of variables declaration//GEN-END:variables
}
