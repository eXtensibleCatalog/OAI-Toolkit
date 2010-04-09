/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import info.extensiblecatalog.OAIToolkit.api.Importer;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

/**
 * Command line arguments
 * <dl>
 * <dt>-convert</dt>
 * <dd>Flag to convert file(s) with raw MARC records into MARCXML</dd>
 * <dt>-load</dt>
 * <dd>Flag to load file(s) into the OAI repository</dd>
 * <dt>-source &lt;dir&gt;</dt>
 * <dd>The directory where the toolkit looks for files to process</dd>
 * <dt>-destination</dt>
 * <dd>The directory that the toolkit moves the source files into as it successfully 
 * completes the processing of each file</dd>
 * <dt>-destination_xml</dt>
 * <dd>The directory that the toolkit places MARCXML versions of the source data.</dd>
 * <dt>-error</dt>
 * <dd>The directory that the toolkit moves files into when there is a processing 
 * error for that file.</dd>
 * <dt>-error_xml</dt>
 * <dd>The directory that the toolkit places MARCXML versions of the source data, 
 * if that MARCXML file was unable to be loaded into the OAI repository due 
 * to an error condition.</dd>
 * <dt>-log</dt>
 * <dd>Path to log file for warnings and errors</dd>
 * <dt>-log_detail</dt>
 * <dd>Flag to offer more detailed processing log information</dd>
 * <dt>-marc_schema</dt>
 * <dd>XML Schema file for MARCXML validation</dd>
 * <dt>-marc_encoding</dt>
 * <dd>The encoding of the MARC file</dd>
 * <dt>-char_conversion</dt>
 * <dd>The character conversion method. Possible values: MARC8 (Ansel), 
 * ISO5426, ISO6937, none</dd>
 * <dt>-split_size</dt>
 * <dd>How many records can an XML file contain?</dd>
 * <dt>-lucene_index</dt>
 * <dd>The Lucene index directory to create</dd>
 * <dt>-storage_type</dt>
 * <dd>The storage type of records: MySQL, mixed, Lucene</dd>
 * </dl>
 */
public class CLIProcessor {

        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
    
        //private static final Logger logger = Logging.getLogger();

	/**
	 * Setup the command line options object
	 * @return the command line options object
	 */
	public static Options getCommandLineOptions() {
		
		Option convert = new Option("convert", 
				"Flag to convert file(s) with raw MARC records into MARCXML");
		
		OptionBuilder.withArgName("modify");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(
			"Flag to modify MARCXML file(s) with XSLT transromation with the" +
			"given XSLT files before loading into the OAI repository");
		Option modify = OptionBuilder.create("modify");

		Option load = new Option("load",
			"Flag to load file(s) into the OAI repository");

		Option production = new Option("production", 
				"Flag to switch production mode. It means, that the" +
				"toolkit won't create any temporary files (except error" +
				" records) so it read MARC records, process it, and put " +
				"directly to the database or the target file format");

		OptionBuilder.withArgName("source");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory where the toolkit looks "
				+ "for files to process");
		Option source = OptionBuilder.create("source");

		OptionBuilder.withArgName("destination");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(
			"The directory that the toolkit moves the source files into " 
			+ "as it successfully completes the processing of each file.");
		Option destination = OptionBuilder.create("destination");

		OptionBuilder.withArgName("destination_xml");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(
			"The directory that the toolkit places "
			+ "MARCXML versions of the source data.");
		Option destination_xml = OptionBuilder.create("destination_xml");

		OptionBuilder.withArgName("destination_modifiedxml");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(
			"The directory that the toolkit places "
			+ "modified MARCXML versions of the source data.");
		Option destination_modifiedxml = OptionBuilder.create(
				"destination_modifiedxml");

		OptionBuilder.withArgName("error");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(
			"The directory that the toolkit moves files into when " 
			+ "there is a processing error for that file.");
		Option error = OptionBuilder.create("error");

		OptionBuilder.hasArg();
		OptionBuilder.withArgName("error_xml");
		OptionBuilder.withDescription(
				"The directory that the toolkit places MARCXML versions of " 
				+ "the source data, if that MARCXML file was unable to be "
				+ "loaded into the OAI repository due to an error condition.");
		Option error_xml = OptionBuilder.create("error_xml");

		OptionBuilder.hasArg();
		OptionBuilder.withArgName("error_modifiedxml");
		OptionBuilder.withDescription(
				"The directory that the toolkit places MARCXML versions of " 
				+ "the source data, if that MARCXML file was unable to be "
				+ "loaded into the OAI repository due to an error condition.");
		Option error_modifiedxml = OptionBuilder.create("error_modifiedxml");

		OptionBuilder.withArgName("log");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory of log files for " +
				"warnings and errors");
		Option log = OptionBuilder.create("log");

		Option log_detail = new Option("log_detail",
				"Flag to offer more detailed processing log information");

		OptionBuilder.withArgName("marc_schema");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("XML Schema file for MARCXML validation");
		Option marc_schema = OptionBuilder.create("marc_schema");

		OptionBuilder.withArgName("marc_encoding");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The encoding of the MARC file");
		Option marc_encoding = OptionBuilder.create("marc_encoding");

		OptionBuilder.withArgName("char_conversion");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The character conversion method. " +
				"Possible values: MARC8 (Ansel), ISO5426, ISO6937, none");
		Option char_conversion = OptionBuilder.create("char_conversion");

		OptionBuilder.withArgName("split_size");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("How many records can an XML file" +
				" contain?");
		Option split_size = OptionBuilder.create("split_size");

		OptionBuilder.withArgName("lucene_index");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("Lucene index directory.");
		Option lucene_index = OptionBuilder.create("lucene_index");

		OptionBuilder.withArgName("storage_type");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The storage type of records: MySQL," +
				" mixed, Lucene.");
		Option storage_type = OptionBuilder.create("storage_type");

		Option indent_xml = new Option("indent_xml", "Flag to indent XML");

		Option xml_version_11 = new Option("xml_version_11", 
				"Flag to create XML 1.1 instead of 1.0");
		
                Option translate_leader_bad_chars_to_zero = new Option("translate_leader_bad_chars_to_zero", 
                                "Change the Bad characters in the leader to zeros");
                
                Option translate_nonleader_bad_chars_to_spaces = new Option("translate_nonleader_bad_chars_to_spaces", 
                                "Change the Bad characters in the control and the data fields to spaces");
                
        Option modify_validation = new Option ("modify_validation", "Perform validation check during modify step");
		
		OptionBuilder.withArgName("replace_repository_code");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("Replace repository code.");
		Option replace_repository_code = OptionBuilder.create(
				"replace_repository_code");

		OptionBuilder.withArgName("convert_dir");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory of marc files after " +
				"process.");
		Option convert_dir = OptionBuilder.create("convert_dir");

        Option fileof_deleted_records = new Option("fileof_deleted_records", "The source marc file should be considered as deleted");

        Option lucene_statistics = new Option("lucene_statistics", "Statistics for Lucene Database");

        OptionBuilder.withArgName("stats_lucene_dir");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory of Lucene index given by the user.");
		Option stats_lucene_dir = OptionBuilder.create("stats_lucene_dir");

		OptionBuilder.withArgName("load_dir");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory of marcxml files after" +
				" process.");
		Option load_dir = OptionBuilder.create("load_dir");

		OptionBuilder.withArgName("modify_dir");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The directory of modified marcxml " +
				"files after process.");
		Option modify_dir = OptionBuilder.create("modify_dir");

		OptionBuilder.withArgName("error_suffix");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The suffix of directory name of error" +
				" files after process.");
		Option error_suffix = OptionBuilder.create("error_suffix");

		OptionBuilder.withArgName("destination_suffix");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The suffix of directory name of files" +
				" after process.");
		Option destination_suffix = OptionBuilder.create("destination_suffix");
		
		OptionBuilder.withArgName("default_repository_code");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The default value of " +
				"repository code (field 003).");
		Option default_repository_code = OptionBuilder.create(
				"default_repository_code");

		Option delete = new Option("delete", "Delete temporary files?");
		
		Option ignore_repository_code = new Option(
				"ignore_repository_code", 
				"Do not merge 003 and 001 together.");

		Options options = new Options();
		options.addOption(production);
		options.addOption(convert);
		options.addOption(modify);
		options.addOption(load);
		options.addOption(source);
		options.addOption(destination);
		options.addOption(destination_xml);
		options.addOption(error);
		options.addOption(error_xml);
		options.addOption(log);
		options.addOption(log_detail);
		options.addOption(marc_schema);
		options.addOption(marc_encoding);
		options.addOption(char_conversion);
		options.addOption(split_size);
		options.addOption(lucene_index);
		options.addOption(storage_type);
		options.addOption(indent_xml);
		options.addOption(xml_version_11);
                options.addOption(translate_leader_bad_chars_to_zero);
                options.addOption(modify_validation);
                options.addOption(translate_nonleader_bad_chars_to_spaces);
                options.addOption(fileof_deleted_records);
                options.addOption(stats_lucene_dir);
                options.addOption(lucene_statistics);
		options.addOption(replace_repository_code);
		options.addOption(convert_dir);
		options.addOption(load_dir);
		options.addOption(modify_dir);
		options.addOption(error_suffix);
		options.addOption(destination_suffix);
		options.addOption(delete);
		options.addOption(destination_modifiedxml);
		options.addOption(error_modifiedxml);
		options.addOption(default_repository_code);
		options.addOption(ignore_repository_code);
				
		return options;
	}
	
	public static void process(String[] args, Importer importer) {

		CommandLineParser parser = new GnuParser();
		Options options = CLIProcessor.getCommandLineOptions();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// production
			if (line.hasOption("production")) {
				importer.configuration.setProductionMode(true);
			}

			// convert
			if (line.hasOption("convert")) {
				importer.configuration.setNeedConvert(true);
			}

			// modify
			if (line.hasOption("modify")) {
				importer.configuration.setNeedModify(true);
				importer.configuration.setXsltString(line.getOptionValue(
							"modify"));
			}
			
			// load
			if (line.hasOption("load")) {
				importer.configuration.setNeedLoad(true);
			}

			// log_detail
			if (line.hasOption("log_detail")) {
				importer.configuration.setNeedLogDetail(true);
			}

			// source
			if (line.hasOption("source")) {
				importer.configuration.setSourceDir(line.getOptionValue(
						"source"));
			}
			// destination
			if (line.hasOption("destination")) {
				importer.configuration.setDestinationDir(line.getOptionValue(
						"destination"));
			}
			
			// destination_xml
			if (line.hasOption("destination_xml")) {
				importer.configuration.setDestinationXmlDir(
					line.getOptionValue("destination_xml"));
			}
			
			// destination_modifiedxml
			if (line.hasOption("destination_modifiedxml")) {
				importer.configuration.setDestinationModifiedXmlDir(
					line.getOptionValue("destination_modifiedxml"));
			}
			
			// error
			if (line.hasOption("error")) {
				importer.configuration.setErrorDir(line.getOptionValue(
						"error"));
			}
			
			// error_xml
			if (line.hasOption("error_xml")) {
				importer.configuration.setErrorXmlDir(line.getOptionValue(
						"error_xml"));
			}
			
			// error_modifiedxml
			if (line.hasOption("error_modifiedxml")) {
				importer.configuration.setErrorXmlDir(line.getOptionValue(
						"error_modifiedxml"));
			}
			
			// marc_schema
			if (line.hasOption("marc_schema")) {
				importer.configuration.setMarcSchema(line.getOptionValue(
						"marc_schema"));
			}
			
			// log
			if (line.hasOption("log")) {
				importer.configuration.setLogDir(line.getOptionValue("log"));
			}
			
			// marc_encoding
			if (line.hasOption("marc_encoding")) {
				importer.configuration.setMarcEncoding(line.getOptionValue(
						"marc_encoding"));
			}
			
			// char_conversion
			if (line.hasOption("char_conversion")) {
				importer.configuration.setCharConversion(line.getOptionValue(
						"char_conversion"));
			}
			
			// split_size
			if (line.hasOption("split_size")) {
				importer.configuration.setSplitSize(line.getOptionValue(
						"split_size"));
			}
			
			// lucene_index
			if (line.hasOption("lucene_index")) {
				importer.configuration.setLuceneIndex(line.getOptionValue(
						"lucene_index"));
			}
			
			// storage_type
			if (line.hasOption("storage_type")) {
				importer.configuration.setStorageType(line.getOptionValue(
						"storage_type"));
			}
			
			// indent_xml
			if (line.hasOption("indent_xml")) {
				importer.configuration.setDoIndentXml(true);
			}
			
			// xml_version_11
			if (line.hasOption("xml_version_11")) {
				importer.configuration.setCreateXml11(true);
			}
			
            // modify_validation
			if (line.hasOption("modify_validation")) {
				importer.configuration.setModifyValidation(true);
			}
 
			// translate_leader_bad_chars_to_zero
			if (line.hasOption("translate_leader_bad_chars_to_zero")) {
				importer.configuration.setTranslateLeaderBadCharsToZero(true);
			}
                        
            // translate_nonleader_bad_chars_to_spaces
			if (line.hasOption("translate_nonleader_bad_chars_to_spaces")) {
				importer.configuration.setTranslateNonleaderBadCharsToSpaces(true);
			}
                        
			// convert_dir
			if (line.hasOption("convert_dir")) {
				importer.configuration.setConvertDir(line.getOptionValue(
				"convert_dir"));
			}

			// modify_dir
			if (line.hasOption("modify_dir")) {
				importer.configuration.setModifyDir(line.getOptionValue(
				"modify_dir"));
			}

			// load_dir
			if (line.hasOption("load_dir")) {
				importer.configuration.setLoadDir(line.getOptionValue(
				"load_dir"));
			}

            if (line.hasOption("lucene_statistics")) {
				importer.configuration.setLuceneStatistics(true);
			}

			// lucene Directory for Lucene Statistics
			if (line.hasOption("stats_lucene_dir")) {
				importer.configuration.setStatsLuceneDir(line.getOptionValue(
				"stats_lucene_dir"));
			}

            // error_suffix
			if (line.hasOption("error_suffix")) {
				importer.configuration.setErrorSuffix(line.getOptionValue(
				"error_suffix"));
			}

			// destination_suffix
			if (line.hasOption("destination_suffix")) {
				importer.configuration.setDestinationSuffix(line.getOptionValue(
				"destination_suffix"));
			}
			
			// delete
			if (line.hasOption("delete")) {
				importer.configuration.setDoDeleteTemporaryFiles(true);
			}

			// default_repository_code
			if (line.hasOption("default_repository_code")) {
				importer.configuration.setDefaultRepositoryCode(
						line.getOptionValue("default_repository_code"));
			}

			// ignore_repository_code
			if (line.hasOption("ignore_repository_code")) {
				importer.configuration.setIgnoreRepositoryCode(true);
			}

            if (line.hasOption("fileof_deleted_records")) {
                importer.configuration.setFileOfDeletedRecords(true);
            }

		} catch(ParseException e) {
			prglog.error("[PRG] Parsing of command line arguments failed. " +
    			"Reason: " + e.getMessage() );
		} catch(Exception e) {
			//e.printStackTrace();
			prglog.error("[PRG] " + e);
		}
	}

	/**
	 * Print help message to the standard output
	 */
	public static void help(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Importer", getCommandLineOptions());
	}
}
