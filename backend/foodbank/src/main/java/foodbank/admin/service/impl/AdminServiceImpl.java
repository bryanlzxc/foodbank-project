package foodbank.admin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.entity.WindowData;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.AdminService;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.email.entity.SendEmail;
import foodbank.exceptions.SettingsUpdateException;
import foodbank.exceptions.UserException;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants.EmailMessages;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String ADMIN_ID = "5a45bb3ff36d287dc13af228";
	
	@Override
	public AdminSettings getAdminSettings() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(ADMIN_ID);
	}

	@Override
	public WindowStatus getWindowStatus() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(ADMIN_ID).getWindowStatus();
	}

	@Override
	public String getWindowStartDate() {
		// TODO Auto-generated method stub
		String windowStartDate = null;
		try {
			windowStartDate = adminRepository.findOne(ADMIN_ID).getWindowStartDateTime();
		} catch (NullPointerException e) {
			windowStartDate = ErrorMessages.INACTIVE_WINDOW;
		}
		return windowStartDate;
	}

	@Override
	public void updateWindowOpeningDate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
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
			windowEndDate = adminRepository.findOne(ADMIN_ID).getWindowEndDateTime();
		} catch (NullPointerException e) {
			windowEndDate = ErrorMessages.INACTIVE_WINDOW;
		}
		return windowEndDate;
	}

	@Override
	public Double getDecayRate() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(ADMIN_ID).getDecayRate();
	}

	@Override
	public Double getMultiplierRate() {
		// TODO Auto-generated method stub
		return adminRepository.findOne(ADMIN_ID).getMultiplierRate();
	}

	@Override
	public void updateWindowClosingDate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		if(adminSettings.getWindowStatus() == WindowStatus.INACTIVE) {
			throw new SettingsUpdateException(ErrorMessages.INACTIVE_WINDOW);
		}
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
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
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
					Date startingDate = null;
					Date endingDate = null;
					if(settings.getStartingDate() != null) {
						startingDate = DateParser.convertToDBDate(settings.getStartingDate());
					} else {
						startingDate = new Date();
					}
					if(settings.getClosingDate() != null) {
						endingDate = DateParser.convertToDBDate(settings.getClosingDate());
					} else {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(startingDate);
						calendar.add(Calendar.DAY_OF_YEAR, 7);
						endingDate = calendar.getTime();
					}
					adminSettings.setWindowStartDateTime(startingDate);
					adminSettings.setWindowEndDateTime(endingDate);
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
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		adminSettings.setDecayRate(settings.getDecayRate());
		adminRepository.save(adminSettings);
	}

	@Override
	public void updateMultiplierRate(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		adminSettings.setMultiplierRate(settings.getMultiplierRate());
		adminRepository.save(adminSettings);
	}

	@Override
	public void updateAdminSettings(AdminSettingsDTO settings) {
		// TODO Auto-generated method stub
		// Call this method to perform a batch update of all settings
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
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
				adminSettings.setWindowStartDateTime(null);
				adminSettings.setWindowEndDateTime(null);
			} else {
				adminSettings.setWindowStatus(WindowStatus.ACTIVE);
				Date startingDate = null;
				String startingDateString = settings.getStartingDate();
				Date closingDate = null;
				String closingDateString = settings.getClosingDate();
				if(startingDateString != null) {
					startingDate = DateParser.convertToDBDate(startingDateString);
				} else {
					startingDate = new Date();
				}
				if(closingDateString != null) {
					closingDate = DateParser.convertToDBDate(closingDateString);
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startingDate);
					calendar.add(Calendar.DAY_OF_YEAR, 7);
					closingDate = calendar.getTime();
				}
				adminSettings.setWindowStartDateTime(startingDate);
				adminSettings.setWindowEndDateTime(closingDate);
			}
		}
		adminSettings.setDecayRate(settings.getDecayRate());
		adminSettings.setMultiplierRate(settings.getMultiplierRate());
		adminRepository.save(adminSettings);
	}
	
	private Boolean evaluateClosingDateUpdateIntent(AdminSettings currentSettings, AdminSettingsDTO newSettings) {
		Boolean updateIntent = true;
		String dbClosingDate = currentSettings.getWindowEndDateTime();
		String newClosingDate = newSettings.getClosingDate();
		if(dbClosingDate != null) {
			updateIntent = dbClosingDate.equals(newClosingDate) ? false : true;
		}
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
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		windowData.setDecayRate(adminSettings.getDecayRate());
		windowData.setMultiplierRate(adminSettings.getMultiplierRate());
		if(adminSettings.getWindowEndDateTime() == null || adminSettings.getWindowStatus() == WindowStatus.INACTIVE) {
			windowData.setWindowEndDateTime(null);
		} else {
			windowData.setWindowEndDateTime(DateParser.convertToDate(adminSettings.getWindowEndDateTime()));
		}
		if(adminSettings.getWindowStartDateTime() == null || adminSettings.getWindowStatus() == WindowStatus.INACTIVE) {
			windowData.setWindowStartDateTime(null);
		} else {
			windowData.setWindowStartDateTime(DateParser.convertToDate(adminSettings.getWindowStartDateTime()));
		}
		windowData.setDailyPassword(adminSettings.getDailyPassword());
		windowData.setWindowStatus(adminSettings.getWindowStatus());
		windowData.setUniqueBeneficiaryCount(Integer.valueOf(uniqueUsers.size()));
		return windowData;
	}

	@Override
	public void insertPastRequests() {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		List<Allocation> allocations = allocationRepository.findAll();
		Map<String, Map<String, Integer>> requestAllocationMap = new HashMap<String, Map<String, Integer>>();
		for(Allocation allocation : allocations) {
			Map<String, Integer> allocationByItemsForBeneficiary = new HashMap<String, Integer>();
			allocation.getAllocatedItems().forEach(foodItem -> allocationByItemsForBeneficiary.put(foodItem.getDescription(), foodItem.getAllocatedQuantity()));
			requestAllocationMap.put(allocation.getBeneficiary().getUser().getUsername(), allocationByItemsForBeneficiary);
		}
		List<RequestHistory> pastRequests = new ArrayList<RequestHistory>();
		for(Request request : requests) {
			Integer allocatedQuantity = null;
			allocatedQuantity = requestAllocationMap.get(request.getBeneficiary().getUser().getUsername()).get(request.getFoodItem().getDescription());
			RequestHistory pastRequest = new RequestHistory(request.getBeneficiary(), 
					DateParser.convertToDate(request.getRequestCreationDate()), 
					request.getFoodItem().getCategory(), request.getFoodItem().getClassification(), request.getFoodItem().getDescription(), 
					Integer.valueOf(request.getFoodItem().getQuantity()), allocatedQuantity);
			pastRequests.add(pastRequest);
		}
		pastRequests.forEach(pastRequest -> historyRepository.insert(pastRequest));
		requestRepository.deleteAll();
		allocationRepository.deleteAll();
	}
	
	@Override
	public void generateEmails() throws Exception {
		List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
		for(Beneficiary beneficiary : beneficiaries) {
			String emailAddress = beneficiary.getUser().getEmail();
			new SendEmail(emailAddress, EmailMessages.WINDOW_OPENING_SUBJECT, EmailMessages.WINDOW_OPENING_MESSAGE);
		}
	}
	
	@Override
	@Scheduled(fixedRate = 86400000, initialDelay = 300000)
	public void generateDailyPassword() {
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		int length = 8;
		boolean useLetters = true;
		boolean useNumbers = true;
		String dailyPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		adminSettings.setDailyPassword(dailyPassword);
		User dbUser = userRepository.findByUsername("volunteer");
		dbUser = dbUser == null ? new User("volunteer", null, "volunteer", "volunteer", "volunteer-fb@gmail.com") : dbUser;
		dbUser.setPassword(dailyPassword);
		userRepository.save(dbUser);
		adminRepository.save(adminSettings);
	}

	@Override
	public void resetPassword(String username) throws Exception {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(username);
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		int length = 8;
		boolean useLetters = true;
		boolean useNumbers = true;
		String newPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		new SendEmail(dbUser.getEmail(), EmailMessages.RESET_PASSWORD_SUBJECT, EmailMessages.RESET_PASSWORD_MESSAGE1 + newPassword + EmailMessages.RESET_PASSWORD_MESSAGE2);
		dbUser.setPassword(newPassword);
		userRepository.save(dbUser);
	}

}
