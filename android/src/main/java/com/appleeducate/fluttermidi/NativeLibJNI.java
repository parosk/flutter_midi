package com.appleeducate.fluttermidi;

public class NativeLibJNI {
    public native void init(String sf2path);
    public native void noteOn(int key,int velocity);
    public native void noteOff(int key);
    public native boolean programChange(int channel, int programNumber);
    public native void destroy();
}
