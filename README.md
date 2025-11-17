# 🚀 SpringAI – Intelligent Chat with Spring Boot + Groq Llama 3

**SpringAI** is a fast, production-ready AI chat web application combining Spring Boot with [Groq](https://groq.com/)’s Llama 3 model for intelligent, conversational experiences.

Built for developers and enthusiasts, SpringAI provides a secure, elegant backend + frontend setup, allowing users to interact with state-of-the-art LLMs through a clean, responsive UI.

---

## 🌟 Features

- **Groq API Integration (Llama 3.1 8B - Instant Model)**
  - Send user prompts to Groq `/chat/completions` endpoint
  - Get fast, low-latency conversational AI responses

- **Secure API Key Management**
  - No hard-coded secrets!
  - Uses environment variables (`GROQ_API_KEY`) for Groq credentials

- **Markdown → HTML Rendering**
  - Code blocks
  - Inline code
  - Headings, lists, formatting
  - Powered by CommonMark parser/renderer

- **Responsive Bootstrap UI + Thymeleaf Templates**
  - Chat-style interface
  - Intuitive input/output handling

---

## 📁 Project Structure

```
src/main/java/com/example/QueryAI/
│
├── AiController.java              # Handles UI + Groq API requests
├── QueryAi1Application.java       # Main Spring Boot class
│
src/main/resources/
│
├── templates/
│   └── index.html                 # Chat UI (Thymeleaf)
│
└── application.properties         # Reads GROQ_API_KEY from environment
```

---

## 🔥 How the Backend Works

1. **User submits a question** via `/ask?question=...`
2. **AiController** builds a JSON request:
    ```json
    {
      "model": "llama-3.1-8b-instant",
      "messages": [ { "role": "user", "content": "Your question" } ]
    }
    ```
3. **Spring Boot** sends an HTTP POST to `https://api.groq.com/openai/v1/chat/completions`
4. **Groq API** returns structured JSON. Controller extracts `choices[0].message.content`.
5. **Markdown response** is parsed & rendered to HTML (CommonMark).
6. **Thymeleaf UI** displays the formatted, interactive reply.

---

## 🛣️ API Endpoints

- `GET /`  
  Loads the chat UI page.
- `GET /ask?question=your_text_here`  
  Submits the user’s query to Groq, returns a rich AI-generated HTML response.

---

## 🔐 Environment Variable Setup

**Windows (PowerShell):**
```shell
setx GROQ_API_KEY "your_api_key_here"
```

**Mac/Linux (bash):**
```bash
export GROQ_API_KEY="your_api_key_here"
```

**application.properties:**
```properties
groq.api.key=${GROQ_API_KEY}
server.port=8080
```

---

## ▶️ Running the Project

1. Make sure your environment variable is set: `GROQ_API_KEY`
2. Start the app:
    ```shell
    mvn spring-boot:run
    ```
3. Open [http://localhost:8080/](http://localhost:8080/) in your browser.

---

## 🧩 Dependencies Used

- Spring Boot Web + Thymeleaf
- Java 17+
- CommonMark (Markdown parsing & rendering)

---

## 🎯 Why SpringAI?

SpringAI demonstrates:

- Seamless integration between Spring Boot and Groq’s blazing-fast LLMs
- Full Markdown pipeline: AI → Markdown → HTML → UX
- Minimal, elegant UI powered by Thymeleaf and Bootstrap
- Perfect starting point for building your own custom LLM-driven applications!

---

