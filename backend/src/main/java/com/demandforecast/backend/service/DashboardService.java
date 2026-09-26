package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.dashboard.DashboardSummaryResponse;

public interface DashboardService {
    DashboardSummaryResponse getDashboardSummary();
}
