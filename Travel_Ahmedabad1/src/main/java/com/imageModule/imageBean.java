package com.imageModule;

public class imageBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String base64Image;

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
}