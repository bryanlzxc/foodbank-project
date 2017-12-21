package foodbank.admin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.entity.WindowData;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.AdminService;
import foodbank.exceptions.SettingsUpdateException;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
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
	public String getWindowStartDate() {
		// TODO Auto-generated method stub
		String windowStartDate = null;
		try {
			windowStartDate = adminRepository.findOne(adminId).getWindowStartDateTime();
		} catch (NullPointerException e) {
			windowStartDate = ErrorMessages.INACTIVE_WINDOW;
		}
		return windowStartDate;
	}

	@Override
	public void updateWindowOpeningDate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		Date newStartingDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			newStartingDate = dateFormat.parse(settings.getClosingDate());
		} catch (Exception e) {
			throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
		}
		adminSettings.setWindowStartDateTime(newStartingDate);
		adminRepository.save(adminSettings);
	}
	
	@Override
	public String getWindowEndDate() {
		// TODO Auto-generated method stub
		String windowEndDate = null;
		try {
			windowEndDate = adminRepository.findOne(adminId).getWindowEndDateTime();
		} catch (NullPointerException e) {
			windowEndDate = ErrorMessages.INACTIVE_WINDOW;
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
				adminSettings.setWindowStartDateTime(null);
			} else {
				adminSettings.setWindowStatus(WindowStatus.ACTIVE);
				try {
					adminSettings.setWindowStartDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm")
							.parse(settings.getStartingDate()));
					adminSettings.setWindowEndDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm")
							.parse(settings.getClosingDate()));
				} catch (Exception e) {
					throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
				}
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

	@Override
	public WindowData retrieveWindowData() {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		Set<String> uniqueUsers = new TreeSet<String>();
		for(Request request : requests) {
			uniqueUsers.add(request.getBeneficiary().getUser().getUsername());
		}
		WindowData windowData = new WindowData();
		AdminSettings adminSettings = adminRepository.findOne(adminId);
		windowData.setDecayRate(adminSettings.getDecayRate());
		windowData.setMultiplierRate(adminSettings.getMultiplierRate());
		if(adminSettings.getWindowEndDateTime() == null) {
			windowData.setWindowEndDateTime(null);
		} else {
			windowData.setWindowEndDateTime(DateParser.convertToDate(adminSettings.getWindowEndDateTime()));
		}
		if(adminSettings.getWindowStartDateTime() == null) {
			windowData.setWindowStartDateTime(null);
		} else {
			windowData.setWindowStartDateTime(DateParser.convertToDate(adminSettings.getWindowStartDateTime()));
		}
		windowData.setWindowStatus(adminSettings.getWindowStatus());
		windowData.setUniqueBeneficiaryCount(Integer.valueOf(uniqueUsers.size()));
		return windowData;
	}

}
