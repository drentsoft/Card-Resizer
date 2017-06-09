/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drentsoft.cardresizer;

import java.io.File;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ProfileFile {
    
    public static final String DEFAULT_PROFILE = "ISO Coated v2 300%";
    public static final String DEFAULT_PATH = "org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc";
    
    File path;
    
    public ProfileFile() {
        path = null;
    }
    
    public ProfileFile( File p ) {
        this.path = p;
    }
    
    public File getPath() {
        return path;
    }
    
    @Override
    public String toString() {
        if( path != null ) {
            return path.getName();
        } else {
            return DEFAULT_PROFILE;
        }
    }
    
}
