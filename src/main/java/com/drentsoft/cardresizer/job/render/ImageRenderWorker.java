package com.drentsoft.cardresizer.job.render;

import com.drentsoft.cardresizer.job.ImageJob;
import java.awt.image.BufferedImage;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ImageRenderWorker extends RenderWorker {
    
    ImageJob job;
    int fileID;
    int dimID;
    
    public ImageRenderWorker() {
        super();
        type = "Image";
    }
    
    public void setImage( ImageJob job, int fileID, int dimID ) {
        this.job = job;
        this.fileID = fileID;
        this.dimID = dimID;
    }

    @Override
    protected BufferedImage render() {
        return job.getResizedImage( job.getOriginalImage(job.originals.get(fileID)), job.outputDimensions.get(dimID) );
    }

}
