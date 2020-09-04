package com.ksc.lib;

import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Simple Curl Manager
 */
public class Curl {
    private String url;
    private String header = "";
    private String init = "curl ";
    private String type;
    private HashSet<String> options;
    private Map<String, Object> params;

    public Curl(String url, String type, Map<String, Object> params) {
        this.url = url;
        this.type = type;

        if (params != null && params.size() > 0) {
            this.params = params;
        } else {
            this.params = new HashMap<>();
        }
    }

    public Curl(String url, String type) {
        this(url, type, null);
    }

    public void setOptions(List<String> options) {
        this.options = new HashSet<>(options);
    }

    public void addParameter(String key, String value) {
        this.params.put(key, value);
    }

    public void addHeader(String key, String value) {
        this.header += " -H " + key + ":" + value;
    }

    private String paramsToString() {
        if (this.params == null) {
            return null;
        }

        String option = "";
        if (this.header.contains("x-www-form-urlencoded")) {
            option = "--data-urlencode ";
        } else {
            option = "-d ";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> param : this.params.entrySet()) {
            if (sb.length() != 0) {
                sb.append(" ");
            }

            sb.append(option);
            sb.append(param.getKey());
            sb.append("=");
            sb.append(String.valueOf(param.getValue()));
        }

        return sb.toString();
    }

    private String optionsToString() {
        String s = this.options.toString().replace("[", "").replace("]", "").replace(",", "");
        return this.type == null ? s : s + " -X " + this.type;
    }

    public String run() throws IOException {
        String command = this.init + this.optionsToString() + " " + this.url + this.header + " " + this.paramsToString();
        Process process = Runtime.getRuntime().exec(command);

        return new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }
}
