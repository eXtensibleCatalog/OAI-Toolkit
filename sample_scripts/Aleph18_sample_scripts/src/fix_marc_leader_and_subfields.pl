#!/usr/bin/perl
#
#  This script will take in a MARC file with mixed Marc-8 and UTF-8 formatted
#  MARC records.  It will fix any leader indicator count issues as well
#  as any subfield improper formatting such as early termination of a field
#  and non-lowercase subfield codes.  The MARC::Batch module is required for this script
#  which can be obtained from CPAN.
#
#  When running this script the source .mrc file path should be supplied first, the 
#  marc8 format destination file path is second, and the utf-8 destination file path is third as so:
#
#		perl fix_marc_leader_and_subfields.pl source.mrc marc8_dest.mrc utf8_dest.mrc
#
#  Author: Richard Johnson, University of Notre Dame
#  Created: 3/23/09
#
#
use strict;
use warnings;

if ($#ARGV < 2 ) {
	print "usage: perl fix_marc_leader_and_subfields.pl [source] [marc8-destination] [utf8-destination]\n";
	exit;
}
my $arg1 = $ARGV[0];
my $arg2 = $ARGV[1];
my $arg3 = $ARGV[2];

use MARC::Batch;
use Encode;

#change 1e in text to space
#change all subfield codes that are uppercase to lowercase
sub fixSubfields($) {
    my $field = shift;
	my $found = 0;
	
	if (!($field->is_control_field)) {
	    my @data = @{$field->{_subfields}};
    	for ( my $i=0; $i<@data; $i+=2 ) {
        	if ($data[$i] =~ m/[A-Z]/) {
            	$data[$i] = "\L${data[$i]}";
                $found = 1;
            } # if
            #fix 1e to space if necessary
            $data[$i+1] =~ s/\x1e/\x20/g;
        } # for
        
        ## synchronize our subfields
    	$field->{_subfields} = \@data;	

    } # if
    
    return $found;
}

my $batch = MARC::Batch->new('USMARC',$arg1);
$batch->strict_off();
$batch->warnings_on();

open(OUT,">${arg2}") or die $!;
open(OUT_UTF,">:encoding(utf-8)",$arg3) or die $!;

while (my $record = $batch->next()) {
	
	#fix leader
	my $leader = $record->leader();
	#replace question mark in leader with space
	$leader =~ s/\x3f/\x20/g;
	$record->leader($leader);
	
    my $field;
    my @fields = $record->fields();
    my $hasuppercase = 0;
    foreach $field(@fields){
    	#fix indicators if necessary, must be lowercase
    	my $ind1 = $field->indicator(1);
    	my $ind2 = $field->indicator(2);
    	if ($ind1){
	    	$field->update(ind1 => "\L${ind1}");
    	}
    	if ($ind2){
	    	$field->update(ind2 => "\L${ind2}");
    	}
    		
    	fixSubfields($field);
    }
    
    #for now put record with utf-8 encoding in separate file
    if ($record->encoding() eq "UTF-8"){
    	print OUT_UTF $record->as_usmarc();
    } else {
		print OUT $record->as_usmarc();
    }
}
    
    
   