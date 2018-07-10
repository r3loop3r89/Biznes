package com.shra1.biznes.webservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.NetworkUtils;
import com.shra1.biznes.utils.SharedPreferenceStorage;

import static com.shra1.biznes.utils.Utils.TAG;

public class GetUserProfileTask {

    public static void fire(Context mCtx, ListOfAllUsersTask.ResponseDto.Details clickedItem, GenericResponseCallback c) {


        c.onPreExecuteIon();
        if (NetworkUtils.isConnected()) {

        } else {
            c.onIonCompleted();
            c.onNoInternetError();
            return;
        }

        Ion
                .with(mCtx)
                .load(WebUrls.METHOD_GET, WebUrls.GET_USER_PROFILE + clickedItem.getUserId())
                .setHeader(WebUrls.WS_CONTENT_TYPE_STR, WebUrls.WS_CONTENT_TYPE_VALUE_JSON)
                .setHeader(WebUrls.WS_COOKIE_STR, SharedPreferenceStorage.getInstance(mCtx).getJSESSIONID())
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    c.onIonCompleted();
                    if (e != null) {
                        c.onNetworkError(e);
                        return;
                    } else {
                        String status = String.valueOf(result.getHeaders().code());
                        Log.d(TAG, status);

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


    public static class ResponseDto {
        int success;
        String result;
        String message;
        String description;
        Details data;

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

        public Details getData() {
            return data;
        }

        public void setData(Details data) {
            this.data = data;
        }

        public class Details {
            int userId;
            String userName;
            String firstName;
            String lastName;
            String mobileNumber;
            String emailAddress;
            String address;
            String photoUrl;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
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

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getEmailAddress() {
                return emailAddress;
            }

            public void setEmailAddress(String emailAddress) {
                this.emailAddress = emailAddress;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }
        }
    }
}
