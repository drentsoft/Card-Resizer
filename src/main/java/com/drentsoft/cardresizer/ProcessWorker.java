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

import com.drentsoft.cardresizer.job.ResizeJob;
import static com.drentsoft.cardresizer.ui.JobPanel.PROCESS_READY;
import static com.drentsoft.cardresizer.ui.GlobalPanel.PROCESS_RUNNING;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public class ProcessWorker extends SwingWorker implements PropertyChangeListener {

    ResizeJob job;
    JButton target;
    
    public ProcessWorker() {
        addPropertyChangeListener(this);
    }
    
    public ProcessWorker( ResizeJob job ) {
        this();
        setJob(job);
    }
    
    public void setTarget( JButton target ) {
        this.target = target;
    }
    
    public final void setJob( ResizeJob job ) {
        this.job = job;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        job.run(System.out);
        return 42;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if( getState() == SwingWorker.StateValue.STARTED ) {
            target.setText(PROCESS_RUNNING);
        }
        if( getState() == SwingWorker.StateValue.DONE ) {
            target.setText(PROCESS_READY);
        }
    }
    
    
    
}
