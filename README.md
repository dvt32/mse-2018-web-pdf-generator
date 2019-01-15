# web-pdf-generator
A web app for generating a PDF file from a specified URL (developed using Spring Boot, JMS & WebSocket)

# How to start
1. Import the 3 Gradle projects via Eclipse / Spring Tool Suite.
2. Run "pdf-web", "pdf-service" & "pdf-generator-service" at the same time (the start order doesn't matter)
3. Start the application through your browser from the URL http://localhost:8000 or http://localhost:8000/pdf-web

# How to use
1. Open http://localhost:8000 or http://localhost:8000/pdf-web through your browser
2. Enter a valid URL in the input box
3. Click submit
4. Wait a few seconds for a notification with a download link (don't close the page)

# Notes
- "pdf-web" works on port 8000
- "pdf-service" works on port 8100
- "pdf-generator-service" works on port 8200

# To-do
- Add unit / integration tests
