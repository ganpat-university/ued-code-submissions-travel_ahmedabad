package com.admin.bean;

import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold = 10485760, maxFileSize = 52428800L, maxRequestSize = 104857600L)
public class Places
{
    private String pname;
    private String time;
    private String desc;
    private String loc;
    private String base64Image;
    private Part image;
    
    public String getBase64Image() {
        return this.base64Image;
    }
    
    public void setBase64Image(final String base64Image) {
        this.base64Image = base64Image;
    }
    
    public Part getImage() {
        return this.image;
    }
    
    public void setImage(final Part image) {
        this.image = image;
    }
    
    public String getPname() {
        return this.pname;
    }
    
    public void setPname(final String pname) {
        this.pname = pname;
    }
    
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getLoc() {
        return this.loc;
    }
    
    public void setLoc(final String loc) {
        this.loc = loc;
    }
}
