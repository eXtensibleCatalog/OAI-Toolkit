# This script reads MARCXML which were created from the OAI Toolkit (note: you must use
# the -indent_xml parameter in your script) and generates a list of internal
# lucene (unique) IDs, the format used in the OAI Toolkit's lucene index.  You can then
# compare this list with that of the actual OAI Toolkit's lucene index (-lucene_dump_ids) 
# as a debugging / diagnostic tool.
#
#    Usage:
#
#      perl marcxml2luceneid.pl [list of marcxml files]
#
#    E.g.,
#
#      # Dump lucene ids from marcxml files generated via OAIToolkit
#      perl marcxml2luceneid.pl modifiedxml/*.xml > marcxmlids.txt 2> errors.txt
#
#      # Dump lucene ids from the lucene index itself
#      java -Xmx1024m -jar lib/OAIToolkit-1.0.1.jar -lucene_dump_ids -lucene_index lucene_index > luceneids.txt
#
#      # Compare the two lists; they should be the same.  If not, then the missing records should be accounted for in the OAI Toolkit's librarian_load.log file (grep ERROR log/librarian_load.log).
#
#      sort marcxmlids.txt > marcxmlids.sorted
#      sort luceneids.txt > luceneids.sorted 
#      diff marcxmlids.sorted luceneids.sorted 
#

my $buf = '';
while (<>) {
   $line = $_;

   if ($line =~ /<record /) {
      &process_record(\$buf);
      $buf = '';
   } 
   $buf .= $line;
};
&process_record(\$buf);

sub process_record {
   my ($r) = @_;

   my (@lines) = split(/^/, $$r);

   return if ($lines[0] !~ /<record /);

   my $record_type = '';
   my $status = '';
   my $the001 = ''; 
   my $the003 = ''; 

   foreach my $l (@lines) {
      last if ($record_type && $the001 && $the003);
      if ($l =~ /<leader>.....(.)(.)/) {
         $status = $1;
         $record_type = $2;
         next;
      }
      elsif ($l =~ /<controlfield tag="001">(\d+)</) {
         $the001 = $1;
         next;
      }
      elsif ($l =~ /<controlfield tag="003">([^<]+)</) {
         $the003 = $1;
         next;
      }
   }

   my %record_types = (
      # bibliographic
      'a' => 1,
      'c' => 1,
      'd' => 1,
      'e' => 1,
      'f' => 1,
      'g' => 1,
      'i' => 1,
      'j' => 1,
      'k' => 1,
      'm' => 1,
      'o' => 1,
      'p' => 1,
      'r' => 1,
      't' => 1,

      # authority
      'z' => 2,

      # holdings
      'u' => 3,
      'v' => 3,
      'x' => 3,
      'y' => 3,

      # classification
      'w' => 4,

      # community
      'q' => 5,

      );

   # we do not want to list deletes
   if ($status eq 'd' || ($record_type eq 'z' && ($status eq 's' || $status eq 'x'))) {
      print STDERR "Record status (leader/05) denotes a DELETED record.\nRecord:\n" . $$r . "\n";
      return;
   }

   # default to bib (this is what the OAI Toolkit does)
   if ($record_types{$record_type}) {
      $record_type = $record_types{$record_type};
   } else {
      print STDERR "Invalid record type (leader/06) from record!  Assuming it's a bibliographic record type.\nRecord:\n" . $$r . "\n";
      $record_type = 1;
   }

   if (! $the001 || ! $the003) {
      print STDERR "Couldn't parse required information from record!\nRecord:\n" . $$r . "\n";
      return;
   }

   print $the001 . 't' . $record_type . 'r' . $the003 . "\n";
}
