# DogsListTD

This is an Android app that persistently stores and displays a collection of dogs and related data..<br>.<br>
The app development was done taking into account reuse of code with generics, maintainability by wrapping the alike classes in packages, use of frameworks from trusted resources and addressing common UI/UX scenarios for Android as permissions request for storage. SVG icons an images were used for a wider devices UI coverage.

### Language: 
Kotlin. Reduced development time: More functionality with less lines of code.

### Architecture: 
MVVM. Makes easier to keep dogs list updated and stored.

### Frameworks: 
**Anko**      "shortcuts" for common Android methods: toast, onClick... <br>
**gson**      Helps with the persistent storage by converting any object into a JSON string.<br>
**lifecycle** Provides ViewModel classes and methods for the MVVM architecture implementation.
    
### Design:
The desing was based on the Android's Material Design colors, button sizes and gestures.<br>
Use of the standard toolbar height.<br>
A fab provides a quick access for the main functionality of the app: Adding a dog.<br>
Back to home button and showing sections name at toolbar help with the navigaion of the app.<br>
Validation of fields before saving to prevent storing empty data.<br>
Collapsible toolbar at dog description to take advantage of the screen space.
