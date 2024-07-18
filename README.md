# Android BugBazaar: Your mobile appsec playground to Explore, Exploit, Excel
Welcome to BugBazaar, your gateway to mastering Mobile penetration testing on the Android platform!

## 📱What is it?
BugBazaar is a comprehensive mobile application intentionally designed to be vulnerable, featuring over 30 vulnerabilities. Developed to emulate real-world scenarios, it includes more than 10 modules and features, each replicating real-world functions and the vulnerabilities surrounding them.

<img width="328" height="212" alt="meme" src="https://github.com/payatu/BugBazaar/assets/120417058/7b3a44c1-09b5-4d89-93f6-da542dd6aed6">

## 🔍Why?

We've bundled **30+ vulnerabilities** into a single application, saving you from downloading multiple apps to learn about mobile application pentesting. We've packed a lot into one.

<img width="350" height="206" alt="meme" src="https://github.com/payatu/BugBazaar/assets/120417058/df9a64cc-33fb-4a75-9fbe-96eb202d3e65">


## 🎯For whom?
Whether you're a security enthusiast, developer, beginner exploring the mobile pentesting arena, or a professional looking to hone your skills, BugBazaar has something for everyone on the mobile pentesting learning curve.

<img width="350" height="206" alt="meme" src="https://github.com/payatu/BugBazaar/assets/120417058/0bbca800-f84f-46ff-af7a-8b5de6487136">


 
## 🤔What's in for me?
BugBazaar offers a wide range of vulnerabilities, from "RCE through insecure Dynamic Code Loading" to "One Click Account Takeover via deeplink." We cover "intent Spoofing" to "SQLite db injection," "WebView" bugs to "IPC" misconfigurations in Android  — we've got a lot of things covered.

<img width="380" height="260" alt="meme" src="https://github.com/payatu/BugBazaar/assets/120417058/67054daa-2cc1-4878-9dc4-ac1a55755419">


## 🤓Never-Ending Learning
What's more exciting? Stay in sync with the evolving landscape! BugBazaar regularly updates with fresh vulnerabilities and captivating challenges. Stay vigilant, stay ahead! Get Started Today!

## 📷Screenshots
![Untitled (1715 x 1080 px)](https://github.com/payatu/BugBazaar/assets/151744825/4b291b47-c51e-4644-9871-bbc1a8c388fc)


## ⚠️Vulnerabilities

### WEBVIEW
- Opening arbitrary URLs in the webview
- XSS
- OPEN REDIRECTION
- Account Takeover via Stealing Session ID (Host validation bypass)
- Stealing User token Via JavaScriptInterface class
- Access of Arbitrary files via insecure Flags
    - Note: Only exploitable until API level 28
- Stealing of Arbitrary files via Insecure `WebResourceResponse`
    
### INTENT
- Intent interception
- Account takeover via intent Spoofing
- Steal User's Contact via Insecure Pending Intent
- RCE through insecure Dynamic Code Loading

###  Deep Link  
   - CSRF to add the product to cart
   - Deep link hijacking to load URLs in webview
   - Content Spoofing on Offers activity
    
### IPC COMPONENTS
-  Exported Components
-  Steal User's Contact via typo permission in Content Provider
-  Insecure broadcast receiver
-  Access to Protected Components via broadcast Receiver
-  Insecure services
-  Fragment injection in Refer-Us

### Injections
 - SQL Injection via user input in My order
 - Content Provider SQL Injection in Address
 - Data insertion via insecure Content Provider in Address

### Unintended Data Leakage
- Copy/Paste buffer Caching
- Application backgrounding
- Insecure Logging (logging user creds

### Insecure Storage
- Unencrypted database
- Man in the Disk Attack
- Storing sensitive info in SharedPref
- Hardcoded secrets

### OTHERS
- Improper Input Validation
- Unrestricted file upload
- Misconfigured firebase's firestore
- Passcode Bypass
- Tapjacking
- Improper exception Handling
- Debuggable application
- Backup enabled
- Task Hijacking
- Improper cache handling

### Runtime exploitation
- Runtime code modification
- Login pin bypass via Frida/Objection

### APP Protection
-  EASY LEVEL:
    -  RootBear Library
-  MEDIUM LEVEL:
     -  Magisk detect
     -  Emulator Check
     -  FRIDA DETECTION    
-  ADVANCE LEVEL - ⚠️IN PROGRESS WILL UPDATE IN UPCOMING Release⚠️


## Core Team
|   |   |   |   |   |
|---|---|---|---|---|
| Amit Kumar Prajapat | Lead Security Consultant at Payatu- Mobile | [GitHub](https://github.com/banditAmit) | [LinkedIn](https://www.linkedin.com/in/amit-parjapat/) | [Twitter](https://twitter.com/bit3threat) |
| Vedant Wayal | Senior Security Consultant at Payatu - Mobile | [GitHub](https://github.com/banditVedant) |  [LinkedIn](https://www.linkedin.com/in/vedant-wayal/) | [Twitter](https://twitter.com/h1_squirtle) |
| Akshay Khilari | Security Consultant at Payatu- Mobile | [GitHub](https://github.com/banditAkshay) | [LinkedIn](https://www.linkedin.com/in/akshay-khilari-626126105/)|
