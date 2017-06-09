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
package com.drentsoft.cardresizer.job;

import com.drentsoft.cardresizer.OutputDimension;
import com.drentsoft.cardresizer.PDFPageInfo;
import com.drentsoft.cardresizer.ProfileFile;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class PDFJob extends ResizeJob {
    
    /* PDFs seem to always be saved to 72 DPI so to get the original resolution take
     * the reported image dimensions (180 x 252 for a 2.5 x 3.5 inch poker card)
     * and scale that to the desired DPI (e.g. 300 DPI returns 750 x 1050 for the same 2.5 x 3.5 inch poker card)
    */
    public static final float PDFDPI = 72f; 
    
    public File original;
    public PDDocument doc;
    private PDFRenderer renderer;
    public ArrayList<PDFPageInfo> renderingPages;
    public ProfileFile ICCProfile;
    public boolean useICCProfile;
    
    /* Make sure this is the correct value, default for print will be 300 DPI. 
     * You can derive this value if you know the expected resolution.
    */
    public float actualDPI = 300f; 
    
    public PDFJob() {
        renderingPages = new ArrayList<>();
    }
    
    public PDFJob( PDFJob orig ) {
        super(orig);
        renderingPages = new ArrayList<>();
        original = orig.original;
        actualDPI = orig.actualDPI;
    }

    @Override
    public void run(PrintStream stream) {
        setRenderICCProfile();
        for( OutputDimension dim : outputDimensions ) {
            for( PDFPageInfo info : renderingPages ) {
                if( resizeImage(info, dim) ) {
                    stream.println("Successfully resized");
                } else {
                    System.err.println("Failed to resize " + original );
                }
            }
        }
    }
    
    public void loadDocument() {
        try {
            doc = PDDocument.load(original);
            renderer = new PDFRenderer(doc);
        } catch (IOException ex) {
            Logger.getLogger(PDFJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getOriginalImage( int pageID ) {
        //loadDocument();
        BufferedImage orig = null;
        try {
            if( renderer == null ) {
                System.err.println("Why?");
            }
            orig = renderer.renderImageWithDPI( pageID, actualDPI, ImageType.ARGB);
        } catch (IOException ex) {
            Logger.getLogger(PDFJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orig;
    }
    
    public boolean resizeImage( PDFPageInfo info, OutputDimension dim ) {
        String outFile = info.toString();
        BufferedImage resized = getResizedImage( getOriginalImage(info.pageID), dim );
        String outFilename = dim.getOutputFilename(outFile);
        if( resized == null ) {
            return false;
        }
        boolean success = writeImage( resized, dim.formatName, outFilename );
        return success;
    }
    
    public PDFRenderer getRenderer() {
        return renderer;
    }

    public void clearFile() {
        if( doc != null ) {
            try {
                doc.close();
                renderingPages.clear();
            } catch (IOException ex) {
                Logger.getLogger(PDFJob.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setRenderICCProfile() {        
        if( ICCProfile != null && useICCProfile ) {
            System.out.println("Using ICC Profile: " + ICCProfile.toString() );
            PDDeviceCMYK.INSTANCE = new PDDeviceCMYK() {
                @Override
                protected ICC_Profile getICCProfile() throws IOException {
                    return ICC_Profile.getInstance( ICCProfile.getPath().getAbsolutePath() );
                }
            };
        } else {
            System.out.println("Using ICC Profile: " + ProfileFile.DEFAULT_PROFILE );
            PDDeviceCMYK.INSTANCE = new PDDeviceCMYK() {
                @Override
                protected ICC_Profile getICCProfile() throws IOException {
                    URL url = PDDeviceCMYK.class.getClassLoader().getResource(ProfileFile.DEFAULT_PATH);
                    if (url == null) {
                        throw new IOException("Error loading resource: " + ProfileFile.DEFAULT_PATH);
                    }
                    ICC_Profile iccProfile;
                    try (InputStream input = url.openStream()) {
                        iccProfile = ICC_Profile.getInstance(input);
                    }
                    return iccProfile;
                }
            };
        }
    }

}
