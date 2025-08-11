package com.rcyc.batchsystem.util;

public class Constants {

    public static final String ENCRYPTION_KEY = System.getenv("RCYC_KEY_VALUE");
    public static final String SCHEDULER_API = System.getenv("SCHDEULER_API");
    public static final String REGION = "Region";
    public static final String REGION_INDEX = "region";
    public static final String REGION_DEMO = "region_demo";

    public static final String PORT = "Port";
    public static final String PORT_INDEX = "port";
    public static final String PORT_DEMO_INDEX = "port_demo";

    public static final String SUCCESS = "Success";
    public static final String CMS_BASE_URL="https://qm1gatewayapi.ritzcarltonyachtcollection.com/cms-content/"; //System.getenv("CMS_BASE_URL");

    //public static final String SCHEDULER_API_TEMP = "https://dev3gatewayapi.ritzcarltonyachtcollection.com/rcyc-scheduler";
    public static final String SCHEDULER_API_TEMP = "http://localhost:8090";

    public static final int DELAY_IN_MINUTES =1;

    public static final String ITINERARY ="Itinerary";
    public static final String ITINERARY_INDEX ="itinerary";
    public static final String ITINERARY_DEMO_INDEX ="itinerary_demo";

    public static final String SUITE ="Suite";
    public static final String SUITE_INDEX ="suite";
    public static final String SUITE_DEMO_INDEX ="suite_demo";
    
    public static final String HOTEL ="Hotel";
    public static final String HOTEL_INDEX ="hotel";
    public static final String HOTEL_DEMO_INDEX ="hotel_demo";

    public static final String PRICING ="Pricing";
    public static final String PRICING_INDEX ="pricing";
    public static final String PRICING_DEMO_INDEX ="pricing_demo";

    public static boolean evaluateExpression(String expression) {
        // Very basic evaluator supporting '>' only
        if (expression.contains(">")) {
            String[] parts = expression.split(">");
            try {
                double left = Double.parseDouble(parts[0].trim());
                double right = Double.parseDouble(parts[1].trim());
                return left > right;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
