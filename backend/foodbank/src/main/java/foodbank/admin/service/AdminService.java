package foodbank.admin.service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.entity.WindowData;

public interface AdminService {
	
	AdminSettings getAdminSettings();
	
	WindowStatus getWindowStatus();
	
	String getWindowStartDate();
	
	String getWindowEndDate();
	
	Double getDecayRate();
	
	Double getMultiplierRate();
	
	void updateWindowOpeningDate(final AdminSettingsDTO settings);
	
	void updateWindowClosingDate(final AdminSettingsDTO settings);
	
	WindowStatus updateWindowStatus(final AdminSettingsDTO settings);
	
	void updateDecayRate(final AdminSettingsDTO settings);
	
	void updateMultiplierRate(final AdminSettingsDTO settings);
	
	void updateAdminSettings(final AdminSettingsDTO settings);
	
	WindowData retrieveWindowData();

	void insertPastRequests();
	
	void generateEmails();
	
	void generateDailyPassword();

}
