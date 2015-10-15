## Error Handling ##

There are two types of errors. In the first type, the entire file is bad. This can happen when there is a validation error in an XML file. Such files are moved to their corresponding error directory (defined by **-error** and **-error\_xml arguments**). The other kind of error happens when only some records in the file are bad. This can be caused by some internal MARC problems (mainly in legacy MARC records) or invalid characters, or when the MARCXML record does not validate according to the schema file. These records are stored in a file named bad\_records\_in`_` (eg. bad\_records\_in\_marc1.mrc or bad\_records\_in\_marc23.xml). The location of these files is defined by the -error and -error\_xml arguments. All records in these files have some kind of error. The information about the nature and the location of the error is in the log file. The bad records are not imported into the database, and cannot be harvested by OAI harvester.

Some typical log entries and comments about them follow:

_invalid character_

```
INFO – [LIB] OAI Toolkit corrected the MARC record bibs_w_MFHDs01.mrc having the bad character in the Leader field at _ ('\u000E') position: 24. The character is replaced with zero.
```

The MARC record file, which is bibs\_w\_MFHDs01.mrc, does have the bad character in the Leader field as it says. This is basically the invalid character and the OAI Toolkit corrected it at position 24 and replaced it with zero, because the flag **–translate\_leader\_bad\_chars\_to\_zero** field was used while running the convert process. Similarly, the flag **–translate\_nonleader\_bad\_chars\_to\_spaces** could also be used to replace the non-leader bad characters with spaces in the toolkit. In this example, we replaced this character with a space by turning on character replacement with the **–translate\_leader\_bad\_chars\_to\_zero** parameter.

## Weird Characters ##

In the configuration section, we mentioned that sometimes it is a problem that some existing ILSs put characters into MARC records that are invalid in XML 1.0<sup>1</sup> and current MARC standards.<sup>2</sup> These are the so called ASCII control characters, the first 32 characters in the ASCII code table (except tabulation, line feed, and carriage return). The hexadecimal codes of these characters are x01–x08, x0B–x0C, and x0E–x1F. To create valid XML files, we have the following options:
  1. Filter out the records containing these characters. This is the default behavior; you do not have to add any plus parameter. If the OAI Toolkit runs into such a record, it creates a report about it in the log file, and puts it into a separate file in an error directory. The resulting XML file does not contain this record.
  1. Replace weird characters with 0 (number zero) in the Leader and with space elsewhere. This can be done with the **-translate\_leader\_bad\_chars\_to\_zero (replaces the weird characters in the Leader field with 0** and **–translate\_nonleader\_bad\_chars\_to\_spaces (replaces the weird characters in the Non-Leader fields with spaces)** parameters. When these parameters are used, the replacements are logged, so we can see what was changed.

_<sup>1</sup>   http://www.w3.org/TR/REC-xml/#charsets
(2) Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]_

_<sup>2</sup>   The five MARC standards and the MARCXML schema has a contradiction in what can be allowed at the four last character position of Leader. The MARC allows only „4500” while MARCXML allows „4500” and „    ” (four spaces). But none of them allows any other characters.
http://www.loc.gov/marc/bibliographic/bdleader.html
22 - Length of the implementation-defined portion
0 - Number of characters in the implementation-defined portion of a Directory entry_

http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd
> 

&lt;xsd:simpleType name="leaderDataType" id="leader.st"&gt;


> > 

&lt;xsd:restriction base="xsd:string"&gt;


> > > 

&lt;xsd:whiteSpace value="preserve"/&gt;


> > > 

&lt;xsd:pattern value="[\d ]{5}[\dA-Za-z ]{1}[\dA-Za-z]{1}[\dA-Za-z ]{3}(2| )(2| )[\d ]{5}[\dA-Za-z ]{3}(4500|    )"/&gt;



> > 

&lt;/xsd:restriction&gt;



> 

&lt;/xsd:simpleType&gt;


