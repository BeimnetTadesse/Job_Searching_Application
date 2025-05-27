# 💼 Java Job Searching Application

A desktop application built with **SQLite**, developed as part of an **Advanced Programming** course project. This system allows job seekers and employers to register, post/search for jobs, apply, and manage their profiles — all within an intuitive GUI.

---

## 🚀 Features

* 👨‍💼 **Employer Dashboard**: Post job listings and view applicant details
* 👩‍🎓 **Job Seeker Dashboard**: Browse and apply for available jobs
* 🔐 **Secure Login & Registration**: User authentication with validations
* 📝 **Profile Management**: View and edit personal information
* 💾 **Local SQLite Database**: Simple file-based persistence (included)

---

## 📁 Project Structure

```
FirstJavaFxProject/
├── bin/                # Compiled .class files
├── lib/                # Dependencies
│   └── sqlite-jdbc-3.49.1.0.jar
├── src/                # Java source files
│   ├── app/            # Application entry point (App.java)
│   ├── controller/     # Controllers for UI logic
│   ├── database/       # DAOs and DB handling
│   ├── model/          # Model classes (User, Job, etc.)
│   └── view/           # JavaSwing views
├── jobSearch.db        # SQLite database file
└── README.md           # Project documentation
```

---

## 🛠️ How to Compile & Run

### ✅ Requirements:

* Java 17+
* SQLite JDBC JAR (already included in `lib/`)

### 🧪 Compile

```bash
javac -cp ".:lib/sqlite-jdbc-3.49.1.0.jar" -d bin src/app/*.java src/controller/*.java src/database/*.java src/model/*.java
```

> 💡 On **Windows**, use `;` instead of `:` in the classpath:
> `-cp ".;lib/sqlite-jdbc-3.49.1.0.jar"`

### ▶️ Run

```bash
java -cp ".:lib/sqlite-jdbc-3.49.1.0.jar:bin" app.App
```

---

## 🧩 Technologies Used

* **Java 17**
* **SQLite** (via JDBC)
* **Visual Studio Code**

---

## 👤 Author

**Beimnet Tadesse**
📧 Email: [beimnetasnin@gmail.com](mailto:beimnetasnin@gmail.com)
🐙 GitHub: [@BeimnetTadesse](https://github.com/BeimnetTadesse)

---

## 📄 License

This project was developed for academic purposes.
Feel free to fork, modify, and use it for learning or personal development.

---

