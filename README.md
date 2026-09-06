# Voice Open App — Build aur Install Guide

## Option A: Sirf mobile se build karein (computer chahiye nahi)

Is project mein ek `.github/workflows/build.yml` file already maujood hai jo
**GitHub ke apne server par khud APK build kar deti hai**. Aapko sirf phone
ka browser chahiye.

### Steps (sab kuch phone browser mein):

1. **github.com** par jayein aur free account banayein (agar nahi hai).
2. Upar right corner mein **+** icon > **New repository** dabayein.
   Naam dein jaise `VoiceOpenApp` > **Create repository**.
3. Us naye repo ke andar **"uploading an existing file"** link par tap karein
   (ya **Add file > Upload files**).
4. Is zip ko pehle apne phone mein **extract** karein (koi bhi file manager
   app jaise ZArchiver se), phir **saari files aur folders** (README, app
   folder, .github folder, waghera) select karke upload kar dein. Neeche
   **"Commit changes"** dabayein.

   ⚠️ Zaroori: `.github` folder chupi hui (hidden) ho sakti hai — file
   manager mein "show hidden files" ON karein taake ye bhi upload ho.

5. Repo ke andar **"Actions"** tab par jayein. Ek workflow "Build APK" nazar
   aayega — usay open karke **"Run workflow"** button dabayein.
6. 2-4 minute wait karein (GitHub server par build ho raha hoga — progress
   dikhta rahega).
7. Build complete hone ke baad, usi run ke page par neeche **"Artifacts"**
   section mein `VoiceOpenApp-apk` milega — usay tap karke download kar lein
   (ye ek zip file mein APK dega).
8. Phone mein downloaded zip ko extract karein, `app-debug.apk` par tap
   karein > Install (pehli baar "install from unknown sources" allow karna
   parhega, phone khud bata dega kahan se allow karna hai).

Bas — app phone mein normal app ki tarah install ho kar icon show ho jayega.

## Option B: Computer/laptop mil jaye to (Android Studio)

Ye ek Android app ka source code hai jo voice ya text command se aapke phone mein
installed apps (WhatsApp, YouTube, Camera, Gmail, Facebook, Settings, waghera) ko
seedha open kar deta hai.

## Zaroorat
- Windows/Mac/Linux computer
- Android Studio (free) — download: https://developer.android.com/studio
- Internet connection (pehli martaba Gradle dependencies download karne ke liye)

## Steps

1. Android Studio install karein (agar pehle se nahi hai).
2. Android Studio kholein → "Open" → is `VoiceOpenApp` folder ko select karein.
3. Thora wait karein — Gradle sync khud ba khud ho jayega (pehli martaba 2-5 minute lag sakte hain).
4. Upar toolbar mein green "Run" (▶) button dabayein.
5. Apna phone USB se connect karein (USB Debugging on karke — Settings > Developer
   Options > USB Debugging) — ya phone ki jagah emulator bhi use kar sakte hain.
6. App khud phone mein install ho kar khul jayegi.

## Real APK file banane ke liye (taake dost ko bhi bhej sakein)

Android Studio mein:
`Build` menu > `Build Bundle(s) / APK(s)` > `Build APK(s)`

Build hone ke baad ek `.apk` file milegi (usually
`app/build/outputs/apk/debug/app-debug.apk`) — ye file kisi bhi Android phone
mein directly install ho sakti hai (Settings mein "Install from unknown sources"
allow karna parhega).

## App kaise use karein

- Text box mein likhein: "whatsapp kholo" ya "open whatsapp" → Open dabayein
- Mic button dabayein → bolein "youtube kholo" → app khud recognize karke
  YouTube open kar degi
- Neeche diye gaye quick buttons se seedha bhi apps khol sakte hain

## Naya app add karna ho to

`MainActivity.kt` file mein `appPackages` list mein naya entry add karein:

```kotlin
"spotify" to "com.spotify.music"
```

Bas — "spotify kholo" bolne se wo bhi open hone lag jayegi (agar phone mein
installed ho).

## Note

- Agar koi app phone mein installed nahi hai to ye Play Store khol dega taake
  aap install kar sakein.
- Mic permission pehli baar app istemal karte waqt manga jayega — allow karna
  zaroori hai warna voice command kaam nahi karegi.
