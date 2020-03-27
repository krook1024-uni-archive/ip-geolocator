package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocator {

    private static Logger logger = LoggerFactory.getLogger(GeoLocator.class);

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            logger.debug("Got IP address {}", ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
            logger.debug("Got no IP address or host, using the address of the host");
        }
        logger.info("Downloading JSON information from {}", url);
        String s = IOUtils.toString(url, "UTF-8");
        GeoLocation response = GSON.fromJson(s, GeoLocation.class);
        logger.debug("Got JSON response: {}", response);
        return response;
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            logger.error("Something is wrong", e);
        }
    }

}
