# Valid Schema .XSD Changes #

The OAIToolkit distribution package that is available for download contains some changes to the MARCXML schema file. We modified the schema because we found some existing MARC records which did not fit the original MARCXML schema. The modified schema (MARC21slim\_rochester.xsd) was the result of testing performed at the University of Rochester. We have made changes to accommodate the following errors in our MARC records:
  * We have allowed the Leader’s last two characters to contain any value instead of conforming to the original schema.
  * Some MARC records use other separator characters (end of field,  end of subfield) than the standard.
  * Some MARC records do not contain the last modification field (005) or the datestamp does not conform with the standard.`*`

_`*` Note that if the error is a space as the last character, and the rest is valid, then OAIToolkit corrects this error._

If you have legacy records which do not match the schema, you have two options:
  1. Use the Library of Congress schema so that the importer will not put the “bad” MARC records into storage. You will have fewer records that are valid, but all will of them will match the standard.
  1. Create a custom schema that allows small discrepancies from the standard. This will enable you to import all of your records, though some of them will not match the standard.

You can find more information about the usage of standard or custom schema in OAI Toolkit in the ["Converting, Modifying, and Loading"](ConvertModifyload.md) section. If you do decide to use a custom schema, you should copy it into the web application as well.