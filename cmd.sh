#!/bin/bash

# Start SimpleHtmlServer in the background and redirect output to server.log
java -cp bin com.example.demo.SimpleHtmlServer > server.log 2>&1 &

echo "Simple server has been started."
echo "Access the server at: http://localhost:8080"
