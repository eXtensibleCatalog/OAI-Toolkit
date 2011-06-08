# This script parses the log/librarian_load.log for errors and prints out the lucene id
# of the records in question.  Useful in combiniation with the marcxml2luceneid.pl script
# in record reconciliation.
#
#    Usage:
#
#      perl loaderrorlog2luceneid.pl institution_code log_file
#
#    E.g.,
#
#      perl loaderrorlog2luceneid.pl UNI log/library_load.log > listoferrorids.txt
#      perl loaderrorlog2luceneid.pl "" log/library_load.log > listoferrorids.txt
#
# Errors take the following form:
# ERROR - The MARC record bib_file.mrc.0_1070000.xml#1079675 of Type BIBLIOGRAPHIC isn't well formed. Please correct the errors and load again. 

my $the003 = $ARGV[0];
my $file = $ARGV[1];

my %record_types = (
      'BIBLIOGRAPHIC' => 1,
      'AUTHORITY' => 2,
      'HOLDINGS' => 3,
      'CLASSIFICATION' => 4,
      'COMMUNITY' => 5,
);

open (IN, "< $file");

while (<IN>) {
   $line = $_;

   if ($line =~ /^ERROR - The MARC record ([^ ]+) of Type ([^ ]+) isn't well formed/) {
      my $record_id = $1;
      my $record_type = $2;
      my ($ignore, $the001) = split (/#/, $record_id);
      print $the001 . 't' . $record_types{$record_type} . 'r' . $the003 . "\n";
   } 

};

close (IN);
