package com.shra1.biznes.webservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.NetworkUtils;
import com.shra1.biznes.utils.SharedPreferenceStorage;
import com.shra1.biznes.utils.Utils;


public class RegisterUserTask {
    public static void fire(Context mCtx, RequestDto requestDto, GenericResponseCallback c) {

        c.onPreExecuteIon();
        if (NetworkUtils.isConnected()) {

        } else {
            c.onIonCompleted();
            c.onNoInternetError();
            return;
        }

        Ion
                .with(mCtx)
                .load(WebUrls.REGISTER_USER)
                .setHeader(WebUrls.WS_CONTENT_TYPE_STR, WebUrls.WS_CONTENT_TYPE_VALUE_JSON)
                .setHeader(WebUrls.WS_COOKIE_STR, SharedPreferenceStorage.getInstance(mCtx).getJSESSIONID())
                .setJsonPojoBody(requestDto)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    c.onIonCompleted();
                    if (e != null) {
                        c.onNetworkError(e);
                        return;
                    } else {
                        String status = String.valueOf(result.getHeaders().code());
                        Log.d(Utils.TAG, status);

                        switch (status) {
                            case "200":
                                try {
                                    ResponseDto responseDto = new Gson().fromJson(result.getResult(), ResponseDto.class);
                                    c.on200(responseDto);
                                } catch (Exception e1) {
                                    c.onDataError(e1);
                                }
                                break;
                            case "400":
                                c.on400(status);
                                break;
                            case "500":
                                c.on500(status);
                                break;
                            case "404":
                                c.on404(status);
                                break;
                            case "401":
                                try {
                                    ResponseDto responseDto = new Gson().fromJson(result.getResult(), ResponseDto.class);
                                    c.on401(responseDto);
                                } catch (Exception e2) {
                                    c.onDataError(e2);
                                }
                                break;
                            case "419":
                                c.on419(null);
                                break;
                            default:
                                c.onUnhandledError(status);
                        }
                    }


                });

    }


    public static class RequestDto {
        private String userName;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String password;
        private String mobileNumber;
        private String address;
        private String role;
        private String photoUrl;
        private String authProvider;


        public RequestDto(String userName, String firstName, String lastName, String emailAddress, String password, String mobileNumber, String address, String role, String photoUrl, String authProvider) {
            this.userName = userName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.password = password;
            this.mobileNumber = mobileNumber;
            this.address = address;
            this.role = role;
            this.photoUrl = photoUrl;
            this.authProvider = authProvider;
        }

        public RequestDto() {

        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }

    public static class ResponseDto {
        private int success;
        private String result;
        private String message;
        private String description;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
