# Student Management System (Improved)

A Java console application with CRUD operations and CSV file persistence.

## Requirements
- JDK 8 or newer

## Compile
```bash
javac Main.java Student.java StudentService.java FileService.java
```

## Run
```bash
java Main
```

Student records are stored in `students.csv` in the current working directory.

## Improvements
- One application-wide `Scanner`, with line-based parsing and validation.
- Duplicate student ID prevention.
- Full-file persistence after add, update, and delete.
- Temporary-file write followed by atomic replacement where supported.
- In-memory records change only after a successful file save.
- CSV quoting supports commas and double quotes in student names.
- Invalid persisted rows are reported and skipped instead of stopping the whole load.
- Validation: positive ID, non-empty name, age 1-120, marks 0-100.

## Notes
The application stores CSV text; `Serializable` remains on `Student` for compatibility/future use,
but Java object serialization is not used by this CSV persistence implementation.
