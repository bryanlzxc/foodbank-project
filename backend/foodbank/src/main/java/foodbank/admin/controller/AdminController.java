package foodbank.admin.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.entity.WindowData;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.AdminService;
import foodbank.allocation.service.AllocationService;
import foodbank.email.entity.SendEmail;
import foodbank.history.service.HistoryService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants;

@RestController
@CrossOrigin
@RequestMapping("/admin-settings")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AllocationService allocationService;
	
	@GetMapping("/display-all")
	public AdminSettings getAdminSettings() {
		return adminService.getAdminSettings();
	}
	
	@GetMapping("/display/window-status")
	public Map<String, WindowStatus> getWindowStatus() {
		return Collections.singletonMap("windowStatus", adminService.getWindowStatus());
	}
	
	@GetMapping("/display/opening-date")
	public Map<String, String> getOpeningDate() {
		return Collections.singletonMap("windowStartDateTime", adminService.getWindowStartDate());
	}
	
	@GetMapping("/display/closing-date")
	public Map<String, String> getClosingDate() {
		return Collections.singletonMap("windowEndDateTime", adminService.getWindowEndDate());
	}
	
	@GetMapping("/display/decay-rate")
	public Map<String, Double> getDecayRate() {
		return Collections.singletonMap("decayRate", adminService.getDecayRate());
	}
	
	@GetMapping("/display/multiplier-rate")
	public Map<String, Double> getMultiplierRate() {
		return Collections.singletonMap("multiplierRate", adminService.getMultiplierRate());
	}
	
	@GetMapping("/display/side-bar")
	public WindowData retrieveWindowData() {
		return adminService.retrieveWindowData();
	}
	
	@PostMapping("/update/window-status")
	public ResponseDTO toggleWindowStatus(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null);
		try {
			WindowStatus status = adminService.updateWindowStatus(adminSettings);
			if(status == WindowStatus.ACTIVE) {
				allocationService.generateAllocationList();
				responseDTO.setMessage(MessageConstants.WINDOW_CLOSE_SUCCESS);
			} else {
				adminService.generateEmails();
				adminService.insertPastRequests();
				responseDTO.setMessage(MessageConstants.WINDOW_OPEN_SUCCESS);
			}
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/toggle/window-status")
	public ResponseDTO toggleWindowStatus() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null);
		AdminSettingsDTO adminSettingsDTO = new AdminSettingsDTO();
		adminSettingsDTO.setWindowToggle(true);
		WindowStatus status = adminService.updateWindowStatus(adminSettingsDTO);
		if(status == WindowStatus.ACTIVE) {
			allocationService.generateAllocationList();
			responseDTO.setMessage(MessageConstants.WINDOW_CLOSE_SUCCESS);
		} else {
			adminService.insertPastRequests();
			responseDTO.setMessage(MessageConstants.WINDOW_OPEN_SUCCESS);
		}
		return responseDTO;
	}
	
	@PostMapping("/update/opening-date")
	public ResponseDTO updateOpeningDate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.OPENING_DATE_UPDATE_SUCCESS);
		try {
			adminService.updateWindowOpeningDate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/closing-date")
	public ResponseDTO updateClosingDate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.CLOSING_DATE_UPDATE_SUCCESS);
		try {
			adminService.updateWindowClosingDate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/decay-rate")
	public ResponseDTO updateDecayRate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.DECAY_RATE_UPDATE_SUCCESS);
		try {
			adminService.updateDecayRate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/multiplier-rate")
	public ResponseDTO updateMultiplierRate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.MULTIPLIER_RATE_UPDATE_SUCCESS);
		try {
			adminService.updateMultiplierRate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/batch")
	public ResponseDTO updateAllSettings(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.ADMIN_BATCH_UPDATE_SUCCESS);
		try {
			adminService.updateAdminSettings(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	/*
	private AdminRepository adminRepository;
	private static final String adminId = "59f4a3316f9d43370468907b";
	
	public AdminController(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
    @GetMapping("/display-admin-settings")
    public AdminSettings getAdminSettings() {
        return this.adminRepository.findById(adminId);
    }
    
    @GetMapping("/display-window-status")
    public boolean getWindowStatus() {
    	return this.adminRepository.findById(adminId).isWindowStatus();
    }
    
    @GetMapping("/display-window-end")	//this returns a huge number
    public String getWindowEnd() {
    	return this.adminRepository.findById(adminId).getWindowEndDateTime().toString();
    }
	
    @GetMapping("/display-decay-rate")
    public double getDecayRate() {
    	return this.adminRepository.findById(adminId).getDecayRate();
    }
    
    @GetMapping("/display-multiplier-rate")
    public double getMultiplierRate() {
    	return this.adminRepository.findById(adminId).getMultiplierRate();
    }
    
    @PostMapping("/change-window-close-date={date}")
    public void changeWindowCloseDate(@PathVariable String date) {
    	//need to find out what is the Date passed in
    	//currently it converts json date string into date object
    	AdminSettings admin = this.adminRepository.findById(adminId);
    	Date d = DateParser.parseDateTime(date);
    	if (d != null) {
    		admin.setWindowEndDateTime(d);
    		this.adminRepository.save(admin);
    	}
    }
    
    @PostMapping(path = "/change-window-status", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, String> changeWindowStatus() {
    	AdminSettings admin = this.adminRepository.findById(adminId);		//may need to try catch or throw exception here for the id if it is not found in db
    	boolean currentStatus = admin.isWindowStatus();
    	admin.setWindowStatus(!currentStatus);						//queries for current status, and changes it
    	this.adminRepository.save(admin);
    	return Collections.singletonMap("response", "success");
    }
    
    @PostMapping("/change-decay-rate={decayrate:.+}")
    public void changeDecayRate(@PathVariable ("decayrate") double decayrate) {
    	AdminSettings admin = this.adminRepository.findById(adminId);
    	admin.setDecayRate(decayrate);
		this.adminRepository.save(admin);
    }
    
    @PostMapping("/change-multiplier-rate={multiplierrate:.+}")
    public void changeMultiplierRate(@PathVariable("multiplierrate") double multiplierrate) {
    	AdminSettings admin = this.adminRepository.findById(adminId);
    	admin.setMultiplierRate(multiplierrate);
	    this.adminRepository.save(admin);
    }
    
    @PostMapping("/update-admin-setting")
    public void update(@RequestBody AdminSettings admin) {
        this.adminRepository.save(admin);
    }
    */
    
}
