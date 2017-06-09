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

import com.drentsoft.cardresizer.CardResizer;
import com.drentsoft.cardresizer.FileCheck;
import com.drentsoft.cardresizer.OutputDimension;
import com.drentsoft.cardresizer.PDFPageInfo;
import com.drentsoft.cardresizer.ProcessWorker;
import com.drentsoft.cardresizer.ProfileFile;
import com.drentsoft.cardresizer.job.PDFJob;
import com.drentsoft.cardresizer.job.render.PDFRenderWorker;
import com.drentsoft.cardresizer.ui.GlobalPanel;
import com.drentsoft.cardresizer.ui.JobPanel;
import com.drentsoft.cardresizer.ui.OutputFormatPanel;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class PDFJobPanel extends JobPanel {

    PDFJob job;
    DefaultComboBoxModel profilesModel;
    
    /**
     * Creates new form PDFJobPanel
     */
    public PDFJobPanel() {
        initComponents();
        System.out.println("Loading PDF Panel with no job");
    }
    
    public PDFJobPanel( PDFJob job ) {
        initComponents();
        setProfilesModel( CardResizer.iccProfiles );
        addCustomListeners();
        this.job = job;
        iccProfileCmbo.setSelectedItem( job.ICCProfile );
        useProfileChk.setSelected( job.useICCProfile );
        filesPnl.setJob(job);
        outputPanel.setJob(job);
    }
    
    private void addCustomListeners() {
        filesPnl.getResBtn.addActionListener((ActionEvent e) -> {
            if( filesPnl.pagesList.getSelectedIndex() >= 0 ) {
                if( job.outputDimensions.size() > 0 ) {
                    PDRectangle chuck = job.doc.getPage(filesPnl.pagesList.getSelectedIndex() ).getCropBox();
                    OutputFormatPanel formatPnl = (OutputFormatPanel) outputPanel.outputTabs.getSelectedComponent();
                    float scale = Float.parseFloat( DPISpin.getValue().toString() ) / PDFJob.PDFDPI;
                    formatPnl.setDimensions( (int) (chuck.getWidth() * scale), (int) (chuck.getHeight() * scale ) );
                }
            }
        });
    }
    
    public final void setProfilesModel( ArrayList<ProfileFile> original ) {
        profilesModel = new DefaultComboBoxModel();
        for( ProfileFile f : original ) {
            profilesModel.addElement(f);
        }
        iccProfileCmbo.setModel(profilesModel);
    }
    
    private void addFile( File f ) {
        if( !f.isFile() ) {
            JOptionPane.showMessageDialog(filesPnl, "Please select a single PDF file for PDF jobs.");
        }
        if( FileCheck.isImageFile(f) ) {
            String error = "Please create an Image Job to load images.";
            JOptionPane.showMessageDialog(this, error, WRONG_FORMAT_TITLE, JOptionPane.INFORMATION_MESSAGE );
            System.out.println(error);
        } else if( FileCheck.isPDFFile(f) ) {
            // create PDF thingy
            if( job == null ) {
                job = new PDFJob();
            }
            job.original = f;
            job.loadDocument();
            System.out.println("Found " + job.doc.getNumberOfPages() + " pages" );
            for( int i = 0; i < job.doc.getPages().getCount(); i++ ) {
                PDFPageInfo info = new PDFPageInfo(i);
                filesPnl.pagesModel.addElement(info);
                job.renderingPages.add(info);
            }
            fileBrowseField.setText( job.original.getAbsolutePath() );
            expWidthSpin.setValue( job.doc.getPage(0).getCropBox().getWidth() );
            expHeightSpin.setValue( job.doc.getPage(0).getCropBox().getHeight() );
        } else {
            System.err.println( "Can't add file " + f.getAbsolutePath() + " as it's not a supported format." );
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

        previewBtn = new javax.swing.JButton();
        processJobBtn = new javax.swing.JButton();
        inFileLbl = new javax.swing.JLabel();
        fileBrowseField = new javax.swing.JTextField();
        browseFileBtn = new javax.swing.JButton();
        filesPnl = new com.drentsoft.cardresizer.ui.pdfjob.PDFFilesPanel();
        outputPanel = new com.drentsoft.cardresizer.ui.OutputOverviewPanel();
        jLabel1 = new javax.swing.JLabel();
        DPISpin = new javax.swing.JSpinner();
        getDPIBtn = new javax.swing.JButton();
        expWidthSpin = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        expHeightSpin = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        iccProfileCmbo = new javax.swing.JComboBox<>();
        useProfileChk = new javax.swing.JCheckBox();

        previewBtn.setText("Preview");
        previewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewBtnActionPerformed(evt);
            }
        });

        processJobBtn.setText("Process Job");
        processJobBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processJobBtnActionPerformed(evt);
            }
        });

        inFileLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
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

        jLabel1.setText("Expected DPI:");

        DPISpin.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(300.0f), Float.valueOf(1.0f), Float.valueOf(1000.0f), Float.valueOf(100.0f)));
        DPISpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                DPISpinStateChanged(evt);
            }
        });

        getDPIBtn.setText("Calculate DPI");
        getDPIBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getDPIBtnActionPerformed(evt);
            }
        });

        expWidthSpin.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 50.0f));
        expWidthSpin.setToolTipText("Calculating DPI will be prioritised from the expected width field.");

        jLabel2.setText("Expected Width:");

        jLabel3.setText("Expected Height:");

        expHeightSpin.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 50.0f));

        jLabel4.setText("ICC Profile:");

        iccProfileCmbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iccProfileCmboActionPerformed(evt);
            }
        });

        useProfileChk.setText("Use Custom Profile");
        useProfileChk.setToolTipText("");
        useProfileChk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useProfileChkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filesPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expWidthSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expHeightSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getDPIBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DPISpin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(processJobBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(previewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(inFileLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(iccProfileCmbo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(useProfileChk))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(fileBrowseField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseFileBtn))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inFileLbl)
                    .addComponent(fileBrowseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFileBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(iccProfileCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useProfileChk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filesPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(expHeightSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(getDPIBtn))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(DPISpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(expWidthSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previewBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(processJobBtn))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileBrowseFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileBrowseFieldActionPerformed
        File test = new File( fileBrowseField.getText() );
        if( test.exists() ) {
            addFile(test);
        }
    }//GEN-LAST:event_fileBrowseFieldActionPerformed

    private void browseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileBtnActionPerformed
        JFileChooser fc = GlobalPanel.fc;
        fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        fc.setMultiSelectionEnabled(false);
        int rv = fc.showOpenDialog(this);
        if( rv == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null ) {
            addFile( fc.getSelectedFile() );
        }
    }//GEN-LAST:event_browseFileBtnActionPerformed

    private void previewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewBtnActionPerformed
        if( filesPnl.pagesList.getSelectedIndex() >= 0 ) {
            OutputDimension dim = job.outputDimensions.get(outputPanel.outputTabs.getSelectedIndex() );
            PDFPageInfo info = job.renderingPages.get( filesPnl.pagesList.getSelectedIndex() );
            if( info != null ) {
                System.out.println("Rendering page "  + info.getPageID() );
                PDFRenderWorker worker = new PDFRenderWorker();
                worker.setPage( job, info.getPageID(), dim );
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
                JOptionPane.showMessageDialog(this, "Nope");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a file to preview.");
        }
    }//GEN-LAST:event_previewBtnActionPerformed

    private void getDPIBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getDPIBtnActionPerformed
        if( job.doc == null ) {
            JOptionPane.showMessageDialog(this, "Please load a PDF document first.");
            return;
        }
        float DPI;// = Float.valueOf( DPISpin.getValue().toString() );
        if( (float) expWidthSpin.getValue() > 0 ) {
            float expected = Float.parseFloat( expWidthSpin.getValue().toString() );
            float stated = job.doc.getPage(0).getCropBox().getWidth();
            DPI = PDFJob.PDFDPI * ( expected / stated );
            DPISpin.setValue( DPI );
        } else if( (int) expHeightSpin.getValue() > 0 ) {
            float expected = Float.parseFloat( expHeightSpin.getValue().toString() );
            float stated = job.doc.getPage(0).getCropBox().getHeight();
            DPI = PDFJob.PDFDPI * ( expected / stated );
            DPISpin.setValue( DPI );
        } else {
            JOptionPane.showMessageDialog(this, "Please put in your expected width or height to calculate DPI.");
        }
    }//GEN-LAST:event_getDPIBtnActionPerformed

    private void DPISpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_DPISpinStateChanged
        job.actualDPI = Float.parseFloat(DPISpin.getValue().toString());
    }//GEN-LAST:event_DPISpinStateChanged

    private void processJobBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processJobBtnActionPerformed
        ProcessWorker worker = new ProcessWorker(job);
        worker.setTarget(processJobBtn);
        worker.execute();
    }//GEN-LAST:event_processJobBtnActionPerformed

    private void useProfileChkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useProfileChkActionPerformed
        job.useICCProfile = useProfileChk.isSelected();
    }//GEN-LAST:event_useProfileChkActionPerformed

    private void iccProfileCmboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iccProfileCmboActionPerformed
        job.ICCProfile = (ProfileFile) iccProfileCmbo.getSelectedItem();
    }//GEN-LAST:event_iccProfileCmboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner DPISpin;
    private javax.swing.JButton browseFileBtn;
    private javax.swing.JSpinner expHeightSpin;
    private javax.swing.JSpinner expWidthSpin;
    private javax.swing.JTextField fileBrowseField;
    private com.drentsoft.cardresizer.ui.pdfjob.PDFFilesPanel filesPnl;
    private javax.swing.JButton getDPIBtn;
    private javax.swing.JComboBox<String> iccProfileCmbo;
    private javax.swing.JLabel inFileLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.drentsoft.cardresizer.ui.OutputOverviewPanel outputPanel;
    public javax.swing.JButton previewBtn;
    private javax.swing.JButton processJobBtn;
    private javax.swing.JCheckBox useProfileChk;
    // End of variables declaration//GEN-END:variables
}
