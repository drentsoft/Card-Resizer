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

import com.drentsoft.cardresizer.CardResizer;
import com.drentsoft.cardresizer.FileCheck;
import com.drentsoft.cardresizer.ImageInfo;
import com.drentsoft.cardresizer.ProcessWorker;
import com.drentsoft.cardresizer.job.ImageJob;
import com.drentsoft.cardresizer.job.render.ImageRenderWorker;
import com.drentsoft.cardresizer.ui.GlobalPanel;
import com.drentsoft.cardresizer.ui.JobPanel;
import com.drentsoft.cardresizer.ui.OutputFormatPanel;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ImageJobPanel extends JobPanel {

    ImageJob job;
    
    /**
     * Creates new form ImageJobPanel
     */
    public ImageJobPanel() {
        initComponents();
        setCustomListeners();
        System.out.println("Loading Image Panel with no job");
    }

    public ImageJobPanel(ImageJob newJob) {
        initComponents();
        setCustomListeners();
        job = newJob;
        filesPnl.setJob(job);
        outputPanel.setJob(job);
    }
    
    private void setCustomListeners() {
        filesPnl.getResBtn.addActionListener((ActionEvent e) -> {
            if( filesPnl.filesList.getSelectedIndex() >= 0 ) {
                if( job.outputDimensions.size() > 0 ) {
                    try {
                        BufferedImage chuck = ImageIO.read( job.originals.get(filesPnl.filesList.getSelectedIndex()).path );
                        OutputFormatPanel formatPnl = (OutputFormatPanel) outputPanel.outputTabs.getSelectedComponent();
                        formatPnl.setDimensions( (int) chuck.getWidth(), (int) chuck.getHeight() );
                    } catch (IOException ex) {
                        Logger.getLogger(JobPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private void addDirectory( File d ) {
        System.err.println("Can't add directory in PDF Job.");
    }
    
    private void addFile( File f ) {
        if( FileCheck.isImageFile(f) ) {
            if( !alreadyInJob(f) ) {
                ImageInfo info = new ImageInfo(f);
                filesPnl.filesModel.addElement( info );
                job.originals.add(info);
            } else {
                JOptionPane.showMessageDialog(this, "Image already in job.", "Duplicate entry", JOptionPane.INFORMATION_MESSAGE );
            }
        } else if( FileCheck.isPDFFile(f) ) {
            // create PDF thingy
            JOptionPane.showMessageDialog(this, "Please create a PDF Job to load PDFs", WRONG_FORMAT_TITLE, JOptionPane.INFORMATION_MESSAGE );
        } else {
            System.err.println( "Can't add file " + f.getAbsolutePath() + " as it's not a supported format." );
        }
    }
    
    private void checkAddFile( File f ) {
        if( f.isDirectory() ) {
            addDirectory(f);
        } else if( f.isFile() ) {
            addFile(f);
        }
    }
    
    private boolean alreadyInJob( File f ) {
        for( ImageInfo info : job.originals ) {
            if( info.path.equals(f) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inFileLbl = new javax.swing.JLabel();
        fileBrowseField = new javax.swing.JTextField();
        browseFileBtn = new javax.swing.JButton();
        filesPnl = new com.drentsoft.cardresizer.ui.imagejob.ImageFilesPanel();
        outputPanel = new com.drentsoft.cardresizer.ui.OutputOverviewPanel();
        processJobBtn = new javax.swing.JButton();
        previewBtn = new javax.swing.JButton();

        inFileLbl.setText("Input File:");

        fileBrowseField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileBrowseFieldActionPerformed(evt);
            }
        });

        browseFileBtn.setText("Browse");
        browseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileBtnActionPerformed(evt);
            }
        });

        processJobBtn.setText("Process Job");
        processJobBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processJobBtnActionPerformed(evt);
            }
        });

        previewBtn.setText("Preview");
        previewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inFileLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileBrowseField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseFileBtn))
            .addComponent(filesPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(processJobBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(previewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inFileLbl)
                    .addComponent(fileBrowseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFileBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filesPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previewBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(processJobBtn))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileBrowseFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileBrowseFieldActionPerformed
        File test = new File( fileBrowseField.getText() );
        if( test != null ) {
            checkAddFile(test);
        }
    }//GEN-LAST:event_fileBrowseFieldActionPerformed

    private void browseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileBtnActionPerformed
        JFileChooser fc = GlobalPanel.fc;
        fc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        fc.setMultiSelectionEnabled(true);
        int rv = fc.showOpenDialog(this);
        if( rv == JFileChooser.APPROVE_OPTION ) {
            for( File f : fc.getSelectedFiles() ) {
                checkAddFile(f);
            }
        }
    }//GEN-LAST:event_browseFileBtnActionPerformed

    private void previewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewBtnActionPerformed
        if( filesPnl.filesList.getSelectedIndex() >= 0 ) {
            ImageRenderWorker worker = new ImageRenderWorker();
            worker.setImage(job, filesPnl.filesList.getSelectedIndex(), outputPanel.outputTabs.getSelectedIndex() );
            worker.addPropertyChangeListener((PropertyChangeEvent propEvt) -> {
                if( worker.getState() == SwingWorker.StateValue.STARTED ) {
                    previewBtn.setText(PREVIEW_RUNNING);
                }
                if( worker.getState() == SwingWorker.StateValue.DONE ) {
                    try {
                        BufferedImage chuck = (BufferedImage) worker.get();
                        CardResizer.updatePreview(chuck);
                        previewBtn.setText(PREVIEW_READY);
                    } catch (InterruptedException | ExecutionException ex) {
                        System.err.println("Error rendering preview.");
                    }
                }
            });
            worker.execute();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a file to preview.");
        }
    }//GEN-LAST:event_previewBtnActionPerformed

    private void processJobBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processJobBtnActionPerformed
        ProcessWorker worker = new ProcessWorker(job);
        worker.setTarget(processJobBtn);
        worker.execute();
    }//GEN-LAST:event_processJobBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseFileBtn;
    private javax.swing.JTextField fileBrowseField;
    private com.drentsoft.cardresizer.ui.imagejob.ImageFilesPanel filesPnl;
    private javax.swing.JLabel inFileLbl;
    private com.drentsoft.cardresizer.ui.OutputOverviewPanel outputPanel;
    public javax.swing.JButton previewBtn;
    private javax.swing.JButton processJobBtn;
    // End of variables declaration//GEN-END:variables
}
