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
import foodbank.user.dto.UserDTO;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;

@RestController
@CrossOrigin
@RequestMapping("/admin-settings")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AllocationService allocationService;
	
	@GetMapping("/display-all")
	public ResponseDTO getAdminSettings() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			AdminSettings as = adminService.getAdminSettings();
			responseDTO.setResult(as);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/window-status")
	public ResponseDTO getWindowStatus() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, WindowStatus> map = Collections.singletonMap("windowStatus", adminService.getWindowStatus());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/opening-date")
	public ResponseDTO getOpeningDate() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, String> map = Collections.singletonMap("windowStartDateTime", adminService.getWindowStartDate());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/closing-date")
	public ResponseDTO getClosingDate() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, String> map = Collections.singletonMap("windowEndDateTime", adminService.getWindowEndDate());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/decay-rate")
	public ResponseDTO getDecayRate() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, Double> map = Collections.singletonMap("decayRate", adminService.getDecayRate());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/multiplier-rate")
	public ResponseDTO getMultiplierRate() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, Double> map = Collections.singletonMap("multiplierRate", adminService.getMultiplierRate());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/side-bar")
	public ResponseDTO retrieveWindowData() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			WindowData wd = adminService.retrieveWindowData();
			responseDTO.setResult(wd);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@PostMapping("/update/window-status")
	public ResponseDTO toggleWindowStatus(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, null);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, null);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.OPENING_DATE_UPDATE_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.CLOSING_DATE_UPDATE_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DECAY_RATE_UPDATE_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.MULTIPLIER_RATE_UPDATE_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_BATCH_UPDATE_SUCCESS);
		try {
			adminService.updateAdminSettings(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/reset-password")
	public ResponseDTO resetPassword(@RequestBody UserDTO user) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.RESET_PASSWORD_SUCCESS);
		try {
			adminService.resetPassword(user);
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
