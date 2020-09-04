package com.ksc.lib;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import com.ksc.lib.Curl;
import java.util.*;

/**
 * Simple Curl Manager Test With REST API
 */
public class CurlTest {

    @Test
    public void restApiWithCurlManager() {
        String url = "Your URL Info";
        Curl curl = new Curl(url, "POST", null);

        /**
         * Header ex) Header Key = Content-Type, Header Value = application/json
         * 
         */
        curl.addHeader("Header Key", "Header Value");
        curl.setOptions(Arrays.asList("-L", "-k"));
        curl.addParameter("Parameter Key1", "Parameter Value1");
        curl.addParameter("Parameter Key2", "Parameter Value2");

        String result = "";
        try {
            result = curl.run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNotNull(result);
    }
}
