package find.diff.in.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import common.FileOperations;

public class mainFind {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileOperations fo = new FileOperations();

		HashMap<String, String> UPOldHash = new HashMap<String, String>(400000);
		List<List<String>> UPNewList = new ArrayList<>();
		List<List<String>> UPOldList = new ArrayList<>();
		HashMap<String, String> UPNewHash = new HashMap<String, String>(400000);

		List<String> UPDelete = new ArrayList<>();
		List<String> UPAdd = new ArrayList<>();

		String inputFile1 = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\inkrementalne spremembe\\newUP.txt";
		String inputFile2 = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\inkrementalne spremembe\\oldUP.txt";

		int UPNewCount = fo.numOfLines(inputFile1, UPNewList, ";");
		int UPOldCount = fo.numOfLinesHash(inputFile2, UPOldHash, 0, 0, ";");
		fo.numOfLinesHash(inputFile1, UPNewHash, 0, 0, ",");
		fo.numOfLines(inputFile2, UPOldList, ";");

		int countDelete = 0;
		int countAdd = 0;

		for (int i = 0; i < UPNewCount; i++) { // add UsagePoints
			if (!UPOldHash.containsKey(UPNewList.get(i).get(0))) {
				UPAdd.add(UPNewList.get(i).get(0));
				countAdd++;
			}
		}

		for (int i = 0; i < UPOldCount; i++) { // delete UsagePoints
			if (!UPNewHash.containsKey(UPOldList.get(i).get(0))) {
				UPDelete.add(UPOldList.get(i).get(0));
				countDelete++;
			}

		}
		System.out.println(countAdd + " new UsagePoints to add. ");
		System.out.println(countDelete + " old UsagePoints to delete. ");

		
		String outputFile1 = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\inkrementalne spremembe\\CreatedUsagePointsSOAPMessage.xml";

		fo.generateFileFromString(outputFile1, generateCreatedUPRequest(UPAdd));
	}
	
	
	 static String generateCreatedUPRequest (List<String> UPAdd) {		
		String firstHalfOfMessage = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:get=\"http://iec.ch/TC57/2011/GetUsagePointsMessage\" xmlns:mes=\"http://iec.ch/TC57/2011/schema/message\" xmlns:usag=\"http://iec.ch/TC57/2011/UsagePoints\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <get:GetUsagePoints>\r\n" + 
				"         <get:Header>\r\n" + 
				"            <mes:Verb>get</mes:Verb>\r\n" + 
				"            <mes:Noun>UsagePoints</mes:Noun>\r\n" + 
				"             <cim-user>\r\n" + 
				"                    <username>CIM_TEST_MAXIMA@el.si</username>\r\n" + 
				"                    <password>:vbD4549*eG8076+Roq</password>\r\n" + 
				"                    <groups>\r\n" + 
				"                     <group>Admin</group>\r\n" + 
				"                     </groups>\r\n" + 
				"                </cim-user>\r\n" + 
				"         </get:Header>\r\n" + 
				"         <get:Request>\r\n";
		
		String secondHalfOfMessage = "\r\n         </get:Request>\r\n" + 
				"      </get:GetUsagePoints>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		String UPs = "";
		for (String UP : UPAdd) {
			UPs = UPs + "             <get:ID>" + UP + "</get:ID>\r\n";
		}
		String wholeMessage = firstHalfOfMessage + UPs + secondHalfOfMessage;
		//System.out.println(wholeMessage);
		return wholeMessage;
	}
	 
/*	 
	 static String generateDeletedUPRequest (List<String> UPDelete) {		
		String firstHalfOfMessage = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:get=\"http://iec.ch/TC57/2011/GetUsagePointsMessage\" xmlns:mes=\"http://iec.ch/TC57/2011/schema/message\" xmlns:usag=\"http://iec.ch/TC57/2011/UsagePoints\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <get:GetUsagePoints>\r\n" + 
				"         <get:Header>\r\n" + 
				"            <mes:Verb>get</mes:Verb>\r\n" + 
				"            <mes:Noun>UsagePoints</mes:Noun>\r\n" + 
				"             <cim-user>\r\n" + 
				"                    <username>CIM_TEST_MAXIMA@el.si</username>\r\n" + 
				"                    <password>:vbD4549*eG8076+Roq</password>\r\n" + 
				"                    <groups>\r\n" + 
				"                     <group>Admin</group>\r\n" + 
				"                     </groups>\r\n" + 
				"                </cim-user>\r\n" + 
				"         </get:Header>\r\n" + 
				"         <get:Request>\r\n";
		
		String secondHalfOfMessage = "\r\n         </get:Request>\r\n" + 
				"      </get:GetUsagePoints>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		String UPs = "";
		for (String UP : UPAdd) {
			UPs = UPs + "             <get:ID>" + UP + "</get:ID>\r\n";
		}
		String wholeMessage = firstHalfOfMessage + UPs + secondHalfOfMessage;
		//System.out.println(wholeMessage);
		return wholeMessage;
	}
	*/
}
