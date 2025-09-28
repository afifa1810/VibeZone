# 🎶 VibeZone – Mood-Based Music Recommendation App  

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](#)  
[![Kotlin](https://img.shields.io/badge/Code-Kotlin-purple.svg)](#)  
[![Python](https://img.shields.io/badge/Backend-Flask-blue.svg)](#)  
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> *VibeZone* uses AI-powered facial emotion detection to instantly recommend music that matches your mood.  
> Snap a selfie → Detect mood → Get a curated playlist from YouTube automatically.  

---

## 📚 Table of Contents
- [✨ Features](#-features)
- [⚙ How It Works](#%EF%B8%8F-how-it-works)
- [🛠 Tech Stack](#-tech-stack)
- [🚀 Installation](#-installation)
- [▶ Usage](#%EF%B8%8F-usage)
- [❓ FAQ](#-faq)
- [🤝 Contributing](#-contributing)
- [📜 License](#-license)

---

## ✨ Features
- Detects *user’s mood* via camera in real time  
- *20 curated songs* per detected mood using YouTube API  
- Sleek Android UI with *Login/Signup* flow  
- Easy navigation between mood detection & recommendations  
- Modular backend powered by *Flask + Python AI model* 

---

## ⚙ How It Works
1. *User opens app* → logs in or signs up  
2. *Camera opens* and captures the face  
3. *Flask backend* (DeepFace/AI Model) detects emotion (happy, sad, neutral…)  
4. Backend queries *YouTube API* for mood-based songs  
5. App displays 20 clickable YouTube song links in a smooth list view  

---

## 🛠 Tech Stack

| Layer | Technology | Purpose |
|-------|------------|----------|
| Android Frontend | Kotlin + XML | UI + camera handling |
| Backend | Flask (Python) | API for mood detection & song fetch |
| AI / ML | DeepFace / OpenCV / TensorFlow | Emotion detection |
| API | YouTube Data API | Fetch songs per mood |
| Networking | Retrofit | Communication between Android & Flask |
| User Authentication | Firebase | Login/Signup |

---

## 🚀 Installation

### 1️⃣ Clone Repository  
bash
git clone https://github.com/afifa1810/VibeZone.git
cd VibeZone


### 2️⃣ Android App Setup  
- Open in *Android Studio*  
- Add your *YouTube API key* in local.properties or the designated constants file  
- Sync Gradle and run on emulator or physical device  

### 3️⃣ Flask Backend Setup  
bash
cd flask_server
venv\scripts\activate
pip install Flask deepface requests
python app.py

- Server runs at http://127.0.0.1:5000 (update in Android Retrofit base URL)
- Add your local IP address, must be same at your mobile and laptop.

> ⚠ Make sure camera & Internet permissions are enabled in the app.

---

## ▶ Usage
1. Launch *VibeZone* on your device  
2. Log in or sign up  
3. Allow camera permission  
4. Wait for the mood to be detected  
5. Enjoy your *auto-curated playlist* 🎶  

---

## ❓ FAQ

| Question | Answer |
|----------|--------|
| Mood not detected? | Ensure good lighting and face within frame. |
| Playlist empty? | Check your YouTube API quota or Internet connection. |
| Backend not responding? | Verify Flask server URL and run python app.py first. |

---

## 🤝 Contributing
Contributions are welcome!  

1. Fork the repository  
2. Create a branch feature/YourFeature  
3. Commit changes and push  
4. Open a Pull Request  

Please ensure your code follows the project style and includes tests/screenshots where applicable.

---

## 📜 License
Distributed under the MIT License. See LICENSE for details.

---
