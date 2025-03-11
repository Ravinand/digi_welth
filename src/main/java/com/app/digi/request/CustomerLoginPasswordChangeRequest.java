package com.app.digi.request;

public class CustomerLoginPasswordChangeRequest{
    private String newPassword;
    private String reEnterPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }

    @Override
    public String toString() {
        return "CustomerLoginPasswordChangeRequest{" +
                "newPassword='" + newPassword + '\'' +
                ", reEnterPassword='" + reEnterPassword + '\'' +
                '}';
    }
}
