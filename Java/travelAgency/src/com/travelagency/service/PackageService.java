/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.travelagency.service;

import com.travelagency.dao.PackageDAO;
import com.travelagency.dao.PackageDAOImpl;
import com.travelagency.model.PackageModel;

import java.util.List;

public class PackageService {
    private final PackageDAO dao = new PackageDAOImpl();

    public void addPackage(PackageModel pkg) { dao.addPackage(pkg); }
    public void updatePackage(PackageModel pkg) { dao.updatePackage(pkg); }
    public void deletePackage(int id) { dao.deletePackage(id); }
    public List<PackageModel> getAllPackages() { return dao.getAllPackages(); }
}