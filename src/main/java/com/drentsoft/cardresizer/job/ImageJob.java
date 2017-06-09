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

import com.drentsoft.cardresizer.ImageInfo;
import com.drentsoft.cardresizer.OutputDimension;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ImageJob extends ResizeJob {
    
    public ArrayList<ImageInfo> originals;
    
    public ImageJob() {
        originals = new ArrayList<>();
        outputDimensions = new ArrayList<>();
    }
    public ImageJob( ImageJob original ) {
        super(original);
        originals = new ArrayList<>(original.originals);
    }
    
    @Override
    public void run( PrintStream stream ) {
        for( ImageInfo f : originals ) {
            for( OutputDimension dim : outputDimensions ) {
            System.out.println("Processing " + dim.toString() );
                if( resizeImage( f.getPath(), dim ) ) {
                    stream.println("Successfully resized");
                } else {
                    System.err.println("Failed to resize " + f.getPath() );
                }
            }
        }
    }
    
    @Override
    protected String getOriginalFilename( File f ) {
        String filename = "";
        String name = "";
        for( ImageInfo info : originals ) {
            if( info.path.equals(f) ) {
                filename = info.getOutputName();
                break;
            }
        }
        if( filename.isEmpty() ) {
            System.err.println("Couldn't find image file in list. Resorting to outputting original name.");
            name = f.getName();
            filename = name.substring( 0, f.getName().lastIndexOf(".") );
        }
        return filename;
    }

}
