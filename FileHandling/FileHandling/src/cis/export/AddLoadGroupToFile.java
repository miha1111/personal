package cis.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import common.*;

public class AddLoadGroupToFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// odpre in prebere CIS file
		FileOperations Fo = new FileOperations();

		String cisFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\ELExports\\Usagepoints12092019.csv";
		List<List<String>> CisData = new ArrayList<>();
		int numOfLinesCIS;
		
		// odpre in prebere Stratum - LoadGroup mapping file
		String stratumLodGroupFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\LoadProfile\\MappingStratumLoadGroup.csv";
		HashMap<String, String> StratumLoadGroupMap = new HashMap<String, String>(50);
		int lgHashID = 0;
		int lgHashData = 1;
		int numOfLinesStratumLG;
		
		// odpre in prebere UsagePoint - Stratum mapping file
		String stratumUPFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\LoadProfile\\Stratumi.csv";
		HashMap<String, String> StratumUPMap = new HashMap<String, String>(400000);
		int upHashID = 0;
		int upHashData = 1;
		int numOfLinesUPStratum;
		
		numOfLinesCIS = Fo.numOfLines(cisFileName, CisData, "|");
		//numOfLinesStratumLG = Fo.numOfLinesHash(stratumLodGroupFileName, StratumLoadGroupMap,  lgHashID, lgHashData, "|"); 
		numOfLinesUPStratum = Fo.numOfLinesHash(stratumUPFileName, StratumUPMap,  upHashID, upHashData, "|"); 
		numOfLinesStratumLG = Fo.numOfLinesHash(stratumLodGroupFileName, StratumLoadGroupMap, lgHashID,  lgHashData, ";"); 

		
		for (int i = 0; i < numOfLinesCIS; i++) {
			if ((i>0)) {							// preskoci prvo vrstico... header
				String tempUP = CisData.get(i).get(0); 		// obravnavano merilno mesto
				
				if (StratumUPMap.containsKey(tempUP)) {
					String tempUPStratum = StratumUPMap.get(tempUP);		// get stratum for tempUP
					if (tempUPStratum.equals("Stratum S 0")) {
						tempUPStratum = "Stratum S 1";
					}
					String tempUPLoadGroup = StratumLoadGroupMap.get(tempUPStratum);		// get LoadGroup for stratum of tempUP
					CisData.get(i).set(27, tempUPLoadGroup);
				}
				else {
					String tempUPStratum = "1";		// get stratum for tempUP
					String tempUPLoadGroup = StratumLoadGroupMap.get(tempUPStratum);		// get LoadGroup for stratum of tempUP
					CisData.get(i).set(27, tempUPLoadGroup);								// zapise LoadGroup-o		
				}
				
				
				
				try{
					if ((!CisData.get(i).get(28).isEmpty()) | (!(Float.parseFloat(CisData.get(i).get(28)) == 0f))) {									// zapise p in q
						float contractPower = Float.parseFloat(CisData.get(i).get(28));
						CisData.get(i).set(25, String.valueOf(0.1*contractPower));				// p
						CisData.get(i).set(26, String.valueOf(0.1*contractPower*0.3287));		// q											
					}
					else {
						CisData.get(i).set(25, String.valueOf(5));	
						CisData.get(i).set(26, String.valueOf(5*0.3287));													
					}
					
				} catch(NumberFormatException ex){ // handle your exception
					CisData.remove(i);
					numOfLinesCIS = CisData.size();
				}
				
				
				
				
				/*try{
    int i = Integer.parseInt(input);
} catch(NumberFormatException ex){ // handle your exception
    ...
}*/
				
				
				

			}
		}
		
		String OutputFile = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\ELExports\\CorrectedCISExport.csv";
		
		Fo.generateFile(OutputFile, CisData, "|");

		
	}

}
