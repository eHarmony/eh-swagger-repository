package com.eharmony.services.swagger.persistence;

import com.eharmony.services.swagger.model.Dashboard;

import java.util.List;

public interface DashboardRepository {
    List<Dashboard> getAllDashboards();

    Dashboard getDashboardById(String dashboardId);

    void saveDashboard(Dashboard dashboard) throws Exception;

    void removeDashboardById(String dashboardId) throws Exception;
}
