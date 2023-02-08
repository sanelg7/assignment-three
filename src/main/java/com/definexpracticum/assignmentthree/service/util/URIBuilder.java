package com.definexpracticum.assignmentthree.service.util;


public class URIBuilder {


    // URLBuilder class is used to build the outgoing request URL. Used to retrieve data from weather API.
    private String baseURL;

    private String location;

    private static final String unit = "unitGroup=metric";

    private String timeInterval;

    private String range;

    private String apiKey;


    private static final String contentType = "&contentType=json";

    public String build() {
        String url = baseURL + location + "/" +
                range + "?" +
                unit + timeInterval +
                apiKey + contentType;

            return url;

    }


    public URIBuilder setTimeInterval(String timeInterval) {
        this.timeInterval = "&include=" + timeInterval;
        return this;
    }
    public URIBuilder setLocation(String location) {
        this.location = location;
        return this;
    }


    public URIBuilder setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        return this;
    }

    public URIBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public URIBuilder setRange(String range) {
        this.range = range;
        return this;
    }

    @Override
    public String toString() {
        return "URIBuilder{" +
                "baseURL='" + baseURL + '\'' +
                ", location='" + location + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
