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

		// odpre in prebere UsagePoint - EnergyConsumer mapping file
		String EquipmentUPFileName = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\LoadProfile\\MappingUPEquipment.csv";
		HashMap<String, String> EquipmentUPMap = new HashMap<String, String>(400000);
		int uPEQHashID = 0;
		int uPEQHashData = 2;
		int numOfLinesUPEQ;

		HashMap<String, String> MRIDUPMap = new HashMap<String, String>(400000);
		int uPMRIDHashID = 0;
		int uPMRIDHashData = 1;

		numOfLinesCIS = Fo.numOfLines(cisFileName, CisData, "|");
		// numOfLinesStratumLG = Fo.numOfLinesHash(stratumLodGroupFileName,
		// StratumLoadGroupMap, lgHashID, lgHashData, "|");
		numOfLinesUPStratum = Fo.numOfLinesHash(StratumUPFileName, StratumUPMap, upHashID, upHashData, "|");
		numOfLinesStratumLG = Fo.numOfLinesHash(StratumLodGroupFileName, StratumLoadGroupMap, lgHashID, lgHashData, ";");
		numOfLinesUPEQ = Fo.numOfLinesHash(EquipmentUPFileName, EquipmentUPMap, uPEQHashID, uPEQHashData, ";");
		Fo.numOfLinesHash(EquipmentUPFileName, MRIDUPMap, uPMRIDHashID, uPMRIDHashData, ";");

		
		List<List<String>> CisDataNew = new ArrayList<>();

		
		for (int i = 0; i < numOfLinesCIS; i++) {
			if ((i > 0)) { // preskoci prvo vrstico... header
				String tempUP = CisData.get(i).get(0); // obravnavano merilno mesto

				if (MRIDUPMap.containsKey(tempUP)) {
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

				try {
					if ((!CisData.get(i).get(28).isEmpty()) | (!(Float.parseFloat(CisData.get(i).get(28)) == 0f))) { // zapise
																														// p
																														// in
																														// q
						float contractPower = Float.parseFloat(CisData.get(i).get(28));
						CisDataNew.get(cisSizeNew).set(25, String.valueOf(0.1 * contractPower)); // p
						CisDataNew.get(cisSizeNew).set(26, String.valueOf(0.1 * contractPower * 0.3287)); // q
					} else {
						CisDataNew.get(cisSizeNew).set(25, String.valueOf(5));
						CisDataNew.get(cisSizeNew).set(26, String.valueOf(5 * 0.3287));
					}

					
					if (EquipmentUPMap.get(CisData.get(i).get(0)).equals("NULL")) {
						CisDataNew.get(cisSizeNew).set(16, (""));
					}
					else {
						CisDataNew.get(cisSizeNew).set(16, ("_" + EquipmentUPMap.get(CisData.get(i).get(0))));
					}
					
					
						CisDataNew.get(cisSizeNew).set(0, ("_" + MRIDUPMap.get(CisData.get(i).get(0))));
					
					CisDataNew.get(cisSizeNew).set(30, ("_" + CisData.get(i).get(30)));


				} catch (NumberFormatException ex) { // handle your exception
					CisDataNew.remove(cisSizeNew);
				} catch (NullPointerException np) {
					CisData.remove(cisSizeNew);
				}
				}
				
			}
		}

		String OutputFile = "D:\\Dokumenti\\CIM\\vmesniki_do\\SCADA\\CIS_Integration\\ELExports\\CorrectedCISExport.csv";

		Fo.generateFile(OutputFile, CisDataNew, "|");

	}

}
