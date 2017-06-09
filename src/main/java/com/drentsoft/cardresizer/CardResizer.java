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
package com.drentsoft.cardresizer;

import com.drentsoft.cardresizer.ui.GlobalPanel;
import com.drentsoft.cardresizer.ui.HelpPanel;
import com.drentsoft.cardresizer.ui.ProgramSettingsPanel;
import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Derwent Ready @ Drentsoft
 */
public class CardResizer {
    
    public static final String APP_TITLE = "Drentsoft Card Resizer";
    
    public static final String PROP_FILE = "settings.properties";
    public static final String HELP_URL = "https://github.com/drentsoft/Card-Resizer/wiki";
    public static final String PDF_SETTING = "LOAD_PDF";
    public static final String IMG_SETTING = "LOAD_IMG";
    public static final String DIR_SETTING = "DIR";
    public static final String PRO_SETTING = "PROF_DIR";
    
    public static JFrame wnd;
    public static GlobalPanel pnl;
    public static JDialog preview;
    public static JLabel previewImg;
    public static JScrollPane previewScroll;
    
    public static DefaultComboBoxModel alphaModesModel;
    public static DefaultComboBoxModel formatsModel;
    public static ArrayList<ProfileFile> iccProfiles;
    public static JMenuBar menuBar;
    
    private JDialog settingsDlg;
    private ProgramSettingsPanel settingsPnl;
    
    private JDialog helpDlg;
    private JFXPanel fxPanel;
    private WebView browser;
    private WebEngine webEngine;

    public static Properties properties;
    
    private final boolean debug = false;
    
    public CardResizer() {        
        loadProperties();
        
        wnd = new JFrame(APP_TITLE);
        setIcon();        
        loadMenu();
        
        pnl = new GlobalPanel();
        wnd.add( new JScrollPane(pnl), BorderLayout.CENTER );
        wnd.setSize( 732 , 818 );
        if( debug ) {
        wnd.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println(wnd.getWidth() + " , " + wnd.getHeight() );
            }
        });
        }
        wnd.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        wnd.setLocationRelativeTo(wnd);
        wnd.setVisible(true);
        
        //createHelp();
        createSettingsDialog();
        createPreviewDialog();
        
        loadModels();
        if( properties != null ) {
            int loadPDF = Integer.parseInt(properties.getProperty(PDF_SETTING));
            int loadImage = Integer.parseInt(properties.getProperty(IMG_SETTING));
            if( loadImage == 1 ) {
                pnl.createImageJob();
            }
            if( loadPDF == 1 ) {
                pnl.createPDFJob();
            }
        } else {
            pnl.createImageJob();
            pnl.createPDFJob();
        }
    }
    
    private void setIcon() {
        try {
            wnd.setIconImage( ImageIO.read(getClass().getResource("/cardResizerIcon.png")) );
        } catch (IOException ex) {
            System.err.println("Couldn't load application icon.");
            System.exit( 1 );
        }
    }
    
    private void loadMenu() {
        menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsItem = new JMenuItem("Edit");
        settingsMenu.add(settingsItem);
        settingsItem.addActionListener((ActionEvent e) -> {
            updateSettingsPanel();
            settingsDlg.setSize( 200, 290 );
            settingsDlg.setLocationRelativeTo(wnd);
            settingsDlg.setVisible(true);
        });
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Contents");
        helpMenu.add( helpItem );
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        helpItem.addActionListener((ActionEvent e) -> {
            loadHelpURL();
        });
        wnd.setJMenuBar(menuBar);
    }
    
    private void loadModels() {
        alphaModesModel = new DefaultComboBoxModel();
        alphaModesModel.addElement( "CLEAR" );
        alphaModesModel.addElement( "SRC" );
        alphaModesModel.addElement( "SRC_OVER" );
        alphaModesModel.addElement( "DST_OVER" );
        alphaModesModel.addElement( "SRC_IN" );
        alphaModesModel.addElement( "DST_IN" );
        alphaModesModel.addElement( "SRC_OUT" );
        alphaModesModel.addElement( "DST_OUT" );
        alphaModesModel.addElement( "DST" );
        alphaModesModel.addElement( "DST_ATOP" );
        alphaModesModel.addElement( "OR" );
        loadICCProfiles();
    }
    
    private void loadICCProfiles() {
        iccProfiles = new ArrayList<>();
        iccProfiles.add( new ProfileFile() );
        File profilesDir = new File( properties.getProperty(PRO_SETTING) );
        if( profilesDir.exists() && profilesDir.isDirectory() ) {
            for( File f : profilesDir.listFiles() ) {
                if( f.getName().toLowerCase().endsWith("icc") ) {
                    iccProfiles.add( new ProfileFile(f) ); 
                }
            }
        }
        System.out.println("Loaded " + iccProfiles.size() +  " ICC Profiles");
    }
    
    private void createPreviewDialog() {
        preview = new JDialog(wnd, APP_TITLE + " - Preview Image");
        previewImg = new JLabel();
        previewScroll = new JScrollPane(previewImg);
        preview.add(previewScroll, BorderLayout.CENTER);
        preview.setModal(false);
    }
    
    private void createSettingsDialog() {
        settingsDlg = new JDialog(wnd);
        settingsDlg.setTitle(APP_TITLE + " - Settings");
        settingsPnl = new ProgramSettingsPanel();
        settingsDlg.setContentPane( settingsPnl );
        settingsPnl.saveSettingsBtn.addActionListener((ActionEvent e) -> {
            saveSettings();
        });
        settingsPnl.cancelBtn.addActionListener((ActionEvent e) -> {
            updateSettingsPanel();
            settingsDlg.setVisible(false);
        });
        settingsDlg.setModal(false);
    }
    
    private void createHelp() {
        fxPanel = new JFXPanel();
        helpDlg = new JDialog(wnd, "Drentsoft Card Resizer - Help");
        createHelpScene();
        helpDlg.add(fxPanel);
        helpDlg.setSize( 800, 600 );
        helpDlg.setLocationRelativeTo(wnd);        
    }
    
    private void loadHelpURL() {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse( new URI(HELP_URL) );
            } catch (URISyntaxException | IOException e) {
                System.err.println( e.getMessage() );
            }
        } else {
            helpDlg = new JDialog(wnd, "Drentsoft Card Resizer - Help");
            HelpPanel hlpPnl = new HelpPanel();
            hlpPnl.closeBtn.addActionListener((ActionEvent e) -> {
                helpDlg.setVisible(false);
            });
            helpDlg.setContentPane( hlpPnl );
            helpDlg.pack();
            helpDlg.setSize( 700, 90 );
            helpDlg.setLocationRelativeTo(wnd);
            helpDlg.setVisible(true);
            hlpPnl.setURL(HELP_URL);
        }
    }
    
    private void createHelpScene() {
        PlatformImpl.startup(() -> {
            browser = new WebView();
            webEngine = browser.getEngine();
            webEngine.load( HELP_URL );
            BorderPane bp = new BorderPane();
            bp.setCenter( browser );
            fxPanel.setScene( new Scene(bp) );
        });
    }
    
    private void loadProperties() {
        if( ! new File(PROP_FILE).exists() ) {
            createPropertiesFile();
        }
        if( new File(PROP_FILE).exists() ) {
            InputStream in = null;
            try {
                in = new FileInputStream(PROP_FILE);
                properties = new Properties();
                properties.load( in );
            } catch (IOException ex) {
                Logger.getLogger(CardResizer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        System.err.println( e.getMessage() );
                    }
                }
            }
        }
    }
    
    private void createPropertiesFile() {
        OutputStream out = null;
        try {
            properties = new Properties();
            out = new FileOutputStream(PROP_FILE);
            properties.setProperty(PDF_SETTING, "1");
            properties.setProperty(IMG_SETTING, "1");
            properties.setProperty(DIR_SETTING, "");
            properties.setProperty(PRO_SETTING, "");
            // save properties to project root folder
            properties.store(out, null);
        } catch (IOException ex) {
            Logger.getLogger(CardResizer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if( out != null ) {
                try {
                    out.close();
                } catch( IOException e ) {
                    System.err.println( e.getMessage() );
                }
            }
        }
    }
    
    private void saveSettings() {
        int loadPDF = (settingsPnl.loadPDFChk.isSelected()) ? 1 : 0;
        properties.setProperty(PDF_SETTING, String.valueOf( loadPDF ) );
        int loadImage = (settingsPnl.loadImgChk.isSelected()) ? 1 : 0;
        properties.setProperty(IMG_SETTING, String.valueOf( loadImage ) );
        properties.setProperty(DIR_SETTING, settingsPnl.dirField.getText() );
        properties.setProperty(PRO_SETTING, settingsPnl.profilesTxt.getText() );
        loadICCProfiles();
        pnl.updatePDFJobs();
        OutputStream out = null;
        try {
            out = new FileOutputStream(PROP_FILE);
            properties.store( out, "");
        } catch (IOException ex) {
            Logger.getLogger(CardResizer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if( out != null ) {
                try {
                    out.close();
                } catch( IOException e ) {
                    
                }
            }
        }
    }
    
    private void updateSettingsPanel() {
        int loadPDF = Integer.parseInt(properties.getProperty(PDF_SETTING));
        int loadImage = Integer.parseInt(properties.getProperty(IMG_SETTING));
        if( loadPDF == 1 ) {
            settingsPnl.loadPDFChk.setSelected(true);
        } else {
            settingsPnl.loadPDFChk.setSelected(false);
        }
        if( loadImage == 1 ) {
            settingsPnl.loadImgChk.setSelected(true);
        } else {
            settingsPnl.loadImgChk.setSelected(false);
        }
        settingsPnl.dirField.setText(properties.getProperty(DIR_SETTING));
        settingsPnl.profilesTxt.setText(properties.getProperty(PRO_SETTING));
    }
    
    public static void updatePreview( BufferedImage img ) {
        CardResizer.previewScroll.setPreferredSize( new Dimension(img.getWidth(), img.getHeight()) );
        CardResizer.preview.pack();
        CardResizer.previewImg.setIcon( new ImageIcon(img) );
        CardResizer.preview.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Couldn't find system look.");
        }
        new CardResizer();
    }

}
