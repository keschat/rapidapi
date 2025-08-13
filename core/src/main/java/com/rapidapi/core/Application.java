package com.rapidapi.core;

import com.rapidapi.core.http.JettyServer;

public class Application {

    public static void main(String[] args) {

        try {
            JettyServer server = new JettyServer();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
