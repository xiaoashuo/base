package com.jsignature.seal.pdf.pkcs12;

/**
 * @author ys
 * @topic
 * @date 2020/3/12 18:16
 */
public class Extension {
    private String oid;
    private boolean critical;
    private byte[] value;

    public String getOid() {
        return oid;
    }
    public byte[] getValue() {
        return value;
    }
    public boolean isCritical() {
        return critical;
    }
}
