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

import java.awt.AlphaComposite;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class MaskSettings {
    
    public File maskPath;
    public BufferedImage maskImage;
    public double maskRotation = 0;
    public float maskAlpha = 1;
    public AlphaMode alphaMode = AlphaMode.DST_OUT;
    
    public MaskSettings() {
        setAlphaMode( AlphaMode.DST_OUT );
    }
    
    public MaskSettings( MaskSettings orig ) {
        if( orig.maskPath != null ) {
            this.maskPath = new File(orig.maskPath.getAbsolutePath());
        }
        loadMaskImage();
        this.maskRotation = orig.maskRotation;
        this.maskAlpha = orig.maskAlpha;
        this.alphaMode = orig.alphaMode;
    }

    public void setMaskPath( File path ) {
        this.maskPath = path;
        if( maskPath == null ) {
            maskImage = null;
        }
    }
    
    public void setRotation( double theta ) {
        this.maskRotation = Math.toRadians(theta);
    }
    
    public void setAlpha( float a ) {
        this.maskAlpha = a;
    }
    
    public final void setAlphaMode( AlphaMode mode ) {
        this.alphaMode = mode;
    }
    
    public final void loadMaskImage() {
        if( maskPath == null ) {
            System.out.println("Job has no mask set.");
            return;
        }
        try {
            maskImage = ImageIO.read( maskPath );
            System.out.println("Loaded mask.");
            if( maskRotation != 0 ) {
                double cos = Math.abs(Math.cos(maskRotation));
                double sin = Math.abs(Math.sin(maskRotation));
                int w = (int) (maskImage.getWidth() * cos + maskImage.getHeight() * sin);
                int h = (int) (maskImage.getWidth() * sin + maskImage.getHeight() * cos);
                AffineTransform tx = new AffineTransform();
                tx.translate( (w - maskImage.getWidth())/2, (h - maskImage.getHeight())/2 );
                tx.rotate(maskRotation, maskImage.getWidth() / 2, maskImage.getHeight() / 2 );
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
                maskImage = op.filter(maskImage, null);
                System.out.println("Rotated mask");
            }
        } catch (IOException ex) {
            System.err.println( "Can't load mask file: " + maskPath.getAbsolutePath() );
        }
    }
    
    public BufferedImage getMaskImage() {
        if( maskImage == null ) {
            loadMaskImage();
        }
        return maskImage;
    }
    
    public AlphaComposite getAlphaComposite() {
        System.out.println( "Set composite to: " + alphaMode + " , " + maskAlpha );
        return AlphaComposite.getInstance(alphaMode.getID(), maskAlpha);
    }
    
}
