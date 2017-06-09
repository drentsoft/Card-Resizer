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

import java.awt.Dimension;
import java.io.File;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class OutputDimension extends Dimension {
    
    public File outputPath;
    public String prefix;
    public String suffix;
    public String formatName;
    public double imageRotation;
    
    public MaskSettings maskSettings;
    
    public OutputDimension() {
        outputPath = null;
        width = 1024;
        height = 1024;
        prefix = "";
        suffix = "";
        formatName = "png";
        imageRotation = 0;
        maskSettings = new MaskSettings();
    }
    
    public OutputDimension( OutputDimension dim ) {
        if( dim.outputPath != null ) {
            outputPath = new File( dim.outputPath.getAbsolutePath() );
        }
        width = dim.width;
        height = dim.height;
        prefix = dim.prefix;
        suffix = dim.suffix;
        formatName = dim.formatName;
        imageRotation = dim.imageRotation;
        maskSettings = new MaskSettings(dim.maskSettings);
    }
    
    public String getOutputFilename( String originalFilename ) {
        StringBuilder sb = new StringBuilder();
        sb.append( outputPath.getAbsolutePath() );
        if( !outputPath.getAbsolutePath().endsWith(File.separator) ) {
            sb.append(File.separator);
        }
        sb.append(prefix);
        sb.append( originalFilename );
        sb.append(suffix);
        sb.append(".");
        sb.append(formatName);
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return width + "," + height + "..." + prefix + "..." + suffix + "///" + formatName;
    }

}
