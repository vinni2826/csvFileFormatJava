package csvRearrange;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class EnrolleessInfo {

	public static <E> void main(String[] args) {
		final String CSV_TYPE = "csv";
		String fileLocation = "C:/EnrolleesInfo.csv";
		if(FilenameUtils.getExtension(fileLocation).equalsIgnoreCase(CSV_TYPE)) {
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withSkipLines(1).build();
		
	      String [] nextLine;
	      ArrayList<String []> enrollees = new ArrayList<String []>();     
	      
			while ((nextLine = reader.readNext()) != null) {
			
			enrollees.add(nextLine);
				
			  }
			
			List<String[]> listRes = enrollees.stream()
			        .sorted((x, y) -> {
			            int res = x[4].compareTo(y[4]);  //Comparing by company name
			            if (res != 0) return res;
			            res = x[1].compareTo(y[1]); //Comparing by first name 
			            if (res != 0) return res;
			            return x[2].compareTo(y[2]);  //Comparing by last name
			        })
			        .collect(Collectors.toList());

			List<String[]> toRemove = new ArrayList<String[]>();
			for(String[] x:listRes) {

				String company = x[4];
				
				for(String[] j:listRes) {
					if(listRes.indexOf(j)<=listRes.indexOf(x))
						continue;
					if(company.equalsIgnoreCase(j[4])) {
						if(x[0].equalsIgnoreCase(j[0])) {
							if(Integer.parseInt(x[3])<Integer.parseInt(j[3]))
								toRemove.add(x);
							else
								toRemove.add(j);
						}
					}
						
				}
				
			}
			listRes.removeAll(toRemove);
			String [] header = {"User Id","First Name","Last Name","Version","Insurance Company"};
			
			CSVWriter writer = new CSVWriter(new FileWriter(fileLocation, false)) ; 
					writer.writeNext(header);
					for(String[] lines : listRes){
					    writer.writeNext(lines);
					}
			
		        writer.close();
		        
			System.out.println("CSV Enrollment file successfully formatted");
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
