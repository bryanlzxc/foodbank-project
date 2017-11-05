package foodbank.admin.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import foodbank.admin.entity.Admin;
import foodbank.admin.repository.AdminRepository;
import foodbank.util.DateParser;

@RestController
@CrossOrigin
@RequestMapping("/admin-control")
public class AdminController {
	
	private AdminRepository adminRepository;
	private static final String adminId = "59f4a3316f9d43370468907b";
	
	public AdminController(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
    @GetMapping("/display-admin-settings")
    public Admin getAdminSettings() {
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
    	Admin admin = this.adminRepository.findById(adminId);
    	Date d = DateParser.parseDateTime(date);
    	if (d != null) {
    		admin.setWindowEndDateTime(d);
    		this.adminRepository.save(admin);
    	}
    }
    
    @PostMapping(path = "/change-window-status", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, String> changeWindowStatus() {
    	Admin admin = this.adminRepository.findById(adminId);		//may need to try catch or throw exception here for the id if it is not found in db
    	boolean currentStatus = admin.isWindowStatus();
    	admin.setWindowStatus(!currentStatus);						//queries for current status, and changes it
    	this.adminRepository.save(admin);
    	return Collections.singletonMap("response", "success");
    }
    
    @PostMapping("/change-decay-rate={decayrate:.+}")
    public void changeDecayRate(@PathVariable ("decayrate") double decayrate) {
    	Admin admin = this.adminRepository.findById(adminId);
    	admin.setDecayRate(decayrate);
		this.adminRepository.save(admin);
    }
    
    @PostMapping("/change-multiplier-rate={multiplierrate:.+}")
    public void changeMultiplierRate(@PathVariable("multiplierrate") double multiplierrate) {
    	Admin admin = this.adminRepository.findById(adminId);
    	admin.setMultiplierRate(multiplierrate);
	    this.adminRepository.save(admin);
    }
    
    @PostMapping("/update-admin-setting")
    public void update(@RequestBody Admin admin) {
        this.adminRepository.save(admin);
    }
    
}
