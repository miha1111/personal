package custom.stratum1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*LOGIKA
 * vzameš drugi atribut v vrstici (tj atribut 1) in tretji atribut (tj atribut št. 2). 
 * Zapomnis si v kateri vrstici si v array listi int
 * preveri v vseh vrsticah ce se še kje pojavi ta kombinacija atributa 2 in 3. 
 * v tisti vrsticiko se pojavi si zapomni katera vrstica je to bila. Dodaj vrstico v array listo int
 * pojdi do konca dokumenta
 * preveri koliko vrstic si našel
 * èe so 3 vrstice ali veè zapiši v vsako vrstico v èetrti stolpec (tj atribut 3) "Stanovanje"
 * pojdi do konca dokumenta
 * nato pojdi se enkrat cez dokument in vsepovsod kjer ni podatka napisi stanovanjski blok
 */




public class Stratum1 {
	
    private static final String DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String address = null;
		String city = null;		
		int numOfRecord = 0;
		List <Integer> sameAddress = new ArrayList<Integer>();
		String inputFile = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\primer_gospodinjci1.csv";
		String outputFile = "C:\\Users\\el6403\\Desktop\\obdelani_podatki\\output\\primer_gospodinjci2.csv";

		
		List<String> tempLine1 = new ArrayList<>();
		List<String> tempLine2 = new ArrayList<>();	
		List<List<String>> all = new ArrayList<>();
		
		numOfRecord = numOfLines (inputFile, all);			// get numbers of rows in table
				
		for (int i = 0; i < numOfRecord; i++) {				//gre cez vse vrstice in nato vsako posebej obravnava
			if((i % (numOfRecord/10))==((numOfRecord/10)-1)) {
				System.out.println("Processing .... " + 100.00f*(i+1)/numOfRecord + "% ");
			}
			
			tempLine1  = all.get(i);
			address = tempLine1.get(1);
			city = tempLine1.get(2);
			if (tempLine1.size() < 4) {

				for (int j = 0; j < numOfRecord; j++) {			// obravnava vsake vrstice posebej
					tempLine2  = all.get(j);
//					System.out.println(address);
//					System.out.println(tempLine2.get(1));				
					if ((tempLine2.get(1).equals(address))&&(tempLine2.get(2).equals(city))) {
						sameAddress.add(j);
	
//						System.out.println("isti");
					}
//					System.out.println(j);
				}
				
				String aa = null;
				
				for (int k = 0; k < sameAddress.size(); k++) {
					tempLine2 = all.get(sameAddress.get(k));		// dobi index od prvega ponovljenega objekta
					tempLine2 = new ArrayList<>(tempLine2);
					aa = String.valueOf(sameAddress.size());
//					System.out.println(aa);				
					tempLine2.add(aa);
					if(sameAddress.size()>3) {
						tempLine2.add("Stanovanje");
					}
					else {
						tempLine2.add("Stanovanjska hiša");
					}
					all.add(sameAddress.get(k), tempLine2);
					
					all.remove(sameAddress.get(k)+1);
//					System.out.println("Naslov na MM: " + all.get(sameAddress.get(k))) ;
//					System.out.println("Naslov na MM: " + tempLine2.get(0) +" ponovljen " + tempLine2.get(3) + " krat");
					
				}
				sameAddress.clear();
			}
		}
		
		generateFile(outputFile, all);
	

		//File myfile = new File("C:\\Users\\el6403\\Desktop\\obdelani_podatki\\primer_gospodinjci.csv");

			//BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\el6403\\Desktop\\test_BTP.csv"));
			//BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\el6403\\Desktop\\obdelani_podatki\\primer_gospodinjci.csv"));
			
			
			//File myfile = new File("C:\\Users\\el6403\\Desktop\\obdelani_podatki\\primer_gospodinjci.csv");
			
			
	        
	        /*
	        System.out.printf("There are %d lines in the file\n", lines.size());
	        
	        System.err.printf("The last line is: %s", lines.get(lines.size()-1));
			*/
						
			//D:\Dokumenti\CIM\vmesniki do\SCADA\LoadProfile\obdelani_podatki
			/*
			
			List<String> lineList = new ArrayList<>();
			String str;
			String usgePointID;
			
			usgePointID = "3004407";
			///////
			///////////
	        while ((str = in.readLine())!= null) {
	            String [] lineString = str.split(";");
				lineList = Arrays.asList(lineString);
				if (lineList.indexOf(usgePointID)!=(-1)) {
					System.out.println("Switch with BTP = " + usgePointID + " is found in RTP " + lineList.get(1));
					break;
				}
				
				//System.out.println(lineList.indexOf(switchID));		
				/*
				for (ListIterator<String> iter = lineList.listIterator(); iter.hasNext(); ) {
				    String cell = iter.next();
				    System.out.println(cell);
				    
				    
				    
				    // 1 - can call methods of element
				    // 2 - can use iter.remove() to remove the current element from the list
				    // 3 - can use iter.add(...) to insert a new element into the list
				    //     between element and iter->next()
				    // 4 - can use iter.set(...) to replace the current element
				}
	            
	            
	        }
	        //////////////
	    	
	    	//////////////
	    	
	    	*/
	    	
	        //in.close();			
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

	
	
	public static int numOfLines (String fileName) {			// returns numbers of lines
		int num = 0;
		BufferedReader in = null;
		
		try {
			in = new BufferedReader (new FileReader(fileName));
			while ((in.readLine())!= null) {
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
		return num;
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
		System.out.println("Whole Document is saved into cache.");
		return num;
	}	
}