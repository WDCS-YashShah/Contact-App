package com.mycontacts.data.model;

import com.google.gson.annotations.SerializedName;

public class CloudContactsModel {

    String name;

    @SerializedName("image")
    String profileImage;

    String phone;
    String email;

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Checking contact equality against email
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof CloudContactsModel)) {
            return ((CloudContactsModel) obj).getEmail().equalsIgnoreCase(email);
        }
        return false;
    }
}