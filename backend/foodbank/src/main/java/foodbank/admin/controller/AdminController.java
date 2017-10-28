package foodbank.admin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.tomcat.util.bcel.Const;
import org.bson.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.admin.entity.Admin;
import foodbank.admin.repository.AdminRepository;

@RestController
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
    
    @PostMapping("/change-window-status")
    public void changeWindowStatus() {
    	Admin admin = this.adminRepository.findById(adminId);		//may need to try catch or throw exception here for the id if it is not found in db
    	boolean currentStatus = admin.isWindowStatus();
    	admin.setWindowStatus(!currentStatus);						//queries for current status, and changes it
    	this.adminRepository.save(admin);
    }
    
    @GetMapping("/display-window-end")	//this returns a huge number
    public String getWindowEnd() {
    	return this.adminRepository.findById(adminId).getWindowEndDateTime().toString();
    }
    
    @PostMapping("/change-window-close-date={date}")		
    public void changeWindowCloseDate(@PathVariable String date) {
    	//need to find out what is the Date passed in
    	//currently it converts json date string into date object
    	Admin admin = this.adminRepository.findById(adminId);
    	Date d = parseDateTime(date);
    	if (d != null) {
    		admin.setWindowEndDateTime(d);
    		this.adminRepository.save(admin);
    	}
    }
	
    @GetMapping("/display-decay-rate")
    public double getDecayRate() {
    	return this.adminRepository.findById(adminId).getDecayRate();
    }
    
    @GetMapping("/display-multiplier-rate")
    public double getMultiplierRate() {
    	return this.adminRepository.findById(adminId).getMultiplierRate();
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
    
    public static Date parseDateTime(String dateString) {
        if (dateString == null) return null;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        if (dateString.contains("T")) dateString = dateString.replace('T', ' ');
        if (dateString.contains("Z")) dateString = dateString.replace("Z", "+0000");
        else
            dateString = dateString.substring(0, dateString.lastIndexOf(':')) + dateString.substring(dateString.lastIndexOf(':')+1);
        try {
            return fmt.parse(dateString);
        }
        catch (ParseException e) {
            System.out.println("Parse Exception");
            return null;
        }
    }
    
}
