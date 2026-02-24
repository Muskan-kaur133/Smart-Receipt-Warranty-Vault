# Warranty Vault 

Warranty Vault is an Android application that helps users store and manage product warranties digitally.  
Users can add, edit, delete, search, and sort warranty records easily.

---

##  Features

- Add new warranty items
- Edit existing warranty details
- Delete warranty with confirmation popup
- Search products by name
- Sort by:
  - Expiry Date (Ascending / Descending)
  - Product Name (A–Z / Z–A)
- View detailed warranty information in popup
- Expiry status indicator (Active / Expired)

---

##  Technologies Used

- Java
- Android Studio
- Room Database
- SQLite (used internally by Room)
- MVVM Architecture
- LiveData
- RecyclerView
- ViewModel

---

##  Architecture

This project follows **MVVM Architecture**:

- **Model** → WarrantyItem (Entity)
- **View** → Activities & XML layouts
- **ViewModel** → WarrantyViewModel
- **DAO** → WarrantyDao (Database operations)
- **Room Database** → WarrantyDatabase

---

##  Project Structure

- MainActivity → Displays warranty list
- AddWarrantyActivity → Add new warranty
- EditWarrantyActivity → Edit existing warranty
- WarrantyAdapter → RecyclerView adapter
- WarrantyViewModel → Handles business logic
- WarrantyDao → Database queries
- WarrantyDatabase → Room database instance
- WarrantyItem → Entity class

---

##  How to Run

1. Open project in Android Studio
2. Build the project
3. Run on Emulator or Physical Device

To generate APK:
- Build → Generate App Bundle(s) / APK(s) → Build APK(s)
