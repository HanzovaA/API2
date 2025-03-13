package com.example.testapi3;
public class Launch {
    private String name;
    private String window_start;
    private Rocket rocket;
    private Pad pad;
    private String image;

    public String getName() { return name; }
    public String getWindowStart() { return window_start; }
    public Rocket getRocket() { return rocket; }
    public Pad getPad() { return pad; }
    public String getImage() { return image; }

    static class Rocket {
        private Configuration configuration;

        public Configuration getConfiguration() { return configuration; }
    }

    static class Configuration {
        private String name;

        public String getName() { return name; }
    }

    static class Pad {
        private String name;
        public String getName() { return name; }
    }
}
