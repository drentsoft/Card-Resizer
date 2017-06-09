/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drentsoft.cardresizer;

import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class FileCheck {
    
    public static boolean isImageFile( File f ) {
        for( String ext : ImageIO.getReaderFileSuffixes() ) {
            if( f.getName().endsWith(ext) ) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPDFFile( File f ) {
        String fname = f.getName().toLowerCase();
        return ( fname.endsWith("pdf") ); //|| fname.endsWith("ai") || fname.endsWith("psd") );
    }

}
