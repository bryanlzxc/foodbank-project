package foodbank.admin.service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;

public interface AdminService {
	
	AdminSettings getAdminSettings();
	
	WindowStatus getWindowStatus();
	
	String getWindowEndDate();
	
	Double getDecayRate();
	
	Double getMultiplierRate();
	
	void updateWindowClosingDate(final AdminSettingsDTO settings);
	
	WindowStatus updateWindowStatus(final AdminSettingsDTO settings);
	
	void updateDecayRate(final AdminSettingsDTO settings);
	
	void updateMultiplierRate(final AdminSettingsDTO settings);
	
	void updateAdminSettings(final AdminSettingsDTO settings);

}
