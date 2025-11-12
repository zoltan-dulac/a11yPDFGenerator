# 🧩 a11yPDFGenerator

**a11yPDFGenerator** is a command-line Java application that converts well-formed HTML into **accessible, tagged PDFs (PDF/UA)** using the [OpenHTMLtoPDF](https://github.com/danfickle/openhtmltopdf) library.

This tool is designed for developers and accessibility engineers who need reproducible, high-quality PDF exports that preserve document structure, language metadata, and custom fonts — essential for meeting WCAG 2.x / PDF/UA compliance.

---

## ✨ Features

- Converts HTML + CSS into PDF/UA-compliant PDFs  
- Preserves document structure, language, and title metadata  
- Supports embedded and local fonts (`@font-face`)  
- Fully self-contained (builds to a single runnable `.jar`)  
- Runs on any platform with Java 17+  
- Open source under Apache 2.0

---

## 🧱 Project structure

```
a11yPDFGenerator/
├── pom.xml                           # Maven build file and dependencies
├── README.md                         # You’re reading it!
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── useragentman/
│       │           └── html2pdf/
│       │               └── a11yPDFGenerator.java   # Main CLI app
│       └── resources/
│           └── fonts/
│               └── OpenSans-Regular.ttf            # Default embedded font
└── examples/
    └── test.html                    # Sample HTML input file
```

---

## ⚙️ Requirements

- **Java 17** or newer (e.g., OpenJDK 17, Temurin 17)
- **Apache Maven 3.8+**

Check versions:

```bash
java -version
mvn -version
```

---

## 🧩 Installation and build

Clone and build the project with Maven:

```bash
git clone https://github.com/<your-username>/a11yPDFGenerator.git
cd a11yPDFGenerator
mvn clean package
```

This produces a runnable shaded JAR in:

```
target/a11yPDFGenerator-1.0.0.jar
```

---

## 🚀 Usage

Basic syntax:

```bash
java -jar target/a11yPDFGenerator-1.0.0.jar <input.html> <output.pdf> [--lang <language>] [--title "<document title>"]
```

### Example

```bash
java -jar target/a11yPDFGenerator-1.0.0.jar examples/test.html test.pdf --lang en --title "Accessibility Test Document"
```

Output:

```
baseUri is file:/Users/you/git/a11yPDFGenerator/examples/
✅ Generated accessible PDF: /Users/you/git/a11yPDFGenerator/test.pdf
```

---

## 🧠 Command-line options

| Option | Description |
|---------|--------------|
| `<input.html>` | The HTML source file to convert. Must be valid UTF-8. |
| `<output.pdf>` | Destination PDF file. Will be overwritten if it exists. |
| `--lang <code>` | Sets the PDF’s document language (default: `en`). |
| `--title "<title>"` | Sets the PDF’s title metadata. |

---

## 🧩 How it works

`a11yPDFGenerator.java`:

1. Reads an HTML file into memory.
2. Uses `PdfRendererBuilder` from `openhtmltopdf-pdfbox` to render a PDF/UA-compliant document.
3. Registers `OpenSans-Regular.ttf` as a default embedded font.
4. Applies PDF metadata (title, language, and subject).
5. Saves a fully tagged, accessible PDF.

---

## 🧪 Example HTML

`examples/test.html` demonstrates a minimal accessible HTML input:

```html
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta name="generator" content=
"HTML Tidy for HTML5 for Apple macOS version 5.8.0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Accessibility Test Document</title>

<style>
    @font-face {
      font-family: "BodyFont";
      src: url("../src/main/resources/OpenSans-Regular.ttf");
    }

    * {
        font-family: 'BodyFont';
    }

    body {
      font-family: 'BodyFont';
      line-height: 1.5;
      color: #222;
      margin: 2em;
    }
    h1, h2 {
      color: #004080;
    }
    figure {
      text-align: center;
      margin: 1.5em 0;
    }
    figcaption {
      font-style: italic;
      font-size: 0.9em;
    }
    table {
      border-collapse: collapse;
      width: 100%;
      margin-top: 1em;
    }
    th, td {
      border: 1px solid #888;
      padding: 6px;
    }
    th {
      background-color: #f2f2f2;
    }
    caption {
      font-weight: bold;
      margin-bottom: 0.5em;
    }
</style>
</head>
<body>


  <header>
    <h1>Accessibility PDF Test</h1>
    <p>This is a test HTML file to verify PDF/UA tagging using <code>openhtmltopdf</code>.</p>
  </header>

  <main>
    <h2>1. Image with Alt Text</h2>
    <figure>
      <img src="JPEG_example_flower.jpg"
           alt="A close-up photo of a pink flower with a yellow center." width="300" />
      <figcaption>Example image included for accessibility testing.</figcaption>
    </figure>

    <h2>2. Ordered List</h2>
    <ol>
      <li>Check that headings are tagged properly.</li>
      <li>Ensure alt text appears in the PDF structure tree.</li>
      <li>Verify reading order is correct.</li>
    </ol>

    <h2>3. Simple Table</h2>
    <table>
      <caption>Fruit Nutritional Values</caption>
      <thead>
        <tr>
          <th scope="col">Fruit</th>
          <th scope="col">Calories</th>
          <th scope="col">Vitamin C (mg)</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">Apple</th>
          <td>95</td>
          <td>8.4</td>
        </tr>
        <tr>
          <th scope="row">Orange</th>
          <td>62</td>
          <td>70</td>
        </tr>
        <tr>
          <th scope="row">Banana</th>
          <td>105</td>
          <td>10</td>
        </tr>
      </tbody>
    </table>

    <h2>4. Link Example</h2>
    <p>
      Learn more about <a href="https://www.w3.org/WAI/">W3C Web Accessibility Initiative (WAI)</a>.
    </p>
  </main>

  <footer>
    <p>Footer Information: Document generated for testing <strong>a11yPDFGenerator</strong>.</p>
  </footer>

</body>
</html>

```

---

## 🧾 Output

- The generated PDF contains tagged structure for headings and paragraphs.  
- Document metadata (language, title) is included in the PDF’s properties.  
- The embedded OpenSans font ensures consistent text rendering across platforms.

---

## 🧩 Troubleshooting

| Problem | Cause | Fix |
|----------|--------|-----|
| `Font list is empty` | Font path invalid or missing font file | Ensure fonts exist in `src/main/resources/fonts/` and registered via `builder.useFont(...)` |
| `Content is not allowed in prolog` | File has BOM or invalid XML characters | Clean with [HTML Tidy](https://www.html-tidy.org/) or re-save as UTF-8 without BOM |
| `Index 0 out of bounds` | No usable font loaded | Use a known-good TTF (e.g., OpenSans or Liberation Sans) |

---

## 🧑‍💻 Contributing

1. Fork this repository  
2. Create a feature branch (`git checkout -b feature/my-improvement`)  
3. Commit and push changes  
4. Submit a Pull Request

Issues and feature requests are welcome!

---

## 📜 License

Licensed under the **Apache License 2.0**.  
See the [LICENSE](LICENSE) file for details.

---

## 🧭 Credits

- [OpenHTMLtoPDF](https://github.com/danfickle/openhtmltopdf) — HTML → PDF engine  
- [Apache PDFBox](https://pdfbox.apache.org/) — PDF creation backend  
- [Open Sans](https://fonts.google.com/specimen/Open+Sans) — default embedded font  
- Created by [Zoltan Hawryluk](https://useragentman.com/)  
  to advance accessible document generation workflows.
