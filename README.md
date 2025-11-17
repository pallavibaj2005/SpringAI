🚀 SpringAI – Spring Boot + Groq LLM Chat Application

SpringAI is a lightweight, production-ready AI chat interface built using Spring Boot, Groq LLM API, and Thymeleaf.
It provides a clean backend + frontend setup that lets users ask questions and receive AI-generated responses using Llama 3 models running on Groq’s ultra-fast inference engine.

🌟 Key Features
🔹 Groq API Integration (Llama 3.1 8B - Instant Model)

Sends user prompts to Groq’s /chat/completions endpoint

Returns conversational AI responses

Uses fast and low-latency inference

🔹 Secure API Key Handling

No hard-coded secrets

Uses environment variables:

groq.api.key=${GROQ_API_KEY}

🔹 Markdown → HTML Rendering

Supports:

Code blocks

Inline code

Headings

Lists

Formatting

Using CommonMark parser + renderer

🔹 Bootstrap UI + Thymeleaf

Fully responsive interface

Input form + chat-style output

Clean UX for AI responses

📁 Project Structure
src/main/java/com/example/QueryAI/
│
├── AiController.java                # Handles UI + Groq API requests
├── QueryAi1Application.java         # Main Spring Boot class
│
src/main/resources/
│
├── templates/
│   └── index.html                  # Chat UI
│
└── application.properties           # Reads GROQ_API_KEY from environment

🔥 How It Works (Backend Flow)
1️⃣ User submits question via /ask?question=...
2️⃣ Controller builds JSON request:
{
  "model": "llama-3.1-8b-instant",
  "messages": [
    {
      "role": "user",
      "content": "Your question"
    }
  ]
}

3️⃣ Spring sends HTTPS POST request to:
https://api.groq.com/openai/v1/chat/completions

4️⃣ API returns structured JSON

Controller extracts:

choices[0].message.content

5️⃣ Markdown → HTML conversion

Uses CommonMark to render:

Code blocks (```java)

Inline code

Bold/italic

Headings

6️⃣ Thymeleaf displays formatted HTML response
📌 API Endpoints
GET /

Loads the UI page.

GET /ask?question=your_text_here

Sends user query to Groq and returns rendered HTML response.

🔐 Environment Variable Setup
Windows (PowerShell)
setx GROQ_API_KEY "your_api_key_here"

Mac/Linux (bash)
export GROQ_API_KEY="your_api_key_here"

application.properties
groq.api.key=${GROQ_API_KEY}
server.port=8080

▶️ Running the Project
mvn spring-boot:run


Visit:

http://localhost:8080/


🧩 Dependencies Used

Spring Boot Web + Thymeleaf

Java 17+

CommonMark (Markdown parser + renderer)

org.json

HttpClient (Java 11+)

🎯 Purpose of This Project

SpringAI is built to demonstrate:

Clean integration between Spring Boot & Groq LLMs

Secure configuration with no hard-coded secrets

Markdown response rendering pipeline

A minimal, clean UI

How to build your own AI app backend
