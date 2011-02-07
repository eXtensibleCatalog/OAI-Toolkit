/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.marc4j.Constants;
import org.marc4j.MarcException;
import org.marc4j.MarcPermissiveStreamReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;
import org.marc4j.converter.CharConverter;
import org.marc4j.converter.impl.AnselToUnicode;
import org.marc4j.converter.impl.Iso5426ToUnicode;
import org.marc4j.converter.impl.Iso6937ToUnicode;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.LeaderImpl;
import org.marc4j.marc.impl.SubfieldImpl;

import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;
import info.extensiblecatalog.OAIToolkit.importer.importers.IImporter;
import info.extensiblecatalog.OAIToolkit.importer.statistics.ConversionStatistics;
import info.extensiblecatalog.OAIToolkit.importer.statistics.LoadStatistics;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;


/**
 * Convert MARC records to MARCXML
 *
 * @author Király Péter pkiraly@tesuji.eu
 */
public class Converter {

        private static String library_convertlog = "librarian_convert";
        private static String programmer_log = "programmer";

	//private static final Logger logger = Logging.getLogger();
        private static final Logger libconvertlog = Logging.getLogger(library_convertlog);
        private static final Logger prglog = Logging.getLogger(programmer_log);

	/** The range of non allowable characters in XML 1.0 (ASCII Control Characters) */
	private static final String WEIRD_CHARACTERS =
		"[\u0001-\u0008\u000b-\u000c\u000e-\u001f]";

	/** Regex Pattern for the non allowable characters in XML 1.0 (ASCII
	 * Control Characters) */
	private static final Pattern WEIRD_CHARACTERS_PATTERN =
		Pattern.compile(WEIRD_CHARACTERS);

	/** encoding of the MARC file */
	//private String encoding = "UTF-8";

	/**
	 * Converts <encoding> to UTF-8 Valid encodings are: MARC8, ISO5426, ISO6937
	 */
	private String convertEncoding = null;

	/** perform Unicode normalization */
	private boolean normalize = true;

	/** How many records can an xml file contain? */
	private int splitSize = 10000;

	/** The directory that the toolkit moves records into
	 * when there is a processing error for that file. */
	private String errorDir;

	/** The file, where to the application writes out bad records */
	private File badRecordFile;

	private boolean doIndentXml = false;

	/** create XML 1.1 */
	private boolean createXml11 = false;

	/** Translate the Bad Characters in the Leader (1-8, B-C, E-1F hexadecimal codes)
	 * to zeros */
	private boolean translateLeaderBadCharsToZero = false;

        /** Translate the Bad Characters in the (Non-Leader)Control and Data Fields (1-8, B-C, E-1F hexadecimal codes)
	 * to spaces */
	private boolean translateNonleaderBadCharsToSpaces = false;

	private String controlNumberOfLastReadRecord = null;

	/**
	 * The {@link Modifier} which apply XSLT transformation over MARCXML, to
	 * modify records
	 */
	private Modifier modifier = null;

	/**
	 * Record importer ({@link IImporter}), which import MARCXML to
	 * database. This is used here only if the <code>-load</code> and
	 * <code>-production</code> flags are turned on.
	 */
	private IImporter recordImporter = null;

	private LoadStatistics loadStatistics = null;

	private boolean ignoreRepositoryCode = false;

	private String defaultRepositoryCode = null;

	private MarcWriter badRecordWriter = null;

	private File currentMarcFile = null;

	public Converter() {
	}

	public void convert(String marcFile, String xmlFile) throws Exception {
		convert(new File(marcFile), new File(xmlFile));
	}

	/**
	 * Convert MARC format to XML
	 *
	 * @param marcFile
	 *            The MARC file
	 * @param xmlFile
	 *            The output MARCXML file
	 * @return The number of records converted
	 * @throws Exception
	 */
	public ConversionStatistics convert(File marcFile, File xmlFile)
			throws Exception {

		ConversionStatistics statistics = new ConversionStatistics();
		currentMarcFile = marcFile;
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(marcFile);
		} catch (FileNotFoundException e) {
			throw new Exception("File Not Found error: " + marcFile + ", "
					+ e.getMessage());
		}
		long fileSize = marcFile.length();
		MarcPermissiveStreamReader reader = null;
		boolean permissive      = true;
		boolean convertToUtf8   = true;
		reader = new MarcPermissiveStreamReader(inputStream, permissive, convertToUtf8);

		MarcXmlWriter writer = null;
		if(recordImporter == null) {
			writer = getWriter(xmlFile, 0);
		} else {
			recordImporter.setCurrentFile(marcFile.getName() + ".xml");
			loadStatistics = new LoadStatistics();
		}
		badRecordWriter = getBadRecordWriter(marcFile);

		Record record = null;
		/* the invalid characters
		 * '\u0001' - in the end of the Leader: 450
		 * '\u001F' - as subfield separator
		 */

		/** do the record have invalid characters? */
		boolean hasInvalidChars;

		/** the index of an invalid character */
		//int invalidCharIndex;

		/** the weird character matcher */
		Matcher matcher;

		/** record counter */
		int counter = 0;

		/** the previous percent value */
		int prevPercent = 0;

		/** the percent of imported records in the size of file */
		int percent;
		try {
			while (reader.hasNext()) {

				try {
					record = reader.next();
				} catch (MarcException me) {
					libconvertlog.error("[LIB] " + ExceptionPrinter.getStack(me) + "\n");
							//+ " \n \t The control number of the last successfully "
							//+ "read record is '"
							//+ controlNumberOfLastReadRecord + "'\n");
                    //libconvertlog.error("[LIB] " + "The control number of this record is" + record.getControlNumber() + "\n");
                    //libconvertlog.error("[LIB] " + "The record buffer is" + record.toString() + "\n");
                                        statistics.addInvalid();
					continue;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				counter++;
				controlNumberOfLastReadRecord = record.getControlNumber();

                				//if(!reader.isCorrupted()) {
				if (Constants.MARC_8_ENCODING.equals(convertEncoding)) {
					record.getLeader().setCharCodingScheme('a');
				}

				hasInvalidChars = false;
				matcher = WEIRD_CHARACTERS_PATTERN.matcher(record.toString());
				if(matcher.find()) {
					hasInvalidChars = doReplacements(record, matcher);
				}
				if(!hasInvalidChars) {

					//if(!ignoreRepositoryCode) {
						//merge003and001(record);
					//}

					if(modifier != null) {
						String xml = modifier.modifyRecord(record, false);
						//System.out.println(xml);
						try {
							Record newRecord = MARCRecordWrapper.MARCXML2Record(xml);
							if(newRecord == null){
								prglog.error("[PRG] Error occured when transforming record "
									+ marcFile.getName() + "#" + record.getControlNumber());
							} else {
								record = newRecord;
							}
						} catch(Exception e) {
							e.printStackTrace();
							prglog.error("[PRG] Error on record " + marcFile.getName() + "#" + record.getControlNumber()
									+ " " + ExceptionPrinter.getStack(e));
							System.out.println(xml);
						}
					}

					if(recordImporter == null) {
						writer.write(record);
					} else {
						List<ImportType> typeList = recordImporter.importRecord(record, false);
						loadStatistics.add(typeList);
						if(typeList.contains(ImportType.INVALID)) {
							recordImporter.writeBadRecord(record);
						}
					}

					// close previous, open new
					if(recordImporter == null
						&& 0 < splitSize
						&& statistics.getConverted() > 0
						&& 0 == statistics.getConverted() % splitSize)
					{
						writer.close();
						writer = getWriter(xmlFile, statistics.getConverted());
					}
					statistics.addConverted();
				} else {
					prglog.error("[PRG] INVALID " + record.getControlNumber());
					statistics.addInvalid();
					
					badRecordWriter.write(record);
				}

				if((0 == counter % 100)){
					System.out.print('.');
					if(reader.hasNext()) {
						try {
							if(inputStream != null && inputStream.available() != 0) {
								percent = (int)((fileSize - inputStream.available())
									* 100 / fileSize);
								if((0 == percent % 10) && percent != prevPercent) {
									System.out.println(" (" + percent + "%)");
									prevPercent = percent;
								}
							}
						} catch(IOException e) {
							e.printStackTrace();
							prglog.error("[PRG] " + ExceptionPrinter.getStack(e));
						}
					}
					//System.gc();
				}
			}
		} finally {
			if(writer != null) {
				writer.close();
			}
			badRecordWriter.close();
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(0 == statistics.getInvalid()) {
				boolean deleted = badRecordFile.delete();
				if(!deleted) {
					prglog.error("[PRG] Unable to delete bad records file: " + badRecordFile);
				}
			}
		}
		libconvertlog.info("[PRG] " + statistics.toString());
                prglog.info("[PRG] " + statistics.toString());
		return statistics;
	}

	private OutputStream getOutputStream(File xmlFile, int counter)
			throws Exception {
		OutputStream out = null;
		if (null != xmlFile) {
			try {
				if(0 == counter) {
					out = new FileOutputStream(xmlFile.getAbsolutePath());
				} else {
					String newFilename = xmlFile.getAbsolutePath()
										.replace(".xml", "_" + counter + ".xml");
                                        libconvertlog.info("[PRG] Continue " + xmlFile.getName() + " to "
							+ newFilename + "\n\n");
					prglog.info("[PRG] Continue " + xmlFile.getName() + " to "
							+ newFilename + "\n\n");
					out = new FileOutputStream(newFilename);
				}
			} catch (FileNotFoundException e) {
				throw new Exception("File Not Found error: " + xmlFile + ", "
						+ e.getMessage());
			}
		} else {
			out = System.out;
		}
		return out;
	}

	private MarcXmlWriter getWriter(File xmlFile, int counter) throws Exception {

		OutputStream out = getOutputStream(xmlFile, counter);

		MarcXmlWriter writer = null;
		writer = new MarcXmlWriter(out, "UTF8", doIndentXml); //, createXml11);
		//writer.setIndent(doIndentXml);
		//writer.setCreateXml11(createXml11);
		setConverter(writer);
		if (normalize == true) {
			writer.setUnicodeNormalization(true);
		}

		return writer;
	}

	private MarcWriter getBadRecordWriter(File marcFile) {
		badRecordFile = new File(errorDir,
				ImporterConstants.ERROR_FILE_PREFIX + marcFile.getName());
		try {
			return new MarcStreamWriter(new FileOutputStream(badRecordFile));
		} catch(FileNotFoundException e) {
			prglog.error("[PRG] Error occured in opening file " + badRecordFile
					+ ". The toolkit won't able to write out bad records to"
					+ " distinct file. Cause: " + e);
			return null;
		}
	}

	private void setConverter(MarcXmlWriter writer) throws Exception {

		if (null != convertEncoding) {
			CharConverter charconv = null;
			try {
				if (convertEncoding != null) {
					if (Constants.MARC_8_ENCODING.equals(convertEncoding)) {
						charconv = new AnselToUnicode();
					} else if (Constants.ISO5426_ENCODING.equals(convertEncoding)) {
						charconv = new Iso5426ToUnicode();
					} else if (Constants.ISO6937_ENCODING.equals(convertEncoding)) {
						charconv = new Iso6937ToUnicode();
					} else {
						throw new Exception("Unknown character set");
					}
					writer.setConverter(charconv);
				}
			} catch (javax.xml.parsers.FactoryConfigurationError e) {
				e.printStackTrace();
				throw new Exception(e);
			} catch (MarcException e) {
				e.printStackTrace();
				throw new Exception("There is a problem with character conversion: "
					+ convertEncoding + " " + e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}

	private RecordLine getLineOfRecord(String recordString, int position) {
		return new RecordLine(recordString, position);
	}

	public void setEncoding(String encoding) {
		//this.encoding = encoding;
	}

	public void setConvertEncoding(String convertEncoding) {
		if("MARC8".equals(convertEncoding) ||
			"ISO5426".equals(convertEncoding) ||
			"ISO6937".equals(convertEncoding)) {
			this.convertEncoding = convertEncoding;
		} else if("none".equals(convertEncoding)) {
			this.convertEncoding = null;
		}
	}


	/**
	 * Replace invalid characters in a record
	 * @param record
	 * @param line
	 */
	@SuppressWarnings("unchecked")
	private void modifyRecord(Record record, RecordLine line, String invalidCharacter, String badCharacterLocator) {


            // change LEADER
                if(translateLeaderBadCharsToZero == true) {
                    String leaderReplaced = "The character is replaced with zero.\n";
                    if(line.getLine().startsWith("LEADER")) {
			record.setLeader(new LeaderImpl(
					record.getLeader().toString()
						.replaceAll(WEIRD_CHARACTERS, "0")));

                 libconvertlog.info("[LIB] OAI Toolkit corrected the MARC record #" +
                         record.getControlNumber() +
                         "having the bad character in the Leader field at " +
                         invalidCharacter + "." +
                         leaderReplaced);
                 /*logger.warn( "[LIB] The MARC record " +
                            (currentMarcFile.getName()) +
                            "#" + record.getControlNumber() +
                            " is corrupted." +
                            " Cause: invalid character " +
                            invalidCharacter + ", " +
                            "." + ApplInfo.LN +
                            leaderReplaced +
                            badCharacterLocator) ;*/
                    }
                }

                // change control fields
                if(translateNonleaderBadCharsToSpaces == true) {
                    String NonleaderReplaced = "The character is replaced with space.\n";
                    if(line.getLine().startsWith("00")) {
                            String tag = line.getLine().substring(0,3);
                            ControlField fd = (ControlField)record.getVariableField(tag);
                            fd.setData(fd.getData().replaceAll(WEIRD_CHARACTERS, " "));
                            record.addVariableField(fd);

                     libconvertlog.info("[LIB] OAI Toolkit corrected the MARC record #" +
                         record.getControlNumber() +
                         "having the bad character in the Non-Leader field at" +
                         invalidCharacter + "." +
                         NonleaderReplaced);
                     /*logger.warn( "[LIB] The MARC record " +
                                (currentMarcFile.getName()) +
                                 "#" + record.getControlNumber() +
                                 " is corrupted." +
                                " Cause: invalid character " +
                                invalidCharacter + ", " +
                                "." + ApplInfo.LN +
                                NonleaderReplaced +
                                badCharacterLocator) ; */

                    // change data fields
                    } else if (line.getLine().startsWith("LEADER") == false){
                            String tag = line.getLine().substring(0,3);
                            DataField fd = (DataField)record.getVariableField(tag);
                            record.removeVariableField(fd);

                            // indicators
                            fd.setIndicator1(String.valueOf(fd.getIndicator1())
                                            .replaceAll(WEIRD_CHARACTERS, " ").charAt(0));
                            fd.setIndicator2(String.valueOf(fd.getIndicator2())
                                            .replaceAll(WEIRD_CHARACTERS, " ").charAt(0));

                            // subfields
                            List<Subfield> sfs = (List<Subfield>)fd.getSubfields();
                            List<Subfield> newSfs = new ArrayList<Subfield>();
                            List<Subfield> oldSfs = new ArrayList<Subfield>();
                            // replace the subfields' weird characters
                            for(Subfield sf : sfs) {
                                    oldSfs.add(sf);
                                    char code;
                                    if(WEIRD_CHARACTERS_PATTERN.matcher(String.valueOf(sf.getCode())).find()) {
                                            code = String.valueOf(sf.getCode())
                                                    .replaceAll(WEIRD_CHARACTERS, " ").charAt(0);
                                    } else {
                                            code = sf.getCode();
                                    }
                                    newSfs.add(new SubfieldImpl(
                                                    code,
                                                    sf.getData().replaceAll(WEIRD_CHARACTERS, " ")));
                            }
                            // remove old subfields ...
                            for(Subfield sf : oldSfs) {
                                    fd.removeSubfield(sf);
                            }
                            // ... and add the new ones
                            for(Subfield sf : newSfs) {
                                    fd.addSubfield(sf);
                            }
                            record.addVariableField(fd);

                         libconvertlog.info("[LIB] OAI Toolkit corrected the MARC record #" +
                         record.getControlNumber() +
                         "having the bad character in the Non-Leader field at" +
                         invalidCharacter + "." +
                         NonleaderReplaced);
                         /*logger.warn( "[LIB] The MARC record " +
                                    (currentMarcFile.getName()) +
                                     "#" + record.getControlNumber() +
                                     " is corrupted." +
                                    " Cause: invalid character " +
                                    invalidCharacter + ", " +
                                    "." + ApplInfo.LN +
                                    NonleaderReplaced +
                                    badCharacterLocator) ;*/
                    }
            }
        }

	/**
	 * Merge 003 and 001 as the record's new Control Number
	 * @param record
	 */
	private void merge003and001(Record record) {
		ControlField collection = (ControlField)record.getVariableField("003");
                ControlField fd = (ControlField)record.getVariableField("001");
                if (fd !=null) {
                    String newID = (collection != null)
                            ? collection.getData() + record.getControlNumber()
                            : (defaultRepositoryCode != null)
                                    ? defaultRepositoryCode + record.getControlNumber()
                                    : null;
                    if(newID != null) {
                            fd.setData(newID);
                            record.addVariableField(fd);
                    }
                }
                else {
                    libconvertlog.error("[LIB] The control field 001 is missing in the record. " +
                            "The file which it belongs to is: " +
                            currentMarcFile.getName() +
                            "Please correct these errors and run the Toolkit again." +
                            "\n The whole record is as follows:\n" +
                            record.toString());
                }
	}

	/**
	 * Handle weird character replacements. Collect the location of such
	 * caracters, call the character replacement method
	 * ({@link #modifyRecord(Record, RecordLine)}), log the locations.
	 * @param record The marc record object
	 * @param matcher The regex matcher, which contains the locations
	 * @return true if the record still contains invalid characters,
	 * otherwise false
	 */
	private boolean doReplacements(Record record, Matcher matcher) {
		boolean hasInvalidChars = true;
		String recordString = record.toString();

		//invalidCharIndex = matcher.start();
		List<Integer> invalidCharsIndex = new ArrayList<Integer>();
		do {
			invalidCharsIndex.add(matcher.start());
		} while(matcher.find());
		StringBuffer badCharLocator = new StringBuffer();
		List<String> invalidChars = new ArrayList<String>();
		for(Integer i : invalidCharsIndex) {
			RecordLine line = getLineOfRecord(recordString, i);
			badCharLocator.append(line.getErrorLocation()).append(ApplInfo.LN);
			invalidChars.add(line.getInvalidChar()+ " ("
					+ line.getInvalidCharHexa() + ")" + " position: " + i);
                        String invalidCharacter = (line.getInvalidChar() + " (" + line.getInvalidCharHexa() + ")" + "position: " + i);
                        String badCharacterLocator = (line.getErrorLocation() + ApplInfo.LN);
                        if(translateLeaderBadCharsToZero == true || translateNonleaderBadCharsToSpaces == true) {
                            modifyRecord(record, line, invalidCharacter, badCharacterLocator);
			}
		}
                String replaced = "The character is replaced with space or zeros.\n" ;
		if(translateLeaderBadCharsToZero == false && translateNonleaderBadCharsToSpaces == false) {
			if(null != badRecordWriter) {
				badRecordWriter.write(record);
			}
			replaced = "";
			hasInvalidChars = true;
		} else {
			matcher = WEIRD_CHARACTERS_PATTERN.matcher(record.toString());
			hasInvalidChars = matcher.find() ? true : false;
		}
		StringBuffer logEntry = new StringBuffer();
		logEntry.append("[LIB] The MARC record #")
			.append(currentMarcFile.getName())
			.append("#").append(record.getControlNumber())
			.append(" is corrupted.")
			.append(" Cause: invalid character ")
			.append(TextUtil.join(invalidChars, ", "))
			.append("." + ApplInfo.LN)
			.append(replaced)
			.append(badCharLocator);

		if(translateLeaderBadCharsToZero == false && translateNonleaderBadCharsToSpaces == false) {
			prglog.error("[PRG] " + logEntry.toString());
		}
		return hasInvalidChars;
	}

	public String getSettings() {
		StringBuffer settings = new StringBuffer();
		settings.append("Converter settings: ");
		settings.append("createXml11: ").append(createXml11);
                settings.append(", translateLeaderBadCharsToZero: ")
                        .append(translateLeaderBadCharsToZero);
                settings.append(", translateNonleaderBadCharsToSpaces: ")
                        .append(translateNonleaderBadCharsToSpaces);
		return settings.toString();
	}

	public void setNormalize(boolean normalize) {
		this.normalize = normalize;
	}

	public int getSplitSize() {
		return splitSize;
	}

	public void setSplitSize(int splitSize) {
		this.splitSize = splitSize;
	}

	public void setErrorDir(String errorDir) {
		this.errorDir = errorDir;
	}

	public void setDoIndentXml(boolean doIndentXml) {
		this.doIndentXml = doIndentXml;
	}

	public boolean isCreateXml11() {
		return createXml11;
	}

	public void setCreateXml11(boolean createXml11) {
		this.createXml11 = createXml11;
	}

        public boolean isTranslateLeaderBadCharsToZero() {
		return translateLeaderBadCharsToZero;
	}

	public void setTranslateLeaderBadCharsToZero(boolean translateLeaderBadCharsToZero) {
		this.translateLeaderBadCharsToZero = translateLeaderBadCharsToZero;
	}

        public boolean isTranslateNonleaderBadCharsToSpaces() {
		return translateNonleaderBadCharsToSpaces;
	}

	public void setTranslateNonleaderBadCharsToSpaces(boolean translateNonleaderBadCharsToSpaces) {
		this.translateNonleaderBadCharsToSpaces = translateNonleaderBadCharsToSpaces;
	}

	public String getControlNumberOfLastReadRecord() {
		return controlNumberOfLastReadRecord;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public void setRecordImporter(IImporter recordImporter) {
		this.recordImporter = recordImporter;
	}

	public LoadStatistics getLoadStatistics() {
		return loadStatistics;
	}

	public boolean doesIgnoreRepositoryCode() {
		return ignoreRepositoryCode;
	}

	public void setIgnoreRepositoryCode(boolean ignoreRepositoryCode) {
		this.ignoreRepositoryCode = ignoreRepositoryCode;
	}

	public String getDefaultRepositoryCode() {
		return defaultRepositoryCode;
	}

	public void setDefaultRepositoryCode(String defaultRepositoryCode) {
		this.defaultRepositoryCode = defaultRepositoryCode;
	}
}
