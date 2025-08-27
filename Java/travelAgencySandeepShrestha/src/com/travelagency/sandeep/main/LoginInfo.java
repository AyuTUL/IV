/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.sandeep.main;

import java.util.HashMap;

public class LoginInfo {
    private HashMap<String, String> loginInfo = new HashMap<>();

    public LoginInfo() {
        // Fixed Admin credentials
        loginInfo.put("admin", "admin123");
    }

    public HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }
}

