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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public abstract class ResizeJob {
    
    public ArrayList<OutputDimension> outputDimensions;
    
    public ResizeJob() {
        outputDimensions = new ArrayList<>();
    }
    public ResizeJob( ResizeJob original ) {
        outputDimensions = new ArrayList<>();
        for( OutputDimension dim : original.outputDimensions) {
            outputDimensions.add( new OutputDimension(dim) );
        }
    }
    
    public abstract void run( PrintStream stream );
    
    public BufferedImage getOriginalImage( File original ) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(original);
        } catch( IOException e ) {
            System.err.println( "Can't read image file " + original.getAbsolutePath() );
        }
        return orig;
    }
    
    public BufferedImage getResizedImage( BufferedImage orig, OutputDimension dim ) {
        BufferedImage resized = null;
        dim.maskSettings.loadMaskImage();
        resized = new BufferedImage( dim.width, dim.height,  BufferedImage.TYPE_4BYTE_ABGR );
        Graphics2D g = resized.createGraphics();
        g.setColor( new Color( 1, 1, 1, 0 ) );
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(orig, 0, 0, dim.width, dim.height, null );
        /* Mask has already been rotated for the job */
        if( dim.maskSettings.maskImage != null ) {
            System.out.println("Drawing mask");
            g.setComposite( dim.maskSettings.getAlphaComposite() );
            g.drawImage(dim.maskSettings.maskImage, 0, 0, dim.width, dim.height, null );
        }
        /* Do final rotation of composited image */
        if( dim.imageRotation != 0 ) {
            double theta = Math.toRadians(dim.imageRotation);
            double cos = Math.abs(Math.cos(theta));
            double sin = Math.abs(Math.sin(theta));
            int w = (int) (dim.width * cos + dim.height * sin);
            int h = (int) (dim.width * sin + dim.height * cos);
            AffineTransform tx = new AffineTransform();
            tx.translate( (w - dim.width)/2, (h - dim.height)/2 );
            tx.rotate(theta, dim.width / 2, dim.height / 2 );
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
            resized = op.filter(resized, null);
        }
        g.dispose();
        return resized;
    }
    
    public boolean resizeImage( File original, OutputDimension dim ) {
        String filename = getOriginalFilename( original );
        BufferedImage resized = getResizedImage( getOriginalImage(original), dim );
        String outFilename = dim.getOutputFilename(filename);
        if( resized == null ) {
            return false;
        }
        boolean success = writeImage( resized, dim.formatName, outFilename );
        return success;
    }
    
    public boolean writeImage( BufferedImage img, String format, String outFilename ) {
        boolean success = false;
        int attempts = 0;
        while( attempts < 10 ) {
            try {
                File outFile = new File(outFilename);
                if( !outFile.getParentFile().exists() ) {
                    outFile.getParentFile().mkdirs();
                }
                success = ImageIO.write(img, format, outFile );
                if( success ) {
                    break;
                }
            } catch (IOException ex ) {
                System.err.println("Couldn't write to file " + outFilename + " on attempt " + (++attempts) );
                success = false;
            }
        }
        return success;
    }
    
    protected String getOriginalFilename( File f ) {
        String name = f.getName();
        String filename = name.substring( 0, f.getName().lastIndexOf(".") );
        return filename;
    }
    
}
