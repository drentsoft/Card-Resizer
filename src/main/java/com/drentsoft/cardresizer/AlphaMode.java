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

/**
 *
 * @author Derwent Ready @ Drentsoft
 */
public enum AlphaMode {
    
    CLEAR( 1 ),
    SRC( 2 ),
    SRC_OVER( 3 ),
    DST_OVER( 4 ),
    SRC_IN( 5 ),
    DST_IN( 6 ),
    SRC_OUT( 7 ),
    DST_OUT( 8 ),
    DST( 9 ),
    SRC_ATOP( 10 ),
    DST_ATOP( 11 ),
    OR( 12 );
    
    public final int ID;
    
    AlphaMode( int id ) {
        this.ID = id;
    }
    
    public int getID() {
        return ID;
    }
    
    public static AlphaMode getMode( int id ) {
        for( AlphaMode mode : AlphaMode.values() ) {
            if( mode.getID() == id ) {
                return mode;
            }
        }
        return DST_OVER;
    }
    
    @Override
    public String toString() {
        return name();
    }
    
}
