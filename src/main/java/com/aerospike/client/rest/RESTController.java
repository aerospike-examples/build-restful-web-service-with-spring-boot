package com.aerospike.client.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

@Controller
public class RESTController {
	@Autowired
	AerospikeClient client;
    @RequestMapping(value="/as/{namespace}/{set}/getAll/{key}", method=RequestMethod.GET)
    public @ResponseBody JSONRecord getAll(@PathVariable("namespace") String namespace, @PathVariable("set") String set, @PathVariable("key") String keyvalue) throws Exception {
    	
    	Policy policy = new Policy();
    	Key key = new Key(namespace, set, keyvalue);
        Record result = client.get(policy, key);
     
        return new JSONRecord(result);
    }
    
    /*
     * CSV flights file upload
     */
    @RequestMapping(value="/uploadFlights", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/uploadFlights", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, 
            @RequestParam("file") MultipartFile file){
    	
    	if (!file.isEmpty()) {
    		try {
    			WritePolicy wp = new WritePolicy();
    			String line =  "";
    			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
    			while ((line = br.readLine()) != null) {

    				// use comma as separator
    				String[] flight = line.split(",");
					
    				/*
    				 * write the record to Aerospike
    				 * NOTE: Bin names must not exceed 14 characters
    				 */
					client.put(wp,
    						new Key("test", "flights",flight[0].trim() ),
    						new Bin("YEAR", Integer.parseInt(flight[1].trim())),	
    						new Bin("DAY_OF_MONTH", Integer.parseInt(flight[2].trim())),
    						new Bin("FL_DATE", flight[3].trim()),
    						new Bin("AIRLINE_ID", Integer.parseInt(flight[4].trim())),	
    						new Bin("CARRIER", flight[5].trim()),
    						new Bin("FL_NUM", Integer.parseInt(flight[6].trim())),
    						new Bin("ORI_AIRPORT_ID", Integer.parseInt(flight[7].trim())),
    						new Bin("ORIGIN", flight[8].trim()),
    						new Bin("ORI_CITY_NAME", flight[9].trim()),
    						new Bin("ORI_STATE_ABR", flight[10].trim()),
    						new Bin("DEST", flight[11].trim()),
    						new Bin("DEST_CITY_NAME", flight[12].trim()),
    						new Bin("DEST_STATE_ABR", flight[13].trim()),
    						new Bin("DEP_TIME", Integer.parseInt(flight[14].trim())),
    						new Bin("ARR_TIME", Integer.parseInt(flight[15].trim())),
    						new Bin("ELAPSED_TIME", Integer.parseInt(flight[16].trim())),
    						new Bin("AIR_TIME", Integer.parseInt(flight[17].trim())),
    						new Bin("DISTANCE", Integer.parseInt(flight[18].trim()))
    				);
		
    				System.out.println("Flight [ID= " + flight[0] 
								+ " , year=" + flight[1] 
								+ " , DAY_OF_MONTH=" + flight[2] 
								+ " , FL_DATE=" + flight[3] 
								+ " , AIRLINE_ID=" + flight[4] 
								+ " , CARRIER=" + flight[5] 
								+ " , FL_NUM=" + flight[6] 
								+ " , ORIGIN_AIRPORT_ID=" + flight[7] 
								+ "]");

    			}
    			br.close();

    			return "You successfully uploaded " + name;
    		} catch (Exception e) {
    			return "You failed to upload " + name + " => " + e.getMessage();
    		}
    	} else {
    		return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
}
