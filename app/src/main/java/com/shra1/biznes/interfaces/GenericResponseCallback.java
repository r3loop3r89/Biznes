/*
 *
 *  * *******************************************************************************
 *  *  * Project Id/Name    : 20171212 (Tipping Point)
 *  *  * Module Id/Name     : Tipping Point
 *  *  * Purpose            : Interface to allow the communication between activity & web service classes.
 *  *  * Copy Right         : nWorks Technologies
 *  *  * Revision History :
 *  *    Version    Date            Created By      Reason
 *  *    1.00       2018-02-06   Manasi PAWAR   Class Created
 *  *  ******************************************************************************
 *
 */
package com.shra1.biznes.interfaces;

public interface GenericResponseCallback {
    public void onPreExecuteIon();
    public void onIonCompleted();
    public void on200(Object obj);
    public void on400(String status);
    public void on500(String status);
    public void on404(String status);
    public void on401(Object obj);
    public void on419(Object obj);
    public void onNetworkError(Exception e);
    public void onDataError(Exception e);
    public void onNoInternetError();
    public void onUnhandledError(String status);
}
