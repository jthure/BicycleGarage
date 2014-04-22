package test;

import static org.junit.Assert.*;
import garage.Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDatabase {
	Database db;

	@Before
	public void setUp() throws Exception {
		db = new Database();
	}

	@After
	public void tearDown() throws Exception {
		db = null;
	}

	// @Test
	// public void testBarcodesList() {
	// String barcode=db.getNextBarcode();
	// while(barcode!=null){
	// System.out.println(barcode);
	// barcode = db.getNextBarcode();
	// }
	// }

	@Test
	public void testSaveReadBarcodes() {
		for(int i =0;i<99998;i++){
			db.getNextBarcode();
		}
		db.saveBarcodes();
		db=null;
		db = new Database();
		db.readBarcodes();
		String barcode = db.getNextBarcode();
		while (barcode != null) {
			System.out.println(barcode);
			barcode = db.getNextBarcode();
		}
	}

}
