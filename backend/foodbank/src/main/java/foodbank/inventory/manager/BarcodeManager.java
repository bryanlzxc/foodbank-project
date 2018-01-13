package foodbank.inventory.manager;

import java.util.Map;

import foodbank.util.FileManager;

public class BarcodeManager {
	
	public static Map<String, String[]> barcodeMap = FileManager.generateBarcodeMap();

}
