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

		String cisFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\ELExports\\CIS-2019-12-03-2.csv";
		List<List<String>> CisData = new ArrayList<>();
		int numOfLinesCIS;

		// odpre in prebere Stratum - LoadGroup mapping file
		String StratumLodGroupFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\LoadProfile\\MappingStratumLoadGroup.csv";
		HashMap<String, String> StratumLoadGroupMap = new HashMap<String, String>(50);
		int lgHashID = 0;
		int lgHashData = 1;
		int numOfLinesStratumLG;

		// odpre in prebere UsagePoint - Stratum mapping file
		String StratumUPFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\LoadProfile\\Stratumi.csv";
		HashMap<String, String> StratumUPMap = new HashMap<String, String>(400000);
		int upHashID = 0;
		int upHashData = 1;
		int numOfLinesUPStratum;


		HashMap<String, String> MRIDUPMap = new HashMap<String, String>(400000);
		int uPMRIDHashID = 0;
		int uPMRIDHashData = 1;

		numOfLinesCIS = Fo.numOfLines(cisFileName, CisData, "|");
		// numOfLinesStratumLG = Fo.numOfLinesHash(stratumLodGroupFileName,
		// StratumLoadGroupMap, lgHashID, lgHashData, "|");
		numOfLinesUPStratum = Fo.numOfLinesHash(StratumUPFileName, StratumUPMap, upHashID, upHashData, "|");
		numOfLinesStratumLG = Fo.numOfLinesHash(StratumLodGroupFileName, StratumLoadGroupMap, lgHashID, lgHashData, ";");

		
		List<List<String>> CisDataNew = new ArrayList<>();

		
		for (int i = 0; i < numOfLinesCIS; i++) {
				String tempUP = CisData.get(i).get(0).substring(1); // obravnavano merilno mesto

					CisDataNew.add(CisData.get(i));
					int cisSizeNew = CisDataNew.size()-1;
					
				if (StratumUPMap.containsKey(tempUP)) {
					String tempUPStratum = StratumUPMap.get(tempUP); // get stratum for tempUP
					if (tempUPStratum.equals("Stratum S 0")) {
						tempUPStratum = "Stratum S 1";
					}
					String tempUPLoadGroup = StratumLoadGroupMap.get(tempUPStratum); // get LoadGroup for stratum of
																						// tempUP
					CisDataNew.get(cisSizeNew).set(27, tempUPLoadGroup);
				} else {
					String tempUPStratum = "1"; // get stratum for tempUP
					String tempUPLoadGroup = StratumLoadGroupMap.get(tempUPStratum); // get LoadGroup for stratum of
																						// tempUP
					CisDataNew.get(cisSizeNew).set(27, tempUPLoadGroup); // zapise LoadGroup-o
				}
				
			}


		String OutputFile = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\ELExports\\CorrectedCISExport.csv";

		Fo.generateFile(OutputFile, CisDataNew, "|");

	}

}
