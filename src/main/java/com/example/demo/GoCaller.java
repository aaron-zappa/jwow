package com.example.demo;

public class GoCaller {

    static {
        // Load the native library containing the Go function
        // System.loadLibrary("yourgolibraryname"); // Replace with the actual library name
    }

    /**
     * Declares the native method that will call the Go function.
     * The actual implementation is in the Go code.
     *
     * @param input A string to pass to the Go function.
     * @return The string result returned by the Go function.
     */
    private native String callGoFunction(String input);

    /**
     * Calls the native Go function.
     *
     * @param input The string input for the Go function.
     * @return The string output from the Go function.
     */
    public String performGoCall(String input) {
        return callGoFunction(input);
    }

    public static void main(String[] args) {
        // Example usage:
        // GoCaller goCaller = new GoCaller();
        // String result = goCaller.performGoCall("Hello from Java!");
        // System.out.println("Result from Go: " + result);
    }
}