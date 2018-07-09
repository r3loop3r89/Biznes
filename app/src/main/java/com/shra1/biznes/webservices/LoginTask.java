package com.shra1.biznes.webservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.shra1.biznes.interfaces.GenericResponseCallback;
import com.shra1.biznes.utils.NetworkUtils;
import com.shra1.biznes.utils.SharedPreferenceStorage;
import com.shra1.biznes.utils.Utils;

import java.util.List;

public class LoginTask {

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
                .load(WebUrls.LOGIN)
                .setHeader(WebUrls.WS_CONTENT_TYPE_STR, WebUrls.WS_CONTENT_TYPE_VALUE_JSON)
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
                        List<String> cookies = result.getHeaders().getHeaders().getAll(WebUrls.WS_SET_COOKIE_STR);
                        if (cookies != null && cookies.size() > 0) {
                            for (int i = 0; i < cookies.size(); i++) {
                                String validCookie[] = cookies.get(0).trim().split(";");
                                String validJSessionId = validCookie[0];
                                Log.d("validJSessionId", validJSessionId);
                                SharedPreferenceStorage.getInstance(mCtx).setJSESSIONID(validJSessionId);
                            }
                        }
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
                                    ResponseDto responseDtox = new Gson().fromJson(result.getResult(), ResponseDto.class);
                                    c.on401(responseDtox);
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
        public static final String AUP_APPLICATION = "AUP_APPLICATION";
        private String username;
        private String password;
        private String authProvider;

        public RequestDto(String username, String password, String authProvider) {

            this.username = username;
            this.password = password;
            this.authProvider = authProvider;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAuthProvider() {
            return authProvider;
        }

        public void setAuthProvider(String authProvider) {
            this.authProvider = authProvider;
        }
    }

    public static class ResponseDto {
        int success;
        String result;
        String message;
        String description;
        String domainSpecificStatus;
        String errorMessage;
        Details data;

        public String getDomainSpecificStatus() {
            return domainSpecificStatus;
        }

        public void setDomainSpecificStatus(String domainSpecificStatus) {
            this.domainSpecificStatus = domainSpecificStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

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
            String userId;
            String userName;
            String displayName;
            String role;
            String authProvider;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getAuthProvider() {
                return authProvider;
            }

            public void setAuthProvider(String authProvider) {
                this.authProvider = authProvider;
            }
        }
    }

}
