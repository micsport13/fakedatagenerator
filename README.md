# Fake Data Generator (FDG)
This tool was created to solve some of the frustrations of seeding databases for testing.  I created this tool to read table definitions from YAML (maybe other formats in the future) and create an in-memory set of data that obeys your database constraints.  The FDG is comprised of several objects:
- Tables
- Schemas
- Rows
- Columns
- Data Types
- Constraints

Each of these objects corresponds as best as possible to their real life database counterparts.  For example, a column is made up of a data type and a schema is comprised of constraints and columns.

# How to create a table
Create a .yml file in your working directory.  The yml file is constructed like this:
```
- name: Members
  schema:
    columns:
      - column:
          name: id
          type:
            name: integer
          constraints:
            - type: not_null
          generator:
            package: number
            method: positive
        table_constraints:
          - type: primary_key
      - column:
          name: first_name
          type:
            name: varchar
            max_length: 50
          generator:
            package: name
            method: firstName
      - column:
          name: last_name
          type:
            name: varchar
            max_length: 50
          generator:
            package: name
            method: lastName
      - column:
          name: email
          type:
            name: varchar
            max_length: 50
          generator:
            package: internet
            method: emailAddress`
````
This table called Members is comprised of 4 columns: Id, first_name, last_name, and email.  Within each column object within the schema, there are 4 main components.  First is the name of the column, which is checked against Database naming conventions (represented by the regex `[A-z]\w+`).  Second is the data type which is dependent upon the data type in question.  Please consult the java documentation for supported parameters but each data type will have a type name and a set of parameters that define the data type.  This will ensure that the data type created by the tool conforms with Database data types without loss of precision upon insert.  Third, is the set of column constraints for the column.  Currently only not null and check constraints are supported for this object.  For table level constraints, like foreign keys or primary keys, see the table_constraints section.  Fourth is the generator, which is related to the netfaker (Link needed) library to generate data for the columns.

Alongside the columns are table_constraints.  It is not required to provide a table constraint but this is a powerful tool for creating data that obeys your table level constraints.  Currently, there are several types that are still being built out but they include Unique, Primary Key, Table Level Constraints (such as ensuring that a column is larger than another column), and Foreign Key constraints.  The state of these constraints is still under development and therefore are not yet stable.
