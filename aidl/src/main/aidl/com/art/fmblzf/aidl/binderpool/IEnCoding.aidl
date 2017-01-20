// IEnCoding.aidl
package com.art.fmblzf.aidl.binderpool;

// Declare any non-default types here with import statements

interface IEnCoding {
    String encrypt(String content);
    String decrypt(String content);
}
