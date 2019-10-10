# DogsListTD

This is an Android app that persistently stores and displays a collection of dogs and related data.

### Language: 
Kotlin. Reduced development time: More functionality with less lines of code.

### Architecture: 
MVVM. Makes easier to keep dogs list updated and stored.

### Frameworks: 
**Anko**      "shortcuts" for common Android methods: toast, onClick... 
<br>
**gson**      Helps with the persistent storage by converting any object into a JSON string.
<br>
**lifecycle** Provides ViewModel classes and methods for the MVVM architecture implementation.
    
### Design:
The desing was based on the Android's Material Design colors, button sizes and gestures.
Use of the standard toolbar height.
A fab icon provides a quick access for the main functionality of the app.
Back to home button and showing sections name at toolbar help with the navigaion of the app.
Validation of fields before saving to prevent storing empty data.
Collapsible toolbar at dog description to take advantage of the screen space.
