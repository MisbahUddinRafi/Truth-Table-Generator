# 🎓 Truth Table Generator

> A powerful, intuitive JavaFX application for generating truth tables from Boolean expressions with support for infix-to-postfix conversion and real-time validation.

<div align="center">

[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-blue)]()
[![Language](https://img.shields.io/badge/Language-Java%2011%2B-orange)]()
[![Framework](https://img.shields.io/badge/Framework-JavaFX-red)]()
[![License](https://img.shields.io/badge/License-MIT-green)]()
[![Status](https://img.shields.io/badge/Status-Active%20Development-brightgreen)]()

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [User Guide](#user-guide)
- [Installation & Setup](#installation--setup)
- [How It Works](#how-it-works)
- [Future Enhancements](#future-enhancements)
- [About the Developer](#about-the-developer)

---

## 🎯 Overview

**Truth Table Generator** is an educational desktop application built with JavaFX that simplifies the creation and visualization of truth tables for Boolean expressions. Whether you're learning digital logic, preparing for exams, or working on logic design projects, this tool provides an intuitive interface to convert complex Boolean expressions into comprehensive truth tables.

### 📚 Background

This project was developed as a learning initiative combining:
- Java & JavaFX knowledge from academic coursework (2nd Semester)
- Preparation for a Digital Logic Design course
- Practical implementation of core Boolean algebra concepts

The philosophy is **simplicity** — in interface, functionality, and codebase design.

---

## ✨ Features

- **🔤 Boolean Expression Input**: Enter expressions in infix notation with support for primary Boolean operators.
- **📊 Automatic Truth Table Generation**: Generates minimal, optimized truth tables with n+1 columns (n variables + 1 output).
- **🔄 Infix to Postfix Conversion**: Displays RPN notation for better understanding of expression evaluation.
- **✅ Real-time Validation**: Validates expressions before processing to prevent errors.
- **🎨 Clean, Intuitive UI**: User-friendly interface with convenient operator buttons and clear results display.
- **⚡ Complex Expression Support**: Handles parentheses and correct operator precedence automatically.

### 📈 Input & Output Specifications

**Input Format:**
- Boolean expression in infix notation with maximum 10 variables (A-Z, a-z)
- **Supported Operators**: AND (`.`), OR (`+`), NOT (`'`), and parentheses `()` for grouping
- Auto-inserts implicit AND operators between adjacent operands (e.g., `AB` → `A.B`)

**Output:**
- Truth table with n columns for variables + 1 column for expression output (2<sup>n</sup> rows)
- Postfix (RPN) notation of the Boolean expression

---

## 📸 Screenshots

<div align="center">

### Main Interface - Expression Input

<img width="500" alt="Main Page - Expression Input" src="https://github.com/user-attachments/assets/745be9b4-075f-44ab-a446-001eb3e72f5d" />

*Enter your Boolean expression with convenient operator buttons*

### Results Page - Truth Table & Postfix Display

<img width="500" alt="Results Page - Truth Table" src="https://github.com/user-attachments/assets/f79fec0c-2d97-4d6c-9b3b-ea20b3525605" />

*View the complete truth table with infix and postfix notations*

### Additional Interface View

<img width="500" alt="Interface View" src="https://github.com/user-attachments/assets/131a5df3-d938-4096-be96-48cf1dfe5059" />

*Comprehensive application interface*

</div>

---

## 📖 User Guide

**1. Launch** → Open the main input page  
**2. Enter Expression** → Type directly (e.g., `A+B.C'`) or use operator buttons  
**3. Generate** → Click **Generate** button to validate and create truth table  
**4. View Results** → See original expression, postfix notation, and complete truth table  
**5. Return** → Click **Back to Main** to modify and retry  

**Tips:**
- Use consistent variable names (A, B, C or a, b, c)
- Enclose complex sub-expressions in parentheses
- Operator precedence: NOT `'` > AND `.` > OR `+`
- Missing AND operators are inserted automatically

---

## 🔧 Installation & Setup

### Prerequisites
- **Java**: JDK 11+ ([Download](https://www.oracle.com/java/technologies/downloads/))
- **JavaFX SDK**: 11+ ([Download](https://gluonhq.com/products/javafx/))
- **IDE**: IntelliJ IDEA (Community or Ultimate)

### Setup in IntelliJ IDEA

1. **Clone/Download** the project and open in IntelliJ

2. **Configure JDK**:
   - Go to **File** → **Project Structure** → **Project**
   - Set **SDK**: Select or download JDK 11+
   - Set **Language level**: 11+

3. **Add JavaFX Library**:
   - Download JavaFX SDK and note the path (e.g., `C:\javafx-sdk-21`)
   - Go to **File** → **Project Structure** → **Libraries**
   - Click **+** → **Java** → Select JavaFX SDK folder
   - Name it `javafx-sdk` and click **OK**

4. **Configure Module**:
   - Go to **File** → **Project Structure** → **Modules** → **Dependencies**
   - Add the library: Click **+** → **Library** → Select `javafx-sdk`
   - Click **Apply** and **OK**

5. **Set VM Options**:
   - Go to **Run** → **Edit Configurations**
   - Create/Edit configuration for Main class
   - Under **VM options**, add:
     ```
     --module-path C:\javafx-sdk-21\lib --add-modules javafx.controls,javafx.fxml
     ```
     (Replace path with your JavaFX SDK location)

6. **Set Main Class**:
   - In Run Configuration, set **Main class** to: `utilities.Main`

7. **Run**: Click **Run** button or press **Shift+F10**

### Project Structure
```
Truth Table Generator/
├── src/
│   ├── controllers/
│   │   ├── MainPageController.java
│   │   ├── ResultPageController.java
│   │   └── AboutUsController.java
│   ├── utilities/
│   │   ├── Main.java (Entry point)
│   │   ├── TruthTableGeneratorHelper.java (Core logic)
│   │   └── ErrorHandler.java
│   └── view/
│       ├── mainPage.fxml
│       ├── resultPage.fxml
│       ├── aboutUs.fxml
│       ├── Styling.css
│       └── images/
├── README.md
└── Truth Table Generator.iml
```

---

## ⚙️ How It Works

1. **Expression Validation**: Input is normalized by inserting implicit AND operators (e.g., `AB` → `A.B`)
2. **Infix to Postfix**: Uses the Shunting Yard Algorithm with precedence: NOT (3) > AND (2) > OR (1)
3. **Truth Table Generation**: Extracts variables, generates all 2^n input combinations, and evaluates the postfix expression for each
4. **Stack-based Evaluation**: Postfix expression is evaluated using a stack for each input combination

### Core Components
- **TruthTableGeneratorHelper.java**: Expression validation, conversion algorithms, table generation
- **Controllers**: UI interactions and scene management
- **Main.java**: Application entry point

---

## � Future Enhancements

- **Dual Expression Comparison**: Generate and compare truth tables for equivalence verification
- **Additional Operators**: XOR, XNOR, NAND, NOR support
- **Expression Simplification**: Automatic Boolean algebra minimization and Karnaugh Maps
- **Export Features**: PDF, CSV, Excel export capabilities
- **Educational Tools**: Step-by-step visualization, interactive learning mode, expression history
- **UI Enhancements**: Dark mode, syntax highlighting, drag-and-drop support

---

## 👨‍💼 About the Developer

<div align="center">

### **Md. Misbah Uddin Rafi**

🎓 **Computer Science & Engineering (CSE)**  
BUET - Bangladesh University of Engineering and Technology  
**Student ID:** 2305069

---

This project represents a passion for combining academic learning in Java/JavaFX with practical application of Digital Logic Design concepts. It demonstrates proficiency in:

✅ Object-Oriented Programming (Java)  
✅ GUI Development (JavaFX)  
✅ Algorithm Implementation (Infix-to-Postfix, Expression Parsing)  
✅ Boolean Algebra & Digital Logic  
✅ Software Design & User Experience  

**Created**: As preparation for advanced coursework in Digital Logic Design  
**Purpose**: Educational tool to bridge theoretical concepts with practical implementation

</div>

---

## 📜 License

This project is open source and available under the MIT License.

---

<div align="center">

### ⭐ If you find this project helpful, please consider giving it a star!

**Built with ❤️ using Java & JavaFX**

[Report Issues](https://github.com) | [Suggest Features](https://github.com) | [Documentation](https://github.com)

</div> 
