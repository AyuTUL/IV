/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.dao;

import com.travelagency.model.PackageModel;
import java.util.List;

public interface PackageDAO {
    void addPackage(PackageModel pkg);
    void updatePackage(PackageModel pkg);
    void deletePackage(int id);
    List<PackageModel> getAllPackages();
}
