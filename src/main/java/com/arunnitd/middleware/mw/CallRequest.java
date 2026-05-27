package com.arunnitd.middleware.mw;

public class CallRequest {
    private String phoneNumber;
    private int userTap;
    private String templateName; // Changed to standard camelCase

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserTap() {
        return userTap;
    }

    public void setUserTap(int userTap) {
        this.userTap = userTap;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}