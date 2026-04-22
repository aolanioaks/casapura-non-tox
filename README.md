# Casa Pura Living

A non-toxic living guide Android app that helps users discover clean, safe alternatives to everyday household products — from water filters and shampoo to bedding and clothing.


## Overview

Casa Pura Living solves a real problem: people who want to eliminate toxins, plastics, and harsh chemicals from their home have no single, organized resource to guide them. This app provides a curated product directory organized by room and category, with a personal swap tracker to measure progress over time.

## Features

- **Browse by Room** — Products organized into 6 categories: Water, Kitchen, Bathroom, Bedroom, Laundry, and Clothing
- **Product Detail** — Each product explains why it's clean, what it replaces, and its certifications (EWG Verified, GOTS Organic, BPA-Free, etc.)
- **Swap Tracker** — Mark products as swapped and track your CPL Score — a percentage showing how non-toxic your home is
- **View Product** — Opens the brand's website directly from the app
- **Filter by Category** — Quickly browse a specific room from the Browse screen

## Screens

| Screen | Description |
|---|---|
| Home | Logo hero, category grid, featured products, live swap score |
| Browse | Full product list with category filter chips |
| Product Detail | Product info, certifications, swap toggle, external link |
| Swap Tracker | Progress bars per category, completed swap checklist |



## Tech Stack

| Component | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material3 |
| Architecture | MVVM (ViewModel + StateFlow) |
| Navigation | Jetpack Navigation Compose |
| HTTP Client | Ktor (with local asset fallback) |
| Local Database | Room |
| Image Loading | Coil |
| Serialization | Kotlin Serialization |



**Data flow:**
- Product catalog is fetched via Ktor from a remote JSON endpoint. If the network is unavailable, the app falls back to a bundled `assets/products.json` file so it always works offline.
- User swap history is stored locally using Room so it persists between sessions.



## Project Structure


app/src/main/java/com/pura/app/
├── data/
│   ├── api/          # Ktor HTTP client
│   ├── db/           # Room database, DAO, Entity
│   ├── model/        # Product data class, ProductCategory enum
│   └── repository/   # PuraRepository
├── ui/
│   ├── components/   # BottomNavBar
│   ├── navigation/   # NavGraph, PuraRoute
│   ├── screens/      # HomeScreen, BrowseScreen, ProductDetailScreen, SwapTrackerScreen
│   └── theme/        # Color, Type, Theme
├── viewmodel/        # PuraViewModel, UI state classes
├── MainActivity.kt
└── PuraApplication.kt


## Author
By Aolani Oaks