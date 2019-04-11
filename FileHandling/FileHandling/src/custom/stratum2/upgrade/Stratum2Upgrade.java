package custom.stratum2.upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import common.FileOperations;

public class Stratum2Upgrade {

	    
	    static FileOperations Operation = new FileOperations();
	    
		public static void main(String[] args) {
					
			Integer lineNumberF1 = 0;
			Integer lineNumberF2 = 0;
			int defalutedMM = 0;
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			long spentTimeMinutes =0;
			long spentTImeSec = 0;
			long spentTimeMiliSec = 0;
			
        	//System.out.println(System.currentTimeMillis());

			//List<List<String>> allGospodinjci = new ArrayList<>();
		    HashMap<String, String> allProizvodnjaHash = new HashMap<String, String>(350000);


			List<List<String>> allMM = new ArrayList<>();

			
			String inputFile1 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\proizvajalci.csv";
			String inputFile2 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\all_csv_OK.csv";
			String outputFile1 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\output\\MM_final.csv";		
		
			HashMap<String, String> sifrant = new HashMap<String, String>();
			sifrant.put("Ni podatka", "0");
			sifrant.put("Stanovanjska hiša", "1");
			sifrant.put("Stanovanje", "2");
			sifrant.put("Stanovanjska hiša s samooskrbo", "3");
			sifrant.put("Industrijski objekt", "4");
			sifrant.put("Javni objekt", "5");
			sifrant.put("Gospodarsko poslopje", "6");
			sifrant.put("Poslovni prostor", "7");
			sifrant.put( "Razvestljava", "8");
			sifrant.put("Komunalna urejenost", "9");
			sifrant.put("Bazna postaja", "10");
			sifrant.put("Polnilna postaja", "11");
			sifrant.put("Sonèna elektrarna", "12");
			sifrant.put("Hidroelektrarna", "13");
			sifrant.put("Kogeneracija", "14");
			sifrant.put("Vetrna elektrarna", "15");
//		    System.out.println(sifrant); 

		    lineNumberF1 = Operation.numOfLinesHash(inputFile1, allProizvodnjaHash, 0, 2);
		    lineNumberF2 = Operation.numOfLines(inputFile2, allMM);

		    String usagePointID = null;
			String stratum = null;
			int j = 0;
			
	        for (List <String> tempLineMM : allMM) {
	        	
	        	if((j % (lineNumberF2/100))==((lineNumberF2/100)-1)) {
					System.out.println("Processing .... " + 100.00f*(j+1)/lineNumberF2 + "% ");
				}
	        	
				usagePointID = tempLineMM.get(0);
				stratum = tempLineMM.get(2);
			//	if ((stratum.equals("Stanovanjska hiša"))||(stratum.equals("Stanovanje"))||(stratum.equals("Stanovanjska hiša s samooskrbo"))) {
					
					

					if (allProizvodnjaHash.get(usagePointID) != null){
						tempLineMM.set(2, allProizvodnjaHash.get(usagePointID)); 		// correct the value (description) from samooskrba.csv
						tempLineMM.set(1, sifrant.get(allProizvodnjaHash.get(usagePointID))); 		// correct the value (ID) from samooskrba.csv
					}
					if (tempLineMM.get(2).equals("Ni podatka")) {
						defalutedMM ++;
					}
//					else {
//						tempLineMM.set(1, "0"); 		// default value for stratum description
//						tempLineMM.set(2, "Ni podatka"); 		// default value for stratum ID
//						defalutedMM ++;
//					}
								
				//}				

					if (!(tempLineMM.get(1).equals(sifrant.get(tempLineMM.get(2))))) {
					tempLineMM.set(1, sifrant.get(tempLineMM.get(2)));
				}
				j++;
	        }

	        Operation.generateFile(outputFile1, allMM);
			endTime = System.currentTimeMillis();
			if ((endTime - startTime) > 59999) {
				spentTimeMinutes = ((endTime - startTime)/1000)/60;
				spentTImeSec = ((endTime - startTime)/1000)%60;
			}
			else {
				spentTImeSec = ((endTime - startTime)/1000);
				spentTimeMiliSec = ((endTime - startTime)%1000);
			}
			System.out.println("Process successfully finished in " + spentTimeMinutes + " min " + spentTImeSec + "." + spentTimeMiliSec +" s. ");
			System.out.println(defalutedMM + " UsagePoints still don't have STRATUM.");	

			
		}

}
