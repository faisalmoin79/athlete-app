import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;


public class Test2 {

	public static int solution(int[] A) {
		// write your code in Java SE 8
		int result = 1;
		List<Integer> list = Arrays.stream(A) // IntStream
				.boxed() // Stream<Integer>
				.collect(Collectors.toList());

		for (int i = 0; i < A.length; i++) {
			if (list.contains(result)) {
				result++;
			} else {
				break;
			}

		}
		return result;

	}
	
	@Data
	public static class CallCost{
		private String number;
		private String duration;
		private int calculatedCost;
	}
	

    public static boolean isCallRecordValid(String callRecord){
        String callRecordPattern = "^[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}\\,[0-9]{2}\\:[0-5][0-9]\\:[0-5][0-9]$";
        return Pattern.matches(callRecordPattern, callRecord);
    }
    
    
	public static int solution(String activityLog) {
		int billAmount = 0;
		CallCost callCost = null;
		
		String phoneNumber = null;
		String callDuration = null;
		
		String[] callRecords = activityLog.split(";");
 		
		Map<String, List<CallCost>> callCostByPhoneNumberMap = new HashMap<>();
		for (String callRecord : callRecords) {
			phoneNumber = callRecord.split(",")[0];
			callDuration = callRecord.split(",")[1];
			
			callCost = new CallCost();
			callCost.setNumber(phoneNumber);
			callCost.setDuration(callDuration);
			
			
			if(isCallRecordValid(callRecord)) {
				callCost.setCalculatedCost(calculateCost(callDuration));
			}else {
				callCost.setCalculatedCost(0);
			}
			
			saveCallCostToMap(callCost, phoneNumber, callCostByPhoneNumberMap);
 			
		}
		
		System.out.println("callCostByNumberBeforeAdjustment: "+ callCostByPhoneNumberMap);
		
		adjustCost(callCostByPhoneNumberMap);
		System.out.println("callCostByNumberAfterAdjustment: "+ callCostByPhoneNumberMap);
		
		billAmount = getTotalCallCost(callCostByPhoneNumberMap);
		
		
		return billAmount;
	}
	
	private static int getTotalCallCost(Map<String, List<CallCost>> callCostByPhoneNumberMap) {
		Collection<List<CallCost>> callCostsCollection = callCostByPhoneNumberMap.values();
		int totalCallCost = 0;

		List<List<CallCost>> callListCollection = callCostsCollection.stream().collect(Collectors.toList());
		
		for (List<CallCost> callList : callListCollection) {
			for (CallCost callCost : callList) {
				totalCallCost+=callCost.getCalculatedCost();
			}
		}
		return totalCallCost;
		 
	}


	private static void adjustCost(Map<String, List<CallCost>> callCostByPhoneNumberMap) {
		Collection<List<CallCost>> callCostsCollection = callCostByPhoneNumberMap.values();
		int longestDurationCallIndex = -1;
		int longestDuration = 1;

		List<List<CallCost>> callListCollection = callCostsCollection.stream().collect(Collectors.toList());
		
		for (List<CallCost> callList : callListCollection) {
			if(callList.size() > longestDuration) {
				longestDuration=callList.size();
				++longestDurationCallIndex;
			}
		}
		List<CallCost> longestDurationCallList = callListCollection.get(longestDurationCallIndex);
		for (CallCost callCost : longestDurationCallList) {
			callCost.setCalculatedCost(0);// cost is free
		}
	}


	private static void saveCallCostToMap(CallCost callCost, String phoneNumber,
			Map<String, List<CallCost>> callCostByPhoneNumberMap) {
		if(callCostByPhoneNumberMap.containsKey(phoneNumber)) {
			List<CallCost> callCostList = callCostByPhoneNumberMap.get(phoneNumber);
			callCostList.add(callCost);
			
		}else {
			@SuppressWarnings("serial")
			List<CallCost> callCostList = new ArrayList<CallCost>()
			{{
				add(callCost);
			}};
			callCostByPhoneNumberMap.put(phoneNumber, callCostList);
		}
	}
	


	private static int calculateCost(String callDuration) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		
		return ran.nextInt(60);
	}


	public static void main(String[] args) {
//		int[] input = { -1,-3};
//		System.out.println(Demo.solution(input));
		String activityLog = "415-555-0001,00:01:00;415-555-0001,00:01:00;415-555-0002,00:01:06";
		
		System.out.println(Test2.solution(activityLog));
	}

}
