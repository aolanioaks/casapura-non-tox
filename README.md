# Casa Pura Living
 
An Android app that helps users discover non-toxic alternatives to everyday household products.
 
## What It Does
 
Users can browse clean products organized by room category: Water, Kitchen, Bathroom, Bedroom, Laundry, and Clothing. 
Each product includes details on why it's a safer choice and what it replaces. Users can mark products as swapped to track their progress.
 
## Features
 
- Browse products by room category
- View product details, certifications, and why each product is clean
- Mark products as swapped
- Track completed swaps on the Swaps screen
## Tech Stack
 
- **Kotlin** — programming language
- **Jetpack Compose** — UI
- **Ktor** — fetches product data from a JSONBin API
- **Room** — local database that saves swap history
- **Coil** — loads product images
- **MVVM** — app architecture 
## How It Works
 
Products are fetched from a hosted JSON API using Ktor. When a user marks a product as swapped, that record is saved locally using Room so it persists between sessions.

## By Aolani Oaks