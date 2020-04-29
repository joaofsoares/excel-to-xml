# excel-to-xml

This library intend to convert Excel files in XML.

You must use three columns:

First Column is the node.
Second Column is the field.
Third Column is the value.

E.g.

Input:

Person       ID       01
Person       Name     Example
Department   Name     Store
Address      Street   Avenue One

Output:

<Person>
    <ID>01</ID>
    <Name>Example</Name>
</Person>
<Department>
    <Name>Store</Name>
</Department>
<Address>
    <Street>Avenue One</Street>
</Address>

## Compile

sbt compile

## Test

sbt test

## Build Package

sbt package

This command generates a SNAPSHOT, it doesn't contain dependencies:
excel-to-xml_2_13-X.Y.Z-SNAPSHOT.jar

## Build Full Package

sbt assembly

This command generates a full package with dependencies:
excel-to-xml-X.Y.Z.jar
