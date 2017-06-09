package com.drentsoft.cardresizer.ui;

import com.drentsoft.cardresizer.OutputDimension;
import java.awt.Dimension;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class OutputFormatPanel extends javax.swing.JPanel {

    JobPanel owner;
    OutputDimension dim;
    
    Dimension previousAspect;
    
    boolean loaded = false;
    
    public OutputFormatPanel() {
        initComponents();
    }
    
    /**
     * Creates new form OutputFormatPanel
     * @param dim
     */
    public OutputFormatPanel(  OutputDimension dim ) {
        initComponents();
        this.dim = dim;
        int i = 0;
        for( String form : ImageIO.getWriterFileSuffixes() ) {
            formatsCmbo.addItem(form);
            if( form.equals(dim.formatName) ) {
                formatsCmbo.setSelectedIndex(i);
            }
            i++;
        }
        previousAspect = new Dimension( dim.width, dim.height );
        xSpin.setValue( dim.width );
        ySpin.setValue( dim.height );
        prefixTxt.setText( dim.prefix );
        suffixTxt.setText( dim.suffix );
        if( dim.outputPath != null ) {
            outputTxt.setText( dim.outputPath.getAbsolutePath() );
        }
        imgRotSpin.setValue( dim.imageRotation );
        maskPanel.setOutputDimension(dim);
        loaded = true;
    }
    
    public void setDimensions( int x, int y ) {
        boolean selected = lockChk.isSelected();
        if( selected ) {
            lockChk.setSelected(false);
        }
        xSpin.setValue( x );
        ySpin.setValue( y );
        if( selected ) {
            lockChk.setSelected(selected);
        }
    }
    
    public double getAspectRatio( int x, int y ) {
        return (double) x / (double) y;
    }
    
    private void setOutputDirByText() {
        try {
            File test = new File( outputTxt.getText() );
            if( test.isDirectory() && test.canWrite() ) {
                dim.outputPath = test;
            }
        } catch (NullPointerException e) {
            System.err.println( "No such folder: " + outputTxt.getText() );
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

        outputDirLbl = new javax.swing.JLabel();
        outputTxt = new javax.swing.JTextField();
        outputBrowseBtn = new javax.swing.JButton();
        outputPrefixLbl = new javax.swing.JLabel();
        prefixTxt = new javax.swing.JTextField();
        outputSuffixLbl = new javax.swing.JLabel();
        suffixTxt = new javax.swing.JTextField();
        outputFormatLbl = new javax.swing.JLabel();
        formatsCmbo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        xSpin = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        ySpin = new javax.swing.JSpinner();
        lockChk = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        imgRotSpin = new javax.swing.JSpinner();
        maskPanel = new com.drentsoft.cardresizer.ui.MaskSettingsPanel();

        outputDirLbl.setText("Output:");

        outputTxt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                outputTxtCaretUpdate(evt);
            }
        });
        outputTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputTxtActionPerformed(evt);
            }
        });

        outputBrowseBtn.setText("Browse");
        outputBrowseBtn.setToolTipText("");
        outputBrowseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputBrowseBtnActionPerformed(evt);
            }
        });

        outputPrefixLbl.setText("Prefix:");

        prefixTxt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                prefixTxtCaretUpdate(evt);
            }
        });
        prefixTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prefixTxtActionPerformed(evt);
            }
        });

        outputSuffixLbl.setText("Suffix:");

        suffixTxt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                suffixTxtCaretUpdate(evt);
            }
        });
        suffixTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suffixTxtActionPerformed(evt);
            }
        });

        outputFormatLbl.setText("Format:");

        formatsCmbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formatsCmboActionPerformed(evt);
            }
        });

        jLabel2.setText("X:");

        xSpin.setModel(new javax.swing.SpinnerNumberModel(1024, 1, null, 1));
        xSpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xSpinStateChanged(evt);
            }
        });

        jLabel3.setText("Y:");

        ySpin.setModel(new javax.swing.SpinnerNumberModel(1024, 1, null, 1));
        ySpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ySpinStateChanged(evt);
            }
        });

        lockChk.setText("Lock Aspect");

        jLabel4.setText("Rotation:");

        imgRotSpin.setModel(new javax.swing.SpinnerNumberModel(0, -360, 360, 90));
        imgRotSpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                imgRotSpinStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(outputPrefixLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prefixTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputSuffixLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suffixTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputFormatLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formatsCmbo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(outputDirLbl)
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(outputTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputBrowseBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ySpin, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lockChk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgRotSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addComponent(maskPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(imgRotSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(xSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(ySpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lockChk)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputDirLbl)
                    .addComponent(outputTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputBrowseBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputPrefixLbl)
                    .addComponent(prefixTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputSuffixLbl)
                    .addComponent(suffixTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputFormatLbl)
                    .addComponent(formatsCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maskPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void outputTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputTxtActionPerformed
        setOutputDirByText();
    }//GEN-LAST:event_outputTxtActionPerformed

    private void outputBrowseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputBrowseBtnActionPerformed
        GlobalPanel.fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        GlobalPanel.fc.setMultiSelectionEnabled(false);
        int rv = GlobalPanel.fc.showOpenDialog(this);
        if( rv == JFileChooser.APPROVE_OPTION ) {
            if( GlobalPanel.fc.getSelectedFile() != null ) {
                dim.outputPath = GlobalPanel.fc.getSelectedFile();
                outputTxt.setText( GlobalPanel.fc.getSelectedFile().getAbsolutePath() );
            }
}
    }//GEN-LAST:event_outputBrowseBtnActionPerformed

    private void formatsCmboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formatsCmboActionPerformed
        if( loaded ) {
            dim.formatName = formatsCmbo.getSelectedItem().toString();
            System.out.println("Format set to: " + dim.formatName );
        }
    }//GEN-LAST:event_formatsCmboActionPerformed

    private void xSpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xSpinStateChanged
        if( lockChk.isSelected() ) {
            double ratio = getAspectRatio( previousAspect.width, previousAspect.height );
            int newVal = (int) Math.round(Double.parseDouble(xSpin.getValue().toString()) / ratio);
            ySpin.setValue( newVal );
        }
        previousAspect.width = Integer.parseInt( xSpin.getValue().toString() );
        dim.width = previousAspect.width;
    }//GEN-LAST:event_xSpinStateChanged

    private void ySpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ySpinStateChanged
        if( lockChk.isSelected() ) {
            double ratio = getAspectRatio( previousAspect.width, previousAspect.height );
            int newVal = (int) Math.round(Double.parseDouble(ySpin.getValue().toString()) * ratio);
            xSpin.setValue( newVal );
        }
        previousAspect.height = Integer.parseInt(ySpin.getValue().toString() );
        dim.height = previousAspect.height;
    }//GEN-LAST:event_ySpinStateChanged

    private void prefixTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prefixTxtActionPerformed
        dim.prefix = prefixTxt.getText();
    }//GEN-LAST:event_prefixTxtActionPerformed

    private void suffixTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suffixTxtActionPerformed
        dim.suffix = suffixTxt.getText();
    }//GEN-LAST:event_suffixTxtActionPerformed

    private void prefixTxtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_prefixTxtCaretUpdate
        dim.prefix = prefixTxt.getText();
    }//GEN-LAST:event_prefixTxtCaretUpdate

    private void suffixTxtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_suffixTxtCaretUpdate
        dim.suffix = suffixTxt.getText();
    }//GEN-LAST:event_suffixTxtCaretUpdate

    private void imgRotSpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_imgRotSpinStateChanged
        dim.imageRotation = Double.parseDouble( imgRotSpin.getValue().toString() );
    }//GEN-LAST:event_imgRotSpinStateChanged

    private void outputTxtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_outputTxtCaretUpdate
        setOutputDirByText();
    }//GEN-LAST:event_outputTxtCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> formatsCmbo;
    private javax.swing.JSpinner imgRotSpin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox lockChk;
    private com.drentsoft.cardresizer.ui.MaskSettingsPanel maskPanel;
    private javax.swing.JButton outputBrowseBtn;
    private javax.swing.JLabel outputDirLbl;
    private javax.swing.JLabel outputFormatLbl;
    private javax.swing.JLabel outputPrefixLbl;
    private javax.swing.JLabel outputSuffixLbl;
    private javax.swing.JTextField outputTxt;
    private javax.swing.JTextField prefixTxt;
    private javax.swing.JTextField suffixTxt;
    private javax.swing.JSpinner xSpin;
    private javax.swing.JSpinner ySpin;
    // End of variables declaration//GEN-END:variables
}
