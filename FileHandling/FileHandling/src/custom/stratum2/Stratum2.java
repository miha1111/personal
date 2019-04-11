package custom.stratum2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Stratum2 {
	
    private static final String DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
	public static void main(String[] args) {
		
		/*
		 * preveri za vsako merilno mesto ki ima Stanovanjska hiša ali Stanovanje ali #N/V ali obstaja v cache-u
		 * èe ga najdeš zapiši vrednosti iz cache-a, èe ne default (Martin)
		 * zapri file
		 */
		//List<String> sifranti = new ArrayList<>();		
		Integer lineNumberF1 = 0;
		Integer lineNumberF2 = 0;
		int defalutedMM = 0;

		List<List<String>> allGospodinjci = new ArrayList<>();
		List<List<String>> allMM = new ArrayList<>();

		
		String inputFile1 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\output\\gospodinjci_csv2.csv";
		String inputFile2 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\all.csv";
		String outputFile1 = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\output\\all_csv2.csv";		
	
		HashMap<String, String> sifrant = new HashMap<String, String>();
		sifrant.put("Stanovanjska hiša", "1");
		sifrant.put("Stanovanje", "2");
		sifrant.put("Stanovanjska hiša s samooskrbo", "3");
		sifrant.put("Industrijski objekt", "4");
		sifrant.put("Javni objekt", "5");
		sifrant.put("Gospodarsko poslopje", "6");
		sifrant.put("Poslovni prostor", "7");
		sifrant.put( "Razvestljava", "8");
		sifrant.put("Komunalna urejenost", "9");
		sifrant.put("Komunalna urejenost", "10");
		sifrant.put("Polnilna postaja", "11");
		sifrant.put("Sonèna elektrarna", "12");
		sifrant.put("Hidroelektrarna", "13");
		sifrant.put("Kogeneracija", "14");
		sifrant.put("Vetrna elektrarna", "15");
//	    System.out.println(sifrant); 

	    lineNumberF1 = numOfLines(inputFile1, allGospodinjci);
	    lineNumberF2 = numOfLines(inputFile2, allMM);

	
		String usagePointID = null;
		String stratum = null;
		int j = 0;
		
        for (List <String> tempLineMM : allMM) {
        	
        	if((j % (lineNumberF2/100))==((lineNumberF2/100)-1)) {
				System.out.println("Processing .... " + 100.00f*(j+1)/lineNumberF2 + "% ");
			}
        	
			usagePointID = tempLineMM.get(0);
			stratum = tempLineMM.get(2);
			if ((stratum.equals("Stanovanjska hiša"))||(stratum.equals("Stanovanje"))||(stratum.equals("#N/V"))) {
				for (int i = 0; i < lineNumberF1; i++) {
					if (allGospodinjci.get(i).get(0).equals(usagePointID)) {
						tempLineMM.set(2, allGospodinjci.get(i).get(3)); 		// correct the value (description) from gospodinjci.csv
						tempLineMM.set(1, sifrant.get(allGospodinjci.get(i).get(3))); 		// correct the value (ID) from gospodinjci.csv
						break;
					}
					else if(i==(lineNumberF1-1)) {
						tempLineMM.set(2, "Stanovanjska hiša"); 		// default value for stratum description
						tempLineMM.set(1, "1"); 		// default value for stratum ID
						defalutedMM ++;
					}
				}					
			}
			
			if (!(tempLineMM.get(1).equals(sifrant.get(tempLineMM.get(2))))) {
				tempLineMM.set(1, sifrant.get(tempLineMM.get(2)));
			}
			j++;
        }

        generateFile(outputFile1, allMM);
		System.out.println("Process successfully finished. " + defalutedMM + " UsagePoints were assigned default value: Stanovanjska hiša.");	
	}
	
	
	
	public static int numOfLines (String fileName, List<List<String>> all) {		// returns numbers of lines and save file "fileName" to cache "all"
		int num = 0;
		BufferedReader in = null;
		String str = null;
		List <String> listOfStr = null;
		//HashMap<String, List<List<String>>> allHash = new HashMap<String, List<List<String>>>();
		
		try {
			in = new BufferedReader (new FileReader(fileName));
	        while ((str = in.readLine())!= null) {
	            String [] lineString = str.split(";");
				listOfStr = Arrays.asList(lineString);
				all.add(listOfStr);
				num++;
			}
		}
		catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try { if (in!=null) in.close(); }
			catch(IOException ignore) {}
		}
		
		System.out.println("There are " + num + " lines in " + fileName);
		System.out.println("Whole document is saved into cache.");
		return num;
	}
	
	
	
	
	public static void generateFile (String fileName, List<List<String>> all) {
		System.out.println("Started writing data into " + fileName + ". ");
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(fileName)));
            //Write the CSV file header
            //writer.append(FILE_HEADER.toString());
             
            //Add a new line separator after the header
            writer.append(NEW_LINE_SEPARATOR);
             
            //Write a list of all familly members to the CSV file
            for ( List <String> fm : all) {
            	for (String fm_att : fm) {
            		writer.append(fm_att);
            		writer.append(DELIMITER);
            	}
            	writer.append(NEW_LINE_SEPARATOR);	            	
            }
            System.out.println("Content successfully writen to file " + fileName + "! ");	
            
            
        } catch (IOException ex) {
        	   System.out.println("Error opening/writing to file " + fileName + "!");	        	
        } finally {
           try {
        	   writer.close();
        	   } 
           catch (Exception ex) {
        	   System.out.println("Error closing file!");
           }
        }  
     }
}
