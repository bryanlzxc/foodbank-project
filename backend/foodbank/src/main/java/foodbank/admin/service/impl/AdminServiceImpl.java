package foodbank.admin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.AdminService;
import foodbank.exceptions.SettingsUpdateException;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	private static final String adminId = "59f4a3316f9d43370468907b";
	
	@Override
	public AdminSettings getAdminSettings() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(adminId);
	}

	@Override
	public WindowStatus getWindowStatus() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(adminId).getWindowStatus();
	}

	@Override
	public String getWindowEndDate() {
		// TODO Auto-generated method stub
		String windowEndDate = null;
		try {
			windowEndDate = adminRepository.findOne(adminId).getWindowEndDateTime();
		} catch (NullPointerException e) {
			windowEndDate = "The window is currently inactive.";
		}
		return windowEndDate;
	}

	@Override
	public Double getDecayRate() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(adminId).getDecayRate();
	}

	@Override
	public Double getMultiplierRate() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(adminId).getMultiplierRate();
	}

	@Override
	public void updateWindowClosingDate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		Date newClosingDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			newClosingDate = dateFormat.parse(settings.getClosingDate());
		} catch (Exception e) {
			throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
		}
		adminSettings.setWindowEndDateTime(newClosingDate);
		adminRepository.save(adminSettings);
	}

	@Override
	public WindowStatus updateWindowStatus(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		Boolean toggle = settings.getWindowToggle();
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		WindowStatus currentWindowStatus = null;
		if(toggle) {
			currentWindowStatus = adminSettings.getWindowStatus();
			if(currentWindowStatus == WindowStatus.ACTIVE) {
				adminSettings.setWindowStatus(WindowStatus.INACTIVE);
				adminSettings.setWindowEndDateTime(null);
			} else {
				adminSettings.setWindowStatus(WindowStatus.ACTIVE);
			}
		}
		adminRepository.save(adminSettings);
		return currentWindowStatus;
	}

	@Override
	public void updateDecayRate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		adminSettings.setDecayRate(settings.getDecayRate());
		adminRepository.save(adminSettings);
	}

	@Override
	public void updateMultiplierRate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		adminSettings.setMultiplierRate(settings.getMultiplierRate());
		adminRepository.save(adminSettings);
	}

	@Override
	public void updateAdminSettings(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		// Call this method to perform a batch update of all settings
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		Boolean closingDateUpdateIntent = evaluateClosingDateUpdateIntent(adminSettings, settings);
		if(closingDateUpdateIntent) {
			Date newClosingDate = null;
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				newClosingDate = dateFormat.parse(settings.getClosingDate());
			} catch (Exception e) {
				throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
			}
			adminSettings.setWindowEndDateTime(newClosingDate);
		}
		if(settings.getWindowToggle()) {
			WindowStatus currentWindowStatus = adminSettings.getWindowStatus();
			if(currentWindowStatus == WindowStatus.ACTIVE) {
				adminSettings.setWindowStatus(WindowStatus.INACTIVE);
				adminSettings.setWindowEndDateTime(null);
			} else {
				adminSettings.setWindowStatus(WindowStatus.ACTIVE);
			}
		}
		adminSettings.setDecayRate(settings.getDecayRate());
		adminSettings.setMultiplierRate(settings.getMultiplierRate());
		adminRepository.save(adminSettings);
	}
	
	private Boolean evaluateClosingDateUpdateIntent(AdminSettings currentSettings, AdminSettingsDTO newSettings) {
		Boolean updateIntent = false;
		String dbClosingDate = currentSettings.getWindowEndDateTime();
		String newClosingDate = newSettings.getClosingDate();
		updateIntent = dbClosingDate.equals(newClosingDate) ? false : true;
		return updateIntent;
	}

}
